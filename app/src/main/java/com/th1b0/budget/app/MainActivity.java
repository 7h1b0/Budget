package com.th1b0.budget.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityMainBinding;
import com.th1b0.budget.features.budgetform.BudgetFormActivity;
import com.th1b0.budget.features.budgetmonth.BudgetMonthFragment;
import com.th1b0.budget.features.budgets.BudgetFragment;
import com.th1b0.budget.features.categories.CategoryFragment;
import com.th1b0.budget.features.history.HistoryFragment;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.features.transactionform.TransactionFormActivity;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DataManager;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;

public final class MainActivity extends AppCompatActivity
    implements Toolbar, BottomNavigationView.OnNavigationItemSelectedListener,
    FragmentManager.OnBackStackChangedListener {

  private static final String ACTION_ADD_BUDGET = "com.th1b0.budget.ADD_BUDGET";
  private static final String ACTION_ADD_TRANSACTION = "com.th1b0.budget.ADD_TRANSACTION";

  public static final String TOOLBAR_TITLE = "toolbar_title";

  private ActivityMainBinding mView;
  private Disposable mSubscription;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);
    mView = DataBindingUtil.setContentView(this, R.layout.activity_main);

    initializeToolbar();
    initializeBottomNavigationView();
    handleFirstLaunch();
    getFragmentManager().addOnBackStackChangedListener(this);

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
      displayFragment(BudgetMonthFragment.newInstance());
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

  @Override protected void onStop() {
    super.onStop();
    if (mSubscription != null) {
      mSubscription.dispose();
    }
  }

  private void initializeToolbar() {
    setSupportActionBar(mView.included.toolbar);
  }

  private void initializeBottomNavigationView() {
    BottomNavigationView navigation = mView.navigation;
    navigation.setOnNavigationItemSelectedListener(this);
  }

  private void displayFragment(@NonNull Fragment fragment) {
    getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
  }

  @Override public void setToolbarTitle(CharSequence title) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(title);
    }
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        FragmentManager fm = getFragmentManager();
        fm.popBackStack();
        return true;

      default:
        return false;
    }
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    clearBackStack();

    switch (item.getItemId()) {
      case R.id.home:
        displayFragment(BudgetMonthFragment.newInstance());
        return true;

      case R.id.transactions:
        displayFragment(TransactionFragment.newInstance());
        return true;

      case R.id.categories:
        displayFragment(CategoryFragment.newInstance());
        return true;

      case R.id.budget:
        displayFragment(BudgetFragment.newInstance());
        return true;

      case R.id.history:
        displayFragment(HistoryFragment.newInstance());
        return true;

      default:
        return true;
    }
  }

  private void handleFirstLaunch() {
    mSubscription = DataManager.getInstance(this)
        .isDatabaseEmpty()
        .filter(isDatabaseEmpty -> isDatabaseEmpty)
        .doOnNext(ignored -> initializeDatabase())
        .subscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
        .subscribe();
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

  private void clearBackStack() {
    FragmentManager fm = getFragmentManager();
    for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
      fm.popBackStack();
    }
  }

  @Override public void onBackStackChanged() {
    final boolean enableArrow = getFragmentManager().getBackStackEntryCount() > 0;
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(enableArrow);
    }
  }
}
