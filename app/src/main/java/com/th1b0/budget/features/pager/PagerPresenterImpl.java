package com.th1b0.budget.features.pager;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class PagerPresenterImpl extends PresenterImpl<PagerView> implements PagerPresenter {

  PagerPresenterImpl(@NonNull DataManager dataManager) {
    super(dataManager);
  }

  @Override public void loadBalance(int month, int year) {
    mSubscription.add(mDataManager.getTransactions(year, month).map(transactions -> {
      double incomes = 0;
      double expenses = 0;
      for (Transaction transaction : transactions) {
        if (transaction.getValue() >= 0) {
          incomes += transaction.getValue();
        } else {
          expenses -= transaction.getValue();
        }
      }
      return new PresentationBalance(incomes, expenses, incomes - expenses);
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(balance -> {
      PagerView view = getView();
      if (view != null) {
        view.onBalanceLoaded(balance);
      }
    }, error -> {
      PagerView view = getView();
      if (view != null) {
        view.onError(error.getMessage());
      }
    }));
  }
}
