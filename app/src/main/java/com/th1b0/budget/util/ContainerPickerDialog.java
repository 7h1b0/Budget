package com.th1b0.budget.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import com.th1b0.budget.R;
import com.th1b0.budget.model.Container;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 7h1b0.
 */

public class ContainerPickerDialog extends DialogFragment {

  public static final String SELECTED = "position";

  public interface OnContainerSet {
    void onContainerSet(@NonNull Container container);
  }

  private OnContainerSet mListener;

  public static ContainerPickerDialog newInstance(ArrayList<Container> containers, int position) {
    ContainerPickerDialog dialog = new ContainerPickerDialog();
    Bundle args = new Bundle();
    args.putParcelableArrayList(Container.CONTAINERS, containers);
    args.putInt(SELECTED, position);
    dialog.setArguments(args);
    return dialog;
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof OnContainerSet)) {
      throw new IllegalStateException("Activity must implement OnContainerSet. Found: " + activity);
    } else {
      mListener = (OnContainerSet) activity;
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    final ArrayList<Container> containers =
        getArguments().getParcelableArrayList(Container.CONTAINERS);
    final int position = getArguments().getInt(SELECTED);

    final String[] titles = getCategoryTitles(containers);

    return new AlertDialog.Builder(getActivity()).setTitle(R.string.set_container)
        .setSingleChoiceItems(titles, position, null)
        .setPositiveButton(R.string.valid, (dialog, which) -> {
          final int selectedCategory =
              ((AlertDialog) dialog).getListView().getCheckedItemPosition();
          if (mListener != null && selectedCategory < containers.size() && selectedCategory >= 0) {
            mListener.onContainerSet(containers.get(selectedCategory));
          }
        })
        .setNegativeButton(R.string.cancel, null)
        .create();
  }

  @Override public void onDismiss(DialogInterface dialog) {
    super.onDismiss(dialog);
    mListener = null;
  }

  private String[] getCategoryTitles(List<Container> containers) {
    List<String> titles = new ArrayList<>(containers.size());
    for (Container container : containers) {
      titles.add(container.getTitle());
    }
    return titles.toArray(new String[titles.size()]);
  }

  private boolean isArgumentValid() {
    return getArguments().containsKey(SELECTED) && getArguments().containsKey(Container.CONTAINERS);
  }
}
