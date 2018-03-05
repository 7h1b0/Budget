package com.th1b0.budget.features.budgetmonth;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentPagerBinding;
import com.th1b0.budget.databinding.FragmentRecyclerWithoutFabBinding;
import com.th1b0.budget.features.drawer.Toolbar;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import java.util.ArrayList;

/**
 * Created by 7h1b0
 */

public final class BudgetMonthFragment extends Fragment
    implements BudgetMonthView, BudgetAdapter.OnClickBudget {

  public static final String YEAR = "year";
  public static final String MONTH = "month";
  public static final String TITLE = "title";

  private BudgetMonthPresenter mPresenter;
  private BudgetAdapter mAdapter;
  private FragmentPagerBinding mView;

  public static BudgetMonthFragment newInstance() {
    return newInstance(null, DateUtil.getCurrentMonth(), DateUtil.getCurrentYear());
  }

  public static BudgetMonthFragment newInstance(int month, int year) {
    return newInstance(null, month, year);
  }

  public static BudgetMonthFragment newInstance(String title, int month, int year) {
    BudgetMonthFragment fragment = new BudgetMonthFragment();
    Bundle args = new Bundle();
    args.putInt(YEAR, year);
    args.putInt(MONTH, month);
    args.putString(TITLE, title);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new BudgetMonthPresenterImpl(DataManager.getInstance(getActivity()));
    mAdapter = new BudgetAdapter(this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = DataBindingUtil.inflate(inflater, R.layout.fragment_pager, container, false);
    return mView.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    initializeToolbar(getArguments().getString(TITLE));
    initializeRecycler();

    int month = getArguments().getInt(MONTH);
    int year = getArguments().getInt(YEAR);

    mView.included.noItem.setVisibility(View.GONE);
    mPresenter.attach(this);
    mPresenter.loadBudgets(month, year);
    mPresenter.loadBalance(month, year);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  private void initializeToolbar(@Nullable String title) {
    Toolbar toolbar = (Toolbar) getActivity();
    toolbar.setToolbarTitle(title);
  }

  @Override public void onBudgetLoaded(@NonNull ArrayList<PresentationBudget> budgets) {
    mAdapter.addAll(budgets);
    if (budgets.isEmpty()) {
      mView.included.text.setText(getString(R.string.no_budget));
      mView.included.noItem.setVisibility(View.VISIBLE);
    } else {
      mView.included.noItem.setVisibility(View.GONE);
    }
  }

  @Override public void onBalanceLoaded(@NonNull PresentationBalance balance) {
    mView.balance.setText(getString(R.string.float_value, balance.getBalance()));
    mView.incomes.setText(getString(R.string.float_value, balance.getIncomes()));
    mView.expenses.setText(getString(R.string.float_value, balance.getExpenses()));
  }

  @Override public void onError(@Nullable String error) {
    String msg = error;
    if (TextUtils.isEmpty(msg)) {
      msg = getString(R.string.error_occurred);
    }

    Snackbar.make(mView.coordinator, msg, Snackbar.LENGTH_LONG).show();
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.setAdapter(mAdapter);
  }

  private boolean isArgumentValid() {
    return getArguments().containsKey(MONTH) && getArguments().containsKey(YEAR);
  }

  @Override public void onClickBudget(@NonNull PresentationBudget budget) {
    // set title budget.getTitle()
    getActivity().getFragmentManager()
        .beginTransaction()
        .replace(R.id.frame_container,
            TransactionFragment.newInstance(budget.getTitle(), getArguments().getInt(YEAR),
                getArguments().getInt(MONTH), budget.getId()))
        .addToBackStack("TransactionFragment")
        .commit();
  }
}
