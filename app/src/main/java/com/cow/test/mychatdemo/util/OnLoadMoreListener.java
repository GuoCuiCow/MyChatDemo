package com.cow.test.mychatdemo.util;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by cuiguo on 2017/3/7.
 * 加载更多接口
 */

public abstract class OnLoadMoreListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager layoutManager;
    private int itemCount, lastPosition, lastItemCount;

    public abstract void onLoadMore();

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

        if (lastItemCount != itemCount && lastPosition == itemCount - 1) {
            lastItemCount = itemCount;
            this.onLoadMore();
        }
    }
}
