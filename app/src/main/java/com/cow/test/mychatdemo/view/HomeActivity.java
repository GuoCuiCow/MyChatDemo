package com.cow.test.mychatdemo.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.cow.test.mychatdemo.R;
import com.cow.test.mychatdemo.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 主页，分为三个页面，消息，群组，我的
 */
public class HomeActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.layFrame)
    FrameLayout mLayFrame;
    @BindView(R.id.activity_home)
    RelativeLayout mActivityHome;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar mBottomNavigationBar;

    private List<Fragment> fragments;

    @Override
    public int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        // TODO: 2017/3/7 需要写toolbar
        initBottom();
    }


    private void initBottom() {
        //设置底部导航
        mBottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.icon_message_select, getString(R.string.home_message)).setInactiveIconResource(R.drawable.icon_message_unsel).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorGray))
                .addItem(new BottomNavigationItem(R.drawable.icon_group_select, getString(R.string.home_group)).setInactiveIconResource(R.drawable.icon_group_unsel).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorGray))
                .addItem(new BottomNavigationItem(R.drawable.icon_mine_select, getString(R.string.home_mine)).setInactiveIconResource(R.drawable.icon_mine_unsel).setActiveColorResource(R.color.colorPrimary).setInActiveColorResource(R.color.colorGray))
                .setFirstSelectedPosition(0)
                .initialise();
        fragments = getFragments();
        setDefaultFragment();
        mBottomNavigationBar.setTabSelectedListener(this);
    }

    /**
     * 设置默认显示的fragment
     */
    private void setDefaultFragment() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.layFrame, MessageListFragment.newInstance(getString(R.string.home_message),"'"));
        transaction.commit();
    }
    /**
     * 获取fragment
     *
     * @return fragment列表
     */
    private List<Fragment> getFragments() {

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MessageListFragment.newInstance(getString(R.string.home_message),"'"));
        fragments.add(GroupFragment.newInstance(getString(R.string.home_group),"'"));
        fragments.add(MineFragment.newInstance(getString(R.string.home_mine),"'"));
        return fragments;
    }


    @Override
    public void onTabSelected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.replace(R.id.layFrame, fragment);
                ft.commitAllowingStateLoss();//选择性的提交，和commit有一定的区别，他不保证数据完整传输
            }
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
