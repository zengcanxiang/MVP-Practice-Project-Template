package cn.zengcanxiang.mvp_practice_project_template.base.helper;

import android.support.annotation.DrawableRes;

import cn.zengcanxiang.mvp_practice_project_template.R;


/**
 * 标题+viewPager 组合相关属性自由构建器
 */
public class MultiTypeBuilder {

    private boolean isAdjustMode = false;
    private boolean isShowCutOffRule = true;
    private int intrinsicWidth = 15;
    private int titleViewHeight = 38;
    private int titleViewMarginsLeft = 5, titleViewMarginsRight = 0, titleViewMarginsTop = 0, titleViewMarginsBottom = 0;
    private int indicatorColor = R.color.colorAccent;
    private int titleViewValueSize = 15;
    private int multiTypeNormalColor;
    private int multiTypeSelectedColor = R.color.colorAccent;
    private int multiTypeBgColor;
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
     * 设置是否平分屏幕(不超出屏幕)
     */
    public MultiTypeBuilder isAdjustMode(boolean isAdjustMode) {
        this.isAdjustMode = isAdjustMode;
        return this;
    }

    /**
     * 是否显示标题与下方的分割线
     */
    public MultiTypeBuilder isShowCutOffRule(boolean isShowCutOffRule) {
        this.isShowCutOffRule = isShowCutOffRule;
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
        this.titleViewMarginsLeft = left;
        this.titleViewMarginsTop = top;
        this.titleViewMarginsRight = right;
        this.titleViewMarginsBottom = bottom;
        return this;
    }

    /**
     * 设置标题栏的文字大小
     */
    public MultiTypeBuilder setTitleViewValueSize(int titleViewValueSize) {
        this.titleViewValueSize = titleViewValueSize;
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
     * 设置viewPager缓存页面个数
     */
    public MultiTypeBuilder setScreenPageLimit(int screenPageLimit) {
        this.screenPageLimit = screenPageLimit;
        return this;
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

    public int getTitleViewValueSize() {
        return titleViewValueSize;
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

    public int getTitleViewMarginsLeft() {
        return titleViewMarginsLeft;
    }

    public int getTitleViewMarginsRight() {
        return titleViewMarginsRight;
    }

    public int getTitleViewMarginsTop() {
        return titleViewMarginsTop;
    }

    public int getTitleViewMarginsBottom() {
        return titleViewMarginsBottom;
    }

    public int getScreenPageLimit() {
        return screenPageLimit;
    }

}
