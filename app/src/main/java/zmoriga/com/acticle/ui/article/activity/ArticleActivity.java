package zmoriga.com.acticle.ui.article.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import zmoriga.com.acticle.R;
import zmoriga.com.acticle.ui.article.fragment.ArticleFragment;
import zmoriga.com.acticle.ui.article.fragment.CommentFragment;
import zmoriga.com.common.base.BaseFragmentAdapter;
import zmoriga.com.common.baseapp.AppManager;
import zmoriga.com.common.swipeback.SwipeAppcompatActivity;

/**
 *
 */

public class ArticleActivity extends SwipeAppcompatActivity implements ViewPager.OnPageChangeListener{



    private String contentId;
    private String title;
    private List<Fragment> fragments;
    private List<String> fragmentNames;
    private ArticleFragment articleFragment;
    private CommentFragment commentFragment;


    @Bind(R.id.button_back)
    ImageButton btBack;
    @OnClick(R.id.button_back)
    void onClick(){
        getSwipeBackLayout().scrollToFinishActivity();
    }
    @Bind(R.id.ac_ar_tabs)
    TabLayout tabs;
    @Bind(R.id.ac_ar_viewpager)
    ViewPager viewPager;

    public static void start(Context context, String contentId, String title){
        Intent intent = new Intent(context, ArticleActivity.class);
        intent.putExtra("contentId", contentId);
        intent.putExtra("title", title);
        context.startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_article;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        contentId = getIntent().getStringExtra("contentId");
        title= getIntent().getStringExtra("title");
        fragments = new ArrayList<>();
        fragmentNames = new ArrayList<>();
        fragmentNames.add("文章");
        articleFragment = ArticleFragment.newInstance(Integer.valueOf(contentId));
        fragments.add(articleFragment);
        commentFragment = CommentFragment.newInstance(Integer.valueOf(contentId));
        fragments.add(commentFragment);
        fragmentNames.add("评论");
        BaseFragmentAdapter adapter = new BaseFragmentAdapter(getSupportFragmentManager(), fragments, fragmentNames);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onBackPressed() {
        getSwipeBackLayout().scrollToFinishActivity();
        AppManager.getAppManager().removeActivity(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0) {
            setSwipeBackEnable(true);
        }else {
            setSwipeBackEnable(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
