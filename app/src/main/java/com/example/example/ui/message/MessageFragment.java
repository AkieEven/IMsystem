package com.example.example.ui.message;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.activity.ChatActivity;
import com.example.example.databinding.FragmentMessageBinding;
import com.example.example.bean.User;
import com.example.example.adapter.MessageItemAdapter;
import com.example.example.messagehandler.event.ClientEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class MessageFragment extends Fragment implements ClientEventListener {
    public static final String EXTRA_ID = "com.example.example.ID";

    private FragmentMessageBinding binding;
    private ArrayList<User> friends;
    private MessageItemAdapter messageItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //MessageViewModel messageViewModel =
        //        new ViewModelProvider(this).get(MessageViewModel.class);
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        friends = new ArrayList<User>(Arrays.asList(new User("李明"), new User("Maria"),new User("王强")));

        messageItemAdapter = new MessageItemAdapter(friends);
        messageItemAdapter.setOnItemClickListener(new MessageItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(EXTRA_ID,messageItemAdapter.getData().get(position).getId());
                startActivity(intent);
            }
        });
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.messageList.setAdapter(messageItemAdapter);
        binding.messageList.setLayoutManager(layoutManager);
        binding.messageList.setItemAnimator(new DefaultItemAnimator());

        //final TextView textView = binding.textMessage;
        //messageViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
    }
}