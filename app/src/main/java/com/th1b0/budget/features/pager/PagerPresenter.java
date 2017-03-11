package com.th1b0.budget.features.pager;

import com.th1b0.budget.util.BasePresenter;

/**
 * Created by 7h1b0.
 */

interface PagerPresenter extends BasePresenter<PagerView> {
  void loadBalance(int month, int year);
}
