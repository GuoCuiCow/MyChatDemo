package com.cow.test.mychatdemo.base;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import com.cow.test.mychatdemo.util.TUtil;
import com.cow.test.mychatdemo.util.ToastUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cuiguo on 2017/2/28.
 */

public abstract class BaseActivity<P extends BasePresenter, M extends BaseModel>  extends AppCompatActivity {
    protected String TAG;
    public P mPresenter;
    public M mModel;
    protected Context mContext;
    protected Unbinder binder;
    protected InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init(savedInstanceState);
    }
    /** [初始化] */
    private void init(Bundle savedInstanceState) {
        this.setContentView(this.getLayoutId());
        mContext = this;
        binder = ButterKnife.bind(this);
        TAG = getClass().getSimpleName();
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
        addProgress();
        initView(savedInstanceState);
        initEvent();
        initData();
    }
    private void addProgress() {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        ProgressBar progressBar =new ProgressBar(this);
        progressBar.setVisibility(View.INVISIBLE);
        viewGroup.addView(progressBar);
    }
    protected static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    public abstract int getLayoutId();

    public abstract void initView(Bundle savedInstanceState);

    protected void initEvent(){}

    protected  void initData(){}
    protected void hideSoftKeyboard() {
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
