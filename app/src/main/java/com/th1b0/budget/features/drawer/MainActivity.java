package com.th1b0.budget.features.drawer;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityMainBinding;
import com.th1b0.budget.features.detail.DetailFragment;
import com.th1b0.budget.features.categories.CategoryFragment;
import com.th1b0.budget.features.budget.BudgetFragment;
import com.th1b0.budget.features.budgetform.BudgetFormActivity;
import com.th1b0.budget.features.history.HistoryFragment;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.features.transactionform.TransactionFormActivity;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.Preferences;
import java.util.ArrayList;

public final class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {

  private static final String ACTION_ADD_BUDGET = "com.th1b0.budget.ADD_BUDGET";
  private static final String ACTION_ADD_TRANSACTION = "com.th1b0.budget.ADD_TRANSACTION";

  public static final String TOOLBAR_TITLE = "toolbar_title";

  private ActivityMainBinding mView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mView = DataBindingUtil.setContentView(this, R.layout.activity_main);

    initializeToolbar();
    initializeNavigationView();
    initializeDrawer();
    handleFirstLaunch();

    if (savedInstanceState == null) {
      switch (getIntent().getAction()) {
        case ACTION_ADD_BUDGET:
          startActivity(BudgetFormActivity.newInstance(this));
          break;

        case ACTION_ADD_TRANSACTION:
          startActivity(TransactionFormActivity.newInstance(this));
          break;

        default:

      }
      display(DetailFragment.newInstance(), null);
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    setToolbarTitle(savedInstanceState.getString(TOOLBAR_TITLE));
  }

  @Override protected void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    if (getSupportActionBar() != null && !TextUtils.isEmpty(getSupportActionBar().getTitle())) {
      savedInstanceState.putString(TOOLBAR_TITLE, String.valueOf(getSupportActionBar().getTitle()));
    }
  }

  private void initializeToolbar() {
    setSupportActionBar(mView.included.toolbar);
  }

  private void initializeNavigationView() {
    mView.navigationView.setNavigationItemSelectedListener(this);
  }

  private void initializeDrawer() {
    final ActionBarDrawerToggle actionBarDrawerToggle =
        new ActionBarDrawerToggle(this, mView.drawer, mView.included.toolbar, 0, 0);
    mView.drawer.addDrawerListener(actionBarDrawerToggle);
    actionBarDrawerToggle.syncState();
  }

  private void display(@NonNull Fragment fragment, CharSequence title) {
    setToolbarTitle(title);
    displayFragment(fragment);
  }

  private void displayFragment(@NonNull Fragment fragment) {
    getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
  }

  private void setToolbarTitle(CharSequence title) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(title);
    }
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    mView.drawer.closeDrawers();

    switch (item.getItemId()) {
      case R.id.home:
        display(DetailFragment.newInstance(), null);
        return true;

      case R.id.transactions:
        display(TransactionFragment.newInstance(), item.getTitle());
        return true;

      case R.id.categories:
        display(CategoryFragment.newInstance(), item.getTitle());
        return true;

      case R.id.budget:
        display(BudgetFragment.newInstance(), item.getTitle());
        return true;

      case R.id.history:
        display(HistoryFragment.newInstance(), item.getTitle());
        return true;

      default:
        return true;
    }
  }

  private void handleFirstLaunch() {
    if (Preferences.isFirstLaunch(this)) {
      initializeDatabase();
      Preferences.setFirstLaunch(this, false);
    }
  }

  private void initializeDatabase() {
    ArrayList<Budget> budgets = new ArrayList<>(5);
    budgets.add(new Budget(getString(R.string.food), 100));
    budgets.add(new Budget(getString(R.string.diner), 100));
    budgets.add(new Budget(getString(R.string.hobby), 100));
    budgets.add(new Budget(getString(R.string.shopping), 100));
    budgets.add(new Budget(getString(R.string.transport), 100));

    ArrayList<Category> categories = new ArrayList<>(5);
    categories.add(
        new Category(getString(R.string.food), ContextCompat.getColor(this, R.color.category_food),
            R.mipmap.ic_food));
    categories.add(new Category(getString(R.string.diner),
        ContextCompat.getColor(this, R.color.category_diner), R.mipmap.ic_diner));
    categories.add(new Category(getString(R.string.hobby),
        ContextCompat.getColor(this, R.color.category_hobby), R.mipmap.ic_hobby));
    categories.add(new Category(getString(R.string.shopping),
        ContextCompat.getColor(this, R.color.category_shopping), R.mipmap.ic_shopping));
    categories.add(new Category(getString(R.string.transport),
        ContextCompat.getColor(this, R.color.category_transport), R.mipmap.ic_transport));

    DataManager.getInstance(this).initializeDatabase(budgets, categories);
  }
}
