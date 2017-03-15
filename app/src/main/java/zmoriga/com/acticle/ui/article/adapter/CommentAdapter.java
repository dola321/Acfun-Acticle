package zmoriga.com.acticle.ui.article.adapter;

import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zmoriga.com.acticle.R;
import zmoriga.com.acticle.bean.Comment;
import zmoriga.com.acticle.bean.Comments;
import zmoriga.com.acticle.db.CommentHelper;
import zmoriga.com.acticle.widget.FloorsView;
import zmoriga.com.acticle.widget.ParseCommentText;


/**
 *
 */

public class CommentAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {

    private SparseArray<Comment> mAttr ;
    private SparseArray<View> mViewAttr ;

    public CommentAdapter(int layoutResId) {
        super(layoutResId, null);
        mAttr = new SparseArray<>();
        mViewAttr = new SparseArray<>();
    }

    @Override
    protected void convert(BaseViewHolder holder, Integer integer) {
        Comment comment = mAttr.get(integer);
        holder.setText(R.id.comment_username, comment.getUserName());
        holder.setText(R.id.comment_time, comment.getPostDate());
        holder.setText(R.id.comment_count, "#" + comment.getCount());
        TextView content = holder.getView(R.id.comment_comment);
        ParseCommentText.setCommentContent(comment.getContent(), content);
        FloorsView floorsView = holder.getView(R.id.floors_view);
        floorsView.bindComments(mAttr, mViewAttr);
        floorsView.setComment(comment);
    }

    public void clearData(){
        getData().clear();
        setNewData(getData());
    }

    public void addData(Comments comments){
        JsonObject commentContentArr = comments.getData().getCommentContentArr();
        Gson gson = new Gson();
        for (Map.Entry<String, JsonElement> entry : commentContentArr.entrySet()){
            String key = entry.getKey();
            JsonObject content = commentContentArr.getAsJsonObject(key);
            Comment comment = gson.fromJson(content, Comment.class);
            comment.setHelper(CommentHelper.create());
            mAttr.put(comment.getCid(), comment);
        }
        addData(comments.getData().getCommentList());
    }

}
