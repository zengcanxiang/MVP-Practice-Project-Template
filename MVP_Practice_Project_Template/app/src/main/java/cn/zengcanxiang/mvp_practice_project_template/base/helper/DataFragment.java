package cn.zengcanxiang.mvp_practice_project_template.base.helper;

import android.app.Dialog;

import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.MVPBaseFragment;
import cn.zengcanxiang.mvp_practice_project_template.util.ToastUtils;
import dmax.dialog.SpotsDialog;

/**
 * 提供默认的数据获取等待dialog
 */
public abstract class DataFragment<BP extends DataPresenter, BM extends BaseModel> extends MVPBaseFragment<BP, BM>
        implements DataView {
    private Dialog mDialog;

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onGetDataStart() {
        if (mDialog == null) {
            mDialog = new SpotsDialog(mContext, "请稍候...");
        }
        if (!mDialog.isShowing())
            mDialog.show();
    }

    @Override
    public void onGetDataStop() {
        if (mDialog != null)
            mDialog.dismiss();
    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFail() {
        ToastUtils.show(mContext, "服务器忙,请稍后再试!");
    }

    @Override
    public void noData() {
        ToastUtils.show(mContext, "设备无网络,请检查网络连接!");
    }
}
