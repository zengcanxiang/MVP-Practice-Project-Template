package cn.zengcanxiang.mvp_practice_project_template.core.people;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.adapter.PeopleViewpagerAdapter;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.MVPBaseFragment;


public class PeopleMainFragment extends MVPBaseFragment {
    ViewPager mViewPager;
    PeopleViewpagerAdapter adapterTest;
    private TabLayout mTabLayout;

    @Override
    public int initLayout(Bundle savedInstanceState) {
        return R.layout.fragment_people;
    }

    @Override
    public void disposeBusiness() {
    }

    @Override
    public void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(PeopleDataFragment.newInstance(PeopleDataFragment.MODE_COLLECTION));
        list.add(PeopleDataFragment.newInstance(PeopleDataFragment.MODE_DOWNLOAD));
        list.add(PeopleDataFragment.newInstance(PeopleDataFragment.MODE_HISTORY));
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapterTest = new PeopleViewpagerAdapter(getChildFragmentManager(), list);
        mViewPager.setAdapter(adapterTest);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setupWithViewPager(mViewPager);
//        CoordinatorTabLayout mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
//        mCoordinatorTabLayout
//                .setTitle("用户名")
//                .setBackEnable(false)
//                .setImageArray(mImageArray, mColorArray)
//                .setupWithViewPager(mViewPager);
//        mCoordinatorTabLayout.getSetting().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ToastUtils.show(mContext, "提示");
//            }
//        });
    }

    @Override
    public void setViewsListener() {

    }

    @Override
    public void onDestroyView() {
        mViewPager.setAdapter(null);
        super.onDestroyView();
    }
}
