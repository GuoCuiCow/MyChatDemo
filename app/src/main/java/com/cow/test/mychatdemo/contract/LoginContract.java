package com.cow.test.mychatdemo.contract;

import com.cow.test.mychatdemo.base.BaseModel;
import com.cow.test.mychatdemo.base.BasePresenter;
import com.cow.test.mychatdemo.base.BaseView;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by cuiguo on 2017/2/28.
 */

public interface LoginContract {
    //登录
    abstract class ILoginPresenter extends BasePresenter<ILoginModel,ILoginView> {
        public abstract void login(String userName, String password);
        public abstract void register(String userName, String password) throws HyphenateException;
    }

    interface ILoginModel extends BaseModel {
        Observable<Object[]> login(Map<String,RequestBody> params, List<MultipartBody.Part> files);

    }

    interface ILoginView extends BaseView {
        void showContent();
        void goHomeActivity();
    }
}
