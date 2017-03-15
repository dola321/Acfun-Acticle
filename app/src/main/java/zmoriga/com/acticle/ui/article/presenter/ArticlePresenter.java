package zmoriga.com.acticle.ui.article.presenter;

import io.reactivex.subscribers.ResourceSubscriber;
import zmoriga.com.acticle.ui.article.contract.ArticleContract;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */

public class ArticlePresenter extends ArticleContract.Presenter{
    @Override
    public void getDataRequest(final int contentId) {
        mRxManage.add(mModel.getData(contentId).subscribeWith(new ResourceSubscriber<String>(){

            @Override
            public void onNext(String s) {
                mView.returnData(s);
            }

            @Override
            public void onError(Throwable t) {
                LogUtil.logd(t.toString());
                mView.showError("throw" + t.toString());

            }

            @Override
            public void onComplete() {
                mView.returnComplete();
            }
        }));
    }
}
