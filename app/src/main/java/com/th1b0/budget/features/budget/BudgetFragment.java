package com.th1b0.budget.features.budget;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentHomeBinding;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class BudgetFragment extends Fragment
    implements BudgetView {

  public static final String YEAR = "year";
  public static final String MONTH = "month";

  private BudgetPresenter mPresenter;
  private BudgetAdapter mAdapter;
  private FragmentHomeBinding mView;

  public static BudgetFragment newInstance() {
    return newInstance(DateUtil.getCurrentMonth(), DateUtil.getCurrentYear());
  }

  public static BudgetFragment newInstance(int month, int year) {
    BudgetFragment dialog = new BudgetFragment();
    Bundle args = new Bundle();
    args.putInt(YEAR, year);
    args.putInt(MONTH, month);
    dialog.setArguments(args);
    return dialog;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new BudgetPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new BudgetAdapter();
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
    return mView.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    initializeRecycler();

    int month = getArguments().getInt(MONTH);
    int year = getArguments().getInt(YEAR);

    mView.included.noItem.setVisibility(View.GONE);
    mPresenter.loadBudgets(month, year);
    mPresenter.loadBalance(month, year);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  @Override public void onBudgetLoaded(ArrayList<PresentationBudget> budgets) {
    mAdapter.addAll(budgets);
    if (budgets.isEmpty()) {
      mView.included.text.setText(getString(R.string.no_budget));
      mView.included.noItem.setVisibility(View.VISIBLE);
    } else {
      mView.included.noItem.setVisibility(View.GONE);
    }
  }

  @Override public void onBalanceLoaded(PresentationBalance balance) {
    mView.balance.setText(String.format(getString(R.string.float_value), balance.getBalance()));
    mView.incomes.setText(String.format(getString(R.string.float_value), balance.getIncomes()));
    mView.expenses.setText(String.format(getString(R.string.float_value), balance.getExpenses()));
  }

  @Override public void onError(String error) {
    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.setAdapter(mAdapter);
  }

  private boolean isArgumentValid() {
    return getArguments().containsKey(MONTH) && getArguments().containsKey(YEAR);
  }
}
