package com.th1b0.budget.features.transaction;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.model.TransactionItem;
import java.util.ArrayList;

import static android.support.v7.widget.RecyclerView.NO_POSITION;

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

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
    switch (viewType) {
      case R.layout.item_header:
        return new HeaderViewHolder(view);

      case R.layout.item_transaction:
      default:
        TransactionViewHolder vh = new TransactionViewHolder(view);
        view.setOnClickListener(v -> {
          final int position = vh.getAdapterPosition();
          if (position != NO_POSITION) {
            mListener.onTransactionClick((Transaction) mItems.get(position));
          }
        });
        return vh;
    }
  }

  @Override public int getItemViewType(int position) {
    return mItems.get(position).getLayout();
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final TransactionItem item = mItems.get(position);

    switch (getItemViewType(position)) {
      case R.layout.item_header:
        HeaderViewHolder viewHeader = (HeaderViewHolder) holder;
        viewHeader.bindTo(item);
        break;

      case R.layout.item_transaction:
        TransactionViewHolder viewTransaction = (TransactionViewHolder) holder;
        Transaction transaction = (Transaction) item;
        viewTransaction.bindTo(transaction);
        break;
    }
  }

  @Override public long getItemId(int position) {
    return mItems.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  void addAll(ArrayList<TransactionItem> items) {
    mItems = items;
    notifyDataSetChanged();
  }

  interface OnTransactionClick {
    void onTransactionClick(@NonNull Transaction transaction);
  }
}

