package com.netsun.toocle.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.netsun.toocle.R;
import com.netsun.toocle.model.CircleFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    BottomNavigationBar homeBar;

    ArrayList<Fragment> fragments = null;
    CircleFragment circleFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        homeBar = (BottomNavigationBar)findViewById(R.id.home_bar);
        homeBar.setMode(BottomNavigationBar.MODE_FIXED);
        homeBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        homeBar.setBarBackgroundColor(R.color.homeBarUnSelect);
        homeBar.addItem(new BottomNavigationItem(R.drawable.toocle,"生意圈").setActiveColorResource(R.color.homeBarSelect))
        .addItem(new BottomNavigationItem(R.drawable.sms,"消息中心").setActiveColorResource(R.color.homeBarSelect))
        .addItem(new BottomNavigationItem(R.drawable.dzp,"大宗品").setActiveColorResource(R.color.homeBarSelect))
        .addItem(new BottomNavigationItem(R.drawable.find,"生意搜").setActiveColorResource(R.color.homeBarSelect))
        .setFirstSelectedPosition(0)
        .initialise();
        homeBar.setTabSelectedListener(this);
        fragments = getFragments();
        setDefaultFragment();
        homeBar.setTabSelectedListener(this);

    }
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<Fragment>();
        circleFragment = CircleFragment.newInstence("生意圈");
        fragments.add(circleFragment);
        return fragments;
    }
    private void setDefaultFragment() {
//        取得碎片管理器
        FragmentManager manager = getFragmentManager();
//        开启事物
        FragmentTransaction transaction = manager.beginTransaction();
//        把碎片与布局关联
        if (circleFragment == null) {
            circleFragment = CircleFragment.newInstence("生意圈");
        }
        transaction.replace(R.id.laycontent,circleFragment);
//        提交事物
        transaction.commit();
    }

    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                if (circleFragment == null){
                    circleFragment = CircleFragment.newInstence("生意圈");
                }
                transaction.replace(R.id.laycontent,circleFragment);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
