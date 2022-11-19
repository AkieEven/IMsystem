package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.example.connection.IMClientBootstrap;
import com.example.example.databinding.ActivityNewFriendBinding;
import com.example.example.databinding.ActivityNewGroupBinding;
import com.example.example.utils.MessageUtil;

public class NewGroupActivity extends AppCompatActivity {
    ActivityNewGroupBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.createGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String groupName = binding.groupName.getText().toString();
                IMClientBootstrap.getInstance().sendMsg(MessageUtil.buildCreateGroupMessage(groupName),false);
            }
        });
    }

}