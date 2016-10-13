package com.th1b0.budget.features.wizard;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Transaction;

/**
 * Created by 7h1b0.
 */

public class CategoryDialog extends DialogFragment {

  public interface OnCategorySet {
    void onCategorySet(int category);
  }

  private OnCategorySet mListener;

  public static CategoryDialog newInstance(int category) {
    CategoryDialog dialog = new CategoryDialog();
    Bundle args = new Bundle();
    args.putInt(Transaction.CATEGORY, category);
    dialog.setArguments(args);
    return dialog;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof OnCategorySet)) {
      throw new IllegalStateException("Activity must implement OnCategorySet");
    } else {
      mListener = (OnCategorySet) activity;
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final String[] categories = getResources().getStringArray(R.array.categories_title);
    final int category = getArguments().getInt(Transaction.CATEGORY);

    return new AlertDialog.Builder(getActivity()).setTitle(R.string.set_categorie)
        .setSingleChoiceItems(categories, category, null)
        .setPositiveButton(R.string.valid, (dialog, which) -> {
          if (mListener != null) {
            final int selectedCategory =
                ((AlertDialog) dialog).getListView().getCheckedItemPosition();
            mListener.onCategorySet(selectedCategory);
          }
        })
        .setNegativeButton(R.string.cancel, null)
        .create();
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    mListener = null;
  }
}
