package cn.zengcanxiang.mvp_practice_project_template.base.helper;


import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.net.UnknownHostException;

import cn.zengcanxiang.mvp_practice_project_template.BuildConfig;
import cn.zengcanxiang.mvp_practice_project_template.bean.ResultBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 对网络请求回调与View之间自动化关联进行封装
 */
public abstract class DFCallback<T> implements Callback<ResultBean<T>> {

    private DataView mView;
    private boolean mIsHandleView = true;

    public DFCallback(DataView view) {
        mView = view;
        mView.onGetDataStart();
    }

    public DFCallback(DataView view, boolean isHandleView) {
        mView = view;
        mIsHandleView = isHandleView;
        if (mIsHandleView) {
            mView.onGetDataStart();
        }

    }

    @Override
    public void onResponse(Call<ResultBean<T>> call, Response<ResultBean<T>> response) {
        if (mIsHandleView) {
            mView.getDataSuccess();
        }
        if (response.code() == 200) {
            onSucceed(response.body());
        } else {
            try {
                ResultBean resultBean = JSONObject.parseObject(response.errorBody().string(), ResultBean.class);
                onFail(response.code(), resultBean);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (mIsHandleView) {
            mView.onGetDataStop();
        }
    }

    @Override
    public void onFailure(Call<ResultBean<T>> call, Throwable t) {
        if (call.isCanceled()) {
            return;
        }
        if (BuildConfig.DEBUG) {
            t.printStackTrace();
        }
        if (t instanceof UnknownHostException) {
            if (mIsHandleView) {
                mView.noData();
            }
        } else {
            if (mIsHandleView) {
                mView.getDataFail();
            }
        }
        if (mIsHandleView) {
            mView.onGetDataStop();
        }
    }

    /**
     * 有响应，请求是成功的，有正确的业务数据返回
     */
    public abstract void onSucceed(ResultBean<T> resultBean);

    /**
     * 有响应，请求到达服务器，但是服务器认为这次请求是不正确的，返回相应的错误信息
     *
     * @param retrofitCode Retrofit errBody里的code
     */
    public abstract void onFail(int retrofitCode, ResultBean errorMsgBean);

}
