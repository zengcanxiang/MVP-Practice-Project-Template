package cn.zengcanxiang.mvp_practice_project_template.base.helper.state;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;

import am.widget.stateframelayout.StateFrameLayout;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataActivity;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataPresenter;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;

public abstract class StateActivity<BP extends DataPresenter, BM extends BaseModel> extends DataActivity<BP, BM>
        implements StateFrameLayout.OnAllStateClickListener, StateInterface {

    private int bodyLayoutId;
    private StateFrameLayout stateFrameLayout;
    protected AppBarLayout appBar;

    @Override
    public final void initActivityWritCode() {
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bodyLayoutId = bindContentLayoutId(savedInstanceState);
        initHeadView();
        initState();
        disposeBusiness();
    }

    private void initHeadView() {
        int headLayoutId = bindHeadLayoutId();
        appBar = (AppBarLayout) findViewById(R.id.app_bar);
        ViewStub headViewStub = (ViewStub) findViewById(R.id.base_state_head);
        if (headLayoutId != 0 && headViewStub != null) {
            appBar.setVisibility(View.VISIBLE);
            headViewStub.setLayoutResource(headLayoutId);
            headViewStub.inflate();
        }
    }

    private void initState() {
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
        initViews();
        setViewsListener();
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

    @Override
    public boolean isOpenPageManager() {
        return true;
    }

    @Override
    public void onNormalClick(StateFrameLayout layout) {
    }

    @Override
    public void onLoadingClick(StateFrameLayout layout) {
    }
}
