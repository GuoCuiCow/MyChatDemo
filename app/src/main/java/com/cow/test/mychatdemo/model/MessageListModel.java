package com.cow.test.mychatdemo.model;

import com.cow.test.mychatdemo.contract.MessageListContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.Map;

/**
 * Created by cuiguo on 2017/3/1.
 */

public class MessageListModel implements MessageListContract.IMessageListModel {
    @Override
    public Map<String, EMConversation> getAllConversations() {
        return EMClient.getInstance().chatManager().getAllConversations();
    }
}
