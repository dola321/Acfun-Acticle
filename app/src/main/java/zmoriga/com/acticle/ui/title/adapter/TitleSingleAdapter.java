package zmoriga.com.acticle.ui.title.adapter;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.LinkedHashSet;
import java.util.List;

import zmoriga.com.acticle.R;
import zmoriga.com.acticle.bean.Title;
import zmoriga.com.common.commonutils.TimeUtil;

/**
 *
 */

public class TitleSingleAdapter extends BaseQuickAdapter<Title, BaseViewHolder> {

    public TitleSingleAdapter(int layoutId, List<Title> data) {
        super(layoutId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Title title) {
        holder.setText(R.id.tv_item_username, title.getUser().getUsername())
                .setText(R.id.tv_item_title, Html.fromHtml(title.getTitle()))
                .setText(R.id.tv_item_time, TimeUtil.getfriendlyTime(title.getReleaseDate()))
                .setText(R.id.tv_item_stows, "" + title.getStows())
                .setText(R.id.tv_item_viewer, "" + title.getViews())
                .setText(R.id.tv_item_description, title.getDescription())
                .setText(R.id.tv_item_comments, "" + title.getComments());
    }

}
