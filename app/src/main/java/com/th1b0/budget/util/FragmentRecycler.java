package com.th1b0.budget.util;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentRecyclerViewBinding;

/**
 * Created by 7h1b0.
 */

public abstract class FragmentRecycler<T extends Presenter, S> extends Fragment {

  public static final int CONFIRM_DELETE = 2;

  protected FragmentRecyclerViewBinding mView;
  protected T mPresenter;
  protected S mAdapter;

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false);
    return mView.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    mView.included.noItem.setVisibility(View.GONE);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  protected void onError(@Nullable String error) {
    String msg = error;
    if (TextUtils.isEmpty(msg)) {
      msg = getString(R.string.error_occurred);
    }

    Snackbar.make(mView.coordinator, msg, Snackbar.LENGTH_LONG).show();
  }
}
