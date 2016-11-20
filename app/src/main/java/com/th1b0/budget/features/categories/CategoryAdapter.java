package com.th1b0.budget.features.categories;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Category;
import java.util.ArrayList;

/**
 * Created by 7h1b0
 */

final class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewCategory> {

  interface OnCategoryClick {
    void onCategoryClick(@NonNull Category category);
  }

  private ArrayList<Category> mCategories;
  private OnCategoryClick mListener;

  CategoryAdapter(@NonNull OnCategoryClick listener) {
    mListener = listener;
    mCategories = new ArrayList<>();
    setHasStableIds(true);
  }

  @Override public ViewCategory onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
    return new ViewCategory(view);
  }

  @Override public void onBindViewHolder(ViewCategory holder, int position) {
    final Category category = mCategories.get(position);
    final Context context = holder.title.getContext();

    holder.icon.setImageResource(category.getIcon());
    Drawable drawable = holder.icon.getBackground();
    drawable.setColorFilter(category.getColor(), PorterDuff.Mode.SRC);

    holder.title.setText(category.getTitle());
    if (TextUtils.isEmpty(category.getTitleBudget())) {
      holder.budget.setText(context.getString(R.string.no_default_budget));
    } else {
      String budgetLabel = context.getString(R.string.budget_label, category.getTitleBudget());
      holder.budget.setText(budgetLabel);
    }
  }

  @Override public long getItemId(int position) {
    return mCategories.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mCategories.size();
  }

  class ViewCategory extends RecyclerView.ViewHolder {

    ImageView icon;
    TextView title;
    TextView budget;

    ViewCategory(View v) {
      super(v);
      icon = (ImageView) v.findViewById(R.id.icon);
      title = (TextView) v.findViewById(R.id.title);
      budget = (TextView) v.findViewById(R.id.budget);

      v.setOnClickListener(view -> mListener.onCategoryClick(mCategories.get(getLayoutPosition())));
    }
  }

  void addAll(ArrayList<Category> items) {
    mCategories = items;
    notifyDataSetChanged();
  }

  boolean hasOnlyOneElement() {
    return mCategories.size() == 1;
  }
}
