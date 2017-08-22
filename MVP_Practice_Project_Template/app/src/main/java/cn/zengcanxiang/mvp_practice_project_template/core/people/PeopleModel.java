package cn.zengcanxiang.mvp_practice_project_template.core.people;


import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.bean.responses.ResultBean;
import retrofit2.Callback;

public class PeopleModel implements PeopleContract.IPeopleModel {

    @Override
    public void cancel() {

    }

    @Override
    public void getCollectionList(Callback<ResultBean<List<String>>> callback) {

    }

    @Override
    public void getHistoryList(Callback<ResultBean<List<String>>> callback) {

    }

    @Override
    public void getDownloadList(Callback<ResultBean<List<String>>> callback) {

    }
}
