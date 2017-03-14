package com.cow.test.mychatdemo.contract;

import com.cow.test.mychatdemo.base.BaseModel;
import com.cow.test.mychatdemo.base.BasePresenter;
import com.cow.test.mychatdemo.base.BaseView;
import com.cow.test.mychatdemo.data.bean.MessageBean;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by cuiguo on 2017/3/3.
 */

public interface ChatContract {
    abstract class IChatPresenter extends BasePresenter<ChatContract.IChatModel, ChatContract.IChatView> {
        //1.初始获取历史会话msg
        public abstract void getMsg(String username, List<MessageBean> messageBeanList);
        //2.将会话设为已读
        public abstract void markAllMessagesAsRead(String username);

        public abstract void markMessageAsRead(String username,String messageId);

        //3.下拉获取更多历史会话msg
        public abstract void getMoreMsg(String username, List<MessageBean> messageBeanList,int pagesize);



        //4.收到消息
        public abstract void receiveMsg();

        //5.发送消息
        public abstract void sendMsg(String content, String toUsername, EMMessage.ChatType type,List<MessageBean> messageBeanList);
    }

    interface IChatModel extends BaseModel {

        //获取会话
        EMConversation getConversation(String username);
        void sendMsg(EMMessage message);

    }

    interface IChatView extends BaseView {
        //初始化聊天列表
        void initContent();
        //添加新消息（收到或者发送消息时）
        void addMsg();
        void addMsg(MessageBean messageBean);
        //下拉加载更多聊天信息
        void getMoreMsg();
        void refresh(boolean refresh);
        void showUnReadMsg();

    }
}
