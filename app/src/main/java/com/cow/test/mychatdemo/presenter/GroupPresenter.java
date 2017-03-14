package com.cow.test.mychatdemo.presenter;

import com.cow.test.mychatdemo.contract.GroupContract;

import java.util.List;

/**
 * Created by cuiguo on 2017/3/7.
 */

public class GroupPresenter extends GroupContract.IGroupPresenter {
    @Override
    public void getUnreadMsgCount() {

    }

    @Override
    public List<String> getAllContactsFromServer() {
        return mModel.getAllContactsFromServer();
    }

    @Override
    public void onStart() {

    }
}
