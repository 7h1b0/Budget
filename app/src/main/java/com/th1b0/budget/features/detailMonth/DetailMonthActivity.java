package com.th1b0.budget.features.detailMonth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public class DetailMonthActivity extends AppCompatActivity {

  public static Intent newInstance(@NonNull Context context, @NonNull Budget budget) {
    Intent intent = new Intent(context, DetailMonthActivity.class);
    intent.putExtra(Budget.BUDGET, budget);
    return intent;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);

    final Budget budget = getIntent().getExtras().getParcelable(Budget.BUDGET);
    if (budget == null) {
      finish();
      return;
    }

    String title = DateUtil.formatDate(budget.getYear(), budget.getMonth());
    String subtitle =
        String.format(getString(R.string.budget_subtitle), budget.getGoal() + budget.getValue(),
            -budget.getValue(), budget.getGoal());

    initializeToolbar(title, subtitle);

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .replace(R.id.frame_container,
              TransactionFragment.newInstance(budget.getYear(), budget.getMonth()))
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

  private void initializeToolbar(String title, String subtitle) {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(title);
      getSupportActionBar().setSubtitle(subtitle);
    }
  }
}
