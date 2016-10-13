package com.th1b0.budget.features.transaction;

import android.content.Context;
import com.th1b0.budget.model.RecyclerItem;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface TransactionView {
  void onTransactionLoaded(ArrayList<RecyclerItem> transactions);

  void onError(String error);

  Context getContext();
}
