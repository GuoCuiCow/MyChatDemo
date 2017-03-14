package com.cow.test.mychatdemo.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cow.test.mychatdemo.R;
import com.cow.test.mychatdemo.data.bean.MessageBean;
import com.cow.test.mychatdemo.util.TimeUtils;
import com.hyphenate.chat.EMTextMessageBody;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by cuiguo on 2017/3/2.
 */

public class ChatTextFromViewProvider extends ItemViewProvider<MessageBean, ChatTextFromViewProvider.ViewHolder> {




    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_detail_text_from, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull MessageBean message) {
        holder.mTvMessageDetailTime.setText(TimeUtils.stampToDate(message.message.getMsgTime()) );
        holder.mTvMessageDetailTitle.setText(message.message.getFrom());
        EMTextMessageBody body = (EMTextMessageBody) message.message.getBody();
        holder.mIvMessageDetailContent.setText(body.getMessage());
    }

    static class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.tv_message_detail_time)
        TextView mTvMessageDetailTime;
        @BindView(R.id.iv_message_detail_icon)
        ImageView mIvMessageDetailIcon;
        @BindView(R.id.tv_message_detail_title)
        TextView mTvMessageDetailTitle;
        @BindView(R.id.iv_message_detail_content)
        TextView mIvMessageDetailContent;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }
}
