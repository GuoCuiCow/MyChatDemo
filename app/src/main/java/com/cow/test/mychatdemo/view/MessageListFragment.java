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
import com.cow.test.mychatdemo.adapter.ConversationViewProvider;
import com.cow.test.mychatdemo.base.BaseFragment;
import com.cow.test.mychatdemo.contract.MessageListContract;
import com.cow.test.mychatdemo.model.MessageListModel;
import com.cow.test.mychatdemo.presenter.MessageListPresenter;
import com.hyphenate.chat.EMConversation;

import java.util.Map;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageListFragment extends BaseFragment<MessageListPresenter, MessageListModel> implements MessageListContract.IMessageListView {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.rv_messages)
    RecyclerView mRvMessages;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private MultiTypeAdapter mAdapter;
    private Items mItems;
    private Map<String, EMConversation> mConversationMap;
    private ConversationViewProvider mConversationViewProvider;


    public MessageListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MessageListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MessageListFragment newInstance(String param1, String param2) {
        MessageListFragment fragment = new MessageListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void initUI(View view, @Nullable Bundle savedInstanceState) {
        mAdapter = new MultiTypeAdapter();
        /* 注册类型和 View 的对应关系 */
        mConversationViewProvider = new ConversationViewProvider();
        mAdapter.register(EMConversation.class, mConversationViewProvider);
        mRvMessages.setLayoutManager(new LinearLayoutManager(mContext));
        mRvMessages.setAdapter(mAdapter);
        mItems = new Items();
        mAdapter.setItems(mItems);
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();
        initMsgList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initMsgList() {
        mItems.clear();
        mConversationMap = mPresenter.getAllConversations();
        mConversationMap.forEach((k, v) -> mItems.add(v));
        mAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        initMsgList();
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

    @Override
    public void showContent() {

    }
}
