package com.cow.test.mychatdemo.base;


/**
 * desc: BaseView
 * author：rookie on 16/12/6 下午4:07
 */
public interface BaseView {
    void showMessage(String msg);
    void showProgress();//显示进度条
    void hideProgress();//隐藏进度条
}
