package com.th1b0.budget.features.categories;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.th1b0.budget.R;
import com.th1b0.budget.model.Category;

/**
 * Created by 7h1b0.
 */

final class CategoryViewHolder extends RecyclerView.ViewHolder {

  ImageView icon;
  TextView title;
  TextView budget;

  CategoryViewHolder(View v) {
    super(v);
    icon = v.findViewById(R.id.icon);
    title = v.findViewById(R.id.title);
    budget = v.findViewById(R.id.budget);
  }

  void bindTo(Category category, Context context) {
    icon.setImageResource(category.getIcon());
    Drawable drawable = icon.getBackground();
    drawable.setColorFilter(category.getColor(), PorterDuff.Mode.SRC);

    title.setText(category.getTitle());
    if (TextUtils.isEmpty(category.getTitleBudget())) {
      budget.setText(context.getString(R.string.no_default_budget));
    } else {
      String budgetLabel = context.getString(R.string.budget_label, category.getTitleBudget());
      budget.setText(budgetLabel);
    }
  }
}
