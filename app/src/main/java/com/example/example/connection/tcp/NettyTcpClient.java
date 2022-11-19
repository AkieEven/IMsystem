package com.example.example.connection.tcp;

import android.util.Log;

import com.example.example.connection.Config;
import com.example.example.connection.ExecuteServiceFactory;
import com.example.example.connection.Interface.ImClientInterface;
import com.example.example.connection.MsgDispatcher;
import com.example.example.connection.MsgTimeoutTimerManager;
import com.example.example.connection.handler.HeartbeatHandler;
import com.example.example.connection.handler.TCPChannelInitializerHandler;
import com.example.example.connection.handler.TCPReadHandler;
import com.example.example.connection.listener.ConnectStatusCallback;
import com.example.example.connection.listener.OnEventListener;
import com.example.example.bean.MessageProtobuf;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.StringUtil;

public class NettyTcpClient implements ImClientInterface {

    private static volatile NettyTcpClient instance;

    private Channel channel;
    private Bootstrap bootstrap;

    private boolean isClosed;
    private Vector<String> serverList;
    OnEventListener mOnEventListener;
    ConnectStatusCallback mConnectStatusCallback;
    private MsgDispatcher msgDispatcher;
    private ExecuteServiceFactory loopGroup;
    private MsgTimeoutTimerManager msgTimeoutTimerManager;

    private boolean isReconnecting = false;
    private int connectStatus = Config.CONNECT_STATE_FAILURE;
    private String currentHost = "";
    private int currentPort = -1;

    // 重连间隔时长
    private int reconnectInterval = Config.DEFAULT_RECONNECT_BASE_DELAY_TIME;
    // 连接超时时长
    private int connectTimeout = Config.DEFAULT_CONNECT_TIMEOUT;
    // 心跳间隔时间
    private int heartbeatInterval = Config.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    // 应用在后台时心跳间隔时间
    private int foregroundHeartbeatInterval = Config.DEFAULT_HEARTBEAT_INTERVAL_FOREGROUND;
    // 应用在前台时心跳间隔时间
    private int backgroundHeartbeatInterval = Config.DEFAULT_HEARTBEAT_INTERVAL_BACKGROUND;
    // app前后台状态
    private int appStatus = Config.APP_STATUS_FOREGROUND;
    // 消息发送超时重发次数
    private int resendCount = Config.DEFAULT_RESEND_COUNT;
    // 消息发送失败重发间隔时长
    private int resendInterval = Config.DEFAULT_RESEND_INTERVAL;

    private NettyTcpClient() {
    }

    public static NettyTcpClient getInstance() {
        if (instance == null) {
            synchronized (NettyTcpClient.class) {
                if (instance == null) {
                    instance = new NettyTcpClient();
                }
            }
        }
        return instance;
    }

    @Override
    public void init(Vector<String> serverList, OnEventListener listener, ConnectStatusCallback callback) {
        close();
        isClosed = false;
        this.serverList = serverList;
        this.mOnEventListener = listener;
        this.mConnectStatusCallback = callback;
        msgDispatcher = new MsgDispatcher();
        msgDispatcher.setOnEventListener(listener);
        loopGroup = new ExecuteServiceFactory();
        //设置重连线程池
        loopGroup.initBossLoopGroup();
        msgTimeoutTimerManager = new MsgTimeoutTimerManager(this);
        //初始化第一次连接
        resetConnect(true);
    }

    @Override
    public void resetConnect() {
        this.resetConnect(false);
    }

