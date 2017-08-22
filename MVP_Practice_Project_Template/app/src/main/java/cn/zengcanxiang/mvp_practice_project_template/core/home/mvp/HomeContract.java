package cn.zengcanxiang.mvp_practice_project_template.core.home.mvp;


import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.base.helper.DFCallback;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataPresenter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataView;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.Gank;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.ToDayDataBean;

public interface HomeContract {

    interface IHomeModel extends BaseModel {
        void getTodayData(String date, DFCallback<ToDayDataBean> callback);

        /**
         * 获取最后一次有数据的日期
         */
        void getLastTime(DFCallback<List<String>> callback);
    }

    interface IHomeView extends DataView {
    }

    interface IHomeActivityView extends IHomeView {
        void showBoonImg(List<Gank> ganks);

        /**
         * 显示日期
         */
        void showDate(String date);

        void initCategory(List<String> category);

        void initPlay(List<Gank> videos);
    }

    interface IHomeFragmentView extends IHomeView {

        void showModelData(List<Gank> data);

    }

    abstract class HomePresent extends DataPresenter<IHomeModel, IHomeView> {
        /**
         * 获取当天的福利数据
         */
        public abstract void getTodayBoon();

        /**
         * 根据分类获取列表数据
         */
        public abstract void getListDataByCategory(String date, String category);

        /**
         * 根据日期更新数据
         */
        public abstract void byDateUpdateHome(String date);
    }
}
