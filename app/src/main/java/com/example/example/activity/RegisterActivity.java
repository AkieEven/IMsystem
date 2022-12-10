package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.R;
import com.example.example.bean.MessageProtobuf;
import com.example.example.databinding.ActivityRegisterBinding;
import com.example.example.messagehandler.event.ClientEventCenter;
import com.example.example.messagehandler.event.ClientEventListener;
import com.example.example.messagehandler.event.Event;
import com.example.example.messagehandler.MessageProcessor;
import com.example.example.thread.ClientThreadPoolExecutor;
import com.example.example.utils.MD5Util;
import com.example.example.utils.MessageUtil;
import com.example.example.utils.StringProcessor;

public class RegisterActivity extends AppCompatActivity implements ClientEventListener {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ClientEventCenter.registerEventListener(this,Event.REGISTER_EVENT);
    }

    public void register(View view) {
        String displayName = binding.registerDisplayName.getText().toString();
        String email = binding.registerEmail.getText().toString();
        String password = binding.registerPassword.getText().toString();
        String password_check = binding.registerPasswordCheck.getText().toString();

        //邮箱格式
        if (!StringProcessor.isEmail(email)) {
            Toast toast = Toast.makeText(RegisterActivity.this, R.string.register_email_format_error, Toast.LENGTH_SHORT);
            toast.show();
        }
        //用户名过长
        if (displayName.length()>20) {
            Toast toast = Toast.makeText(RegisterActivity.this, R.string.register_displayName_length_error, Toast.LENGTH_SHORT);
            toast.show();
        }
        //密码过长
        if (password.length()>20) {
            Toast toast = Toast.makeText(RegisterActivity.this, R.string.register_password_length_error, Toast.LENGTH_SHORT);
            toast.show();
        }
        //密码不同
        if (!password.equals(password_check)) {
            Toast toast = Toast.makeText(RegisterActivity.this, R.string.register_different_password_notice, Toast.LENGTH_SHORT);
            toast.show();
        }

        // Snackbar.make(binding.getRoot(), R.string.register_different_password_notice, Snackbar.LENGTH_SHORT).;

        String salt = StringProcessor.getRandomString(16);
        password = MD5Util.generate(password_check, salt);

        MessageProcessor.getInstance().sendMsg(MessageUtil.buildRegisterMessage(displayName, email, password, salt),false);
    }


    //注册事件监听器
    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        if (topic.equals(Event.REGISTER_EVENT)) {
            MessageProtobuf.Msg message = (MessageProtobuf.Msg) obj;
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jsonObject = JSON.parseObject(message.getExtend());
                    int status = jsonObject.getIntValue("status");
                    if (status == 1) {
                        Toast toast = Toast.makeText(RegisterActivity.this, R.string.register_succeed, Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                        startActivity(intent);
                    } else {
                        Toast toast = Toast.makeText(RegisterActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
            });
        }
    }
}