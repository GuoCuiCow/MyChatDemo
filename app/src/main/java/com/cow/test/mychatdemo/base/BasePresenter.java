package com.cow.test.mychatdemo.base;

/**
 * desc: BasePresenter
 * author：rookie on 16/12/6 下午12:55
 */
public abstract class BasePresenter<M, V> {

    public M mModel;
    public V mView;
//    public RxManager mRxManager = new RxManager();

    public void attachVM(V v, M m) {
        this.mView = v;
        this.mModel = m;
        this.onStart();
    }

    public void detachVM() {
//        mRxManager.clear();
        mView = null;
        mModel = null;
    }

    public abstract void onStart();
}