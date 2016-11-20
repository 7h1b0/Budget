package com.th1b0.budget.features.transaction;

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
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.model.TransactionItem;
import com.th1b0.budget.util.DateUtil;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

final class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private ArrayList<TransactionItem> mItems;
  private OnTransactionClick mListener;

  TransactionAdapter(@NonNull OnTransactionClick listener) {
    mItems = new ArrayList<>();
    mListener = listener;
    setHasStableIds(true);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
      @TransactionItem.ViewType int viewType) {
    View view;
    switch (viewType) {
      case TransactionItem.TYPE_HEADER:
        view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header, parent, false);
        return new ViewHeader(view);

      case TransactionItem.TYPE_TRANSACTION:
      default:
        view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_transaction, parent, false);
        return new ViewTransaction(view);
    }
  }

  @Override @TransactionItem.ViewType public int getItemViewType(int position) {
    return mItems.get(position).getType();
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final TransactionItem item = mItems.get(position);

    switch (getItemViewType(position)) {
      case TransactionItem.TYPE_HEADER:
        ViewHeader viewHeader = (ViewHeader) holder;
        viewHeader.text.setText(item.getTitle());
        break;

      case TransactionItem.TYPE_TRANSACTION:
        ViewTransaction viewTransaction = (ViewTransaction) holder;
        Transaction transaction = (Transaction) item;
        viewTransaction.description.setText(transaction.getDescription());
        viewTransaction.date.setText(
            DateUtil.formatDate(transaction.getYear(), transaction.getMonth(),
                transaction.getDay()));

        viewTransaction.value.setText(viewTransaction.value.getContext()
            .getString(R.string.float_value,
                transaction.getValue())); viewTransaction.category.setImageResource(
          transaction.getIcon());

        Drawable drawable = viewTransaction.category.getBackground();
        drawable.setColorFilter(transaction.getColor(), PorterDuff.Mode.SRC);

        break;
    }
  }

  @Override public long getItemId(int position) {
    return mItems.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  private class ViewTransaction extends RecyclerView.ViewHolder {

    ImageView category;
    TextView description;
    TextView date;
    TextView value;

    ViewTransaction(View v) {
      super(v);
      category = (ImageView) v.findViewById(R.id.icon_category);
      description = (TextView) v.findViewById(R.id.description);
      date = (TextView) v.findViewById(R.id.date);
      value = (TextView) v.findViewById(R.id.value);

      v.setOnClickListener(
          view -> mListener.onTransactionClick((Transaction) mItems.get(getLayoutPosition())));
    }
  }

  private class ViewHeader extends RecyclerView.ViewHolder {
    public TextView text;

    ViewHeader(View v) {
      super(v);
      text = (TextView) v.findViewById(R.id.text);
    }
  }

  void addAll(ArrayList<TransactionItem> items) {
    mItems = items;
    notifyDataSetChanged();
  }

  interface OnTransactionClick {
    void onTransactionClick(@NonNull Transaction transaction);
  }
}

