package com.th1b0.budget.features.pager;

import com.th1b0.budget.util.Presenter;

/**
 * Created by 7h1b0.
 */

interface PagerPresenter extends Presenter {
  //void loadBudgets(int month, int year);

  void loadBalance(int month, int year);
}
