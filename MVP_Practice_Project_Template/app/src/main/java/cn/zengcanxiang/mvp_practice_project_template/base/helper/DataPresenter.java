package cn.zengcanxiang.mvp_practice_project_template.base.helper;


import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BasePresenter;

public abstract class DataPresenter<BM extends BaseModel, BV extends DataView> extends BasePresenter<BM, BV> {

    public void onDestroy() {
        mModel.cancel();
    }

}
