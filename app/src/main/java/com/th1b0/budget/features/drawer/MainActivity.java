package com.th1b0.budget.features.drawer;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Fragment;
import android.app.FragmentManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityMainBinding;
import com.th1b0.budget.features.budgetform.BudgetFormActivity;
import com.th1b0.budget.features.budgets.BudgetFragment;
import com.th1b0.budget.features.categories.CategoryFragment;
import com.th1b0.budget.features.history.HistoryFragment;
import com.th1b0.budget.features.pager.PagerFragment;
import com.th1b0.budget.features.transaction.TransactionFragment;
import com.th1b0.budget.features.transactionform.TransactionFormActivity;
import com.th1b0.budget.model.Budget;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DataManager;
import java.util.ArrayList;
import rx.Subscription;
import rx.schedulers.Schedulers;

public final class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    FragmentManager.OnBackStackChangedListener {

  private static final String ACTION_ADD_BUDGET = "com.th1b0.budget.ADD_BUDGET";
  private static final String ACTION_ADD_TRANSACTION = "com.th1b0.budget.ADD_TRANSACTION";

  public static final String TOOLBAR_TITLE = "toolbar_title";

  private ActivityMainBinding mView;
  private Subscription mSubscription;
  private ActionBarDrawerToggle mDrawerToggle;

  @Override protected void onCreate(Bundle savedInstanceState) {
    setTheme(R.style.AppTheme);
    super.onCreate(savedInstanceState);
    mView = DataBindingUtil.setContentView(this, R.layout.activity_main);

    initializeToolbar();
    initializeNavigationView();
    initializeDrawer();
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
      display(PagerFragment.newInstance(), null);
    } else {
      this.onBackStackChanged();
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
      mSubscription.unsubscribe();
    }
  }

  private void initializeToolbar() {
    setSupportActionBar(mView.included.toolbar);
  }

  private void initializeNavigationView() {
    mView.navigationView.setNavigationItemSelectedListener(this);
  }

  private void initializeDrawer() {
    mDrawerToggle = new ActionBarDrawerToggle(this, mView.drawer, mView.included.toolbar, 0, 0);
    mView.drawer.addDrawerListener(mDrawerToggle);
    mDrawerToggle.syncState();
    mDrawerToggle.setToolbarNavigationClickListener(v -> onBackPressed());
  }

  private void display(@NonNull Fragment fragment, CharSequence title) {
    setToolbarTitle(title);
    displayFragment(fragment);
  }

  private void displayFragment(@NonNull Fragment fragment) {
    getFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
  }

  public void setToolbarTitle(CharSequence title) {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setTitle(title);
    }
  }

  @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
    mView.drawer.closeDrawers();

    if (getFragmentManager().getBackStackEntryCount() > 0) {
      enableDrawerIndicator();
    }

    switch (item.getItemId()) {
      case R.id.home:
        display(PagerFragment.newInstance(), null);
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

  private void disableDrawerIndicator() {
    mDrawerToggle.setDrawerIndicatorEnabled(false);
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  private void enableDrawerIndicator() {
    if (getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    mDrawerToggle.setDrawerIndicatorEnabled(true);
  }

  @Override public void onBackStackChanged() {
    final boolean enableArrow = getFragmentManager().getBackStackEntryCount() > 0;

    ObjectAnimator animator = ObjectAnimator.ofFloat(mDrawerToggle.getDrawerArrowDrawable(), "progress", enableArrow ? 1f : 0f);
    animator.addListener(new Animator.AnimatorListener() {
          @Override public void onAnimationStart(Animator animator) {
            if (!enableArrow) {
              enableDrawerIndicator();
            }
          }

          @Override public void onAnimationEnd(Animator animator) {
            if (enableArrow) {
              disableDrawerIndicator();
            }
          }

          @Override public void onAnimationCancel(Animator animator) {

          }

          @Override public void onAnimationRepeat(Animator animator) {

          }
        });
    animator.setDuration(150).start();
  }

  @Override public void onBackPressed() {
    if (mView.drawer.isDrawerOpen(GravityCompat.START)) {
      mView.drawer.closeDrawers();
    }
    super.onBackPressed();
  }
}
