package cn.zengcanxiang.mvp_practice_project_template.core.comics;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.adapter.HorizontalHistoryAdapter;
import cn.zengcanxiang.mvp_practice_project_template.adapter.RankingListAdapter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataFragment;

import static android.support.v7.widget.LinearLayoutManager.HORIZONTAL;


public class ComicsFragment extends DataFragment<ComicsPresenter, ComicsModel>
        implements ComicsContract.IComicsView {
    RecyclerView horizontalHistory, rankingList;
    HorizontalHistoryAdapter adapter;
    List<String> s = new ArrayList<>();
    RankingListAdapter rankingListAdapter;

    @Override
    public int initLayout(Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public void disposeBusiness() {
        s.add("");
        s.add("");
        s.add("");
        s.add("");
        s.add("");
        s.add("");
        mPresenter.getContinueReadRecordData();
        mPresenter.getRankingData();
    }

    @Override
    public void initViews() {
        horizontalHistory = (RecyclerView) findViewById(R.id.horizontal_history);
        rankingList = (RecyclerView) findViewById(R.id.ranking_list);
    }


    @Override
    public void setViewsListener() {

    }

    @Override
    public void showContinueReadRecord(List mData) {
        adapter = new HorizontalHistoryAdapter(s, mContext);
        horizontalHistory.setLayoutManager(new LinearLayoutManager(mContext, HORIZONTAL, false));
        horizontalHistory.setAdapter(adapter);
    }

    @Override
    public void showRankingList(List mData) {
        rankingListAdapter = new RankingListAdapter(s, mContext);
        rankingList.setLayoutManager(new LinearLayoutManager(mContext));
        rankingList.setAdapter(rankingListAdapter);
    }

    @Override
    public void showRotation(List mData) {

    }
}