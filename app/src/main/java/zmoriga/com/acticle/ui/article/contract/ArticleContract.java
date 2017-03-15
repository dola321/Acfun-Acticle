package zmoriga.com.acticle.ui.article.contract;

import io.reactivex.Flowable;
import zmoriga.com.common.base.BaseModel;
import zmoriga.com.common.base.BasePresenter;
import zmoriga.com.common.base.BaseView;

/**
 *
 */

public interface ArticleContract {
    interface Model extends BaseModel {
        Flowable<String> getData(final int contentId);
    }

    interface View extends BaseView {
        void returnData(String html);
        void returnComplete();
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getDataRequest(final int contentId);
    }
}
