package zmoriga.com.acticle.ui.article.fragment;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.Bind;
import zmoriga.com.acticle.R;
import zmoriga.com.acticle.bean.Comment;
import zmoriga.com.acticle.bean.Comments;
import zmoriga.com.acticle.ui.article.contract.CommentContract;
import zmoriga.com.acticle.ui.article.model.CommentModel;
import zmoriga.com.acticle.ui.article.presenter.CommentPresenter;
import zmoriga.com.acticle.ui.article.adapter.CommentAdapter;
import zmoriga.com.common.base.BaseFragment;


/**
 *
 */

public class CommentFragment extends BaseFragment<CommentPresenter, CommentModel>
        implements CommentContract.View, SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener{

    @Bind(R.id.cm_recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.cm_swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private int contentId;
    private int mPage = 1;
    private boolean refreshing = false;
    private boolean loadEnd = false;

    private CommentAdapter commentAdapter;

    public static CommentFragment newInstance(int contentId){
        CommentFragment commentFragment = new CommentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("contentId", contentId);
        commentFragment.setArguments(bundle);
        return commentFragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_comment;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {
        if (getArguments() != null){
            contentId = getArguments().getInt("contentId");
        }
        initSwipeRefreshLayout();
        initRecyclerView();
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

    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentAdapter = new CommentAdapter(R.layout.comment_rv_item);
        commentAdapter.setOnLoadMoreListener(this);
        recyclerView.setAdapter(commentAdapter);

        onRefresh();
    }

    @Override
    public void onRefresh() {
        refreshing = true;
        loadEnd = false;
        mPresenter.getDataRequest(contentId, 1);
    }

    @Override
    public void onLoadMoreRequested() {
        mPresenter.getDataRequest(contentId, mPage);
    }

    @Override
    public void returnData(Comments comments) {
        if (mPage <= comments.getData().getTotalPage()){
            if (refreshing){
                commentAdapter.clearData();
                mPage = 1;
                refreshing = false;
            }
            mPage += 1;
            commentAdapter.addData(comments);
        }else {
            loadEnd = true;
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    commentAdapter.loadMoreEnd();
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
                    commentAdapter.loadMoreComplete();
                }
            });
        }
        if (refreshing){
            refreshing = false;
        }

    }

    @Override
    public void showError(String msg) {

    }

}
