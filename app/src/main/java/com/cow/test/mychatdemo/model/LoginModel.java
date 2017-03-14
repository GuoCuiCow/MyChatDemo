package com.cow.test.mychatdemo.model;

import com.cow.test.mychatdemo.contract.LoginContract;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by cuiguo on 2017/2/28.
 */

public class LoginModel implements LoginContract.ILoginModel {
    @Override
    public Observable<Object[]> login(Map<String, RequestBody> params, List<MultipartBody.Part> files) {
        return null;
    }
}
