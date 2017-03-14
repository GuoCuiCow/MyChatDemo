package com.cow.test.mychatdemo.presenter;

import android.util.Log;

import com.cow.test.mychatdemo.contract.LoginContract;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by cuiguo on 2017/2/28.
 */

public class LoginPresenter extends LoginContract.ILoginPresenter {
    @Override
    public void onStart() {

    }

    @Override
    public void login(String userName, String password) {
        EMClient.getInstance().login(userName, password, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.e("login","onSuccess");
//                mView.showMessage("登录成功");
                //以下两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
                EMClient.getInstance().chatManager().loadAllConversations();
                EMClient.getInstance().groupManager().loadAllGroups();
                mView.goHomeActivity();
            }

            @Override
            public void onError(int i, String s) {
//                mView.showMessage(s);
                Log.e("login","onError"+s);
            }

            @Override
            public void onProgress(int i, String s) {
                Log.e("login","onProgress"+s);
            }
        });
    }

    @Override
    public void register(String userName, String password)  {
        try {
            EMClient.getInstance().createAccount(userName,password);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
}
