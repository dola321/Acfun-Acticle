package zmoriga.com.acticle.ui.article.model;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.Map;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zmoriga.com.acticle.api.Api;
import zmoriga.com.acticle.api.ApiConstants;
import zmoriga.com.acticle.bean.Comment;
import zmoriga.com.acticle.bean.Comments;
import zmoriga.com.acticle.ui.article.contract.CommentContract;
import zmoriga.com.common.baserx.RxSchedulers;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */

public class CommentModel implements CommentContract.Model{
    @Override
    public Flowable<Comments> getData(final int contentId, final int page) {
        return Flowable.create(new FlowableOnSubscribe<Comments>() {
            @Override
            public void subscribe(final FlowableEmitter<Comments> e) throws Exception {
                Api.getDefault(3, ApiConstants.PC_URL).getComments("comment_list_json.aspx?contentId=" + contentId + "&currentPage=" + page)
                        .enqueue(new Callback<Comments>() {
                            @Override
                            public void onResponse(Call<Comments> call, Response<Comments> response) {
                                if (!e.isCancelled()) {
                                    e.onNext(response.body());
                                }
                                call.cancel();
                                e.onComplete();
                            }

                            @Override
                            public void onFailure(Call<Comments> call, Throwable t) {
                                if (!e.isCancelled()) {
                                    e.onError(t);
                                }
                                call.cancel();
                                e.onComplete();
                            }
                        });
            }
        }, BackpressureStrategy.BUFFER)
                .compose(RxSchedulers.<Comments>io_main());
    }

}
