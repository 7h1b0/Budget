package com.th1b0.budget.features.container;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Container;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public class ContainerAdapter extends RecyclerView.Adapter<ContainerAdapter.ViewContainer> {

  interface OnContainerClick {
    void onContainerClick(@NonNull Container container);
  }

  private ArrayList<Container> mContainers;
  private Context mContext;
  private OnContainerClick mListener;

  ContainerAdapter(@NonNull Context context, @NonNull OnContainerClick listener) {
    mContext = context;
    mListener = listener;
    mContainers = new ArrayList<>();
    setHasStableIds(true);
  }

  @Override public ViewContainer onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false);
    return new ViewContainer(view);
  }

  @Override public void onBindViewHolder(ViewContainer holder, int position) {
    final Container container = mContainers.get(position);

    holder.title.setText(container.getTitle());
    holder.value.setText(
        String.format(mContext.getString(R.string.float_value), container.getValue()));
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
      value = (TextView) v.findViewById(R.id.value);
      title = (TextView) v.findViewById(R.id.title);

      v.setOnClickListener(
          view -> mListener.onContainerClick(mContainers.get(getLayoutPosition())));
    }
  }

  void addAll(ArrayList<Container> containers) {
    mContainers = containers;
    notifyDataSetChanged();
  }
}
