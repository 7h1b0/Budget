package com.th1b0.budget.features.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.th1b0.budget.R;
import com.th1b0.budget.features.detailmonth.DetailMonthActivity;
import com.th1b0.budget.model.PresentationHistory;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.FragmentRecycler;
import com.th1b0.budget.util.SimpleItemAdapter;
import java.util.ArrayList;
import rx.Subscription;

/**
 * Created by 7h1b0.
 */

public class HistoryFragment
    extends FragmentRecycler<HistoryPresenter, SimpleItemAdapter<PresentationHistory>>
    implements HistoryView {

  private Subscription mSubscription;

  public static HistoryFragment newInstance() {
    return new HistoryFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new HistoryPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new SimpleItemAdapter<>();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initializeRecycler();
    initializeFAB();

    mSubscription = mAdapter.onClick().subscribe(this::onHistoryClick);
    mPresenter.loadHistory();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (mSubscription != null) {
      mSubscription.unsubscribe();
    }
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.setAdapter(mAdapter);
  }

  private void initializeFAB() {
    mView.fab.setVisibility(View.GONE);
  }

  @Override public void onHistoryLoaded(ArrayList<PresentationHistory> histories) {
    mAdapter.addAll(histories);
    if (histories.isEmpty()) {
      mView.included.text.setText(getString(R.string.no_history));
      mView.included.noItem.setVisibility(View.VISIBLE);
    } else {
      mView.included.noItem.setVisibility(View.GONE);
    }
  }

  @Override public void onError(String error) {
    super.onError(error);
  }

  private void onHistoryClick(@NonNull PresentationHistory history) {
    startActivity(DetailMonthActivity.newInstance(getActivity(), history.getMonth(), history.getYear()));
  }
}

