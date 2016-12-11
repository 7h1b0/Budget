package com.th1b0.budget.features.pager;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentPagerBinding;
import com.th1b0.budget.features.budgetmonth.BudgetMonthFragment;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public final class PagerFragment extends Fragment
    implements PagerView {

  public static final String YEAR = "year";
  public static final String MONTH = "month";

  private PagerPresenter mPresenter;
  private FragmentPagerBinding mView;

  public static PagerFragment newInstance() {
    return newInstance(DateUtil.getCurrentMonth(), DateUtil.getCurrentYear());
  }

  public static PagerFragment newInstance(int month, int year) {
    PagerFragment fragment = new PagerFragment();
    Bundle args = new Bundle();
    args.putInt(YEAR, year);
    args.putInt(MONTH, month);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new PagerPresenterImpl(this, DataManager.getInstance(getActivity()));
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

    int month = getArguments().getInt(MONTH);
    int year = getArguments().getInt(YEAR);

    mView.viewpager.setAdapter(getPagerAdapter(year, month));
    mView.tabs.setupWithViewPager(mView.viewpager);

    mPresenter.loadBalance(month, year);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  @Override public void onBalanceLoaded(@NonNull PresentationBalance balance) {
    mView.balance.setText(getString(R.string.float_value, balance.getBalance()));
    mView.incomes.setText(getString(R.string.float_value, balance.getIncomes()));
    mView.expenses.setText(getString(R.string.float_value, balance.getExpenses()));
  }

  @Override public void onError(@Nullable String error) {
    if (TextUtils.isEmpty(error)) {
      error = getString(R.string.error_occurred);
    }
    Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
  }

  private boolean isArgumentValid() {
    return getArguments().containsKey(MONTH) && getArguments().containsKey(YEAR);
  }

  private PagerAdapter getPagerAdapter(int year, int month) {
    PagerAdapter adapter = new PagerAdapter(getChildFragmentManager());

    adapter.add(BudgetMonthFragment.newInstance(year, month), getString(R.string.budgets));
    adapter.add(TransactionFragment.newInstance(year, month), getString(R.string.transactions));

    return adapter;
  }
}
