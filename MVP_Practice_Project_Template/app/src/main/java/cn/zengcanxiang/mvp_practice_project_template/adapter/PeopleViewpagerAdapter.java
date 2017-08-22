package cn.zengcanxiang.mvp_practice_project_template.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class PeopleViewpagerAdapter extends FragmentPagerAdapter {
    //继续阅读是没有看完的，检测用户是否要继续看的
    //最近阅读是浏览记录
    private String[] titles = new String[]{"最近阅读", "已下载", "已收藏"};

    private List<Fragment> mFragments = new ArrayList<>();

    public PeopleViewpagerAdapter(FragmentManager fm, @NonNull List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

}