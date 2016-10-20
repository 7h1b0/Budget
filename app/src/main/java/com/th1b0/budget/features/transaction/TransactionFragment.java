package com.th1b0.budget.features.transaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.th1b0.budget.R;
import com.th1b0.budget.features.transactionform.TransactionFormActivity;
import com.th1b0.budget.model.TransactionItem;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.ConfirmDeletionDialog;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.FragmentRecycler;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class TransactionFragment extends FragmentRecycler<TransactionPresenter, TransactionAdapter>
    implements TransactionView, TransactionAdapter.OnTransactionClick {

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

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initializeRecycler();
    initializeFAB();
    loadTransactions();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case CONFIRM_DELETE:
        if (resultCode == Activity.RESULT_OK && data.hasExtra(ConfirmDeletionDialog.PARCELABLE)) {
          Transaction transaction = data.getExtras().getParcelable(ConfirmDeletionDialog.PARCELABLE);
          mPresenter.deleteTransaction(transaction);
        }
        break;
    }
  }

  @Override public void onTransactionLoaded(ArrayList<TransactionItem> transactions) {
    mAdapter.addAll(transactions);
    if (transactions.isEmpty()) {
      mView.included.text.setText(getString(R.string.no_transaction));
      mView.included.noItem.setVisibility(View.VISIBLE);
    } else {
      mView.included.noItem.setVisibility(View.GONE);
    }
  }

  @Override public void onError(String error) {
    super.onError(error);
  }

  @Override public void onTransactionClick(@NonNull Transaction transaction) {
    View view = View.inflate(getActivity(), R.layout.bottomsheet_edit, null);
    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
    dialog.setContentView(view);
    dialog.show();

    view.findViewById(R.id.edit).setOnClickListener(v -> {
      startActivity(TransactionFormActivity.newInstance(getActivity(), transaction));
      dialog.dismiss();
    });

    view.findViewById(R.id.delete).setOnClickListener(v -> {
      String title = getString(R.string.confirm_transaction_deletion_title);
      String msg = getString(R.string.confirm_transaction_deletion);
      ConfirmDeletionDialog.newInstance(title, msg, transaction, this, CONFIRM_DELETE).show(getFragmentManager(), null);
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
