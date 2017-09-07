package cn.zengcanxiang.mvp_practice_project_template.base.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kelin.translucentbar.library.TranslucentBarManager;

import cn.zengcanxiang.mvp_practice_project_template.BuildConfig;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.UIWithCode;
import cn.zengcanxiang.mvp_practice_project_template.util.AppManager;
import cn.zengcanxiang.mvp_practice_project_template.util.KeyboardUtils;

/**
 * baseActivity
 */
public abstract class MVPBaseActivity<BP extends BasePresent, BM extends BaseModel> extends AppCompatActivity
        implements BaseView, UIWithCode {
    public BP mPresenter;
    protected Activity mContext;
    protected String TAG;
    protected Application app;

    /**
     * 过滤快速点击屏幕的事件
     */
    private static long lastClickTime;

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
        app = getApplication();
        mContext = this;
        mPresenter = ClassUtil.getT(this, 0);
        if (mPresenter != null) {
            BM mModel = ClassUtil.getT(this, 1);
            mPresenter.setVM(this, mModel);
        }
        //强制不横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppManager.getAppManager().addActivity(this);
        int layoutId = initLayout(savedInstanceState);
        if (layoutId != 0) {
            setContentView(layoutId);
            if (isImmersed()) {
                TranslucentBarManager barManager = new TranslucentBarManager(this);
                barManager.transparent(this, R.color.colorPrimary);
            }
        }
        initActivityWritCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    public void initActivityWritCode() {
        initViews();
        disposeBusiness();
        setViewsListener();
    }

    @Override
    public View findViewById(@IdRes int id) {
        View v = super.findViewById(id);
        //如果在release下，findView为空，则进行处理
        if (v == null && !BuildConfig.DEBUG) {
            v = new View(mContext);
        }
        return v;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            //点击空白处隐藏键盘
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            //防止快速的连续点击同一个按钮
            if (isFastClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    protected final void title(@StringRes int titleValueStrId) {
        title(getString(titleValueStrId));
    }

    @SuppressWarnings("all")
    protected final void title(String titleValue) {
        TextView title = (TextView) findViewById(R.id.ig_title);
        title.setText(titleValue);
    }

    protected final void menu(@StringRes int menuValueStrId) {
        menu(getString(menuValueStrId));
    }

    @SuppressWarnings("all")
    protected final void menu(String menuValue) {
        TextView title = (TextView) findViewById(R.id.title_menu);
        title.setText(menuValue);
    }

    @SuppressWarnings("all")
    protected final void backLick(@IdRes int backViewId) {
        final View backView = findViewById(backViewId);
        backView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(mContext);
                backView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 50);
            }
        });
    }

    protected final void backLick() {
        backLick(R.id.title_back);
    }

    @SuppressWarnings("all")
    protected final void visibilityView(@IdRes int viewId, int visibility) {
        findViewById(viewId).setVisibility(visibility);
    }

    protected final void hideBack(boolean isHide) {
        visibilityView(R.id.title_back, isHide ? View.GONE : View.VISIBLE);
    }

    protected final void hideBack(@IdRes int backId, boolean isHide) {
        visibilityView(backId, isHide ? View.GONE : View.VISIBLE);
    }

    protected final void hideMenu(boolean isHide) {
        visibilityView(R.id.title_menu, isHide ? View.GONE : View.VISIBLE);
    }

    protected final void hideMenu(@IdRes int menuId, boolean isHide) {
        visibilityView(menuId, isHide ? View.GONE : View.VISIBLE);
    }

    public final int getColorByRes(@ColorRes int colorId) {
        return ContextCompat.getColor(mContext, colorId);
    }

    public final String format(@StringRes int strId, Object... value) {
        String result;
        try {
            result = String.format(getString(strId), value);
        } catch (Exception e) {
            result = "";
        }
        return result;
    }

    /**
     * 跳转Activity
     */
    protected void startActivity(Class clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    public boolean isImmersed() {
        return true;
    }
}