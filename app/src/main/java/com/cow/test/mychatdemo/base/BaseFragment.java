package com.cow.test.mychatdemo.base;


import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cow.test.mychatdemo.util.FastClickUtils;
import com.cow.test.mychatdemo.util.TUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * desc: BaseFragment
 * author：rookie on 16/12/6 下午7:12
 */
public abstract class BaseFragment<P extends BasePresenter, M extends BaseModel> extends Fragment implements View.OnClickListener {

    protected String TAG;

    protected Context mContext;
    protected Activity mActivity;

    protected Unbinder binder;

    public P  mPresenter;
    public M  mModel;

    protected View mGlobalView; //全局view

    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayoutView() != null) {
            mGlobalView = getLayoutView();
        } else {
            mGlobalView = inflater.inflate(getLayoutId(), container,false);
        }
        return mGlobalView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        TAG = getClass().getSimpleName();
        binder = ButterKnife.bind(this, view);
        mPresenter = TUtil.getT(this, 0);
        mModel = TUtil.getT(this, 1);
        isViewPrepared =true;
        initTitle();
        initUI(view, savedInstanceState);
        if (this instanceof BaseView) mPresenter.attachVM(this, mModel);
        getBundle(getArguments());
        initEvent();
        initData();
        super.onViewCreated(view, savedInstanceState);
    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasFetchData = false;
        isViewPrepared = false;
        if (binder != null) binder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachVM();
    }

    @Override
    public void onClick(View v) {
        if (FastClickUtils.getInstance().isFastClick()) {
            return;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewPrepared) {
//            StatusBarUtils.setStatusBarTextColor(mContext,true);
            lazyFetchDataIfPrepared();
        }
    }
    private void lazyFetchDataIfPrepared() {
        // 用户可见fragment && 没有加载过数据 && 视图已经准备完毕
        if (getUserVisibleHint() && isViewPrepared) {
            //hasFetchData = true;
            lazyFetchData();
        }
    }

    /** [懒加载的方式获取数据，仅在满足fragment可见和视图已经准备好的时候调用一次] */
    protected void lazyFetchData() {}



    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    /** 得到Activity传进来的值 */
    public void getBundle(Bundle bundle) {}

    /** 初始化控件 */
    public abstract void initUI(View view, @Nullable Bundle savedInstanceState);

    /** [初始化监听事件] */
    protected void initEvent(){};
    /** 在监听器之前把数据准备好*/
    protected void initData() {}
//
//    protected void showToast(String msg) {
//        ToastUtils.showToast(msg);
//    }

    /** [初始化标题] */
    private void initTitle() {

    }

    protected void showLoading() {

    }

    protected void hideLoading() {
    }
}
