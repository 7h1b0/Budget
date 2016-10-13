package com.th1b0.budget.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by 7h1b0.
 */

public final class DividerItemDecoration extends RecyclerView.ItemDecoration {

  private Drawable mDivider;

  public DividerItemDecoration(Context context) {
    final TypedArray a = context.obtainStyledAttributes(new int[] { android.R.attr.listDivider });
    mDivider = a.getDrawable(0);
    a.recycle();
  }

  @Override public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
    if (mDivider == null) {
      super.onDrawOver(c, parent, state);
      return;
    }

    int left = parent.getPaddingLeft();
    int right = parent.getWidth() - parent.getPaddingRight();
    int childCount = parent.getChildCount();

    for (int i = 1; i < childCount; i++) {
      View child = parent.getChildAt(i);
      RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

      int top = child.getTop() - params.topMargin;
      int bottom = top + mDivider.getIntrinsicHeight();

      mDivider.setBounds(left, top, right, bottom);
      mDivider.draw(c);
    }
  }
}
