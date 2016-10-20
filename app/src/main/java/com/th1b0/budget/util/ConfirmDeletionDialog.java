package com.th1b0.budget.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import com.th1b0.budget.R;

/**
 * Created by 7h1b0.
 */

public class ConfirmDeletionDialog extends DialogFragment {

  public static final String TITLE = "title";
  public static final String MESSAGE = "msg";
  public static final String PARCELABLE = "parcelable";

  public static ConfirmDeletionDialog newInstance(@NonNull String title, @NonNull String msg,
      @NonNull Parcelable parcelable, @NonNull Fragment target, int requestCode) {
    ConfirmDeletionDialog dialog = new ConfirmDeletionDialog();
    Bundle args = new Bundle();
    args.putParcelable(PARCELABLE, parcelable);
    args.putString(TITLE, title);
    args.putString(MESSAGE, msg);
    dialog.setArguments(args);
    dialog.setTargetFragment(target, requestCode);
    return dialog;
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Parcelable parcelable = getArguments().getParcelable(PARCELABLE);
    final String title = getArguments().getString(TITLE);
    final String msg = getArguments().getString(MESSAGE);

    return new AlertDialog.Builder(getActivity()).setTitle(title)
        .setMessage(msg)
        .setPositiveButton(R.string.delete, (dialog, which) -> {
          Intent intent = new Intent();
          intent.putExtra(PARCELABLE, parcelable);
          getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
        })
        .setNegativeButton(R.string.cancel, null)
        .create();
  }
}
