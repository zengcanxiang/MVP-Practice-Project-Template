package cn.zengcanxiang.mvp_practice_project_template.base.helper;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;

public abstract class MultiTypeFragment<BP extends DataPresenter, BM extends BaseModel> extends StateFragment<BP, BM> {

    private LinearLayout magicIndicatorParent;
    private MagicIndicator magicIndicator;
    private CommonNavigator commonNavigator;
    private ViewPager mViewPager;
    private View cutOffRule;

    private MultiTypeAdapter multiTypeAdapter;
    private CommonNavigatorAdapter navigatorAdapter;

    private List<String> titleData = new ArrayList<>();
    private List<Fragment> viewPagerData = new ArrayList<>();
    private MultiTypeBuilder builder;

    @Override
    public int bodyLayoutId(Bundle savedInstanceState) {
        return R.layout.base_state_multi_type_body;
    }

    @Override
    public void initViews() {
        builder = builder();
        if (builder == null) {
            throw new IllegalArgumentException("builder 不能为空");
        }
        magicIndicator = (MagicIndicator) findViewById(R.id.base_title_magic_indicator);
        mViewPager = (ViewPager) findViewById(R.id.base_title_view_pager);
        magicIndicatorParent = (LinearLayout) findViewById(R.id.base_title_magic_parent);
        cutOffRule = findViewById(R.id.base_title_cut_off_rule);

        magicIndicatorParent.setBackgroundResource(builder.getMultiTypeBgColor());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                UIUtil.dip2px(mContext, builder.getTitleViewHeight()));
        layoutParams.setMargins(UIUtil.dip2px(mContext, builder.getTitleViewMarginsLeft()),
                UIUtil.dip2px(mContext, builder.getTitleViewMarginsTop()),
                UIUtil.dip2px(mContext, builder.getTitleViewMarginsRight()),
                UIUtil.dip2px(mContext, builder.getTitleViewMarginsBottom()));
        magicIndicator.setLayoutParams(layoutParams);
        magicIndicator.requestLayout();

        if (!builder.isShowCutOffRule()) {
            cutOffRule.setVisibility(View.GONE);
        }

        commonNavigator = new CommonNavigator(mContext);
        navigatorAdapter = new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return viewPagerData == null ? 0 : viewPagerData.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                //修改文本view
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titleData.get(index));
                simplePagerTitleView.setTextSize(builder.getTitleViewValueSize());
                simplePagerTitleView.setNormalColor(ContextCompat.getColor(mContext, builder.getMultiTypeNormalColor()));
                simplePagerTitleView.setSelectedColor(ContextCompat.getColor(mContext, builder.getMultiTypeSelectedColor()));

                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                //修改文本下面的Indicator
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setColors(ContextCompat.getColor(mContext, builder.getIndicatorColor()));
                return indicator;
            }
        };
    }

    @Override
    protected void showBody() {
        super.showBody();

        viewPagerData.addAll(bindFragment());
        titleData.addAll(bindTitleValue());

        multiTypeAdapter = new MultiTypeAdapter(getChildFragmentManager(), viewPagerData);
        mViewPager.setOffscreenPageLimit(builder.getScreenPageLimit());
        mViewPager.setAdapter(multiTypeAdapter);

        commonNavigator.setAdapter(navigatorAdapter);
        commonNavigator.setAdjustMode(builder.isAdjustMode());
        magicIndicator.setNavigator(commonNavigator);

        LinearLayout titleContainer = commonNavigator.getTitleContainer();
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(mContext, builder.getIntrinsicWidth());
            }
        });
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    @Override
    public void setViewsListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    /**
     * 绑定fragment集
     */
    protected abstract List<? extends Fragment> bindFragment();

    /**
     * 绑定头部标题数据
     */
    protected abstract List<String> bindTitleValue();

    /**
     * 自定义构造属性，可返回null，则使用默认属性
     */
    protected abstract MultiTypeBuilder builder();

    /**
     * 获取当前显示的fragment
     */
    public final Fragment getCurrentItem() {
        return multiTypeAdapter.getItem(mViewPager.getCurrentItem());
    }

    private class MultiTypeAdapter extends FragmentStatePagerAdapter {

        private List<Fragment> mFragments = new ArrayList<>();

        private MultiTypeAdapter(@NonNull FragmentManager fm, @NonNull List<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public final Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public final int getCount() {
            return mFragments.size();
        }
    }
}
