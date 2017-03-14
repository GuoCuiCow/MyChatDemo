package com.cow.test.mychatdemo.presenter;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.cow.test.mychatdemo.contract.ChatContract;
import com.cow.test.mychatdemo.data.bean.MessageBean;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by cuiguo on 2017/3/3.
 */

public class ChatPresenter extends ChatContract.IChatPresenter implements EMMessageListener {
    private String mStratId;
    private List<EMMessage> mMessages;
    private List<MessageBean> mMessageBeans = new ArrayList<>();//用来缓存加载更多的消息

    @Override
    public void onStart() {
        EMClient.getInstance().chatManager().addMessageListener(this);//设置消息接受监听
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getMsg(String username, List<MessageBean> messageBeanList) {
        mMessages = mModel.getConversation(username).getAllMessages();
        mStratId = mMessages.get(0).getMsgId();//获取顶部id
        // TODO: 2017/3/6 现在只是实现了纯文本message ，需要对不同类型的消息进行分发

        Flowable.just(mMessages)
                .map(messages -> {
                            messages.forEach(message -> mMessageBeans.add(new MessageBean(message)));
                            return mMessageBeans;
                        }
                )
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(beans -> {
                    messageBeanList.addAll(beans);
                    mView.initContent();
                });
    }

    @Override
    public void markAllMessagesAsRead(String username) {
        mModel.getConversation(username).markAllMessagesAsRead();
    }

    @Override
    public void markMessageAsRead(String username,String messageId) {
        mModel.getConversation(username).markMessageAsRead(messageId);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void getMoreMsg(String username, List<MessageBean> messageBeanList, int pagesize) {
        mMessages = mModel.getConversation(username).loadMoreMsgFromDB(mStratId, pagesize);
        mMessageBeans.clear();
        Flowable.just(mMessages)
                .map(messages -> {
                    messages.forEach(message -> mMessageBeans.add(new MessageBean(message)));
                    return mMessageBeans;
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(been -> {
                    messageBeanList.addAll(0, been);
                    mStratId = messageBeanList.get(0).message.getMsgId();
                    mView.refresh(false);
                    mView.getMoreMsg();
                });
    }


    @Override
    public void receiveMsg() {
    }

    @Override
    public void sendMsg(String content, String toUsername, EMMessage.ChatType type, List<MessageBean> messageBeanList) {
        EMMessage message = EMMessage.createTxtSendMessage(content, toUsername);//创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        message.setChatType(type);//如果是群聊，设置chattype，默认是单聊
        messageBeanList.add(new MessageBean(message));
        mModel.sendMsg(message);
        mView.addMsg();
    }

    public void removeMessageListener() {
        EMClient.getInstance().chatManager().removeMessageListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMessageReceived(List<EMMessage> list) {
        mMessageBeans.clear();
        Flowable.just(list)
                .flatMap(messages -> Flowable.fromIterable(messages))
                .map(message -> new MessageBean(message))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(messageBean -> mView.addMsg(messageBean));

    }

    @Override
    public void onCmdMessageReceived(List<EMMessage> list) {


    }

    @Override
    public void onMessageRead(List<EMMessage> list) {

    }

    @Override
    public void onMessageDelivered(List<EMMessage> list) {

    }

    @Override
    public void onMessageChanged(EMMessage message, Object o) {

    }
}
