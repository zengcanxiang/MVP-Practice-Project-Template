package cn.zengcanxiang.mvp_practice_project_template.core.home;

import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataView;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BasePresent;


public interface HomeContract {

    interface IHomeModel extends BaseModel {

    }

    interface IHomeView extends DataView {
    }

    abstract class HomePresent extends BasePresent<IHomeModel, IHomeView> {
    }

}
