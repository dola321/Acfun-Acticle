package zmoriga.com.acticle.ui.article.presenter;

import io.reactivex.subscribers.ResourceSubscriber;
import zmoriga.com.acticle.bean.Comments;
import zmoriga.com.acticle.ui.article.contract.CommentContract;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */

public class CommentPresenter extends CommentContract.Presenter{
    @Override
    public void getDataRequest(int contentId, int page) {
        mRxManage.add(mModel.getData(contentId, page).subscribeWith(new ResourceSubscriber<Comments>() {
            @Override
            public void onNext(Comments comments) {
                mView.returnData(comments);
            }

            @Override
            public void onError(Throwable t) {
                LogUtil.logd(t.toString());
                mView.showError(t.toString());
            }

            @Override
            public void onComplete() {
                mView.returnComplete();
            }
        }));
    }
}
