package cn.zengcanxiang.mvp_practice_project_template.base.helper.multitype;

import android.support.annotation.DrawableRes;

import cn.zengcanxiang.mvp_practice_project_template.R;


/**
 * 标题+viewPager 组合相关属性自由构建器
 */
public class MultiTypeBuilder {
    /**
     * 标题是否绝对平分
     */
    private boolean isAdjustMode = false;
    /**
     * 是否显示分割线
     */
    private boolean isShowCutOffRule = true;
    /**
     * 是否可以滑动
     */
    private boolean isScroll = true;
    /**
     * 分割线颜色
     */
    private int cutOffRuleColor;
    /**
     * 标题文字之间的距离
     */
    private int intrinsicWidth = 15;
    /**
     * 标题栏高度
     */
    private int titleViewHeight = 38;
    /**
     * 标题栏的margin距离
     */
    private int headViewMarginsLeft = 5, headViewMarginsRight = 0, headViewMarginsTop = 0, headViewMarginsBottom = 0;
    /**
     * 标题栏引导条颜色
     */
    private int indicatorColor = R.color.colorAccent;
    /**
     * 标题文字大小
     */
    private int headViewValueSize = 15;
    /**
     * 标题未选中颜色
     */
    private int multiTypeNormalColor;
    /**
     * 标题选中颜色
     */
    private int multiTypeSelectedColor = R.color.colorAccent;
    /**
     * 标题栏背景颜色
     */
    private int multiTypeBgColor;
    /**
     * 主体部分背景颜色
     */
    private int multiTypeBodyBgColor;
    /**
     * viewPager缓存页面个数
     */
    private int screenPageLimit = 1;

    public MultiTypeBuilder(int normalColor, int bgColor) {
        this.multiTypeNormalColor = normalColor;
        this.multiTypeBgColor = bgColor;
    }

    /**
     * 设置标题文字之间的距离
     *
     * @param width dpValue
     */
    public MultiTypeBuilder setIntrinsicWidth(int width) {
        this.intrinsicWidth = width;
        return this;
    }

    /**
     * 设置是否可以滑动
     */
    public MultiTypeBuilder isScroll(boolean isScroll) {
        this.isScroll = isScroll;
        return this;
    }

    /**
     * 设置是否平分屏幕(不超出屏幕)
     */
    public MultiTypeBuilder isAdjustMode(boolean isAdjustMode) {
        this.isAdjustMode = isAdjustMode;
        return this;
    }

    /**
     * 设置是否显示标题与下方的分割线
     */
    public MultiTypeBuilder isShowCutOffRule(boolean isShowCutOffRule) {
        this.isShowCutOffRule = isShowCutOffRule;
        return this;
    }

    /**
     * 设置分割线颜色
     *
     * @param cutOffRuleColor colorId
     */
    public MultiTypeBuilder setCutOffRuleColor(@DrawableRes int cutOffRuleColor) {
        this.cutOffRuleColor = cutOffRuleColor;
        return this;
    }

    /**
     * 设置标题下面的Indicator颜色
     *
     * @param indicatorColor colorId
     */
    public MultiTypeBuilder setIndicatorColor(@DrawableRes int indicatorColor) {
        this.indicatorColor = indicatorColor;
        return this;
    }

    /**
     * 设置标题栏高度
     *
     * @param titleViewHeight dpValue
     */
    public MultiTypeBuilder setTitleViewHeight(int titleViewHeight) {
        this.titleViewHeight = titleViewHeight;
        return this;
    }

    /**
     * 设置标题栏的margin距离
     */
    public MultiTypeBuilder setTitleViewMargins(int left, int top, int right, int bottom) {
        this.headViewMarginsLeft = left;
        this.headViewMarginsTop = top;
        this.headViewMarginsRight = right;
        this.headViewMarginsBottom = bottom;
        return this;
    }

    /**
     * 设置标题栏的文字大小
     */
    public MultiTypeBuilder setHeadViewValueSize(int headViewValueSize) {
        this.headViewValueSize = headViewValueSize;
        return this;
    }

    /**
     * 设置标题栏文字未选中颜色
     *
     * @param multiTypeNormalColor colorId
     */
    public MultiTypeBuilder setMultiTypeNormalColor(@DrawableRes int multiTypeNormalColor) {
        this.multiTypeNormalColor = multiTypeNormalColor;
        return this;
    }

    /**
     * 设置标题栏文字选中颜色
     *
     * @param multiTypeSelectedColor colorId
     */
    public MultiTypeBuilder setMultiTypeSelectedColor(@DrawableRes int multiTypeSelectedColor) {
        this.multiTypeSelectedColor = multiTypeSelectedColor;
        return this;
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param multiTypeBgColor colorId
     */
    public MultiTypeBuilder setMultiTypeBgColor(@DrawableRes int multiTypeBgColor) {
        this.multiTypeBgColor = multiTypeBgColor;
        return this;
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param multiTypeBodyBgColor colorId
     */
    public MultiTypeBuilder setMultiTypeBodyBgColor(@DrawableRes int multiTypeBodyBgColor) {
        this.multiTypeBodyBgColor = multiTypeBodyBgColor;
        return this;
    }

    /**
     * 设置viewPager缓存页面个数
     */
    public MultiTypeBuilder setScreenPageLimit(int screenPageLimit) {
        this.screenPageLimit = screenPageLimit;
        return this;
    }

    public boolean isScroll() {
        return isScroll;
    }

    public int getCutOffRuleColor() {
        return cutOffRuleColor;
    }

    public int getMultiTypeBodyBgColor() {
        return multiTypeBodyBgColor;
    }

    public boolean isAdjustMode() {
        return isAdjustMode;
    }

    public boolean isShowCutOffRule() {
        return isShowCutOffRule;
    }

    public int getIntrinsicWidth() {
        return intrinsicWidth;
    }

    public int getIndicatorColor() {
        return indicatorColor;
    }

    public int getTitleViewHeight() {
        return titleViewHeight;
    }

    public int getHeadViewValueSize() {
        return headViewValueSize;
    }

    public int getMultiTypeNormalColor() {
        return multiTypeNormalColor;
    }

    public int getMultiTypeSelectedColor() {
        return multiTypeSelectedColor;
    }

    public int getMultiTypeBgColor() {
        return multiTypeBgColor;
    }

    public int getHeadViewMarginsLeft() {
        return headViewMarginsLeft;
    }

    public int getHeadViewMarginsRight() {
        return headViewMarginsRight;
    }

    public int getHeadViewMarginsTop() {
        return headViewMarginsTop;
    }

    public int getHeadViewMarginsBottom() {
        return headViewMarginsBottom;
    }

    public int getScreenPageLimit() {
        return screenPageLimit;
    }

}
