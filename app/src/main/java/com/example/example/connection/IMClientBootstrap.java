package com.example.example.connection;

import android.util.Log;

import com.example.example.IMApp;
import com.example.example.bean.MessageProtobuf;
import com.example.example.connection.Interface.ImClientInterface;
import com.example.example.utils.IMClientListener;
import com.example.example.utils.IMClientStatusListener;

import java.util.Vector;

public class IMClientBootstrap {
    private static final String TAG = IMClientBootstrap.class.getSimpleName();
    private static final IMClientBootstrap INSTANCE = new IMClientBootstrap();
    private ImClientInterface imClient;
    private boolean isActive = false;

    private IMClientBootstrap() {
    }

    public static IMClientBootstrap getInstance() {
        return INSTANCE;
    }

    public synchronized void init() {
        if(!isActive) {
            Vector<String> serverList = IMApp.sharedInstance().getServerList();

            if(serverList == null || serverList.size()==0) {
                System.out.println("IMClientBootstrap：服务器列表为空！");
                return;
            }

            if(imClient != null) imClient.close();
            imClient = ImClientFactory.getClient();

            if(imClient == null) System.out.println("IMClientBootstrap：IMClient启动失败！");
            else {
                imClient.setAppStatus(IMApp.sharedInstance().isBackground());
                imClient.init(serverList,new IMClientListener(),new IMClientStatusListener());
                isActive = true;
            }
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void sendMsg(MessageProtobuf.Msg msg) {
        if(isActive) {
            imClient.sendMsg(msg);
        } else {
            Log.d(TAG,"连接层未启动！");
        }
    }

    public void sendMsg(MessageProtobuf.Msg msg,boolean isJoinTimeoutManager) {
        if(isActive) {
            imClient.sendMsg(msg,isJoinTimeoutManager);
        } else {
            Log.d(TAG,"连接层未启动！");
        }
    }
}
