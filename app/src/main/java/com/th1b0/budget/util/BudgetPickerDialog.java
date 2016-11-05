package com.th1b0.budget.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Budget;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7h1b0.
 */

public class BudgetPickerDialog extends DialogFragment {

  public static final String SELECTED = "position";

  public interface OnBudgetSet {
    void onBudgetSet(@NonNull Budget budget);
  }

  private OnBudgetSet mListener;

  public static BudgetPickerDialog newInstance(ArrayList<Budget> budgets, int position) {
    BudgetPickerDialog dialog = new BudgetPickerDialog();
    Bundle args = new Bundle();
    args.putParcelableArrayList(Budget.BUDGETS, budgets);
    args.putInt(SELECTED, position);
    dialog.setArguments(args);
    return dialog;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof OnBudgetSet)) {
      throw new IllegalStateException("Activity must implement OnBudgetSet. Found: " + activity);
    } else {
      mListener = (OnBudgetSet) activity;
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    final ArrayList<Budget> budgets =
        getArguments().getParcelableArrayList(Budget.BUDGETS);
    final int position = getArguments().getInt(SELECTED);

    final String[] titles = getCategoryTitles(budgets);

    return new AlertDialog.Builder(getActivity()).setTitle(R.string.set_budget)
        .setSingleChoiceItems(titles, position, null)
        .setPositiveButton(R.string.valid, (dialog, which) -> {
          final int selectedCategory =
              ((AlertDialog) dialog).getListView().getCheckedItemPosition();
          if (mListener != null && selectedCategory < budgets.size() && selectedCategory >= 0) {
            mListener.onBudgetSet(budgets.get(selectedCategory));
          }
        })
        .setNegativeButton(R.string.cancel, null)
        .create();
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    mListener = null;
  }

  private String[] getCategoryTitles(List<Budget> budgets) {
    List<String> titles = new ArrayList<>(budgets.size());
    for (Budget budget : budgets) {
      titles.add(budget.getTitle());
    }
    return titles.toArray(new String[titles.size()]);
  }

  private boolean isArgumentValid() {
    return getArguments().containsKey(SELECTED) && getArguments().containsKey(Budget.BUDGETS);
  }
}
