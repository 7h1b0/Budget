package com.th1b0.budget.features.budget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.th1b0.budget.R;
import com.th1b0.budget.model.PresentationBudget;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

final class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.ViewBudget> {

  private ArrayList<PresentationBudget> mItems;
  private Context mContext;

  BudgetAdapter(@NonNull Context context) {
    mContext = context;
    mItems = new ArrayList<>();
    setHasStableIds(true);
  }

  @Override public BudgetAdapter.ViewBudget onCreateViewHolder(ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
    return new ViewBudget(view);
  }

  @Override public void onBindViewHolder(BudgetAdapter.ViewBudget holder, int position) {
    final PresentationBudget budget = mItems.get(position);
    final double res = budget.getValue() + budget.getOut();

    holder.detail.setText(
        String.format(mContext.getString(R.string.budget_of_goal), negation(budget.getOut()),
            budget.getValue()));
    holder.title.setText(budget.getTitle());

    holder.value.setText(String.format(mContext.getString(R.string.float_value), res));
    if (res >= 0) {
      holder.value.setTextColor(ContextCompat.getColor(mContext, R.color.green));
    } else {
      holder.value.setTextColor(ContextCompat.getColor(mContext, R.color.red));
    }
  }

  @Override public long getItemId(int position) {
    return mItems.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  class ViewBudget extends RecyclerView.ViewHolder {

    TextView detail;
    TextView title;
    TextView value;

    ViewBudget(View v) {
      super(v);
      title = (TextView) v.findViewById(R.id.title);
      value = (TextView) v.findViewById(R.id.value);
      detail = (TextView) v.findViewById(R.id.detail);
    }
  }

  void addAll(ArrayList<PresentationBudget> items) {
    mItems = items;
    notifyDataSetChanged();
  }

  private double negation(double number) {
    return number == 0 ? number : -number;
  }
}

