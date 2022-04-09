package com.example.example.ui.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.databinding.FragmentMessageBinding;
import com.example.example.model.User;
import com.example.example.utils.ItemAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import cn.jiguang.imui.messages.MsgListAdapter;


public class MessageFragment extends Fragment {

    private FragmentMessageBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MessageViewModel messageViewModel =
                new ViewModelProvider(this).get(MessageViewModel.class);
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<User> friends = new ArrayList<User>(Arrays.asList(new User("王小儿"),new User("王老五"),new User("狗"),new User("狗"),new User("狗"),new User("狗"),new User("狗"),new User("狗"),new User("狗")));
        ItemAdapter itemAdapter = new ItemAdapter(friends);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        binding.messageList.setAdapter(itemAdapter);
        binding.messageList.setLayoutManager(layoutManager);

//        final TextView textView = binding.textMessage;
//        messageViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}