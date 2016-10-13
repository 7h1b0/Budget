package com.th1b0.budget.features.transaction;

import com.th1b0.budget.model.Header;
import com.th1b0.budget.model.RecyclerItem;
import com.th1b0.budget.model.Transaction;
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

  TransactionPresenterImpl(TransactionView view, DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void loadTransaction() {
    mSubscription.add(mDataManager.getTransactions(12)
        .map(this::addHeader)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mView::onTransactionLoaded, error -> mView.onError(error.getMessage())));
  }

  @Override public void loadTransaction(int month, int year) {
    mSubscription.add(mDataManager.getTransactions(month, year)
        .map((Func1<ArrayList<Transaction>, ArrayList<RecyclerItem>>) ArrayList::new)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(mView::onTransactionLoaded, error -> mView.onError(error.getMessage())));
  }

  @Override public void deleteTransaction(Transaction transaction) {
    mDataManager.deleteTransaction(transaction);
  }

  private ArrayList<RecyclerItem> addHeader(ArrayList<Transaction> transactions) {
    int month = -1;
    ArrayList<RecyclerItem> result = new ArrayList<>();

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
