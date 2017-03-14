package com.cow.test.mychatdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.cow.test.mychatdemo.R;
import com.cow.test.mychatdemo.base.BaseActivity;
import com.cow.test.mychatdemo.contract.LoginContract;
import com.cow.test.mychatdemo.model.LoginModel;
import com.cow.test.mychatdemo.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cuiguo on 2017/2/28.
 */

public class LoginActivity extends BaseActivity<LoginPresenter, LoginModel> implements LoginContract.ILoginView {
    @BindView(R.id.login_progress)
    ProgressBar mLoginProgress;
    @BindView(R.id.phone)
    AutoCompleteTextView mPhone;
    @BindView(R.id.password)
    EditText mPassword;
    @BindView(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    @BindView(R.id.email_register_button)
    Button mEmailRegisterButton;
    @BindView(R.id.email_login_form)
    LinearLayout mEmailLoginForm;
    @BindView(R.id.login_form)
    ScrollView mLoginForm;

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
    }

    @Override
    public void showContent() {

    }

    @Override
    public void goHomeActivity() {
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
    }



    @Override
    public void showMessage(String msg) {
//        showToast(msg);
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @OnClick({R.id.email_sign_in_button, R.id.email_register_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.email_sign_in_button:
                mPresenter.login(mPhone.getText().toString(),mPassword.getText().toString());
                break;
            case R.id.email_register_button:
                mPresenter.register(mPhone.getText().toString(),mPassword.getText().toString());
                break;
        }
    }
}
