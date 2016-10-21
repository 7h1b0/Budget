package com.th1b0.budget.util;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 7h1b0.
 */

public final class ScrollFABBehavior extends FloatingActionButton.Behavior {
  private static final String TAG = "ScrollAwareFABBehavior";

  public ScrollFABBehavior(Context context, AttributeSet attrs) {
    super();
  }

  public boolean onStartNestedScroll(CoordinatorLayout parent, FloatingActionButton child,
      View directTargetChild, View target, int nestedScrollAxes) {
    return true;
  }

  @Override public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child,
      View dependency) {
    return dependency instanceof RecyclerView;
  }

  @Override
  public void onNestedScroll(CoordinatorLayout coordinatorLayout, FloatingActionButton child,
      View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed,
        dyUnconsumed);

    if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
      child.hide();
    } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
      child.show();
    }
  }
}
