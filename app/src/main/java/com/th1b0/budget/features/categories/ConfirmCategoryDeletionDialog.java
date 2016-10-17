package com.th1b0.budget.features.categories;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Category;

/**
 * Created by 7h1b0.
 */

public class ConfirmCategoryDeletionDialog extends DialogFragment {

  public static ConfirmCategoryDeletionDialog newInstance(@NonNull Category category,
      @NonNull Fragment target, int requestCode) {
    ConfirmCategoryDeletionDialog dialog = new ConfirmCategoryDeletionDialog();
    Bundle args = new Bundle();
    args.putParcelable(Category.CATEGORY, category);
    dialog.setArguments(args);
    dialog.setTargetFragment(target, requestCode);
    return dialog;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Category category = getArguments().getParcelable(Category.CATEGORY);

    return new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.confirm_category_deletion_title))
        .setMessage(getString(R.string.confirm_category_deletion))
        .setPositiveButton(R.string.delete, (dialog, which) -> {
          Intent intent = new Intent();
          intent.putExtra(Category.CATEGORY, category);
          getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        })
        .setNegativeButton(R.string.cancel,
            (dialog, which) -> getTargetFragment().onActivityResult(getTargetRequestCode(),
                Activity.RESULT_CANCELED, null))
        .create();
  }
}
