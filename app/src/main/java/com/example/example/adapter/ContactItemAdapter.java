package com.example.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.example.R;
import com.example.example.bean.ContactGroup;
import com.example.example.bean.User;

import java.util.ArrayList;

public class ContactItemAdapter extends BaseExpandableListAdapter {
    private ArrayList<ContactGroup> mData;
    private Context mContext;

    public ContactItemAdapter(ArrayList<ContactGroup> data,Context context) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mData.get(i).getGroupList().size();
    }

    @Override
    public Object getGroup(int i) {
        return mData.get(i);
    }

    @Override
    public User getChild(int i, int i1) {
        return mData.get(i).getGroupList().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View view, ViewGroup parent) {
        GroupViewHolder groupViewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.group_for_contactlist,parent,false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.countView = view.findViewById(R.id.group_count);
            groupViewHolder.groupNameView = view.findViewById(R.id.group_name);
            groupViewHolder.indicatorView = view.findViewById(R.id.group_indicator);
            view.setTag(groupViewHolder);
        }
        else {
            groupViewHolder = (GroupViewHolder) view.getTag();
        }
        groupViewHolder.groupNameView.setText(mData.get(position).getGroupName());
        groupViewHolder.countView.setText(mData.get(position).getOnlineCountInAllToString());
        if(isExpanded) {
            groupViewHolder.indicatorView.setImageResource(R.drawable.ic_group_folder_32dp);
        }
        else {
            groupViewHolder.indicatorView.setImageResource(R.drawable.ic_group_expand_32dp);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int itemPosition, boolean isExpanded, View view, ViewGroup parent) {
        ItemViewHolder itemViewHolder;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_for_contactlist,parent,false);
            itemViewHolder = new ItemViewHolder();
            itemViewHolder.avatarView = view.findViewById(R.id.image_for_contact_item);
            itemViewHolder.contactNameView = view.findViewById(R.id.name_for_contact_item);
            itemViewHolder.statusView = view.findViewById(R.id.status_for_contact_item);
            view.setTag(itemViewHolder);
        }
        else {
            itemViewHolder = (ItemViewHolder) view.getTag();
        }
        // TODO: 2022/4/11 查找头像路径方法更改
        itemViewHolder.avatarView.setImageResource(R.drawable.background);
        itemViewHolder.contactNameView.setText(mData.get(groupPosition).getGroupList().get(itemPosition).getDisplayName());
        itemViewHolder.statusView.setText(mData.get(groupPosition).getGroupList().get(itemPosition).getStatus());
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    class GroupViewHolder {
        TextView groupNameView;
        TextView countView;
        ImageView indicatorView;
    }

    class ItemViewHolder {
        ImageView avatarView;
        TextView contactNameView;
        TextView statusView;
    }
}
