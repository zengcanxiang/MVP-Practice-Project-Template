package cn.zengcanxiang.mvp_practice_project_template.base.helper.list;

/**
 * 刷新界面需要实现的接口
 */
public interface RefreshInterface {
    /**
     * 加载更多完成
     */
    void loadMoreComplete();

    /**
     * 刷新更多完成
     */
    void refreshComplete();

    /**
     * 刷新回调
     */
    void refresh();

    /**
     * 加载更多回调
     */
    void loadMore();

    /**
     * 设置刷新是否启用
     */
    void setUpRefresh(boolean isRefresh);

    /**
     * 设置加载更多是否启用
     */
    void setUpLoadMore(boolean isLoadMore);


    /**
     * 绑定刷新view
     */
    Object bindRefreshView();

    /**
     * 绑定加载更多view
     */
    Object bindLoadMoreView();
}
