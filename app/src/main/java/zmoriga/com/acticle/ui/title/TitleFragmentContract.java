package zmoriga.com.acticle.ui.title;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import zmoriga.com.acticle.bean.Title;
import zmoriga.com.acticle.bean.TitlesList;
import zmoriga.com.common.base.BaseModel;
import zmoriga.com.common.base.BasePresenter;
import zmoriga.com.common.base.BaseView;

/**
 *
 */

public interface TitleFragmentContract {
    interface Model extends BaseModel {
        //请求获取新闻
        Flowable<List<Title>> getTitleListData(final int channel, final int startPage, final int sort, final int range);
    }
    interface View extends BaseView {
        //返回获取的新闻
        void returnTitlesListData(List<Title> titleList);
        void returnComplete();
        //返回顶部
        void scrollToTop();
    }
    abstract class Presenter extends BasePresenter<View, Model> {
        //发起获取新闻请求
        public abstract void getTitleListDataRequest(final int channel, final int startPage, final int sort, final int range);
    }
}
