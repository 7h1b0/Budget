package com.th1b0.budget.features.transaction;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentRecyclerViewBinding;
import com.th1b0.budget.features.wizard.TransactionFormActivity;
import com.th1b0.budget.model.RecyclerItem;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DividerItemDecoration;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class TransactionFragment extends Fragment
    implements TransactionView, TransactionAdapter.OnTransactionClick {

  public static final int CONFIRM_DELETE = 2;

  private TransactionPresenter mPresenter;
  private TransactionAdapter mAdapter;
  private FragmentRecyclerViewBinding mView;

  public static TransactionFragment newInstance() {
    return new TransactionFragment();
  }

  public static TransactionFragment newInstance(int year, int month) {
    TransactionFragment fragment = new TransactionFragment();
    Bundle bundle = new Bundle();
    bundle.putInt(Transaction.YEAR, year);
    bundle.putInt(Transaction.MONTH, month);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new TransactionPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new TransactionAdapter(getActivity(), this);
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
    loadTransactions();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case CONFIRM_DELETE:
        if (resultCode == Activity.RESULT_OK && data.hasExtra(Transaction.TRANSACTION)) {
          Transaction transaction = data.getExtras().getParcelable(Transaction.TRANSACTION);
          mPresenter.deleteTransaction(transaction);
        }
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  @Override public void onTransactionLoaded(ArrayList<RecyclerItem> transactions) {
    mAdapter.addAll(transactions);
  }

  @Override public void onError(String error) {
    Snackbar.make(mView.coordinator, error, Snackbar.LENGTH_LONG).show();
  }

  @Override public void onTransactionClick(@NonNull Transaction transaction) {
    View view = View.inflate(getActivity(), R.layout.bottomsheet_transaction, null);
    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
    dialog.setContentView(view);
    dialog.show();

    view.findViewById(R.id.edit).setOnClickListener(v -> {
      startActivity(TransactionFormActivity.newInstance(getActivity(), transaction));
      dialog.dismiss();
    });

    view.findViewById(R.id.delete).setOnClickListener(v -> {
      ConfirmDialog.newInstance(transaction, this, CONFIRM_DELETE).show(getFragmentManager(), null);
      dialog.dismiss();
    });
  }

  private boolean isDetailMonth() {
    return getArguments() != null
        && getArguments().containsKey(Transaction.YEAR)
        && getArguments().containsKey(Transaction.MONTH);
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

  private void loadTransactions() {
    if (isDetailMonth()) {
      mPresenter.loadTransaction(getArguments().getInt(Transaction.MONTH),
          getArguments().getInt(Transaction.YEAR));
    } else {
      mPresenter.loadTransaction();
    }
  }
}
