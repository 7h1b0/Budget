package com.th1b0.budget.features.transaction;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Header;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.model.TransactionItem;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import com.th1b0.budget.util.PresenterImpl;
import java.util.ArrayList;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class TransactionPresenterImpl extends PresenterImpl<TransactionView>
    implements TransactionPresenter {

  TransactionPresenterImpl(@NonNull DataManager dataManager) {
    super(dataManager);
  }

  @Override public void loadTransaction() {
    mSubscription.add(mDataManager.getTransactions(250)
        .map(this::addHeader)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(transactions -> {
          TransactionView view = getView();
          if (view != null) {
            view.onTransactionLoaded(transactions);
          }
        }, error -> {
          TransactionView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }

  @Override public void loadTransaction(int year, int month, long idBudget) {
    mSubscription.add(mDataManager.getTransactions(year, month, idBudget)
        .map((Func1<ArrayList<Transaction>, ArrayList<TransactionItem>>) ArrayList::new)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(transactions -> {
          TransactionView view = getView();
          if (view != null) {
            view.onTransactionLoaded(transactions);
          }
        }, error -> {
          TransactionView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }

  @Override public void loadTransaction(int year, int month) {
    mSubscription.add(mDataManager.getTransactions(year, month)
        .map((Func1<ArrayList<Transaction>, ArrayList<TransactionItem>>) ArrayList::new)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(transactions -> {
          TransactionView view = getView();
          if (view != null) {
            view.onTransactionLoaded(transactions);
          }
        }, error -> {
          TransactionView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }

  @Override public void deleteTransaction(@NonNull Transaction transaction) {
    mSubscription.add(mDataManager.deleteTransaction(transaction)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(transactions -> {
        }, error -> {
          TransactionView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }

  private ArrayList<TransactionItem> addHeader(ArrayList<Transaction> transactions) {
    int month = -1;
    ArrayList<TransactionItem> result = new ArrayList<>();

    for (Transaction transaction : transactions) {
      if (transaction.getMonth() != month) {
        result.add(new Header(DateUtil.getMonthName(transaction.getMonth())));
        month = transaction.getMonth();
      }
      result.add(transaction);
    }

    return result;
  }
}
