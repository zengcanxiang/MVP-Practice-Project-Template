package cn.zengcanxiang.mvp_practice_project_template.base.helper.state;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * 状态布局界面接口
 */
public interface StateInterface {

    /**
     * 绑定头部布局的id
     */
    @LayoutRes
    int bindHeadLayoutId();

    /**
     * 绑定中间显示布局的id
     */
    @LayoutRes
    int bindContentLayoutId(Bundle savedInstanceState);

    /**
     * 是否开启界面处理,默认开启
     */
    boolean isOpenPageManager();

    /**
     * 显示加载中布局
     */
    void showLoadView();

    /**
     * 显示错误布局
     */
    void showErrorView();

    /**
     * 显示无数据布局
     */
    void showEmptyView();

    /**
     * 显示正常布局
     */
    void showContentView();

    /**
     * 绑定加载中布局
     */
    View bindLoadView();
    /**
     * 绑定无数据布局
     */
    View bindEmptyView();
    /**
     * 绑定错误布局
     */
    View bindErrorView();
}
