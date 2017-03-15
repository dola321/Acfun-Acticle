package zmoriga.com.acticle.ui.title;

import java.util.List;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zmoriga.com.acticle.api.Api;
import zmoriga.com.acticle.api.ApiConstants;
import zmoriga.com.acticle.bean.Title;
import zmoriga.com.acticle.bean.TitlesList;
import zmoriga.com.acticle.ui.title.TitleFragmentContract;
import zmoriga.com.common.baserx.RxSchedulers;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */


public class TitleFragmentModel implements TitleFragmentContract.Model{
    @Override
    public Flowable getTitleListData(final int channel, final int startPage, final int sort, final int range) {
        return Flowable.create(new FlowableOnSubscribe<List<Title>>() {
            @Override
            public void subscribe(final FlowableEmitter<List<Title>> e) throws Exception {
                Api.getDefault(1, ApiConstants.OFFICIAL_URL).getTitleList(Api.getCacheControl(), sort, startPage, ApiConstants.PAGE_SIZE, channel, range)
                        .enqueue(new Callback<TitlesList>() {
                            @Override
                            public void onResponse(Call<TitlesList> call, Response<TitlesList> response) {
                                if (!e.isCancelled()) {
                                    e.onNext(response.body().getData().getList());
                                }
                                call.cancel();
                                e.onComplete();
                            }

                            @Override
                            public void onFailure(Call<TitlesList> call, Throwable t) {
                                if (!e.isCancelled()) {
                                    e.onError(t);
                                }
                                call.cancel();
                                e.onComplete();
                            }
                        });

            }
        }, BackpressureStrategy.BUFFER)
                .compose(RxSchedulers.<List<Title>>io_main());
    }
}
