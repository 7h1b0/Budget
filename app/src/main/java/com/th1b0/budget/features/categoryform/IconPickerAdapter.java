package com.th1b0.budget.features.categoryform;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.th1b0.budget.R;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by 7h1b0.
 */

final class IconPickerAdapter extends RecyclerView.Adapter<IconPickerAdapter.ViewHolder> {

  interface OnIconSelected {
    void onIconSelected(@DrawableRes int icon);
  }

  private final OnIconSelected mListener;
  private final ArrayList<Integer> mIcons;

  IconPickerAdapter(@NonNull ArrayList<Integer> icons, @NonNull OnIconSelected listener) {
    mIcons = icons;
    mListener = listener;
    setHasStableIds(true);
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_icon, parent, false);
    ViewHolder vh = new ViewHolder(view);

    view.setOnClickListener(v -> {
      final int position = vh.getAdapterPosition();
      if (position != NO_POSITION) {
        mListener.onIconSelected(mIcons.get(position));
      }
    });

    return vh;
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
      icon = v.findViewById(R.id.icon);
    }
  }
}
