package com.cow.test.mychatdemo.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cow.test.mychatdemo.R;
import com.cow.test.mychatdemo.adapter.ChatTextFromViewProvider;
import com.cow.test.mychatdemo.adapter.ChatTextToViewProvider;
import com.cow.test.mychatdemo.base.BaseActivity;
import com.cow.test.mychatdemo.contract.ChatContract;
import com.cow.test.mychatdemo.data.bean.MessageBean;
import com.cow.test.mychatdemo.model.ChatModel;
import com.cow.test.mychatdemo.presenter.ChatPresenter;
import com.cow.test.mychatdemo.util.OnReadMsgListener;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.FlatTypeClassAdapter;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by cuiguo on 2017/3/1.、
 * 聊天页面
 */

public class ChatActivity extends BaseActivity<ChatPresenter, ChatModel> implements ChatContract.IChatView {
    @BindView(R.id.rv_chat)
    RecyclerView mRvChat;
    @BindView(R.id.iv_mic)
    ImageView mIvMic;
    @BindView(R.id.et_content)
    EditText mEtContent;
    @BindView(R.id.tv_send)
    TextView mTvSend;
    @BindView(R.id.srl_content)
    SwipeRefreshLayout mSrlContent;
    @BindView(R.id.tv_new_msg)
    TextView mTvNewMsg;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private MultiTypeAdapter mAdapter;
    private int mUnReadMsg = 0;


    private String mUsername;

    private List<MessageBean> mMessageBeanList = new ArrayList<>();
    EMMessage.ChatType mChatType = EMMessage.ChatType.Chat;
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    public int getLayoutId() {
        return R.layout.activity_chat;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mSrlContent.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onRefresh() {
                mPresenter.getMoreMsg(mUsername, mMessageBeanList, 2);//为了测试，所以每次下拉刷新两个
            }
        });
        initRv();
        initEt();

    }

    //初始化列表
    private void initRv() {
        // TODO: 2017/3/6 查询本地数据库，获得头像和昵称
        mAdapter = new MultiTypeAdapter();
        /* 注册类型和 View 的对应关系 */
        //这里出现了一对多的关系，我们需要改一下
        mAdapter.setFlatTypeAdapter(
                new FlatTypeClassAdapter() {
                    @NonNull
                    @Override
                    public Class onFlattenClass(@NonNull Object item) {
                        return ((MessageBean) item).typeClass;
                    }
                });

        mAdapter.register(MessageBean.TypeFrom.class, new ChatTextFromViewProvider());
        mAdapter.register(MessageBean.TypeTo.class, new ChatTextToViewProvider());
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvChat.setLayoutManager(mLinearLayoutManager);
        mRvChat.setOnScrollListener(new OnReadMsgListener() {
            @Override
            public void onReadMsg(int unReadMsg) {
                //设置已读消息
                if (mUnReadMsg > unReadMsg) {
                    for (int i = mUnReadMsg-unReadMsg; i >0 ; i--) {
                        mPresenter.markMessageAsRead(mUsername,mMessageBeanList.get(mMessageBeanList.size()-i-1).message.getMsgId());
                    }
                    mUnReadMsg = unReadMsg;
                    showUnReadMsg();
                }

            }
        });
        mRvChat.setAdapter(mAdapter);
        mAdapter.setItems(mMessageBeanList);
    }

    private void initEt() {
        mEtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mTvSend.setBackgroundResource(R.drawable.love_bg_btn_send_useable);
                } else {
                    mTvSend.setBackgroundResource(R.drawable.love_bg_btn_send_useless);
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void initData() {
        super.initData();
        mUsername = getIntent().getStringExtra("username");
        mPresenter.getMsg(mUsername, mMessageBeanList);
        mPresenter.markAllMessagesAsRead(mUsername);
    }

    @OnClick({R.id.iv_mic, R.id.tv_send,R.id.tv_new_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_mic:
                break;
            case R.id.tv_send:
                sendMsg();
                break;
            case R.id.tv_new_msg:
                mRvChat.smoothScrollToPosition(mMessageBeanList.size()-1);
                break;
            default:
                break;
        }
    }

    private void sendMsg() {
        String s = mEtContent.getText().toString();
        if (!TextUtils.isEmpty(s)) {
            mPresenter.sendMsg(s, mUsername, mChatType, mMessageBeanList);
            mEtContent.setText("");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.removeMessageListener();
    }

    @Override
    public void initContent() {
        mAdapter.notifyDataSetChanged();
        mRvChat.scrollToPosition(mMessageBeanList.size() - 1);
    }

    @Override
    public void addMsg() {
        mAdapter.notifyDataSetChanged();
        mRvChat.smoothScrollToPosition(mMessageBeanList.size() - 1);
    }

    @Override
    public void addMsg(MessageBean messageBean) {
        mMessageBeanList.add(messageBean);
        //判断是否已经滚动到底部，
        if (!mRvChat.canScrollVertically(1)) {
            mAdapter.notifyItemInserted(mMessageBeanList.size() - 1);
            mRvChat.smoothScrollToPosition(mMessageBeanList.size() - 1);
            mPresenter.markMessageAsRead(mUsername,messageBean.message.getMsgId());
        } else {
            mUnReadMsg = mUnReadMsg + 1;
            showUnReadMsg();
            mAdapter.notifyItemInserted(mMessageBeanList.size() - 1);
        }

    }
    @Override
    public void showUnReadMsg() {
        if (mUnReadMsg > 0) {
            mTvNewMsg.setVisibility(View.VISIBLE);
            mTvNewMsg.setText(mUnReadMsg+"");
        }else {
            mTvNewMsg.setVisibility(View.GONE);
        }
    }

    @Override
    public void getMoreMsg() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void refresh(boolean refresh) {
        mSrlContent.setRefreshing(refresh);
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
