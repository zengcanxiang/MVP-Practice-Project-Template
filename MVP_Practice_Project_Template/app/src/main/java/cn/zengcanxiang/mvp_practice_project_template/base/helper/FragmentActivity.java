package cn.zengcanxiang.mvp_practice_project_template.base.helper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.orhanobut.logger.Logger;

import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BaseModel;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.BasePresent;
import cn.zengcanxiang.mvp_practice_project_template.base.mvp.MVPBaseActivity;

/**
 * 提供简便的对fragment和Activity之间相互操作Activity，暂时不是很完善
 */
public abstract class FragmentActivity<BP extends BasePresent, BM extends BaseModel> extends MVPBaseActivity<BP, BM> {
    /**
     * 不要在该方法内初始化fragment
     */
    public abstract List<Fragment> bindFragment();

    /**
     * 切换fragment(隐藏)
     */
    protected final void checkHide(int viewId, int showIndex) {
        List<Fragment> allFragment = bindFragment();
        hideAllFrag(showIndex);
        FragmentTransaction b = getSupportFragmentManager().beginTransaction();
        if (showIndex >= allFragment.size()) {
            Logger.e("showIndex >= allFragment.size()：showIndex=" + showIndex + ",allFragment.size()=" + allFragment.size());
            return;
        }
        Fragment fragment = allFragment.get(showIndex);
        if (!fragment.isAdded()) {
            b.add(viewId, fragment);
        }
        b.show(fragment);
        b.commit();
    }

    protected final void checkReplac(int viewId, int showIndex) {
        List<Fragment> allFragment = bindFragment();
        FragmentTransaction b = getSupportFragmentManager().beginTransaction();
        if (showIndex >= allFragment.size()) {
            Logger.e("showIndex >= allFragment.size()：showIndex=" + showIndex + ",allFragment.size()=" + allFragment.size());
            return;
        }
        Fragment fragment = allFragment.get(showIndex);
        b.replace(viewId, fragment);
        b.commit();
    }

    /**
     * remove所有fragment
     */
    private FragmentTransaction removeAllFrag(int showIndex) {
        List<Fragment> allFragment = bindFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < allFragment.size(); i++) {
            if (i == showIndex) {
                Logger.e("showIndex >= allFragment.size()：showIndex=" + showIndex + ",allFragment.size()=" + allFragment.size());
                continue;
            }
            fragmentTransaction.remove(allFragment.get(i));
        }
        return fragmentTransaction;
    }

    /**
     * hide所有fragment
     */
    protected void hideAllFrag(int showIndex) {
        List<Fragment> fragments = bindFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == showIndex) {
                continue;
            }
            fragmentTransaction.hide(fragments.get(i));
        }
        fragmentTransaction.commit();
    }


    @Override
    public boolean isImmersive() {
        return false;
    }
}
