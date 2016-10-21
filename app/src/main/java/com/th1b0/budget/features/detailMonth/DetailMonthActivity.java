package com.th1b0.budget.features.detailmonth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.features.budget.BudgetFragment;
import com.th1b0.budget.util.DateUtil;

import static com.th1b0.budget.features.budget.BudgetFragment.MONTH;
import static com.th1b0.budget.features.budget.BudgetFragment.YEAR;

/**
 * Created by 7h1b0.
 */

public final class DetailMonthActivity extends AppCompatActivity {

  public static Intent newInstance(@NonNull Context context, int month, int year) {
    Intent intent = new Intent(context, DetailMonthActivity.class);
    intent.putExtra(YEAR, year);
    intent.putExtra(MONTH, month);
    return intent;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail_month);

    if (!isArgumentValid()) {
      throw new IllegalStateException("Missing arguments. Please use newInstance()");
    }

    int month = getIntent().getExtras().getInt(MONTH);
    int year = getIntent().getExtras().getInt(YEAR);

    String title = DateUtil.formatDate(year, month);
    initializeToolbar(title);

    if (savedInstanceState == null) {
      getFragmentManager().beginTransaction()
          .replace(R.id.frame_container, BudgetFragment.newInstance(month, year))
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

  private void initializeToolbar(String title) {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setTitle(title);
    }
  }

  private boolean isArgumentValid() {
    return getIntent().hasExtra(MONTH) && getIntent().hasExtra(YEAR);
  }
}
