package zmoriga.com.acticle.ui.main.contract;

import zmoriga.com.common.base.BaseModel;
import zmoriga.com.common.base.BasePresenter;
import zmoriga.com.common.base.BaseView;

/**
 *
 */

public interface MainContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView {

    }
    abstract class Presenter extends BasePresenter<View, Model> {

    }
}
