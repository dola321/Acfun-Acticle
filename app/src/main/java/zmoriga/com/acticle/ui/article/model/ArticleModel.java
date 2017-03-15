package zmoriga.com.acticle.ui.article.model;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import zmoriga.com.acticle.api.Api;
import zmoriga.com.acticle.api.ApiConstants;
import zmoriga.com.acticle.bean.Content;
import zmoriga.com.acticle.ui.article.contract.ArticleContract;
import zmoriga.com.common.baserx.RxSchedulers;

/**
 *
 */

public class ArticleModel implements ArticleContract.Model{
    @Override
    public Flowable<String> getData(final int contentId) {
        return Flowable.create(new FlowableOnSubscribe<Content>() {
            @Override
            public void subscribe(final FlowableEmitter<Content> e) throws Exception {

                Api.getDefault(1, ApiConstants.OFFICIAL_URL)
                        .getArticle("contents/" + contentId).enqueue(new Callback<Content>() {
                    @Override
                    public void onResponse(Call<Content> call, Response<Content> response) {
                        if (!e.isCancelled()) {
                            e.onNext(response.body());
                        }
                        call.cancel();
                        e.onComplete();
                    }

                    @Override
                    public void onFailure(Call<Content> call, Throwable t) {
                        if (!e.isCancelled()) {
                            e.onError(t);
                        }
                        call.cancel();
                        e.onComplete();
                    }
                });
            }
        }, BackpressureStrategy.BUFFER)
                .map(new Function<Content, String>() {
                    @Override
                    public String apply(Content content) throws Exception {
                        /*return changeHtmlDoc(content.getData().getFullArticle().getTxt());*/
                        return changeHtmlDoc(content.getData().getArticle().getContent());
                    }
                })

                .compose(RxSchedulers.<String>io_main());
    }

    //更改修正html源码适应手机屏幕(Jsoup)
    public String changeHtmlDoc(String html) {
        Document doc = Jsoup.parse(html);

        Elements elements = doc.select("p:has(img), div:has(img), span:has(img), li");
        for (Element e : elements) {
            e.removeAttr("style");
            e.removeClass("lazyload");
        }

        elements = doc.select("p[style*=line-height]");
        for (Element e : elements){
            String oStyle = e.attr("style");
            StringBuilder mStyle = new StringBuilder();
            String[] firstTemp = oStyle.split("line-height:");                                           //获取line-height:前的字符
            if (firstTemp.length != 1) {
                String[] lastTemp = firstTemp[1].split(";");
                mStyle.append(firstTemp[0] + "line-height: 1.2;");
                for (int i = 1; i < lastTemp.length; i++) {
                    mStyle.append(lastTemp[i] + ";");
                }
            }else {
                mStyle.append("line-height: 1.2;");
            }
            e.attr("style", mStyle.toString());
        }

        elements = doc.select("[style*=nowrap]");
        for (Element e : elements) {
            StringBuilder mStyle = new StringBuilder();
            String oStyle = e.attr("style");
            String[] firstTemp = oStyle.split("nowrap:");                                           //获取nowrap前的字符
            if (firstTemp.length != 1){
                String[] lastTemp = firstTemp[1].split(";");
                mStyle.append(firstTemp[0] + "nowrap: normal;");
                for (int i = 1; i< lastTemp.length; i++){
                    mStyle.append(lastTemp[i] + ";");
                }
            }else {
                mStyle.append("nowrap: normal;");
            }
            e.attr("style", mStyle.toString());
        }

        elements = doc.select("ul, ol");
        for (Element e : elements){
            e.attr("style", "list-style-type:none; margin:0px; padding:0px;");
        }

        elements = doc.select("div[style*=width]");
        for (Element e : elements) {
            String oStyle = e.attr("style");
            String[] firstTemp = oStyle.split(";width:");                                           //获取width:前的字符
            String[] lastTemp = firstTemp[1].split(";");
            String mStyle = firstTemp[0] + ";max-width: 100%;" + lastTemp[1];
            e.attr("style", mStyle);
        }

        elements = doc.select("img[src]");

        for (Element e : elements) {
            e.removeAttr("alt");
            e.removeAttr("height");
            e.removeAttr("width");
            String imgUrl = e.attr("src");
            e.attr("style", "max-width: 100%;max-height: auto; display:block;margin-left:auto;margin-right:auto;");
            e.attr("onclick", "javascript:window.AC.viewImage('" + imgUrl + "');");
            e.attr("src", "file:///android_asset/loading.gif");
            e.attr("ori_link", imgUrl);
        }
        /*final String wtf ="<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_assets/css.css\" />" + doc.outerHtml();*/
        final String wtf = doc.outerHtml();
        return wtf;
    }

    public String replaceHtmlDoc(String html) {
        String reg = "<img[\\w\\W]*?src=\"(.*?)\".*?/>";
        Pattern p = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(html);
        while(m.find()){
            String imgUrl = m.group(1);
            String test = "<img src=\"file:///android_asset/loading.gif\" style=\"max-width: 100%;max-height: auto; display:block;margin-left:auto;margin-right:auto;\" onclick=\"javascript:window.AC.viewImage('" + imgUrl + "');\" ori_link=\"" + imgUrl +"\"/>";
            html = html.replace(m.group(), test);
        }
        String wtf ="<link rel=\"stylesheet\" type=\"text/css\" href=\"file:///android_assets/css.css\" />" + html;
        return wtf;
    }
}
