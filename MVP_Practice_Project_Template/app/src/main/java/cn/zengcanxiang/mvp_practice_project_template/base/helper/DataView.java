package cn.zengcanxiang.mvp_practice_project_template.base.helper;


import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseView;

/**
 * 抽象出获取数据view界面可能需要的几种回调
 */
public interface DataView extends BaseView {

    /**
     * 获取数据开始
     */
    void onGetDataStart();

    /**
     * 获取数据结束
     */
    void onGetDataStop();

    /**
     * 获取数据成功
     */
    void getDataSuccess();

    /**
     * 获取数据失败
     */
    void getDataFail();

    /**
     * 无法获取到数据<br/>
     * 不管是网络错误还是读取数据库错误或者其余异常都可以走这个回调
     */
    void noData();


}
