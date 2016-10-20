package com.th1b0.budget.features.history;

import com.th1b0.budget.model.PresentationHistory;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface HistoryView {

  void onHistoryLoaded(ArrayList<PresentationHistory> histories);

  void onError(String error);
}
