package com.th1b0.budget.features.categoryform;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.th1b0.budget.R;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public class IconPickerDialog extends DialogFragment implements IconPickerAdapter.OnIconSelected {

  interface OnIconSet {
    void onIconSet(@DrawableRes int icon);
  }

  private OnIconSet mListener;

  public static IconPickerDialog newIntance() {
    return new IconPickerDialog();
  }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);
    if (!(activity instanceof OnIconSet)) {
      throw new IllegalStateException("Activity must implement OnIconSet");
    } else {
      mListener = (OnIconSet) activity;
    }
  }

  @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final View view = View.inflate(getActivity(), R.layout.dialog_recycler_view, null);

    IconPickerAdapter adapter = new IconPickerAdapter(getIcons(), this);

    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
    recyclerView.setAdapter(adapter);

    return builder.setTitle(getString(R.string.set_icon)).setView(view).create();
  }

  @Override public void onIconSelected(@DrawableRes int icon) {
    if (mListener != null) {
      mListener.onIconSet(icon);
      dismiss();
    }
  }

  private ArrayList<Integer> getIcons() {
    TypedArray ta = getActivity().getResources().obtainTypedArray(R.array.icon_drawable);
    ArrayList<Integer> icons = new ArrayList<>(ta.length());

    for (int i = 0; i < ta.length(); i++) {
      @DrawableRes int icon = ta.getResourceId(i, 0);
      icons.add(icon);
    }

    ta.recycle();
    return icons;
  }
}
