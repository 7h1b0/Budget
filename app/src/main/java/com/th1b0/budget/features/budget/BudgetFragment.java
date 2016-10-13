package com.th1b0.budget.features.budget;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.th1b0.budget.R;
import com.th1b0.budget.features.detailMonth.DetailMonthActivity;
import com.th1b0.budget.features.pager.PagerActivity;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DividerItemDecoration;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class BudgetFragment extends Fragment implements BudgetView, BudgetAdapter.OnClick {

  private BudgetPresenter mPresenter;
  private BudgetAdapter mAdapter;

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
    return inflater.inflate(R.layout.fragment_recycler_view, container, false);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    // Setup Recycler
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.addItemDecoration(new DividerItemDecoration(getActivity()));
    recyclerView.setAdapter(mAdapter);

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
    try {
      ((PagerActivity) getActivity()).showMessage(error);
    } catch (ClassCastException e) {
      Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }
  }

  @Override public void onClick(Budget budget) {
    startActivity(DetailMonthActivity.newInstance(getActivity(), budget));
  }
}
