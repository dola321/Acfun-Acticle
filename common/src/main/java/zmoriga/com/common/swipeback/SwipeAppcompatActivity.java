package zmoriga.com.common.swipeback;

import android.os.Bundle;
import android.view.View;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.Utils;
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase;
import zmoriga.com.common.R;
import zmoriga.com.common.base.BaseActivity;

/**
 *
 */

public abstract class SwipeAppcompatActivity extends BaseActivity implements SwipeBackActivityBase{

    private SwipeBackHelper mHelper;

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        mHelper = new SwipeBackHelper(this);
        mHelper.onActivityCreate();
        overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_close_exit);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mHelper.onPostCreate();
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        return mHelper.getSwipeBackLayout();
    }
    @Override
    public void setSwipeBackEnable(boolean enable) {
        getSwipeBackLayout().setEnableGesture(enable);
    }

    protected void setVibratorEnable(boolean enable){
        mHelper.setVibratorEnabled(enable);
    }

    public void setSwipeListener(SwipeBackLayout.SwipeListener l){
        mHelper.setSwipeListener(l);
    }

    @Override
    public void scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this);
        getSwipeBackLayout().scrollToFinishActivity();
    }
}
