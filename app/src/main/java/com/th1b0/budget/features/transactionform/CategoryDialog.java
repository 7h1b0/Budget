package com.th1b0.budget.features.transactionform;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Category;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7h1b0.
 */

public final class CategoryDialog extends DialogFragment {

  public static final String SELECTED = "position";

  public interface OnCategorySet {
    void onCategorySet(@NonNull Category category);
  }

  private OnCategorySet mListener;

  public static CategoryDialog newInstance(@NonNull ArrayList<Category> categories, int position) {
    CategoryDialog dialog = new CategoryDialog();
    Bundle args = new Bundle();
    args.putParcelableArrayList(Category.CATEGORIES, categories);
    args.putInt(SELECTED, position);
    dialog.setArguments(args);
    return dialog;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof OnCategorySet)) {
      throw new IllegalStateException("Activity must implement OnCategorySet: Found: " + activity);
    } else {
      mListener = (OnCategorySet) activity;
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    final ArrayList<Category> categories = getArguments().getParcelableArrayList(Category.CATEGORIES);
    final int position = getArguments().getInt(SELECTED);

    final String[] titles = getCategoryTitles(categories);

    return new AlertDialog.Builder(getActivity()).setTitle(R.string.set_category)
        .setSingleChoiceItems(titles, position, null)
        .setPositiveButton(R.string.valid, (dialog, which) -> {
          final int selectedCategory =
              ((AlertDialog) dialog).getListView().getCheckedItemPosition();
          if (mListener != null && selectedCategory < categories.size() && selectedCategory >= 0) {
            mListener.onCategorySet(categories.get(selectedCategory));
          }
        })
        .setNegativeButton(R.string.cancel, null)
        .create();
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    mListener = null;
  }

  private String[] getCategoryTitles(List<Category> categories) {
    List<String> titles = new ArrayList<>(categories.size());
    for (Category category : categories) {
      titles.add(category.getTitle());
    }
    return titles.toArray(new String[titles.size()]);
  }

  private boolean isArgumentValid() {
    return getArguments().containsKey(SELECTED) && getArguments().containsKey(Category.CATEGORIES);
  }
}
