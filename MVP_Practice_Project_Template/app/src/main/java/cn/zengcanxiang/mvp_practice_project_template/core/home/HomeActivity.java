package cn.zengcanxiang.mvp_practice_project_template.core.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import cn.zengcanxiang.mvp_practice_project_template.R;
import cn.zengcanxiang.mvp_practice_project_template.base.helper.FragmentActivity;
import cn.zengcanxiang.mvp_practice_project_template.core.comics.ComicsFragment;
import cn.zengcanxiang.mvp_practice_project_template.core.people.PeopleMainFragment;
import cn.zengcanxiang.mvp_practice_project_template.core.setup.SetupFragment;
import me.majiajie.pagerbottomtabstrip.MaterialMode;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageBottomTabLayout;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;


public class HomeActivity extends FragmentActivity<HomePresenter, HomeModel> implements HomeContract.IHomeView {
    private PageBottomTabLayout bottomTabLayout;
    private NavigationController controller;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    public int initLayout(Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    @Override
    public void disposeBusiness() {
        fragments.add(new ComicsFragment());
        fragments.add(new PeopleMainFragment());
        fragments.add(new SetupFragment());
        checkReplac(R.id.home_mid, 0);
    }

    @Override
    public void initViews() {
        int[] testColors = {0xFF455A64, 0xFF00796B, 0xFF795548};
        bottomTabLayout = (PageBottomTabLayout) findViewById(R.id.tab);
        controller = bottomTabLayout.material()
                .addItem(R.drawable.icon_bottom_data, "漫画", testColors[2])
                .addItem(R.drawable.icon_bottom_people, "萌新", testColors[0])
                .addItem(R.drawable.icon_bottom_setup, "设置", testColors[1])
                .setDefaultColor(0x89FFFFFF)//未选中状态的颜色
                .setMode(MaterialMode.CHANGE_BACKGROUND_COLOR | MaterialMode.HIDE_TEXT)
                .build();
    }

    @Override
    public void setViewsListener() {
        controller.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                checkReplac(R.id.home_mid, index);
            }

            @Override
            public void onRepeat(int index) {

            }
        });
    }

    @Override
    public List<Fragment> bindFragment() {
        return fragments;
    }

    @Override
    public void onGetDataStart() {

    }

    @Override
    public void onGetDataStop() {

    }

    @Override
    public void getDataSuccess() {

    }

    @Override
    public void getDataFail() {

    }

    @Override
    public void noData() {

    }

    @Override
    public boolean isImmersive() {
        return false;
    }
}
