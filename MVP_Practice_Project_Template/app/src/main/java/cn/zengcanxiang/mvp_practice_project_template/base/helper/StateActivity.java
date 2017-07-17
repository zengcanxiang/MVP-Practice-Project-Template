package cn.zengcanxiang.mvp_practice_project_template.base.helper;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import am.widget.stateframelayout.StateFrameLayout;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;

/**
 * 带状态布局的Activity
 *
 * @param <BP> 一般需要请求数据的才需要显示不同的状态布局，所以规定是DataPresenter
 */
public abstract class StateActivity<BP extends DataPresenter, BM extends BaseModel> extends DataActivity<BP, BM> implements StateFrameLayout.OnAllStateClickListener {

    private int bodyLayoutId;
    private StateFrameLayout stateFrameLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int headLayoutId = titleLayoutId();
        bodyLayoutId = bodyLayoutId(savedInstanceState);
        if (headLayoutId != 0) {
            ViewStub headViewStub = (ViewStub) findViewById(R.id.base_state_head);
            headViewStub.setLayoutResource(titleLayoutId());
            headViewStub.inflate();
        }

        if (isOpenPageManager()) {
            stateFrameLayout = (StateFrameLayout) findViewById(R.id.base_state_layout);
            stateFrameLayout.setStateViews(bindLoadView(), bindErrorView(), bindEmptyView());
            stateFrameLayout.setOnStateClickListener(this);
        }
    }

    @Override
    public final int initLayout(Bundle savedInstanceState) {
        return R.layout.base_state;
    }

    protected void showBody() {
        ViewStub bodyViewStub = (ViewStub) findViewById(R.id.base_state_body);
        //如果这个布局已经被渲染出来，那么就会为空
        if (bodyViewStub != null) {
            bodyViewStub.setLayoutResource(bodyLayoutId);
            bodyViewStub.inflate();
            bodyLayoutId = 0;
        }
    }

    @Override
    public void onGetDataStart() {
    }

    @Override
    public void onGetDataStop() {
    }

    @Override
    public void getDataSuccess() {
        showContentView();
        if (bodyLayoutId != 0) {
            showBody();
        }
    }

    @Override
    public void getDataFail() {
        showErrorView();
    }

    @Override
    public void noData() {
        showErrorView();
    }

    protected void showLoadView() {
        if (stateFrameLayout != null)
            stateFrameLayout.loading();
    }

    protected void showErrorView() {
        if (stateFrameLayout != null)
            stateFrameLayout.error();

    }

    protected void showEmptyView() {
        if (stateFrameLayout != null)
            stateFrameLayout.empty();
    }

    protected void showContentView() {
        if (stateFrameLayout != null)
            stateFrameLayout.normal();
    }

    protected View bindLoadView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pager_loading, stateFrameLayout, false);
    }

    protected View bindEmptyView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pager_empty, stateFrameLayout, false);
    }

    protected View bindErrorView() {
        return LayoutInflater.from(mContext).inflate(R.layout.pager_error, stateFrameLayout, false);
    }

    /**
     * 是否开启界面处理,默认开启
     */
    protected boolean isOpenPageManager() {
        return true;
    }

    /**
     * 内容区域布局Id
     */
    public abstract
    @LayoutRes
    int bodyLayoutId(Bundle savedInstanceState);

    /**
     * 标题栏布局Id
     */
    public abstract
    @LayoutRes
    int titleLayoutId();

    @Override
    public void onNormalClick(StateFrameLayout layout) {
    }

    @Override
    public void onLoadingClick(StateFrameLayout layout) {

    }
}
