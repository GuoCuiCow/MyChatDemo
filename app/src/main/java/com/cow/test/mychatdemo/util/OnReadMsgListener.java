package com.cow.test.mychatdemo.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by cuiguo on 2017/3/7.
 * 滑动的时候减少未读消息数
 * 处理未读消息
 */

public abstract class OnReadMsgListener extends RecyclerView.OnScrollListener {
    private LinearLayoutManager layoutManager;
    private int itemCount, lastPosition, lastItemCount, unvisibleItemCount;


    public abstract void onReadMsg(int unReadMsg);

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            itemCount = layoutManager.getItemCount();
            lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
        } else {
            Log.e("OnLoadMoreListener", "The OnLoadMoreListener only support LinearLayoutManager");
            return;
        }
        unvisibleItemCount = itemCount - lastPosition-1;
        this.onReadMsg(unvisibleItemCount);
    }
}
