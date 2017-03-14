package com.cow.test.mychatdemo.model;

import com.cow.test.mychatdemo.contract.ChatContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

/**
 * Created by cuiguo on 2017/3/3.
 */

public class ChatModel implements ChatContract.IChatModel {
    @Override
    public EMConversation getConversation(String username) {
        return EMClient.getInstance().chatManager().getConversation(username);
    }

    @Override
    public void sendMsg(EMMessage message) {
        EMClient.getInstance().chatManager().sendMessage(message);
    }
}
