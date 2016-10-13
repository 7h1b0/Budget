package com.th1b0.budget.features.transaction;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Transaction;

/**
 * Created by 7h1b0.
 */

public final class ConfirmDialog extends DialogFragment {

  public static ConfirmDialog newInstance(Transaction transaction, Fragment target, int requestCode) {
    ConfirmDialog dialog = new ConfirmDialog();
    Bundle args = new Bundle();
    args.putParcelable(Transaction.TRANSACTION, transaction);
    dialog.setArguments(args);
    dialog.setTargetFragment(target, requestCode);
    return dialog;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Transaction transaction = getArguments().getParcelable(Transaction.TRANSACTION);

    return new AlertDialog.Builder(getActivity()).setTitle(getString(R.string.confirm_delete_title))
        .setMessage(getString(R.string.confirm_delete))
        .setPositiveButton(R.string.delete, (dialog, which) -> {
          Intent intent = new Intent();
          intent.putExtra(Transaction.TRANSACTION, transaction);
          getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        })
        .setNegativeButton(R.string.cancel, (dialog, which) ->
            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_CANCELED, null)
        )
        .create();
  }
}