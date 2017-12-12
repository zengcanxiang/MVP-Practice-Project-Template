package cn.zengcanxiang.mvp_practice_project_template.base.helper.list;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import am.widget.stateframelayout.StateFrameLayout;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataActivity;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataPresenter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.state.StateInterface;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.util.NetWorkStateUtils;
import cn.zengcanxiang.mvp_practice_project_template.widget.LoadMoreFooterView;
import cn.zengcanxiang.mvp_practice_project_template.widget.MaterialHeader;

/**
 * 带刷新、加载更多的列表界面
 */
public abstract class DataListActivity<BP extends DataPresenter, BM extends BaseModel> extends DataActivity<BP, BM>
        implements ListInterface, RefreshInterface, StateInterface {

    protected boolean isLoadMore = false;
    protected boolean isRefresh = false;
    protected SmartRefreshLayout refreshLayout;

    protected RecyclerView recyclerView;

    protected AppBarLayout appBar;

    private StateFrameLayout stateFrameLayout;

    private int isInitView = 0;

    @Override
    public final void initActivityWritCode() {
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeadView();
        initState();
        disposeBusiness();
    }

    private void initState() {
        if (isOpenPageManager()) {
            stateFrameLayout = (StateFrameLayout) findViewById(R.id.base_state_layout);
            stateFrameLayout.setStateViews(bindLoadView(), bindErrorView(), bindEmptyView());
            stateFrameLayout.setOnStateClickListener(new StateFrameLayout.OnAllStateClickListener() {
                @Override
                public void onNormalClick(StateFrameLayout layout) {

                }

                @Override
                public void onLoadingClick(StateFrameLayout layout) {

                }

                @Override
                public void onEmptyClick(StateFrameLayout layout) {
                    if (NetWorkStateUtils.isNetwork(mContext)) {
                        showLoadView();
                        disposeBusiness();
                    } else {
                        noData();
                    }
                }

                @Override
                public void onErrorClick(StateFrameLayout layout) {
                    if (NetWorkStateUtils.isNetwork(mContext)) {
                        showLoadView();
                        disposeBusiness();
                    } else {
                        noData();
                    }
                }
            });
        }
    }

    private void initHeadView() {
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        int headLayoutId = bindHeadLayoutId();
        ViewStub headViewStub = (ViewStub) findViewById(R.id.base_state_head);
        if (headLayoutId != 0 && headViewStub != null) {
            appBar.setVisibility(View.VISIBLE);
            headViewStub.setLayoutResource(headLayoutId);
            headViewStub.inflate();
        }
    }

    @Override
    public int initLayout(Bundle savedInstanceState) {
        return R.layout.base_fragment_data_list;
    }

    @Override
    public void initViews() {
        isInitView = 1;
        initListView();
        initRefreshView();
    }

    protected void initRefreshView() {
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.base_fragment_data_refresh);
        refreshLayout.setBackgroundColor(bindBgColor());

        RefreshHeader iHeaderView;
        if (bindRefreshView() instanceof RefreshHeader) {
            iHeaderView = (RefreshHeader) bindRefreshView();
            refreshLayout.setRefreshHeader(iHeaderView);
        }

        RefreshFooter iBottomView;
        if (bindLoadMoreView() instanceof RefreshFooter) {
            iBottomView = (RefreshFooter) bindLoadMoreView();
            refreshLayout.setRefreshFooter(iBottomView);
        }

        refreshLayout.setHeaderHeight(140);
        refreshLayout.setEnableAutoLoadmore(true);
        refreshLayout.setDisableContentWhenRefresh(true);//是否在刷新的时候禁止列表的操作
        refreshLayout.setDisableContentWhenLoading(true);//是否在加载的时候禁止列表的操作
    }

    private void initListView() {
        recyclerView = (RecyclerView) findViewById(R.id.base_fragment_data_list);
    }

    @Override
    public void setViewsListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                isLoadMore = true;
                loadMore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                isRefresh = true;
                refresh();
            }
        });
    }


    @Override
    public void onGetDataStop() {
        super.onGetDataStop();
        autoModifyState();
    }

    @Override
    public void getDataSuccess() {
        showContentView();
        //下面的代码只执行一次
        if (isInitView == 1) {
            return;
        }
        initViews();
        setViewsListener();
        RecyclerView.LayoutManager layoutManager = bindManager();
        if (bindDecoration() != null) {
            recyclerView.addItemDecoration(bindDecoration());
        }
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(bindAdapter());
    }

    @Override
    public void getDataFail() {
        showErrorView();
    }

    @Override
    public void noData() {
        showErrorView();
    }


    @Override
    public int bindBgColor() {
        return ContextCompat.getColor(mContext, android.R.color.transparent);
    }

    @Override
    public RecyclerView.ItemDecoration bindDecoration() {
        return null;
    }

    /**
     * 自动根据变量执行请求状态的改变
     */
    protected final void autoModifyState() {
        if (isRefresh) {
            refreshComplete();
        }
        if (isLoadMore) {
            loadMoreComplete();
        }
    }

    @Override
    public void loadMoreComplete() {
        isLoadMore = false;
        refreshLayout.finishLoadmore();
    }

    @Override
    public void refreshComplete() {
        isRefresh = false;
        refreshLayout.finishRefresh();
    }

    @Override
    public void setUpRefresh(boolean isRefresh) {
        refreshLayout.setEnableRefresh(isRefresh);
    }

    @Override
    public void setUpLoadMore(boolean isLoadMore) {
        refreshLayout.setEnableLoadmore(isLoadMore);
    }

    @Override
    public Object bindRefreshView() {
        return  new MaterialHeader(mContext);
    }

    @Override
    public Object bindLoadMoreView() {
        return new LoadMoreFooterView(mContext);
    }

    @Override
    public int bindContentLayoutId(Bundle savedInstanceState) {
        return 0;
    }

    @Override
    public boolean isOpenPageManager() {
        return true;
    }

    @Override
    public void showLoadView() {
        if (stateFrameLayout != null)
            stateFrameLayout.loading();
    }

    @Override
    public void showErrorView() {
        if (stateFrameLayout != null)
            stateFrameLayout.error();
    }

    @Override
    public void showEmptyView() {
        if (stateFrameLayout != null)
            stateFrameLayout.empty();
    }

    @Override
    public void showContentView() {
        if (stateFrameLayout != null)
            stateFrameLayout.normal();
    }

    @Override
    public View bindLoadView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pager_loading, stateFrameLayout, false);
    }

    @Override
    public View bindEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pager_empty, stateFrameLayout, false);
    }

    @Override
    public View bindErrorView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pager_error, stateFrameLayout, false);
    }
}
