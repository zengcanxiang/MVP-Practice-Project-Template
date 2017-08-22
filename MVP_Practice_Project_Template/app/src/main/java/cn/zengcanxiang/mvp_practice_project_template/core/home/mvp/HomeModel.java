package cn.zengcanxiang.mvp_practice_project_template.core.home.mvp;


import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.api.ApiService;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.DFCallback;
import cn.zengcanxiang.mvp_practice_project_template.bean.ResultBean;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.ToDayDataBean;
import retrofit2.Call;

public class HomeModel implements HomeContract.IHomeModel {
    private Call<ResultBean<ToDayDataBean>> resultBeanCall;
    private Call<ResultBean<List<String>>> history;

    @Override
    public void cancel() {
        if (resultBeanCall != null) {
            resultBeanCall.cancel();
        }
        if (history != null) {
            history.cancel();
        }
    }

    @Override
    public void getTodayData(String time, DFCallback<ToDayDataBean> callback) {
        String[] split = time.split("-");
        resultBeanCall = ApiService.getInstance().dayData(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        resultBeanCall.enqueue(callback);
    }

    @Override
    public void getLastTime(DFCallback<List<String>> callback) {
        history = ApiService.getInstance().history();
        history.enqueue(callback);
    }
}
