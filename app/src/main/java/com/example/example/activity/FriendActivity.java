package com.example.example.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.example.R;
import com.example.example.bean.User;
import com.example.example.database.AppDatabase;
import com.example.example.databinding.ActivityChatBinding;
import com.example.example.databinding.ActivityFriendBinding;
import com.example.example.ui.contacts.ContactsFragment;
import com.example.example.ui.message.MessageFragment;

public class FriendActivity extends AppCompatActivity {
    ActivityFriendBinding binding;
    private String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFriendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        friendId = getIntent().getStringExtra(ContactsFragment.EXTRA_ID);
        User user = AppDatabase.getInstance().UserDao().selectByUserId(friendId);

        binding.friendAvatar.setImageResource(R.drawable.background);
    }

    public void toChat(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("friend_name",friendId);
        startActivity(intent);
    }

    public void moveGroup(View view) {

    }
}