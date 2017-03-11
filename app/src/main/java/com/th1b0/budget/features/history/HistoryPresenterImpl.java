package com.th1b0.budget.features.history;

import android.support.annotation.NonNull;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.PresenterImpl;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 7h1b0.
 */

final class HistoryPresenterImpl extends PresenterImpl<HistoryView> implements HistoryPresenter {

  HistoryPresenterImpl(@NonNull DataManager dataManager) {
    super(dataManager);
  }

  @Override public void loadHistory() {
    mSubscription.add(mDataManager.getHistory()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(histories -> {
          HistoryView view = getView();
          if (view != null) {
            view.onHistoryLoaded(histories);
          }
        }, error -> {
          HistoryView view = getView();
          if (view != null) {
            view.onError(error.getMessage());
          }
        }));
  }
}
