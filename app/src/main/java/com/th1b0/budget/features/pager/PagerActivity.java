package com.th1b0.budget.features.pager;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityMainBinding;
import com.th1b0.budget.features.budget.BudgetFragment;
import com.th1b0.budget.features.settings.SettingsActivity;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.features.wizard.TransactionFormActivity;

public final class PagerActivity extends AppCompatActivity {

  private ActivityMainBinding mView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mView = DataBindingUtil.setContentView(this, R.layout.activity_main);

    setSupportActionBar(mView.toolbar);

    final PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
    pagerAdapter.add(BudgetFragment.newInstance(), getString(R.string.budget));
    pagerAdapter.add(TransactionFragment.newInstance(), getString(R.string.transactions));

    mView.fab.setOnClickListener(v -> startActivity(TransactionFormActivity.newInstance(this)));

    mView.viewpager.setAdapter(pagerAdapter);
    mView.tabs.setupWithViewPager(mView.viewpager);
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = this.getMenuInflater();
    inflater.inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.settings:
        launchSettings();
        return true;

      default:
        return false;
    }
  }

  public void showMessage(String message) {
    Snackbar.make(mView.coordinator, message, Snackbar.LENGTH_LONG).show();
  }

  private void launchSettings() {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
  }
}
