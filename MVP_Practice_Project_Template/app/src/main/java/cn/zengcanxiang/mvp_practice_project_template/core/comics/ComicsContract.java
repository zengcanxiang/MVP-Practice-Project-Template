package cn.zengcanxiang.mvp_practice_project_template.core.comics;


import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataPresenter;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DataView;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;

public interface ComicsContract {
    interface IComicsModel extends BaseModel {
        /**
         * 获取继续阅读列表
         *
         * @param listSize 列表长度
         */
        void getContinueReadRecord(int listSize);

        /**
         * 获取排行数据列表
         */
        void getRankingList();

        /**
         * 获取轮播
         */
        void getRotation();
    }

    interface IComicsView extends DataView {

        /**
         * 显示继续阅读
         */
        void showContinueReadRecord(List mData);

        /**
         * 显示排行数据列表
         */
        void showRankingList(List mData);

        /**
         * 显示轮播
         */
        void showRotation(List mData);
    }

    abstract class ComicsPresent extends DataPresenter<IComicsModel, IComicsView> {
        /**
         * 获取轮播数据
         */
        public abstract void getRotation();

        /**
         * 获取继续阅读列表
         */
        public abstract void getContinueReadRecordData();

        /**
         * 获取排行数据
         */
        public abstract void getRankingData();

    }
}
