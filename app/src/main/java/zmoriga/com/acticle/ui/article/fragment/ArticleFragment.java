package zmoriga.com.acticle.ui.article.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import app.dinus.com.loadingdrawable.LoadingView;
import butterknife.Bind;
import zmoriga.com.acticle.R;
import zmoriga.com.acticle.api.ApiConstants;
import zmoriga.com.acticle.app.AppApplication;
import zmoriga.com.acticle.ui.article.contract.ArticleContract;
import zmoriga.com.acticle.ui.article.model.ArticleModel;
import zmoriga.com.acticle.ui.article.presenter.ArticlePresenter;
import zmoriga.com.common.base.BaseFragment;


/**
 *
 */

public class ArticleFragment extends BaseFragment<ArticlePresenter,ArticleModel> implements ArticleContract.View{

    @Bind(R.id.web_view)
    WebView webview;
    @Bind(R.id.gear_view)
    LoadingView loadingView;

    private WebSettings webSettings;

    private int contentId;

    public static ArticleFragment newInstance(int contentId){
        Bundle args = new Bundle();
        args.putInt("contentId", contentId);
        ArticleFragment fragment = new ArticleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_article;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this,mModel);
    }

    @Override
    protected void initView() {
        contentId = getArguments().getInt("contentId");
        initWebSetting();
        mPresenter.getDataRequest(contentId);
    }

    //初始化WebView设置
    private void initWebSetting(){
        webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //最后加载图片
        webSettings.setBlockNetworkImage(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        /*获得缓存目录 <br>
        * <b>NOTE:</b>请先调用 {@link #isExternalStorageAvailable()} 判断是否可用*/
        String ARTICLE_PATH = AppApplication.getExternalCacheDir("article").getAbsolutePath();
        //webView设置缓存目录
        webSettings.setAppCachePath(ARTICLE_PATH);
        //优先缓存
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //自动布局
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        webview.addJavascriptInterface(new ACJSObject(),"AC");
        //设置Web视图
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {                   //加载网页前

            }

            @Override
            public void onPageFinished(WebView view, String url) {                                  //加载网页后
                webSettings.setBlockNetworkImage(false);

                webview.loadUrl("javascript:(function(){"
                        + "var objs = document.getElementsByTagName(\"img\");"
                        + "for(var i=0;i<objs.length;i++)  "
                        + "{"
                        + "    var imgOriSrc =objs[i].getAttribute(\"ori_link\"); "
                        + "    objs[i].setAttribute(\"src\",imgOriSrc);"
                        + "}" + "})()");
            }

        });
    }
    @Override
    public void returnData(String html) {
        if (html != null) {
            webview.loadDataWithBaseURL(ApiConstants.BASE_URL, html, "text/html", "utf-8", null);
            loadingView.setVisibility(View.GONE);
        }
    }

    @Override
    public void returnComplete() {

    }

    @Override
    public void showError(String msg) {
        showShortToast(msg);
    }


    class ACJSObject {
        @android.webkit.JavascriptInterface
        public void viewImage(String url){
        }
    }
}
