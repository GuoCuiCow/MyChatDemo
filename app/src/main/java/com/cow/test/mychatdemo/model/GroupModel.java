package com.cow.test.mychatdemo.model;

import com.cow.test.mychatdemo.contract.GroupContract;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * Created by cuiguo on 2017/3/7.
 */

public class GroupModel implements GroupContract.IGroupModel {

    @Override
    public List<String> getAllContactsFromServer() {
        try {
            return EMClient.getInstance().contactManager().getAllContactsFromServer();
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        return null;
    }
}
