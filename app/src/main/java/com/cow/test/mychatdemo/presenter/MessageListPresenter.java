package com.cow.test.mychatdemo.presenter;

import com.cow.test.mychatdemo.contract.MessageListContract;
import com.hyphenate.chat.EMConversation;

import java.util.Map;

/**
 * Created by cuiguo on 2017/3/1.
 */

public class MessageListPresenter extends MessageListContract.IMessageListPresenter {
    Map<String, EMConversation> conversations;

    @Override
    public void onStart() {

    }

    @Override
    public void getUnreadMsgCount() {

    }

    @Override
    public Map<String, EMConversation> getAllConversations() {
        conversations = mModel.getAllConversations();
        return conversations;
    }
}
