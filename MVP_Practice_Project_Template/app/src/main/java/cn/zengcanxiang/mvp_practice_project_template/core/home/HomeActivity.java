package cn.zengcanxiang.mvp_practice_project_template.core.home;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.adapter.FragmentAdapter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.WebActivity;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.MVPBaseActivity;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.Gank;
import cn.zengcanxiang.mvp_practice_project_template.core.home.mvp.HomeContract;
import cn.zengcanxiang.mvp_practice_project_template.core.home.mvp.HomeModel;
import cn.zengcanxiang.mvp_practice_project_template.core.home.mvp.HomePresenter;
import cn.zengcanxiang.mvp_practice_project_template.core.meizhi.MeizhiImgActivity;
import cn.zengcanxiang.mvp_practice_project_template.core.meizhi.ShareElement;
import cn.zengcanxiang.mvp_practice_project_template.util.ToastUtils;


public class HomeActivity extends MVPBaseActivity<HomePresenter, HomeModel>
        implements HomeContract.IHomeActivityView {

    private ArrayList<HomeCategoryFragment> list = new ArrayList<>();
    private String showDate;

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mBanner;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private AppBarLayout appBarLayout;
    private String showImgUrl;
    private FloatingActionButton fab;
    private TextView title;
    public DatePickerDialog datePickerDialog;
    private int selectedMonth;
    private int selectedYear;
    private int selectedDay;

    @Override
    public int initLayout(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public void disposeBusiness() {
        mPresenter.getTodayBoon();
    }

    @Override
    public void initViews() {
        mViewPager = (ViewPager) findViewById(R.id.vp);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarlayout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mBanner = (ImageView) findViewById(R.id.banner);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        title = (TextView) findViewById(R.id.title);

        //解决viewpager在NestedScrollView中不显示已经不能滑动的问题
        NestedScrollView scrollView = (NestedScrollView) findViewById(R.id.nest_scrollview);
        scrollView.setFillViewport(true);
    }

    @Override
    public void setViewsListener() {
        mBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareElement.shareDrawable = mBanner.getDrawable();
                Intent intent = new Intent(mContext, MeizhiImgActivity.class);
                intent.putExtra("url", showImgUrl);
                intent.putExtra("title", showDate);
                ActivityOptionsCompat compat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(mContext, mBanner, mContext.getResources().getString(R.string.girl));
                ActivityCompat.startActivity(mContext, intent, compat.toBundle());
            }
        });
        title.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showDatePicker(new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = year + "-" + monthOfYear + "-" + dayOfMonth;
                        showDate(date);
                        mPresenter.byDateUpdateHome(date);
                    }
                });
            }
        });
    }

    @Override
    public void showDate(String date) {
        showDate = date;
        title.setText(date);
        String[] t = date.split("-");
        this.selectedYear = Integer.parseInt(t[0]);
        this.selectedMonth = Integer.parseInt(t[1]);
        this.selectedDay = Integer.parseInt(t[2]);
    }

    @Override
    public void initCategory(List<String> category) {
        list.clear();
        for (String c : category) {
            if (c.equals("休息视频") || c.equals("福利")) {
                continue;
            }
            list.add(HomeCategoryFragment.newInstance(c));
        }
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);

        if (category.size() - 2 > 4) {
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        } else {
            mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        }
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void initPlay(final List<Gank> videos) {
        if (videos.size() > 1) {
            ToastUtils.show(mContext, "暂时只支持播放一个休息视频");
        }
        if (videos == null || videos.isEmpty()) {
            return;
        }
        ToastUtils.show(mContext, "今日的休息视频为:\n" + videos.get(0).getDesc());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, WebActivity.class);
                i.putExtra(WebActivity.KEY_WEB_URL, videos.get(0).getUrl());
                startActivity(i);
            }
        });
    }

    @Override
    public void showBoonImg(List<Gank> ganks) {
        if (ganks.size() > 1) {
            ToastUtils.show(mContext, "今天有多张妹子福利哦,但是我并没有做显示~");
        }
        showImgUrl = ganks.get(0).getUrl();
        Glide.with(mContext).load(showImgUrl + "?imageView2/0/w/" + mBanner.getWidth()).into(mBanner);
    }

    @Override
    public void onGetDataStart() {

    }

    @Override
    public void onGetDataStop() {

    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFail() {
        ToastUtils.show(mContext, "暂时获取不到gankIo数据，稍后再试？");
    }

    @Override
    public void noData() {
        ToastUtils.show(mContext, "所选日期没有数据~");
        showDatePicker(new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "-" + monthOfYear + "-" + dayOfMonth;
                showDate(date);
                mPresenter.byDateUpdateHome(date);
            }
        });
    }

    public String getShowDate() {
        return showDate;
    }

    private void showDatePicker(DatePickerDialog.OnDateSetListener listener) {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(mContext, listener,
                    selectedYear, selectedMonth, selectedDay);
        }
        datePickerDialog.show();
    }
}
