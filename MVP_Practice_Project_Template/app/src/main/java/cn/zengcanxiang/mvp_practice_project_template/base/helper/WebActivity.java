package cn.zengcanxiang.mvp_practice_project_template.base.helper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.just.library.AgentWeb;
import com.just.library.ChromeClientCallbackManager;
import com.orhanobut.logger.Logger;

import cn.zengcanxiang.mvp_practice_project_template.R;

@SuppressWarnings("all")
public class WebActivity extends AppCompatActivity {
    private LinearLayout mLinearLayout;
    private AgentWeb mAgentWeb;
    private ChromeClientCallbackManager.ReceivedTitleCallback mCallback = new ChromeClientCallbackManager.ReceivedTitleCallback() {
        @Override
        public void onReceivedTitle(WebView view, String title) {
            Logger.d(title);
        }
    };
    private String mUrl;
    private String mValue;
    private String mValueMimeType = "text/html";

    public static final String KEY_WEB_URL = "url";
    public static final String KEY_WEB_VALUE = "values";
    public static final String KEY_WEB_VALUE_MIME = "mime";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_web);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(KEY_WEB_URL);
        mValue = intent.getStringExtra(KEY_WEB_VALUE);
        if (TextUtils.isEmpty(mUrl)) {
            mUrl = "";
        }
        mLinearLayout = (LinearLayout) findViewById(R.id.base_web_parent);
        mAgentWeb = AgentWeb.with(this)
                //传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .defaultProgressBarColor() // 使用默认进度条颜色
                .setReceivedTitleCallback(mCallback) //设置 Web 页面的 title 回调
                .createAgentWeb()//
                .ready()
                .go(mUrl);
        //如果url为空，并且value有值，就加载内容
        if (TextUtils.isEmpty(mUrl) && !TextUtils.isEmpty(mValue)) {
            mValueMimeType = intent.getStringExtra(KEY_WEB_VALUE_MIME);
            mAgentWeb.getLoader().loadData(mValue, mValueMimeType, "UTF-8");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWeb.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }
}
