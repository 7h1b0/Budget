package com.th1b0.budget.features.categories;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Category;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by 7h1b0
 */

final class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

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

  @Override public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
    final CategoryViewHolder vh = new CategoryViewHolder(view);

    view.setOnClickListener(v -> {
      final int position = vh.getAdapterPosition();
      if (position != NO_POSITION) {
        mListener.onCategoryClick(mCategories.get(position));
      }
    });

    return vh;
  }

  @Override public void onBindViewHolder(CategoryViewHolder holder, int position) {
    final Category category = mCategories.get(position);
    final Context context = holder.title.getContext();

    holder.bindTo(category, context);
  }

  @Override public long getItemId(int position) {
    return mCategories.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mCategories.size();
  }

  void addAll(ArrayList<Category> items) {
    mCategories = items;
    notifyDataSetChanged();
  }

  boolean hasOnlyOneElement() {
    return mCategories.size() == 1;
  }
}
