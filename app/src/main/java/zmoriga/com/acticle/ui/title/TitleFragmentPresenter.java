package zmoriga.com.acticle.ui.title;



import java.util.List;

import io.reactivex.subscribers.ResourceSubscriber;
import zmoriga.com.acticle.bean.Title;
import zmoriga.com.acticle.ui.title.TitleFragmentContract;
import zmoriga.com.common.baseapp.BaseApplication;
import zmoriga.com.common.commonutils.LogUtil;
import zmoriga.com.common.commonutils.NetWorkUtils;

import static android.R.attr.id;


/**
 *
 */

public class TitleFragmentPresenter extends TitleFragmentContract.Presenter{
    @Override
    public void getTitleListDataRequest(final int channelId, final int startPage, final int sort, final int range) {
        mRxManage.add(mModel.getTitleListData(channelId, startPage, sort, range).subscribeWith(new ResourceSubscriber< List<Title>>(){
            @Override
            public void onNext(List<Title> titleList) {
                mView.returnTitlesListData(titleList);
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                //网络
                if (!NetWorkUtils.isNetConnected(BaseApplication.getAppContext())) {

                }
                //其它
                else {
                    LogUtil.logd(t.toString());
                    mView.showError("throw  " + t.toString());
                }
            }

            @Override
            public void onComplete() {
                mView.returnComplete();
            }
        }));
    }
}
