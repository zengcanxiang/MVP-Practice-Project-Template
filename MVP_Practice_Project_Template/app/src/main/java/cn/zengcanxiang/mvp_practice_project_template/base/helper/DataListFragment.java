package cn.zengcanxiang.mvp_practice_project_template.base.helper;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import am.widget.stateframelayout.StateFrameLayout;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.util.NetWorkStateUtils;
import cn.zengcanxiang.mvp_practice_project_template.widget.LoadMoreFooterView;

/**
 * 抽象封装数据列表(带下拉刷新)的fragment,带有状态布局.<br/>
 * 已经将下拉刷新与状态布局相结合<br/>
 * 如果需要添加标题栏，重写titleLayoutId()方法
 */
public abstract class DataListFragment<BP extends DataPresenter, BM extends BaseModel> extends StateFragment<BP, BM> {

    protected RecyclerView recyclerView;
    protected boolean isLoadMore = false;
    protected boolean isRefresh = false;
    private TwinklingRefreshLayout refreshLayout;

    @Override
    public int bodyLayoutId(Bundle savedInstanceState) {
        return R.layout.base_fragment_data_list;
    }

    @Override
    public final void initViews() {

    }

    @Override
    protected void showBody() {
        super.showBody();
        recyclerView = (RecyclerView) findViewById(R.id.base_fragment_data_list);
        refreshLayout = (TwinklingRefreshLayout) findViewById(R.id.base_fragment_data_refresh);

        refreshLayout.setHeaderView(bindRefreshView());
        refreshLayout.setBottomView(bindLoadMoreView());

        refreshLayout.setHeaderHeight(140);
        refreshLayout.setMaxHeadHeight(240);
        refreshLayout.setFloatRefresh(true);
        refreshLayout.setAutoLoadMore(true);

        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                isRefresh = true;
                refresh();
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                isLoadMore = true;
                loadMore();
            }
        });
        recyclerView.setLayoutManager(bindManager());
        recyclerView.setAdapter(bindAdapter());
    }

    @Override
    public final void setViewsListener() {

    }

    /**
     * 在回调网络请求view方法之后，才会调用stop回调
     */
    @Override
    public void onGetDataStop() {
        autoModifyState();
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

    @Override
    public void onEmptyClick(StateFrameLayout layout) {
        if (NetWorkStateUtils.isNetwork(mContext)) {
            showLoadView();
            disposeBusiness();
        } else {
            noData();
        }
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

    /**
     * 完成加载更多
     */
    protected final void loadMoreComplete() {
        isLoadMore = false;
        refreshLayout.finishLoadmore();
    }

    /**
     * 完成刷新
     */
    protected final void refreshComplete() {
        isRefresh = false;
        refreshLayout.finishRefreshing();
    }

    /**
     * 设置刷新是否启用
     */
    protected final void setUpRefresh(boolean isRefresh) {
        refreshLayout.setEnableRefresh(isRefresh);
    }

    /**
     * 设置加载更多是否启用
     */
    protected final void setUpLoadMore(boolean isLoadMore) {
        refreshLayout.setEnableLoadmore(isLoadMore);
    }

    /**
     * 绑定刷新view
     */
    protected IHeaderView bindRefreshView() {
        ProgressLayout header = new ProgressLayout(mContext);
        header.setColorSchemeResources(R.color.colorAccent);
        return header;
    }

    /**
     * 绑定加载更多view
     */
    protected IBottomView bindLoadMoreView() {
        return new LoadMoreFooterView(mContext);
    }

    /**
     * 绑定适配器
     */
    public abstract RecyclerView.Adapter bindAdapter();

    /**
     * 绑定布局管理
     */
    public abstract RecyclerView.LayoutManager bindManager();

    /**
     * 刷新
     */
    public abstract void refresh();

    /**
     * 加载更多
     */
    public abstract void loadMore();

    public TwinklingRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }
}
