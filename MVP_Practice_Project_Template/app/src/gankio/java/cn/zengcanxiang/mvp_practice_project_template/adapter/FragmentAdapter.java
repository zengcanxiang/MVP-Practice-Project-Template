package cn.zengcanxiang.mvp_practice_project_template.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment;


public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<HomeCategoryFragment> fragments;

    public FragmentAdapter(FragmentManager fm, List<HomeCategoryFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getTitle();
    }
}
