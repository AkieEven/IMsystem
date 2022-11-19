package com.example.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.example.R;
import com.example.example.bean.User;

import java.util.ArrayList;
import java.util.Arrays;

public class MessageItemAdapter extends RecyclerView.Adapter<MessageItemAdapter.ViewHolder> {
    private ArrayList<User> mData;
    private OnItemClickListener mOnItemClickListener;

    //tmp
    ArrayList<String> tmp = new ArrayList<>(Arrays.asList("15:30","你好","19:26","晚上吃什么","16:22","溜了溜了"));
    ArrayList<String> tmp1 = new ArrayList<>(Arrays.asList("你好","晚上吃什么","溜了溜了"));

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    public MessageItemAdapter(ArrayList<User> friends) {
        this.mData = friends;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_messagelist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageItemAdapter.ViewHolder holder,int position) {
        User user = mData.get(position);
        holder.nameTextView.setText(user.getDisplayName());
        /* todo 实现数据库内部消息查询，暂用固定数据代替*/
        holder.timeTextView.setText(tmp.get(position));
        holder.messageTextView.setText(tmp1.get(position));
        holder.avaterImageView.setImageResource(R.drawable.background);
        holder.messageListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(view, holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout messageListItem;
        private TextView messageTextView;
        private TextView timeTextView;
        private TextView nameTextView;
        private ImageView avaterImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            messageListItem = itemView.findViewById(R.id.message_list_item);
            messageTextView = itemView.findViewById(R.id.item_text);
            timeTextView = itemView.findViewById(R.id.item_time);
            nameTextView = itemView.findViewById(R.id.item_name);
            avaterImageView = itemView.findViewById(R.id.item_image);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mOnItemClickListener = listener;
    }

    public ArrayList<User> getData(){
        return mData;
    }

}
