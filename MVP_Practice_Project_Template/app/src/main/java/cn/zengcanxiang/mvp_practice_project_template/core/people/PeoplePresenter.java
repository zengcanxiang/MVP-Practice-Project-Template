package cn.zengcanxiang.mvp_practice_project_template.core.people;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PeoplePresenter extends PeopleContract.PeoplePresent {

    @Override
    void getCollectionList() {
        mView.getDataSuccess();
        mView.onGetDataStop();
        List<String> s = new ArrayList<>();
        int size=new Random().nextInt(7);
        for(int i=0;i<size;i++){
            s.add("");
        }
        mView.updateC(s);
//        mModel.getCollectionList(new DFCallback<List<String>>(mView) {
//            @Override
//            public void onSucceed(ResultBean<List<String>> resultBean) {
//                mView.updateC(resultBean.getData());
//            }
//
//            @Override
//            public void onFail(int code, ResultBean errorMsgBean) {
//
//            }
//        });
    }

    @Override
    void getHistoryList() {
        mView.onGetDataStop();
        mView.getDataSuccess();
        List<String> s = new ArrayList<>();
        int size=new Random().nextInt(7);
        for(int i=0;i<size;i++){
            s.add("");
        }
        mView.updateH(s);
//        mModel.getHistoryList(new DFCallback<List<String>>(mView) {
//            @Override
//            public void onSucceed(ResultBean<List<String>> resultBean) {
//                mView.updateH(resultBean.getData());
//            }
//
//            @Override
//            public void onFail(int code, ResultBean errorMsgBean) {
//
//            }
//        });
    }

    @Override
    void getDownloadList() {
        mView.onGetDataStop();
        mView.getDataSuccess();
        List<String> s = new ArrayList<>();
        int size=new Random().nextInt(7);
        for(int i=0;i<size;i++){
            s.add("");
        }
        mView.updateD(s);
//        mModel.getDownloadList(new DFCallback<List<String>>(mView) {
//            @Override
//            public void onSucceed(ResultBean<List<String>> resultBean) {
//                mView.updateD(resultBean.getData());
//            }
//
//            @Override
//            public void onFail(int code, ResultBean errorMsgBean) {
//
//            }
//        });
    }
}
