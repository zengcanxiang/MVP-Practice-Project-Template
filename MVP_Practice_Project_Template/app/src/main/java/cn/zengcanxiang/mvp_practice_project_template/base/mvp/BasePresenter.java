package cn.zengcanxiang.mvp_practice_project_template.base.mvp;


public abstract class BasePresenter<BM extends BaseModel, BV extends BaseView> {
    protected BM mModel;
    protected BV mView;

    /**
     * @param v BaseView的实现,实际指activity
     * @param m BaseModel的实现
     */
    void setVM(BV v, BM m) {
        this.mView = v;
        this.mModel = m;
    }

}
