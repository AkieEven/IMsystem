package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.example.IMApp;
import com.example.example.bean.MessageProtobuf;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.databinding.ActivityChatBinding;
import com.example.example.bean.Message;
import com.example.example.bean.User;
import com.example.example.event.ClientEventListener;
import com.example.example.event.Event;
import com.example.example.thread.ClientThreadPoolExecutor;
import com.example.example.ui.message.MessageFragment;
import com.example.example.utils.MessageUtil;

import java.util.List;

import cn.jiguang.imui.chatinput.ChatInputView;
import cn.jiguang.imui.chatinput.listener.OnMenuClickListener;
import cn.jiguang.imui.chatinput.model.FileItem;
import cn.jiguang.imui.commons.ImageLoader;
import cn.jiguang.imui.commons.models.IMessage;
import cn.jiguang.imui.messages.MessageList;
import cn.jiguang.imui.messages.MsgListAdapter;

public class ChatActivity extends AppCompatActivity implements ClientEventListener {
    private ActivityChatBinding binding;
    private String friendId;
    private MsgListAdapter msgListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //TODO:另一activity导向该位置，获取聊天记录
        friendId = getIntent().getStringExtra(MessageFragment.EXTRA_ID);

        //初始化MessageList控件
        MessageList messageList = binding.msgList;
        messageList.setShowSenderDisplayName(false);
        messageList.setShowReceiverDisplayName(false);
        MsgListAdapter.HoldersConfig holdersConfig = new MsgListAdapter.HoldersConfig();
        ImageLoader imageLoader = new ImageLoader() {
            @Override
            public void loadAvatarImage(ImageView avatarImageView, String string) {
                if (string.contains("R.drawable")) {
                    Integer resId = getResources().getIdentifier(string.replace("R.drawable.", ""),
                            "drawable", getPackageName());
                    avatarImageView.setImageResource(resId);
                } else {
                    Glide.with(ChatActivity.this)
                            .load(string)
                            .centerCrop()
                            .into(avatarImageView);
                }
            }

            @Override
            public void loadImage(ImageView imageView, String string) {
                Glide.with(ChatActivity.this)
                        .load(string)
                        .centerCrop()
                        .into(imageView);
            }

            @Override
            public void loadVideo(ImageView imageCover, String uri) {

            }
        };
        //设置adapter中的初始数据集
        msgListAdapter = new MsgListAdapter<Message>(friendId, holdersConfig, imageLoader);
        messageList.setAdapter(msgListAdapter);

        //todo:设置ChatInputView的高度
        ChatInputView chatInputView = binding.chatInput;


        //设置监听事件
        chatInputView.setMenuClickListener(new OnMenuClickListener() {
            @Override
            public boolean onSendTextMessage(CharSequence input) {
                if (input.length() == 0) return false;
                else {
                    Message message = new Message(input.toString(),IMessage.MessageType.SEND_TEXT.ordinal());
                    message.setFromUser(IMApp.sharedInstance().getUser());
                    message.setStatus(IMessage.MessageStatus.SEND_GOING);
                    //todo:加入超时重传
                    IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildSingleTextMessage(input.toString(),friendId),false);
                    msgListAdapter.addToStart(message,true);
                    return true;
                }
            }

            @Override
            public void onSendFiles(List<FileItem> list) {

            }

            @Override
            public boolean switchToMicrophoneMode() {
                return false;
            }

            @Override
            public boolean switchToGalleryMode() {
                return false;
            }

            @Override
            public boolean switchToCameraMode() {
                return false;
            }

            @Override
            public boolean switchToEmojiMode() {
                return false;
            }
        });
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        Message message = (Message) obj;
        if (topic.equals(Event.SINGLE_MESSAGE_EVENT)) {
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    if(message.getFromUser().getId().equals(friendId)) {
                        msgListAdapter.addToStart(message,true);
                    }
                }
            });
        }
    }
}