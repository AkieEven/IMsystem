package com.example.example.bean;

import java.util.ArrayList;
import java.util.Comparator;

public class ContactGroup {
    private String groupName;
    private int count;
    private int onlineCount;
    private ArrayList<User> groupList = new ArrayList<>();

    public ContactGroup(String name){
        this.groupName = name;
        count = 0;
        onlineCount = 0;
    }

    public void setGroupList(ArrayList<User> groupList) {
        this.groupList = groupList;
        count = groupList.size();
    }

    public void addGroupMember(User user) {
        this.groupList.add(user);
        count++;
        if(user.isOnline()) onlineCount++;
    }

    public void contactOnline(User person) {
        this.onlineCount = this.onlineCount + 1;
        groupListSort();
    }

    public void contactOffline(User person) {
        this.onlineCount = this.onlineCount-1;
        groupListSort();
    }

    public ArrayList<User> getGroupList() {
        return groupList;
    }

    public String getGroupName() {
        return groupName;
    }

    public int getCount() {
        return count;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public String getOnlineCountInAllToString(){
        return onlineCount+"/"+count;
    }

    private void groupListSort(){
        groupList.sort(new Comparator<User>() {
            @Override
            public int compare(User u1, User u2) {
                if(u1.isOnline() && u2.isOffline()) return -1;
                else if(u1.isOffline() && u2.isOnline()) return 1;
                else return u1.getId().compareTo(u2.getId());
            }
        });
    }
}
