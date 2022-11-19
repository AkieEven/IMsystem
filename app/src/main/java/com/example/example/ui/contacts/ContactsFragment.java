package com.example.example.ui.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.example.UserData;
import com.example.example.activity.AddFriendActivity;
import com.example.example.activity.ChatActivity;
import com.example.example.activity.FriendActivity;
import com.example.example.activity.LoginActivity;
import com.example.example.activity.MainActivity;
import com.example.example.databinding.FragmentContactsBinding;
import com.example.example.bean.ContactGroup;
import com.example.example.bean.User;
import com.example.example.adapter.ContactItemAdapter;
import com.example.example.event.ClientEventListener;
import com.example.example.event.Event;
import com.example.example.thread.ClientThreadPoolExecutor;

import java.util.ArrayList;
import java.util.Arrays;

public class ContactsFragment extends Fragment implements ClientEventListener {
    public static final String EXTRA_ID = "com.example.example.ID";

    private FragmentContactsBinding binding;
    private ContactItemAdapter contactItemAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactsViewModel contactsViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);

        binding = FragmentContactsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //tmp
        ContactGroup contactGroup1 = new ContactGroup("默认分组");
        contactGroup1.addGroupMember(new User("李华"));
        contactGroup1.addGroupMember(new User("小明"));
        ContactGroup contactGroup2 = new ContactGroup("朋友");
        contactGroup2.addGroupMember(new User("王阳明"));
        contactGroup2.addGroupMember(new User("凯鲁亚克"));
        ContactGroup contactGroup3 =new ContactGroup(("classmate"));
        contactGroup3.addGroupMember(new User("Jack"));

        ArrayList<ContactGroup> tmp = new ArrayList<>();
        tmp.add(contactGroup1);
        tmp.add(contactGroup2);
        tmp.add(contactGroup3);

        //data process
        //contactItemAdapter = new ContactItemAdapter(UserData.getContactGroups(),getContext());
        contactItemAdapter = new ContactItemAdapter(tmp,getContext());
        binding.contactListMain.setAdapter(contactItemAdapter);

        // TODO: 2022/4/11 设置点击监听器并跳转至用户详情Activity
        binding.contactListMain.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Intent intent = new Intent(getActivity(), FriendActivity.class);
                intent.putExtra(EXTRA_ID,contactItemAdapter.getChild(i,i1).getId());
                startActivity(intent);
                return true;
            }
        });



        //final TextView textView = binding.textDashboard;
        //contactsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onEvent(String topic, int msgCode, int resultCode, Object obj) {
        if (topic.equals(Event.NEW_GROUP)) {
            ClientThreadPoolExecutor.runOnMainThread(new Runnable() {
                @Override
                public void run() {
                    contactItemAdapter = new ContactItemAdapter(UserData.getContactGroups(),getContext());
                    binding.contactListMain.setAdapter(contactItemAdapter);
                }
            });
        }
    }
}