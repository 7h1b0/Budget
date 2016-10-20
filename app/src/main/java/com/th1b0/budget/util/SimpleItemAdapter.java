package com.th1b0.budget.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.th1b0.budget.R;
import com.th1b0.budget.model.SimpleItem;
import java.util.ArrayList;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by 7h1b0.
 */

public class SimpleItemAdapter<T extends SimpleItem>
    extends RecyclerView.Adapter<SimpleItemAdapter.ViewContainer> {

  private ArrayList<T> mContainers;
  private Context mContext;
  private PublishSubject<T> onClick;

  public SimpleItemAdapter(@NonNull Context context) {
    mContext = context;
    mContainers = new ArrayList<>();
    onClick = PublishSubject.create();
    setHasStableIds(true);
  }

  @Override
  public SimpleItemAdapter.ViewContainer onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_container, parent, false);
    return new ViewContainer(view);
  }

  @Override public void onBindViewHolder(SimpleItemAdapter.ViewContainer holder, int position) {
    final SimpleItem simpleItem = mContainers.get(position);

    holder.title.setText(simpleItem.getTitle());
    holder.value.setText(
        String.format(mContext.getString(R.string.float_value), simpleItem.getValue()));
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

      v.setOnClickListener(view -> onClick.onNext(mContainers.get(getLayoutPosition())));
    }
  }

  public void addAll(ArrayList<T> containers) {
    mContainers = containers;
    notifyDataSetChanged();
  }

  public Observable<T> onClick() {
    return onClick;
  }
}
