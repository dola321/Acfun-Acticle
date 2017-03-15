package zmoriga.com.acticle.ui.title;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import butterknife.Bind;
import io.reactivex.internal.util.LinkedArrayList;
import zmoriga.com.acticle.R;
import zmoriga.com.acticle.api.ApiConstants;
import zmoriga.com.acticle.app.AppApplication;
import zmoriga.com.acticle.bean.Title;
import zmoriga.com.acticle.ui.article.activity.ArticleActivity;
import zmoriga.com.acticle.ui.title.adapter.TitleSingleAdapter;
import zmoriga.com.common.base.BaseFragment;
import zmoriga.com.common.commonutils.LogUtil;

import static android.content.ContentValues.TAG;

/**
 *
 */

public class TitleFragment extends BaseFragment<TitleFragmentPresenter, TitleFragmentModel>
        implements TitleFragmentContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener, AdapterView.OnItemSelectedListener, View.OnTouchListener {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout headerTab;
    private Button buttonNow;

    private int frameId ;
    private int channelId;
    private int mStartPage = 1;
    private int range;
    private int sort;
    private boolean refreshing = false;
    private boolean loadEnd = false;
    private List<Title> data = new ArrayList<>();
    private TitleSingleAdapter titleSingleAdapter;

    public static TitleFragment Create(int type){
        TitleFragment titleFragment = new TitleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("frame_type", type);
        titleFragment.setArguments(bundle);
        return titleFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_title;
    }

    @Override
    public void initPresenter() { mPresenter.setVM(this, mModel);}

    @Override
    protected void initView() {
        if (getArguments() != null) {
            frameId = getArguments().getInt("frame_type");
        }
        initNeeding(frameId);
        initSwipeRefreshLayout();
        initHeaderView(frameId);
        initRecyclerView();

    }

    private void initNeeding(int frameId){
        channelId = 110;
        range = ApiConstants.RANGE_DAY;
        switch (frameId){
            case 0:
                sort = ApiConstants.SORT_VIEW;
                break;
            case 1:
                sort = ApiConstants.SORT_NEWMARK;
                range = ApiConstants.RANGE_WEEK;
                break;
            case 2:
                sort = ApiConstants.SORT_REMARK;
                break;
            case 3:
                sort = ApiConstants.SORT_COLLECT;
            default:
                break;
        }
    }

    private void initSwipeRefreshLayout(){
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private void initHeaderView(int frameId){
        if (frameId >= 1){
            headerTab = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.rv_header, null);
            List<String> strs = (frameId == 1)?
                    Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.sort_name)):
                    Arrays.asList(AppApplication.getAppContext().getResources().getStringArray(R.array.range_name));
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), R.layout.spinner_select, strs);
            AppCompatSpinner spinner = (AppCompatSpinner) headerTab.findViewById(R.id.header_spinner);
            spinner.setAdapter(arrayAdapter);
            spinner.setOnItemSelectedListener(this);

            for (int i = 2; i < 12; i += 2){
                Button button = (Button) headerTab.getChildAt(i);
                if (i == 2) buttonNow = button;
                button.setOnTouchListener(this);
            }

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (frameId == 1){
            sort = (position == 0 )? ApiConstants.SORT_NEWMARK : ApiConstants.SORT_RECENT;
        }else {
            range = (position == 0 )? ApiConstants.RANGE_DAY : ApiConstants.RANGE_WEEK;
        }
        onRefresh();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        titleSingleAdapter = new TitleSingleAdapter(R.layout.rv_item, data);
        titleSingleAdapter.setOnLoadMoreListener(this);
        if (headerTab != null) {
            titleSingleAdapter.addHeaderView(headerTab);
        }

        recyclerView.setAdapter(titleSingleAdapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Title title = (Title) baseQuickAdapter.getItem(i);
                ArticleActivity.start(getContext(), title.getContentId(), title.getTitle());
            }
        });
        onRefresh();

    }

    @Override
    public void returnTitlesListData(List<Title> titlesList) {
        if (!titlesList.isEmpty()){
            if (refreshing){
                data.clear();
                titleSingleAdapter.setNewData(data);
                mStartPage = 1;
                refreshing = false;
            }
            for (Title title: titlesList){
                boolean repeat = false;
                for (Title check: data){
                    if (check.getContentId() == title.getContentId()){
                        repeat = true;
                    }
                }
                if (!repeat){
                    titleSingleAdapter.addData(title);
                }
            }
            mStartPage += 1;

        }else {
            loadEnd = true;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    titleSingleAdapter.loadMoreEnd();
                }
            });
        }
    }

    @Override
    public void returnComplete() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        if (!loadEnd) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    titleSingleAdapter.loadMoreComplete();
                }
            });
        }
        if (refreshing){
            refreshing = false;
        }
    }

    @Override
    public void scrollToTop() {

    }

    @Override
    public void onRefresh() {
        refreshing = true;
        loadEnd = false;
        mPresenter.getTitleListDataRequest(channelId, 1, sort, range);
    }

    @Override
    public void showError(String msg) {
        showShortToast(msg);
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                titleSingleAdapter.loadMoreFail();
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getTitleListDataRequest(channelId, mStartPage, sort, range);

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP && (v.getId() != buttonNow.getId())){
            int id = v.getId();
            channelId = id == R.id.header_button1? 110:
                    id == R.id.header_button2? 73:
                            id == R.id.header_button3? 74:
                                    id == R.id.header_button4? 75:
                                            id == R.id.header_button5? 164: 0;
            buttonNow.setTextColor(getResources().getColor(R.color.colorFontGray));
            buttonNow = (Button) v;
            buttonNow.setTextColor(getResources().getColor(R.color.colorRed));
            onRefresh();
        }
        return false;
    }
}
