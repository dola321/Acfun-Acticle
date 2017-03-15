package zmoriga.com.acticle.ui.main.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import butterknife.Bind;
import io.reactivex.functions.Consumer;
import zmoriga.com.acticle.R;
import zmoriga.com.acticle.app.AppConstant;
import zmoriga.com.acticle.ui.main.fragment.MainFragment;
import zmoriga.com.common.base.BaseActivity;
import zmoriga.com.common.baseapp.AppManager;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_tabs)
    LinearLayout tabs;
    @Bind(R.id.button1)
    ImageButton button1;
    @Bind(R.id.button2)
    ImageButton button2;
    @Bind(R.id.button3)
    ImageButton button3;
    @Bind(R.id.button4)
    ImageButton button4;


    private MainFragment titleListFragment;

    /**
     * 入口
     * @param activity
     */
    public static void startAction(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initPresenter() {

    }
    @Override
    public void initView() {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button1:
                        SwitchTo(0);
                        break;
                    case R.id.button2:
                        SwitchTo(1);
                        break;
                    case R.id.button3:
                        SwitchTo(2);
                        break;
                    case R.id.button4:
                        SwitchTo(3);
                        break;
                    default:
                        break;
                }
            }
        };
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化fragment
        initFragment(savedInstanceState);

        //监听菜单显示或隐藏
        mRxManager.on(AppConstant.MENU_SHOW_HIDE, new Consumer<Boolean>() {
            @Override
            public void accept(Boolean hideOrShow) {
                startAnimation(hideOrShow);
            }
        });
    }


    /**
     * 初始化碎片
     */
    private void initFragment(Bundle savedInstanceState) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        int currentTabPosition = 0;
        if (savedInstanceState != null) {
            titleListFragment = (MainFragment) getSupportFragmentManager().findFragmentByTag("titleListFragment");
            currentTabPosition = savedInstanceState.getInt(AppConstant.HOME_CURRENT_TAB_POSITION);
        } else {
            titleListFragment = new MainFragment();
            transaction.add(R.id.fl_body, titleListFragment, "titleListFragment");
        }
        transaction.commit();
        SwitchTo(currentTabPosition);

    }

    /**
     * 切换
     */
    private void SwitchTo(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        switch (position) {
            //首页
            case 0:
                button1.setImageResource(R.drawable.ic_home_blue_24dp);
                transaction.show(titleListFragment);
                transaction.commitAllowingStateLoss();
                break;
            //排行
            case 1:
                button2.setImageResource(R.drawable.ic_equalizer_blue_24dp);
                transaction.commitAllowingStateLoss();
                break;
            //私信
            case 2:
                button3.setImageResource(R.drawable.ic_message_blue_24dp);
                transaction.commitAllowingStateLoss();
                break;
            //我的
            case 3:
                button4.setImageResource(R.drawable.ic_more_horiz_blue_24dp);
                transaction.commitAllowingStateLoss();
                break;
            default:
                break;
        }
    }

    private void hideFragments(FragmentTransaction transaction) {
        button1.setImageResource(R.drawable.ic_home_black_24dp);
        button2.setImageResource(R.drawable.ic_equalizer_black_24dp);
        button3.setImageResource(R.drawable.ic_message_black_24dp);
        button4.setImageResource(R.drawable.ic_more_horiz_black_24dp);
        if (titleListFragment != null) {

            transaction.hide(titleListFragment);
        }
        /*if (rankFragment != null) {
            button2.setImageResource(R.drawable.ic_equalizer_black_24dp);
            transaction.hide(rankFragment);
        }
        if (letterFragment != null) {
            button3.setImageResource(R.drawable.ic_message_black_24dp);
            transaction.hide(letterFragment);
        }
        if (userFragment != null) {
            button4.setImageResource(R.drawable.ic_more_horiz_black_24dp);
            transaction.hide(userFragment);
        }*/
    }

    /**
     * 菜单显示隐藏动画
     * @param showOrHide
     */
    private void startAnimation(boolean showOrHide){
        ObjectAnimator animator;
        if(!showOrHide){
            animator = ObjectAnimator.ofFloat(tabs, View.TRANSLATION_Y, 0 , tabs.getHeight());
        }else{
            animator = ObjectAnimator.ofFloat(tabs, View.TRANSLATION_Y, tabs.getHeight(), 0);
        }
        animator.setDuration(500);
        animator.start();
    }

    @Override
    public void onBackPressed() {
        AppManager.getAppManager().AppExit(getApplicationContext(),false);
    }
}
