package cn.zengcanxiang.mvp_practice_project_template.base.mvp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelin.translucentbar.library.TranslucentBarManager;

import cn.zengcanxiang.mvp_practice_project_template.BuildConfig;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.UIWithCode;


public abstract class MVPBaseFragment<BP extends BasePresent, BM extends BaseModel> extends Fragment
        implements BaseView, UIWithCode {
    public BP mPresenter;
    protected AppCompatActivity mContext;
    protected View mContentView;
    protected String TAG;
    protected Application app;

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = ClassUtil.getT(this, 0);
        if (mPresenter != null) {
            BM mModel = ClassUtil.getT(this, 1);
            mPresenter.setVM(this, mModel);
        }
        app = mContext.getApplication();
        TAG = getClass().getSimpleName();
        if (mContentView == null) {
            int layoutId = initLayout(savedInstanceState);
            if (layoutId != 0) {
                mContentView = inflater.inflate(layoutId, container, false);
            }
        } else {
            ViewGroup parent = (ViewGroup) mContentView.getParent();
            if (parent != null) {
                parent.removeView(mContentView);
            }
        }
        if (isImmersed()) {
            TranslucentBarManager barManager = new TranslucentBarManager(this);
            barManager.transparent(this, mContentView, R.color.colorPrimary);

        }
        return mContentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (AppCompatActivity) context;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initActivityWritCode();
    }

    @Override
    public void initActivityWritCode() {
        initViews();
        disposeBusiness();
        setViewsListener();
    }

    /**
     * 跳转Activity
     */
    protected void startActivity(Class clz) {
        Intent intent = new Intent(mContext, clz);
        startActivity(intent);
    }

    protected View findViewById(@IdRes int id) {
        View v = mContentView.findViewById(id);
        //如果在release下，findView为空，则进行处理
        if (v == null && !BuildConfig.DEBUG) {
            v = new View(mContext);
        }
        return v;
    }

    public boolean isImmersed() {
        return false;
    }
}
