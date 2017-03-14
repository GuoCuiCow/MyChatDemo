package com.cow.test.mychatdemo.view;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cow.test.mychatdemo.R;
import com.cow.test.mychatdemo.adapter.FriendListViewProvider;
import com.cow.test.mychatdemo.base.BaseFragment;
import com.cow.test.mychatdemo.contract.GroupContract;
import com.cow.test.mychatdemo.model.GroupModel;
import com.cow.test.mychatdemo.presenter.GroupPresenter;

import java.util.List;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends BaseFragment<GroupPresenter, GroupModel> implements GroupContract.IGroupView {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_group)
    RecyclerView mRvGroup;
    private MultiTypeAdapter mAdapter;
    private Items mItems;
    private FriendListViewProvider mFriendListViewProvider;
    private List<String> mUsernames;

    public GroupFragment() {
    }

    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_group;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        initRv();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initRv() {
        mAdapter = new MultiTypeAdapter();
        mFriendListViewProvider = new FriendListViewProvider();
        mAdapter.register(String.class, mFriendListViewProvider);
        mItems = new Items();
        mAdapter.setItems(mItems);
        mRvGroup.setLayoutManager(new LinearLayoutManager(mContext));
        mRvGroup.setAdapter(mAdapter);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();
        getFriends();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getFriends() {
        mUsernames = mPresenter.getAllContactsFromServer();
        if (mUsernames==null){
            return;
        }
        mUsernames.forEach((s) -> mItems.add(s));
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showContent() {

    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }


}
