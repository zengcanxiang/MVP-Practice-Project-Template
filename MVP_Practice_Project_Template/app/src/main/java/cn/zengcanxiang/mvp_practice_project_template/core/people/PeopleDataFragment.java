package cn.zengcanxiang.mvp_practice_project_template.core.people;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.adapter.PeopleDataAdapter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataListFragment;


public class PeopleDataFragment extends DataListFragment<PeoplePresenter, PeopleModel> implements PeopleContract.IPeopleView {
    private PeopleDataAdapter mAdapter;
    private List<String> data = new ArrayList<>();
    /**
     * 请求的数据类型
     */
    private int requestMode = MODE_COLLECTION;

    /**
     * 收藏
     */
    public static final int MODE_COLLECTION = 0;
    /**
     * 历史
     */
    public static final int MODE_HISTORY = 1;
    /**
     * 下载
     */
    public static final int MODE_DOWNLOAD = 2;

    public static PeopleDataFragment newInstance(int model) {
        PeopleDataFragment fragment = new PeopleDataFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("model", model);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void disposeBusiness() {
        if (getArguments() != null) {
            requestMode = getArguments().getInt("model");
        } else {
            throw new IllegalArgumentException("没有传入model参数");
        }
        switch (requestMode) {
            case MODE_COLLECTION:
                mPresenter.getCollectionList();
                break;
            case MODE_HISTORY:
                mPresenter.getHistoryList();
                break;
            case MODE_DOWNLOAD:
                mPresenter.getDownloadList();
                break;
        }
    }

    @Override
    public int titleLayoutId() {
        return 0;
    }

    @Override
    public RecyclerView.Adapter bindAdapter() {
        mAdapter = new PeopleDataAdapter(data, mContext);
        return mAdapter;
    }

    @Override
    public RecyclerView.LayoutManager bindManager() {
        return new LinearLayoutManager(mContext);
    }

    @Override
    public void refresh() {
        switch (requestMode) {
            case MODE_COLLECTION:
                mPresenter.getCollectionList();
                break;
            case MODE_HISTORY:
                mPresenter.getHistoryList();
                break;
            case MODE_DOWNLOAD:
                mPresenter.getDownloadList();
                break;
        }
    }

    @Override
    public void loadMore() {
        switch (requestMode) {
            case MODE_COLLECTION:
                mPresenter.getCollectionList();
                break;
            case MODE_HISTORY:
                mPresenter.getHistoryList();
                break;
            case MODE_DOWNLOAD:
                mPresenter.getDownloadList();
                break;
        }
    }

    @Override
    public void updateC(List<String> s) {
        mAdapter.clear();
        mAdapter.addAll(0,s);
    }

    @Override
    public void updateH(List<String> s) {
        mAdapter.clear();
        mAdapter.addAll(0,s);
    }

    @Override
    public void updateD(List<String> s) {
        mAdapter.clear();
        mAdapter.addAll(0,s);
    }

    @Override
    public boolean isImmersed() {
        return false;
    }
}
