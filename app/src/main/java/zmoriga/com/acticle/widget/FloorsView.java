package zmoriga.com.acticle.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import zmoriga.com.acticle.R;
import zmoriga.com.acticle.bean.Comment;
import zmoriga.com.acticle.db.CommentHelper;
import zmoriga.com.common.commonutils.LogUtil;


/**
 *
 */
public class FloorsView extends LinearLayout {

    private Context mContext;
    private OnClickListener mOnClickListener;
    private SparseArray<Comment> mAttr;
    private SparseArray<View> mViewAttr;
    private ArrayList<View> viewList;
    private Comment comment;
    private Comment mQuote;
    private CommentHelper helper;
    private int quotingId = 0;

    private int mMaxNum = 4;

    public FloorsView(Context context) {
        this(context, null);
    }

    public FloorsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloorsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onLayout(boolean changed, int x, int y, int oX, int oY){
        super.onLayout(changed, x, y, oX, oY);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        final int i = getChildCount() - 1;
        if (i >= 0) {
            View itemView = getChildAt(i);

            ColorDrawable drawable = new ColorDrawable(0xFFFFFEEE);
            drawable.setBounds(itemView.getLeft(), 0,
                    itemView.getRight(), itemView.getBottom());
            drawable.draw(canvas);
        }
        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime){
        Drawable border = ContextCompat.getDrawable(getContext(), R.drawable.floors_item);
        border.setBounds(child.getLeft(), child.getLeft(),
                child.getRight(), child.getBottom());
        border.draw(canvas);
        return super.drawChild(canvas, child, drawingTime);
    }

    public void bindComments(SparseArray<Comment> commentSparseArray, SparseArray<View> viewSparseArray){
        if (mAttr == null){
            mAttr = commentSparseArray;
            mViewAttr = viewSparseArray;
        }
    }

    public void setComment(Comment comment){
        this.comment = comment;
        removeAllViews();
        if (comment.getQuoteId() != 0) {
            quotingId = comment.getQuoteId();
            mQuote = mAttr.get(quotingId);
            helper = mQuote.getHelper();
            if (comment.getRefCount() == 0) {
                helper.setShowing(true);
            }
            if (helper.getShowing()) {
                getViewList();
                showCommentView(viewList);
            }
            addLastView();
        }
    }

    public void setOnClickListener(OnClickListener listener){
        mOnClickListener = listener;
    }

    private void getViewList(){
        viewList = new ArrayList<>();
        Comment quote = mQuote;
        for (int quoteId; quote != null; quoteId = quote.getQuoteId(), quote = mAttr.get(quoteId)) {
            View itemView = mViewAttr.get(quotingId);
            int[] position02 = new int[2];
            if (itemView != null){
                itemView.getLocationOnScreen(position02);
            }
            if (itemView == null || position02[1] >= 0) {
                itemView = LayoutInflater.from(mContext).inflate(R.layout.comments_view_item, null);
                TextView userName = (TextView) itemView.findViewById(R.id.user_name);
                TextView content = (TextView) itemView.findViewById(R.id.comments_content);
                TextView floor = (TextView) itemView.findViewById(R.id.floor);
                if (quote.getNameRed() == 1) {
                    userName.setTextColor(Color.RED);
                }
                userName.setText(quote.getUserName());
                ParseCommentText.setCommentContent(quote.getContent(), content);
                floor.setText("#" + quote.getCount());
                if (mOnClickListener != null) {
                    itemView.setOnClickListener(mOnClickListener);
                }
                quote.getHelper().setRepeat(true);
                mViewAttr.put(quotingId, itemView);
            }

            viewList.add(itemView);

        }
    }

    private void showCommentView(List<View> commentList) {
        if ((commentList != null) && (!commentList.isEmpty())) {
            final int offset = 6;
            for (int i = 0; i < commentList.size(); i++) {
                LayoutParams params = generateDefaultLayoutParams();
                int k = offset * i;

                if (commentList.size() > mMaxNum + 2 && i > mMaxNum) {
                    k = offset * mMaxNum;
                }
                params.leftMargin = k;
                params.rightMargin = k;
                params.topMargin = i  == commentList.size() - 1 ? k : 0;
                View item = commentList.get(i);
                if (item.getParent() != null) {
                    ((ViewGroup) item.getParent()).removeView(item);
                }
                addView(item, 0, params);
            }
        }

    }

    private void addLastView(){
        View lastView = LayoutInflater.from(mContext).inflate(R.layout.comments_view_show, null);
        setLastViewText(lastView, comment);
        lastView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (helper.getShowing()){
                    helper.setShowing(false);
                    int scroll = v.getTop();
                    ((View)getParent().getParent()).scrollBy(0, -scroll);
                    for (View item: viewList){
                        item.setVisibility(GONE);
                    }
                }else {
                    helper.setShowing(true);
                    getViewList();
                    showCommentView(viewList);
                }
                setLastViewText(v, comment);
            }
        });
        LayoutParams params = generateDefaultLayoutParams();
        addView(lastView, params);
    }


    private void setLastViewText(View v, Comment comment) {
        TextView textView = (TextView) v.findViewById(R.id.comments_last);
        TextView textView1 = (TextView) v.findViewById(R.id.comments_last_show);
        if (comment.getRefCount() > 0){
            if (helper.getShowing()) {
                textView.setText("重复引用已显示 ");
                textView1.setText("[收起] ∧");
            } else {
                textView.setText("重复引用已隐藏 ");
                textView1.setText("[展开] ∨");
            }

        }else {
            if (helper.getShowing()) {
                textView.setText(comment.getDeep() + "层引用已显示 ");
                textView1.setText("[收起] ∧");
            } else {
                textView.setText(comment.getDeep() + "层引用已隐藏 ");
                textView1.setText("[展开] ∨");
            }
        }
    }
}
