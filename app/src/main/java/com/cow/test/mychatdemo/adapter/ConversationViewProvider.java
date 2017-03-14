package com.cow.test.mychatdemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cow.test.mychatdemo.R;
import com.cow.test.mychatdemo.util.TimeUtils;
import com.cow.test.mychatdemo.view.ChatActivity;
import com.hyphenate.chat.EMConversation;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by cuiguo on 2017/3/1.
 * 可以和mulitype结合
 */

public class ConversationViewProvider extends ItemViewProvider<EMConversation, ConversationViewProvider.ViewHolder> {
    public void setListener(RecyclerViewListener listener) {
        mListener = listener;
    }

    RecyclerViewListener mListener;
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_conversation, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull EMConversation conversation) {
        holder.mTvFrom.setText( conversation.getLastMessage().getFrom());//来源
        holder.mTvContent.setText( ""+conversation.getLastMessage().getType().toString());//说话者的拼接
        holder.mTvTime.setText(TimeUtils.stampToDate(conversation.getLastMessage().getMsgTime()) );//unix时间，需要转换
        holder.mTvUnreadNumber.setText( ""+conversation.getUnreadMsgCount());//未读消息数
        holder.mRlRoot.setOnClickListener((v)->startActivity(v.getContext(),conversation.getLastMessage().getUserName()));


    }

    private void startActivity(Context mContext,String username) {
        Intent intent = new Intent(mContext, ChatActivity.class);
        intent.putExtra("username",username);
        mContext.startActivity(intent);
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rl_root)
        RelativeLayout mRlRoot;
        @BindView(R.id.iv_icon)
        ImageView mIvIcon;
        @BindView(R.id.tv_from)
        TextView mTvFrom;
        @BindView(R.id.tv_content)
        TextView mTvContent;
        @BindView(R.id.tv_time)
        TextView mTvTime;
        @BindView(R.id.tv_unread_number)
        TextView mTvUnreadNumber;


        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
