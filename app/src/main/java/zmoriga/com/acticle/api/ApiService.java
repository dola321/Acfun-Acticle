package zmoriga.com.acticle.api;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import retrofit2.http.Url;
import zmoriga.com.acticle.bean.Content;
import zmoriga.com.acticle.bean.Comments;
import zmoriga.com.acticle.bean.TitlesList;

/**
 *
 */
public interface ApiService {


    //sort=1&pageNo=1&pageSize=6&recommendSize=6&channelIds=110&range=86400000
    //sort:
    // 1 最多观看
    // 2 最多评论
    // 3 最多收藏
    // 4 最新发布
    // 5 最新回复
    @GET("searches/channel")
    Call<TitlesList> getTitleList(@Header("Cache-Control") String cacheControl, @Query("sort") int sort, @Query("pageNo") int pageNo, @Query("pageSize") int pageSize
            , @Query("channelIds") int channelIds, @Query("range") int range);

    @GET()
    Call<Content> getArticle(@Url String contentId);

    @GET()
    Call<Comments> getComments(@Url String url);

}
