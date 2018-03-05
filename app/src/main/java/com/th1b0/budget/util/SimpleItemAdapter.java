package com.th1b0.budget.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.th1b0.budget.R;
import com.th1b0.budget.model.SimpleItem;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

/**
 * Created by 7h1b0.
 */

public final class SimpleItemAdapter<T extends SimpleItem>
    extends RecyclerView.Adapter<SimpleItemAdapter.ViewContainer> {

  private ArrayList<T> mContainers;
  private final boolean showColor;
  private final OnSimpleItemClick<T> mListener;

  public interface OnSimpleItemClick<S extends SimpleItem> {
    void onSimpleItemClick(@NonNull S item);
  }

  public SimpleItemAdapter(@NonNull OnSimpleItemClick<T> listener, boolean showColor) {
    mContainers = new ArrayList<>();
    mListener = listener;
    this.showColor = showColor;
    setHasStableIds(true);
  }

  public SimpleItemAdapter(OnSimpleItemClick<T> listener) {
    this(listener, false);
  }

  @Override
  public SimpleItemAdapter.ViewContainer onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false);
    ViewContainer vh = new ViewContainer(view);

    view.setOnClickListener(v -> {
      final int position = vh.getAdapterPosition();
      if (position != NO_POSITION) {
        mListener.onSimpleItemClick(mContainers.get(position));
      }
    });

    return vh;
  }

  @Override public void onBindViewHolder(SimpleItemAdapter.ViewContainer holder, int position) {
    final SimpleItem simpleItem = mContainers.get(position);
    final Context context = holder.value.getContext();

    holder.title.setText(simpleItem.getTitle());
    holder.value.setText(context.getString(R.string.float_value, simpleItem.getValue()));

    if (showColor) {
      if (simpleItem.getValue() >= 0) {
        holder.value.setTextColor(ContextCompat.getColor(context, R.color.green));
      } else {
        holder.value.setTextColor(ContextCompat.getColor(context, R.color.red));
      }
    }
  }

  @Override public long getItemId(int position) {
    return mContainers.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mContainers.size();
  }

  class ViewContainer extends RecyclerView.ViewHolder {

    TextView value;
    TextView title;

    ViewContainer(View v) {
      super(v);
      value = v.findViewById(R.id.value);
      title = v.findViewById(R.id.title);
    }
  }

  public void addAll(ArrayList<T> containers) {
    mContainers = containers;
    notifyDataSetChanged();
  }
}
