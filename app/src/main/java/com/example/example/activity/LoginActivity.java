package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.bean.MessageProtobuf;
import com.example.example.connection.IMClientBootstrap;
import com.example.example.database.AppDatabase;
import com.example.example.databinding.ActivityLoginBinding;
import com.example.example.messagehandler.event.ClientEventCenter;
import com.example.example.messagehandler.event.ClientEventListener;
import com.example.example.messagehandler.event.Event;
import com.example.example.thread.ClientThreadPoolExecutor;
import com.example.example.utils.MessageUtil;

public class LoginActivity extends AppCompatActivity implements ClientEventListener {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        IMClientBootstrap.getInstance().init();
        ClientThreadPoolExecutor.runInBackground(new Runnable() {
            @Override
            public void run() {
                AppDatabase.buildDatabase(LoginActivity.this);
            }
        });
        ClientEventCenter.registerEventListener(this,Event.LOGIN_EVENT);
    }

    public void login(View view) {
        String email = binding.loginEmail.getText().toString();
        String password = binding.loginPassword.getText().toString();
        //初始化中会发送用户信息
        IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildHandshakeMsg(email,password),false);
    }

    public void toRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        MessageProtobuf.Msg message = (MessageProtobuf.Msg) obj;
        if (topic.equals(Event.LOGIN_EVENT)) {
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSON.parseObject(message.getExtend());
                    int status = jsonObject.getIntValue("status");
                    if(status == 0) {
                        String info = jsonObject.getString("error");
                        Toast toast = Toast.makeText(LoginActivity.this, info, Toast.LENGTH_SHORT);
                        toast.show();
                    } else {
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}