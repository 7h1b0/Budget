package com.th1b0.budget.features.categories;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
  private Context mContext;
  private OnCategoryClick mListener;

  CategoryAdapter(@NonNull Context context, @NonNull OnCategoryClick listener) {
    mContext = context;
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

    holder.title.setText(category.getTitle());
    holder.icon.setImageResource(category.getIcon());

    Drawable drawable = holder.icon.getBackground();
    drawable.setColorFilter(category.getColor(), PorterDuff.Mode.SRC);
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

    ViewCategory(View v) {
      super(v);
      icon = (ImageView) v.findViewById(R.id.icon);
      title = (TextView) v.findViewById(R.id.title);

      v.setOnClickListener(view -> mListener.onCategoryClick(mCategories.get(getLayoutPosition())));
    }
  }

  void addAll(ArrayList<Category> items) {
    mCategories = items;
    notifyDataSetChanged();
  }
}
