package cn.zengcanxiang.mvp_practice_project_template.core.home.mvp;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.base.helper.DFCallback;
import cn.zengcanxiang.mvp_practice_project_template.bean.ResultBean;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.Gank;
import cn.zengcanxiang.mvp_practice_project_template.bean.responses.ToDayDataBean;

import static cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment.MODEL_ANDROID;
import static cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment.MODEL_APP;
import static cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment.MODEL_IOS;
import static cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment.MODEL_前端;
import static cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment.MODEL_拓展资源;
import static cn.zengcanxiang.mvp_practice_project_template.core.home.HomeCategoryFragment.MODEL_瞎推荐;

public class HomePresenter extends HomeContract.HomePresent {


    private static HashMap<String, ResultBean<ToDayDataBean>> data = new HashMap<>();

    @Override
    public void getTodayBoon() {
        mModel.getLastTime(new DFCallback<List<String>>(mView, false) {
            @Override
            public void onSucceed(ResultBean<List<String>> resultBean) {
                List<String> resultInfo = resultBean.getResults();
                final String t = resultInfo.get(0);
                ((HomeContract.IHomeActivityView) mView).showDate(t);
                if (data.get(t) != null) {
                    mView.getDataSuccess();
                    mView.onGetDataStop();
                    showHomeActivityData(data.get(t));
                    return;
                }
                mModel.getTodayData(t, new DFCallback<ToDayDataBean>(mView) {
                    @Override
                    public void onSucceed(ResultBean<ToDayDataBean> resultBean) {
                        if (!resultBean.getResults().isEmpty()) {
                            data.put(t, resultBean);
                            showHomeActivityData(resultBean);
                        } else {
                            mView.noData();
                        }
                    }

                    @Override
                    public void onFail(int retrofitCode, ResultBean errorMsgBean) {
                        mView.getDataFail();
                    }
                });
            }

            @Override
            public void onFail(int retrofitCode, ResultBean errorMsgBean) {
                mView.getDataFail();
            }
        });
    }

    @Override
    public void byDateUpdateHome(final String date) {
        mModel.getTodayData(date, new DFCallback<ToDayDataBean>(mView) {
            @Override
            public void onSucceed(ResultBean<ToDayDataBean> resultBean) {
                if (!resultBean.getResults().isEmpty()) {
                    data.put(date, resultBean);
                    showHomeActivityData(resultBean);
                } else {
                    mView.noData();
                }
            }

            @Override
            public void onFail(int retrofitCode, ResultBean errorMsgBean) {
                mView.getDataFail();
            }
        });
    }

    @Override
    public void getListDataByCategory(String date, String category) {
        HomeContract.IHomeFragmentView view = (HomeContract.IHomeFragmentView) mView;
        ToDayDataBean bean = data.get(date).getResults();
        //因为会先请求福利数据，保存在data中，所以暂时不考虑为空的情况，按照道理来说，也不应该存在为空的情况
        if (bean != null) {
            mView.getDataSuccess();
            mView.onGetDataStop();
            List<Gank> ganks = byCategoryExtractData(category, bean);
            if (ganks != null) {
                view.showModelData(ganks);
            } else {
                view.noData();
            }
        }
    }

    /**
     * 根据分类提取数据
     */
    private List<Gank> byCategoryExtractData(String category, ToDayDataBean bean) {
        List<Gank> dataList = new ArrayList<>();
        switch (category) {
            case MODEL_ANDROID:
                dataList = bean.getAndroidList();
                break;
            case MODEL_IOS:
                dataList = bean.getiOSList();
                break;
            case MODEL_APP:
                dataList = bean.getAppList();
                break;
            case MODEL_前端:
                dataList = bean.getFrontEndList();
                break;
            case MODEL_拓展资源:
                dataList = bean.getResourcesList();
                break;
            case MODEL_瞎推荐:
                dataList = bean.getRecommendList();
                break;
        }
        return dataList;
    }

    private void showHomeActivityData(ResultBean<ToDayDataBean> resultBean) {
        ((HomeContract.IHomeActivityView) mView).initCategory(resultBean.getCategory());
        ((HomeContract.IHomeActivityView) mView).showBoonImg(resultBean.getResults().getBoonList());
        ((HomeContract.IHomeActivityView) mView).initPlay(resultBean.getResults().getRestVideo());
    }
}
