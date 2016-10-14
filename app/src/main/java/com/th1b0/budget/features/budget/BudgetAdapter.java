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
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DateUtil;
import com.th1b0.budget.util.Preferences;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

final class BudgetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private static final int HEADER = 1;
  private static final int ITEM = 2;

  interface OnClick {
    void onClick(Budget budget);
  }

  private ArrayList<Budget> mItems;
  private Context mContext;
  private OnClick mListener;

  BudgetAdapter(@NonNull Context context, @NonNull OnClick listener) {
    mContext = context;
    mListener = listener;
    mItems = new ArrayList<>();
    setHasStableIds(true);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (viewType == HEADER) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.item_budget_header, parent, false);
      return new ViewBudgetHeader(view);
    } else {
      View view =
          LayoutInflater.from(parent.getContext()).inflate(R.layout.item_budget, parent, false);
      return new ViewBudget(view);
    }
  }

  @Override public int getItemViewType(int position) {
    return position == 0 && Preferences.enlargeFirstCell(mContext) ? HEADER : ITEM;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final Budget budget = mItems.get(position);
    final double res = budget.getGoal() + budget.getValue();

    if (getItemViewType(position) == HEADER) {
      ViewBudgetHeader viewBudgetHeader = (ViewBudgetHeader) holder;
      viewBudgetHeader.expense.setText(
          String.format(mContext.getString(R.string.float_value), negation(budget.getValue())));
      viewBudgetHeader.budget.setText(
          String.format(mContext.getString(R.string.float_value), budget.getGoal()));
      viewBudgetHeader.value.setText(String.format(mContext.getString(R.string.float_value), res));
      viewBudgetHeader.date.setText(DateUtil.formatDate(budget.getYear(), budget.getMonth()));
      if (res >= 0) {
        viewBudgetHeader.value.setTextColor(ContextCompat.getColor(mContext, R.color.green));
      } else {
        viewBudgetHeader.value.setTextColor(ContextCompat.getColor(mContext, R.color.red));
      }
    } else {
      ViewBudget viewBudget = (ViewBudget) holder;
      viewBudget.detail.setText(
          String.format(mContext.getString(R.string.budget_of_goal), negation(budget.getValue()),
              budget.getGoal()));
      viewBudget.date.setText(DateUtil.formatDate(budget.getYear(), budget.getMonth()));

      viewBudget.value.setText(String.format(mContext.getString(R.string.float_value), res));
      if (res >= 0) {
        viewBudget.value.setTextColor(ContextCompat.getColor(mContext, R.color.green));
      } else {
        viewBudget.value.setTextColor(ContextCompat.getColor(mContext, R.color.red));
      }
    }
  }

  @Override public long getItemId(int position) {
    return mItems.get(position).hashCode();
  }

  @Override public int getItemCount() {
    return mItems.size();
  }

  private class ViewBudget extends RecyclerView.ViewHolder {

    TextView detail;
    TextView date;
    TextView value;

    ViewBudget(View v) {
      super(v);
      date = (TextView) v.findViewById(R.id.date);
      value = (TextView) v.findViewById(R.id.value);
      detail = (TextView) v.findViewById(R.id.detail);

      v.setOnClickListener(view -> mListener.onClick(mItems.get(getLayoutPosition())));
    }
  }

  private class ViewBudgetHeader extends RecyclerView.ViewHolder {

    TextView value;
    TextView expense;
    TextView budget;
    TextView date;

    ViewBudgetHeader(View v) {
      super(v);
      value = (TextView) v.findViewById(R.id.value);
      expense = (TextView) v.findViewById(R.id.expense);
      budget = (TextView) v.findViewById(R.id.budget);
      date = (TextView) v.findViewById(R.id.date);

      v.setOnClickListener(view -> mListener.onClick(mItems.get(getLayoutPosition())));
    }
  }

  void addAll(ArrayList<Budget> items) {
    mItems = items;
    notifyDataSetChanged();
  }

  private double negation(double number) {
    return number == 0 ? number : -number;
  }
}

