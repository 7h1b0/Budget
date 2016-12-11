package com.th1b0.budget.features.transaction;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.th1b0.budget.model.TransactionItem;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface TransactionView {
  void onTransactionLoaded(@NonNull ArrayList<TransactionItem> transactions);

  void onError(@Nullable String error);

  @NonNull Context getContext();
}
