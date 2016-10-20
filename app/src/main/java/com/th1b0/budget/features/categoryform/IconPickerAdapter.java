package com.th1b0.budget.features.categoryform;

import android.support.annotation.DrawableRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.th1b0.budget.R;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

final class IconPickerAdapter extends RecyclerView.Adapter<IconPickerAdapter.ViewHolder> {

  interface OnIconSelected {
    void onIconSelected(@DrawableRes int icon);
  }

  private final OnIconSelected mListener;
  private final ArrayList<Integer> mIcons;

  IconPickerAdapter(ArrayList<Integer> icons, OnIconSelected listener) {
    mIcons = icons;
    mListener = listener;
    setHasStableIds(true);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
    return new ViewHolder(v);
  }

  @Override public void onBindViewHolder(ViewHolder viewHolder, int position) {
    viewHolder.icon.setImageResource(mIcons.get(position));
  }

  @Override public long getItemId(int position) {
    return mIcons.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mIcons.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {

    public ImageView icon;

    ViewHolder(View v) {
      super(v);

      icon = (ImageView) v.findViewById(R.id.icon);
      v.setOnClickListener(view -> mListener.onIconSelected(mIcons.get(getLayoutPosition())));
    }
  }
}