    @Override
    public void resetConnect(boolean isFirst) {
        if (!isFirst) {
            try {
                Thread.sleep(Config.DEFAULT_RECONNECT_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (!isClosed && !isReconnecting) {
            synchronized (this) {
                if (!isClosed && !isReconnecting) {
                    isReconnecting = true;
                    onConnectStatusCallback(Config.CONNECT_STATE_CONNECTING);
                    closeChannel();
                    loopGroup.executeBossTask(new ResetConnectRunnable(isFirst));
                }
            }
        }
    }

    private class ResetConnectRunnable implements Runnable {

        private boolean isFirst;

        public ResetConnectRunnable(boolean isFirst) {
            this.isFirst = isFirst;
        }

        @Override
        public void run() {
            if (!isFirst) {
                onConnectStatusCallback(Config.CONNECT_STATE_FAILURE);
            }

            try {
                // 重启时停止心跳
                loopGroup.destroyWorkLoopGroup();

                while (!isClosed) {
                    if (!isNetworkAvailable()) {
                        try {
                            System.out.println("暂无网络连接！");
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }

                    int status = reConnect();
                    if (status == Config.CONNECT_STATE_SUCCESSFUL) {
                        onConnectStatusCallback(status);
                        break;
                    }

                    if (status == Config.CONNECT_STATE_FAILURE) {
                        onConnectStatusCallback(status);
                        try {
                            Thread.sleep(Config.DEFAULT_RECONNECT_INTERVAL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } finally {
                isReconnecting = false;
            }
        }

        //重连逻辑
        private int reConnect() {
            if (!isClosed) {
                try {
                    if (bootstrap != null) {
                        bootstrap.config().group().shutdownGracefully();
                    }
                } finally {
                    bootstrap = null;
                }

                initBootstrap();
                return connectServer();
            }
            return Config.CONNECT_STATE_FAILURE;
        }

        private int connectServer() {
            //无效服务器地址
            if (serverList == null || serverList.size() == 0) {
                return Config.CONNECT_STATE_FAILURE;
            }

            for (int i = 0; (!isClosed && i < serverList.size()); i++) {
                String server = serverList.get(i);

                if (StringUtil.isNullOrEmpty(server)) {
                    return Config.CONNECT_STATE_FAILURE;
                }

                String[] address = server.split(" ");
                for (int j = 0; j < Config.DEFAULT_RECONNECT_COUNT; j++) {
                    if (isClosed || !isNetworkAvailable()) {
                        System.out.println("connectServer:无网络连接！");
                        return Config.CONNECT_STATE_FAILURE;
                    }

                    if (connectStatus != Config.CONNECT_STATE_CONNECTING) {
                        onConnectStatusCallback(Config.CONNECT_STATE_CONNECTING);
                    }
                    System.out.printf("正在进行 %s 的第%d次连接...当前重连延时时长为%dms\n", server, j, j * getReconnectInterval());

                    try {
                        currentHost = address[0];
                        currentPort = Integer.parseInt(address[1]);
                        toServer();

                        if (channel != null) {
                            return Config.CONNECT_STATE_SUCCESSFUL;
                        } else {
                            Thread.sleep(j * getReconnectInterval());
                        }
                    } catch (InterruptedException e) {
                        close();
                        break;
                    }
                }
            }
            return Config.CONNECT_STATE_FAILURE;
        }
    }

    private void toServer() {
        try {
            channel = bootstrap.connect(currentHost, currentPort).sync().channel();
        } catch (Exception e) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Log.e("toServer",String.format("连接服务器失败，主机地址：%s，端口：%d", currentHost, currentPort));
            Log.e("toServer",e.getMessage());
            e.printStackTrace();
            channel = null;
        }
    }

    public int getReconnectInterval() {
        if (mOnEventListener != null && mOnEventListener.getReconnectInterval() > 0) {
            return reconnectInterval = mOnEventListener.getReconnectInterval();
        }
        return reconnectInterval;
    }

    private void initBootstrap() {
        bootstrap = new Bootstrap();
        EventLoopGroup loopGroup = new NioEventLoopGroup();
        bootstrap.group(loopGroup).channel(NioSocketChannel.class);
        //保活
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        //禁用nagle算法
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        //设置连接超时时长
        bootstrap.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, getConnectTimeout());
        //设置初始化channel
        bootstrap.handler(new TCPChannelInitializerHandler(this));
    }

    public int getConnectTimeout() {
        if (mOnEventListener != null && mOnEventListener.getConnectTimeout() > 0) {
            return connectTimeout = mOnEventListener.getConnectTimeout();
        }
        return connectTimeout;
    }

    public int getHeartbeatInterval() {
        return this.heartbeatInterval;
    }

    public ExecuteServiceFactory getLoopGroup() {
        return loopGroup;
    }

    @Override
    public int getServerSentReportMsgType() {
        if (mOnEventListener != null) return mOnEventListener.getServerSentReportMsgType();
        else {
            System.err.println("未设置应用层监听器！");
            return 0;
        }
    }

    @Override
    public int getClientReceivedReportMsgType() {
        if (mOnEventListener != null) return mOnEventListener.getClientReceivedReportMsgType();
        else {
            System.err.println("未设置应用层监听器！");
            return 0;
        }
    }

    @Override
    public MsgDispatcher getMsgDispatcher() {
        return this.msgDispatcher;
    }

    @Override
    public MsgTimeoutTimerManager getMsgTimeoutTimerManager() {
        return msgTimeoutTimerManager;
    }

    private boolean isNetworkAvailable() {
        if (mOnEventListener != null) {
            return mOnEventListener.isNetworkAvailable();
        }
        return false;
    }

    @Override
    public int getResendCount() {
        if ((mOnEventListener != null) && mOnEventListener.getResendCount() != 0) {
            return resendCount = mOnEventListener.getResendCount();
        }
        return resendCount;
    }

    @Override
    public int getResendInterval() {
        if (mOnEventListener != null && mOnEventListener.getResendInterval() != 0) {
            return resendInterval = mOnEventListener.getResendInterval();
        }

        return resendInterval;
    }

    //关闭通道
    private void closeChannel() {
        try {
            if (channel != null) {
                channel.close();
                channel.eventLoop().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("关闭channel出错：" + e.getMessage());
        } finally {
            channel = null;
        }
    }


    private void onConnectStatusCallback(int connectStatus) {
        this.connectStatus = connectStatus;
        switch (connectStatus) {
            case Config.CONNECT_STATE_SUCCESSFUL: {
                if (mConnectStatusCallback != null) {
                    mConnectStatusCallback.OnConnectSuccess();
                }
                System.out.printf("连接成功，服务器主机地址：%s,端口：%s%n", currentHost, currentPort);
                break;
            }

            case Config.CONNECT_STATE_CONNECTING: {
                if (mConnectStatusCallback != null) {
                    mConnectStatusCallback.OnConnecting();
                }
                System.out.println("连接中...");
                break;
            }

            case Config.CONNECT_STATE_FAILURE: {
                if (mConnectStatusCallback != null) {
                    mConnectStatusCallback.OnConnectFailed();
                }
                System.out.println("连接失败！");
                break;
            }
        }
    }

    @Override
    public int getHandshakeMsgType() {
        if (mOnEventListener != null) {
            return mOnEventListener.getHandshakeMsgType();
        }
        return 0;
    }

    @Override
    public int getHeartbeatMsgType() {
        if (mOnEventListener != null) {
            return mOnEventListener.getHeartbeatMsgType();
        } else {
            System.err.println("未设置应用层监听器！");
            return 0;
        }
    }

    @Override
    public MessageProtobuf.Msg getHeartbeatMsg() {
        if (mOnEventListener != null) {
            return mOnEventListener.getHeartbeatMsg();
        }
        return null;
    }

    public void addHeartbeatHandler() {
        if (channel == null || !channel.isActive() || channel.pipeline() == null) return;

        try {
            //移除读写超时handler之后再添加
            if (channel.pipeline().get(IdleStateHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(IdleStateHandler.class.getSimpleName());
            }
            //3次心跳不响应代表连接断开
            channel.pipeline().addFirst(IdleStateHandler.class.getSimpleName(), new IdleStateHandler(
                    heartbeatInterval * 3, heartbeatInterval, 0, TimeUnit.MILLISECONDS));

            //重新添加heartbeathandler
            if (channel.pipeline().get(HeartbeatHandler.class.getSimpleName()) != null) {
                channel.pipeline().remove(HeartbeatHandler.class.getSimpleName());
            }
            if (channel.pipeline().get(TCPReadHandler.class.getSimpleName()) != null) {
                channel.pipeline().addBefore(TCPReadHandler.class.getSimpleName(), HeartbeatHandler.class.getSimpleName(), new HeartbeatHandler(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("添加心跳处理句柄失败：" + e.getMessage());
        }
    }

    @Override
    public void close() {
        if (isClosed) return;

        //关闭channel
        try {
            closeChannel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //关闭bootstrap
        try {
            if (bootstrap != null) {
                bootstrap.config().group().shutdownGracefully();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //释放线程池
        try {
            if (loopGroup != null) {
                loopGroup.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverList != null) {
                    serverList.clear();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            isReconnecting = false;
            channel = null;
            bootstrap = null;
            isClosed = true;
        }

    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public void sendMsg(MessageProtobuf.Msg msg) {
        this.sendMsg(msg, true);
    }

    @Override
    public void sendMsg(MessageProtobuf.Msg msg, boolean isJoinTimeoutManager) {
        if (msg == null) {
            System.out.println("发送消息失败：消息未构建。");
            return;
        }
        if (!StringUtil.isNullOrEmpty(msg.getMsgId())) {
            if (isJoinTimeoutManager) msgTimeoutTimerManager.add(msg);
        }
        if (channel == null) {
            System.out.println("发送消息失败：连接通道不存在。");
            return;
        }
        try {
            channel.writeAndFlush(msg);
        } catch (Exception e) {
            System.out.println("发送消息失败：" + e.getMessage());
        }
    }

    @Override
    public void setAppStatus(boolean isBackground) {
        if (isBackground){
            heartbeatInterval = backgroundHeartbeatInterval;
            appStatus = Config.APP_STATUS_BACKGROUND;
        }
        else {
            heartbeatInterval = foregroundHeartbeatInterval;
            appStatus = Config.APP_STATUS_FOREGROUND;
        }
        addHeartbeatHandler();
    }
}
