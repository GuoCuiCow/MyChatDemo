package com.cow.test.mychatdemo.contract;

import com.cow.test.mychatdemo.base.BaseModel;
import com.cow.test.mychatdemo.base.BasePresenter;
import com.cow.test.mychatdemo.base.BaseView;

import java.util.List;

/**
 * Created by cuiguo on 2017/3/7.
 */

public interface GroupContract {

    abstract class IGroupPresenter extends BasePresenter<GroupContract.IGroupModel,GroupContract.IGroupView> {
        public abstract void getUnreadMsgCount();
        //获取好友列表
        public abstract List<String> getAllContactsFromServer();
    }
    interface IGroupModel extends BaseModel {
        //获取好友列表
        List<String> getAllContactsFromServer();
    }

    interface IGroupView extends BaseView {
        void showContent();
    }
}
