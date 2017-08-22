package cn.zengcanxiang.mvp_practice_project_template.core.meizhi.mvp;


import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BasePresent;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseView;

public interface MeizhiContract {
    public interface IMeizhiView extends BaseView {

    }

    public interface IMeizhiModel extends BaseModel {

    }

    public abstract class MeizhiPresent extends BasePresent<IMeizhiModel, IMeizhiView> {
    }
}
