package com.th1b0.budget.features.detail;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentHomeBinding;
import com.th1b0.budget.features.detailbudget.DetailBudgetActivity;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class DetailFragment extends Fragment
    implements DetailView, DetailAdapter.OnClickBudget {

  public static final String YEAR = "year";
  public static final String MONTH = "month";

  private DetailPresenter mPresenter;
  private DetailAdapter mAdapter;
  private FragmentHomeBinding mView;

  public static DetailFragment newInstance() {
    return newInstance(DateUtil.getCurrentMonth(), DateUtil.getCurrentYear());
  }

  public static DetailFragment newInstance(int month, int year) {
    DetailFragment fragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putInt(YEAR, year);
    args.putInt(MONTH, month);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new DetailPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new DetailAdapter(this);
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
    mView.balance.setText(getString(R.string.float_value, balance.getBalance()));
    mView.incomes.setText(getString(R.string.float_value, balance.getIncomes()));
    mView.expenses.setText(getString(R.string.float_value, balance.getExpenses()));
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

  @Override public void onClickBudget(@NonNull PresentationBudget budget) {
    startActivity(DetailBudgetActivity.newInstance(getActivity(), getArguments().getInt(YEAR),
        getArguments().getInt(MONTH), budget.getId(), budget.getTitle()));
  }
}
