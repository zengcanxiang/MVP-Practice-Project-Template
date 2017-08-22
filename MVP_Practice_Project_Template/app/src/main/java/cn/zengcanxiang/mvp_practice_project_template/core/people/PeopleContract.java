package cn.zengcanxiang.mvp_practice_project_template.core.people;


import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataPresenter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataView;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.ResultBean;
import retrofit2.Callback;

public interface PeopleContract {

    interface IPeopleModel extends BaseModel {
        /**
         * 获取收藏列表
         */
        void getCollectionList(Callback<ResultBean<List<String>>> callback);

        /**
         * 获取浏览历史列表
         */
        void getHistoryList(Callback<ResultBean<List<String>>> callback);

        /**
         * 获取下载列表列表
         */
        void getDownloadList(Callback<ResultBean<List<String>>> callback);
    }

    interface IPeopleView extends DataView {
        void updateC(List<String> s);

        void updateH(List<String> s);

        void updateD(List<String> s);
    }

    abstract class PeoplePresent extends DataPresenter<IPeopleModel, IPeopleView> {

        /**
         * 获取收藏列表
         */
        abstract void getCollectionList();

        /**
         * 获取浏览历史列表
         */
        abstract void getHistoryList();

        /**
         * 获取下载列表列表
         */
        abstract void getDownloadList();
    }
}
