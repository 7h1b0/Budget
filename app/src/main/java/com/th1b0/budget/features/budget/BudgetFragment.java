package com.th1b0.budget.features.budget;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentRecyclerViewBinding;
import com.th1b0.budget.features.detailMonth.DetailMonthActivity;
import com.th1b0.budget.features.wizard.TransactionFormActivity;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DividerItemDecoration;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class BudgetFragment extends Fragment
    implements BudgetView, BudgetAdapter.OnBudgetClick {

  private BudgetPresenter mPresenter;
  private BudgetAdapter mAdapter;
  private FragmentRecyclerViewBinding mView;

  public static BudgetFragment newInstance() {
    return new BudgetFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new BudgetPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new BudgetAdapter(getActivity(), this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false);
    return mView.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initializeRecycler();
    initializeFAB();
    mPresenter.loadBudgets();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  @Override public void onBudgetLoaded(ArrayList<Budget> budgets) {
    mAdapter.addAll(budgets);
  }

  @Override public void onError(String error) {
    Snackbar.make(mView.coordinator, error, Snackbar.LENGTH_LONG).show();
  }

  @Override public void onBudgetClick(@NonNull Budget budget) {
    startActivity(DetailMonthActivity.newInstance(getActivity(), budget));
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.addItemDecoration(new DividerItemDecoration(getActivity()));
    mView.recycler.setAdapter(mAdapter);
  }

  private void initializeFAB() {
    mView.fab.setOnClickListener(
        v -> startActivity(TransactionFormActivity.newInstance(getActivity())));
  }
}
