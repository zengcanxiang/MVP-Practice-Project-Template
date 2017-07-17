package cn.zengcanxiang.mvp_practice_project_template.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * 规范代码的接口
 */
public interface UIWithCode {
    /**
     * 设置当前界面使用的布局文件
     */
    @LayoutRes
    int initLayout(Bundle savedInstanceState);

    /**
     * <p>在子Activity初始化的时候调用</p>
     * <p>将所有对代码规范的的初始化</p>
     */
    void initActivityWritCode();

    /**
     * 描述：处理业务逻辑
     */
    void disposeBusiness();

    /**
     * 描述：把所有View找出来
     */
    void initViews();

    /**
     * 描述：设置View的监控器
     */
    void setViewsListener();
}
