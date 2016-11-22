package com.th1b0.budget.features.detailbudget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public final class DetailBudgetActivity extends AppCompatActivity {

  public static Intent newInstance(@NonNull Context context, int year, int month, long idBudget, @Nullable String title) {
    Intent intent = new Intent(context, DetailBudgetActivity.class);
    intent.putExtra(Transaction.YEAR, year);
    intent.putExtra(Transaction.MONTH, month);
    intent.putExtra(Transaction.ID_BUDGET, idBudget);
    intent.putExtra(Budget.TITLE, title);
    return intent;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_month);

    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    int year = getIntent().getExtras().getInt(Transaction.YEAR);
    int month = getIntent().getExtras().getInt(Transaction.MONTH);
    long idBudget = getIntent().getExtras().getLong(Transaction.ID_BUDGET);
    String budgetTitle = getIntent().getExtras().getString(Budget.TITLE);

    String title = DateUtil.formatDate(year, month);
    initializeToolbar(title, budgetTitle);

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .replace(R.id.frame_container, TransactionFragment.newInstance(year, month, idBudget))
          .commit();
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  private void initializeToolbar(String title, String subTitle) {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(title);
      getSupportActionBar().setSubtitle(subTitle);
    }
  }

  private boolean isArgumentValid() {
    return getIntent().hasExtra(Transaction.YEAR)
        && getIntent().hasExtra(Transaction.MONTH)
        && getIntent().hasExtra(Transaction.ID_BUDGET)
        && getIntent().hasExtra(Budget.TITLE);
  }
}
