package com.th1b0.budget.features.budget;

import android.support.annotation.NonNull;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.model.PresentationBalance;
import com.th1b0.budget.model.PresentationBudget;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import java.util.ArrayList;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class BudgetPresenterImpl extends PresenterImpl<BudgetView> implements BudgetPresenter {

  BudgetPresenterImpl(@NonNull BudgetView view, @NonNull DataManager dataManager) {
    super(view, dataManager);
  }

  @Override public void loadBudgets(int month, int year) {
    mSubscription.add(
        Observable.combineLatest(mDataManager.getBudgets(month, year), mDataManager.getContainers(),
            (budgets, containers) -> {
              ArrayList<PresentationBudget> presBudgets = new ArrayList<>(containers.size());
              presBudgets.addAll(budgets);

              addEmptyContainers(presBudgets, containers);
              return presBudgets;
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(budgets -> {
              if (isViewAttached()) {
                getView().onBudgetLoaded(budgets);
              }
            }, error -> {
              if (isViewAttached()) {
                getView().onError(error.getMessage());
              }
            }));
  }

  @Override public void loadBalance(int month, int year) {
    mSubscription.add(mDataManager.getTransactions(month, year).map(transactions -> {
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
      if (isViewAttached()) {
        getView().onBalanceLoaded(balance);
      }
    }, error -> {
      if (isViewAttached()) {
        getView().onError(error.getMessage());
      }
    }));
  }

  private void addEmptyContainers(ArrayList<PresentationBudget> budgets,
      ArrayList<Container> containers) {
    for (Container container : containers) {
      if (!isInclude(budgets, container)) {
        PresentationBudget presentationBudget =
            new PresentationBudget(container.getId(), container.getTitle(), container.getValue(),
                0);
        budgets.add(presentationBudget);
      }
    }
  }

  private boolean isInclude(ArrayList<PresentationBudget> budgets, @NonNull Container container) {
    for (PresentationBudget budget : budgets) {
      if (budget.getId() == container.getId()) {
        return true;
      }
    }
    return false;
  }
}
