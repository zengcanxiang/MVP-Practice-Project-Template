package cn.zengcanxiang.mvp_practice_project_template.core.home;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.adapter.GankAdapter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataListFragment;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.Gank;
import cn.zengcanxiang.mvp_practice_project_template.core.home.mvp.HomeContract;
import cn.zengcanxiang.mvp_practice_project_template.core.home.mvp.HomeModel;
import cn.zengcanxiang.mvp_practice_project_template.core.home.mvp.HomePresenter;

public class HomeCategoryFragment extends DataListFragment<HomePresenter, HomeModel>
        implements HomeContract.IHomeFragmentView {
    private GankAdapter mAdapter;
    public static final String MODEL_ANDROID = "Android";
    public static final String MODEL_IOS = "iOS";
    public static final String MODEL_前端 = "前端";
    public static final String MODEL_瞎推荐 = "瞎推荐";
    public static final String MODEL_拓展资源 = "拓展资源";
    public static final String MODEL_APP = "App";

    private String mModelType;

    public static HomeCategoryFragment newInstance(String model) {
        HomeCategoryFragment fragment = new HomeCategoryFragment();
        Bundle b = new Bundle();
        b.putString("model", model);
        fragment.setArguments(b);
        return fragment;
    }

    public String getTitle() {
        return mModelType;
    }

    @Override
    public void disposeBusiness() {
        HomeActivity activity = (HomeActivity) mContext;
        mModelType = getArguments().getString("model");
        mPresenter.getListDataByCategory(activity.getShowDate(), mModelType);
    }

    @Override
    public int titleLayoutId() {
        return 0;
    }

    @Override
    public RecyclerView.Adapter bindAdapter() {
        List<Gank> mData = new ArrayList<>();
        mAdapter = new GankAdapter(mData, mContext);
        setUpLoadMore(false);
        setUpRefresh(false);
        getRefreshLayout().setEnableOverScroll(false);
        return mAdapter;
    }

    @Override
    public RecyclerView.LayoutManager bindManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    public void refresh() {

    }

    @Override
    public void loadMore() {

    }


    @Override
    public void showModelData(List<Gank> data) {
        mAdapter.replaceAll(data);
    }
}
