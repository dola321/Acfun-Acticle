package zmoriga.com.acticle.ui.main.fragment;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import zmoriga.com.acticle.R;
import zmoriga.com.acticle.app.AppApplication;
import zmoriga.com.acticle.ui.main.contract.MainContract;
import zmoriga.com.acticle.ui.main.model.MainModel;
import zmoriga.com.acticle.ui.main.presenter.MainPresenter;
import zmoriga.com.acticle.ui.title.TitleFragment;
import zmoriga.com.common.base.BaseFragment;
import zmoriga.com.common.base.BaseFragmentAdapter;

/**
 *
 */

public class MainFragment extends BaseFragment<MainPresenter,MainModel> implements MainContract.View {

    @Bind(R.id.tabs)
    TabLayout tabs;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.fab)
    FloatingActionButton fab;
    private BaseFragmentAdapter fragmentAdapter;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_main;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    public void initView() {
        List<String> fragmentNames = Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.main_frame_name));
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fragmentList.add(TitleFragment.Create(i));
        }
        fragmentAdapter = new BaseFragmentAdapter(getChildFragmentManager(), fragmentList, fragmentNames);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabs.setupWithViewPager(viewPager);
        setPageChangeListener();
    }


    private void setPageChangeListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void showError(String msg) {
        showShortToast(msg);
    }
}
