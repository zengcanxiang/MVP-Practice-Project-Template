package cn.zengcanxiang.mvp_practice_project_template.base.helper.list;


import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;

/**
 * 列表界面需要实现的接口
 */
public interface ListInterface {
    /**
     * 绑定背景颜色值
     */
    @ColorInt
    int bindBgColor();

    /**
     * 绑定适配器
     */
    RecyclerView.Adapter bindAdapter();

    /**
     * 绑定布局管理器
     */
    RecyclerView.LayoutManager bindManager();

    /**
     * 绑定布局分割
     */
    RecyclerView.ItemDecoration bindDecoration();

}
