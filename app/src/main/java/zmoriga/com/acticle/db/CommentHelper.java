package zmoriga.com.acticle.db;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import zmoriga.com.acticle.bean.Comment;

/**
 *
 */

public class CommentHelper {

    public static CommentHelper create(){
        CommentHelper commentHelper = new CommentHelper();
        commentHelper.setShowing(false);
        commentHelper.setRepeat(false);
        return commentHelper;
    }

    private boolean showing;
    public boolean getShowing() {
        return showing;
    }
    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    private boolean repeat;
    public boolean getRepeat() {
        return repeat;
    }
    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

}
