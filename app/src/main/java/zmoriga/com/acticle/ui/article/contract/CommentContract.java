package zmoriga.com.acticle.ui.article.contract;

import io.reactivex.Flowable;
import zmoriga.com.acticle.bean.Comments;
import zmoriga.com.common.base.BaseModel;
import zmoriga.com.common.base.BasePresenter;
import zmoriga.com.common.base.BaseView;

/**
 *
 */

public interface CommentContract {
    interface Model extends BaseModel {
        Flowable<Comments> getData(final int contentId, final int page);
    }

    interface View extends BaseView {
        void returnData(Comments comments);
        void returnComplete();
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getDataRequest(final int contentId, final int page);
    }
}
