package cn.zengcanxiang.mvp_practice_project_template.base.mvp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.kelin.translucentbar.library.TranslucentBarManager;

import cn.zengcanxiang.mvp_practice_project_template.BuildConfig;
import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.UIWithCode;
import cn.zengcanxiang.mvp_practice_project_template.util.AppManager;

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
            //点击坐标是否在当前焦点的view的之外
            if (isShouldHideKeyboard(v, ev)) {
                //是否是点击不隐藏输入法的view
                if (isTouchView(filterViewByIds(), ev)) {
                    return super.dispatchTouchEvent(ev);
                }
                //是否有需要处理的edit_view
                if (hideSoftByEditViewIds() == null || hideSoftByEditViewIds().length == 0) {
                    //没有需要处理的view，但是上面判断出点击是在当前焦点view之外，也就是点击空白处，隐藏输入法
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    v.clearFocus();
                    return super.dispatchTouchEvent(ev);
                }
                if (isFocusEditText(v, hideSoftByEditViewIds())) {
                    //如果是,判断是否是点击其他不隐藏输入法的edit_view
                    if (isTouchView(hideSoftByEditViewIds(), ev)) {
                        return super.dispatchTouchEvent(ev);
                    } else {
                        //如果不是,隐藏输入法
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        v.clearFocus();
                    }
                }
            }
            //防止快速的连续点击同一个按钮
            if (isFastClick()) {
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 是否触摸在要处理的editText
     */
    private boolean isFocusEditText(View v, int... ids) {
        if (v instanceof EditText) {
            EditText tmp_et = (EditText) v;
            for (int id : ids) {
                if (tmp_et.getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否触摸在过滤的view上面
     */
    private boolean isTouchView(View[] views, MotionEvent ev) {
        //如果点击的坐标不是需要处理的，就不管
        if (views == null || views.length == 0) return false;
        int[] location = new int[2];
        for (View view : views) {
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否触摸在过滤的view上面
     */
    private boolean isTouchView(int[] ids, MotionEvent ev) {
        int[] location = new int[2];
        for (int id : ids) {
            View view = findViewById(id);
            if (view == null) continue;
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getX() > x && ev.getX() < (x + view.getWidth())
                    && ev.getY() > y && ev.getY() < (y + view.getHeight())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 传入EditText的Id
     * 没有传入的EditText不做处理
     *
     * @return id 数组
     */
    protected int[] hideSoftByEditViewIds() {
        return null;
    }

    /**
     * 传入要过滤的View
     * 过滤之后点击将不会有隐藏软键盘的操作
     *
     * @return id 数组
     */
    protected View[] filterViewByIds() {
        return null;
    }

    /**
     * 处理短时间多次点击
     */
    private synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
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

    public boolean isImmersed() {
        return true;
    }
}