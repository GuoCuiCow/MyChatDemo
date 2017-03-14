package com.cow.test.mychatdemo.contract;

import com.cow.test.mychatdemo.base.BaseModel;
import com.cow.test.mychatdemo.base.BasePresenter;
import com.cow.test.mychatdemo.base.BaseView;
import com.hyphenate.chat.EMConversation;

import java.util.Map;

/**
 * Created by cuiguo on 2017/3/1.
 * 首页消息列表的契约类
 */

public interface MessageListContract {
    abstract class IMessageListPresenter extends BasePresenter<IMessageListModel,IMessageListView>{
        protected abstract void getUnreadMsgCount();
        //获取所有会话
        protected abstract Map<String, EMConversation> getAllConversations();
    }
    interface IMessageListModel extends BaseModel{
        //获取所有会话
        Map<String, EMConversation> getAllConversations();
    }

    interface IMessageListView extends BaseView{
        void showContent();
    }
}
