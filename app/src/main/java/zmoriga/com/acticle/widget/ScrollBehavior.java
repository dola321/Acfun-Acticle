package zmoriga.com.acticle.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import zmoriga.com.acticle.app.AppConstant;
import zmoriga.com.common.baserx.RxBus;
import zmoriga.com.common.baserx.RxManager;
import zmoriga.com.common.commonutils.LogUtil;

/**
 *
 */

public class ScrollBehavior extends FloatingActionButton.Behavior {

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super();

    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final FloatingActionButton child,
                                       final View directTargetChild, final View target, final int nestedScrollAxes) {
        // Ensure we react to vertical scrolling
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL
                || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }


    @Override
    public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
                               View target, int dxConsumed, int dyConsumed, int dxUnconsumed,
                               int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.hide();
            RxBus.getInstance().post(AppConstant.MENU_SHOW_HIDE,false);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            RxBus.getInstance().post(AppConstant.MENU_SHOW_HIDE,true);
            child.show();
        }
    }
}
