package com.th1b0.budget.features.transactionform;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityTransactionFormBinding;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.ContainerPickerDialog;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

public final class TransactionFormActivity extends AppCompatActivity
    implements DatePickerDialog.OnDateSetListener, CategoryDialog.OnCategorySet,
    TransactionFormView, ContainerPickerDialog.OnContainerSet {

  private ActivityTransactionFormBinding mView;
  private Transaction mTransaction;
  private TransactionFormPresenter mPresenter;
  private ArrayList<Category> mCategories;
  private ArrayList<Container> mContainers;

  public static Intent newInstance(@NonNull Context context) {
    return new Intent(context, TransactionFormActivity.class);
  }

  public static Intent newInstance(@NonNull Context context, @Nullable Transaction transaction) {
    Intent intent = new Intent(context, TransactionFormActivity.class);
    intent.putExtra(Transaction.TRANSACTION, transaction);
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mView = DataBindingUtil.setContentView(this, R.layout.activity_transaction_form);
    mPresenter = new TransactionFormPresenterImpl(this, DataManager.getInstance(this));
    mCategories = new ArrayList<>();
    mContainers = new ArrayList<>();

    if (savedInstanceState != null) {
      mTransaction = savedInstanceState.getParcelable(Transaction.TRANSACTION);
    } else if (isEditMode()) {
      mTransaction = getIntent().getExtras().getParcelable(Transaction.TRANSACTION);
    } else {
      mTransaction = new Transaction();
    }

    setupToolbar();
    fillForm();
    setupListener();

    if (!isCategoriesAndContainersLoaded(savedInstanceState)) {
      mPresenter.loadCategoriesAndContainers();
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.wizard, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        return true;

      case R.id.save:
        if (isFormValid()) {
          updateTransactionFromForm();
          if (isEditMode()) {
            mPresenter.updateTransaction(mTransaction);
          } else {
            mPresenter.addTransaction(mTransaction);
          }
          finish();
        }
        return true;

      default:
        return false;
    }
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mCategories = savedInstanceState.getParcelableArrayList(Category.CATEGORIES);
    mContainers = savedInstanceState.getParcelableArrayList(Container.CONTAINERS);
    updateCategory();
    updateContainer();
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(Transaction.TRANSACTION, mTransaction);
    outState.putParcelableArrayList(Category.CATEGORIES, mCategories);
    outState.putParcelableArrayList(Container.CONTAINERS, mContainers);
  }

  @Override protected void onStop() {
    super.onStop();
    mPresenter.detach();
  }

  private void setupToolbar() {
    setSupportActionBar(mView.included.toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);
      if (isEditMode()) {
        getSupportActionBar().setTitle(R.string.edit_transaction);
      } else {
        getSupportActionBar().setTitle(R.string.add_transaction);
      }
    }
  }

  private void fillForm() {
    mView.description.setText(mTransaction.getDescription());
    if (mTransaction.getValue() != 0) {
      mView.value.setText(String.valueOf(mTransaction.getValue()));
    }
    updateDate();
  }

  private void updateDate() {
    mView.date.setText(DateUtil.formatDate(mTransaction.getYear(), mTransaction.getMonth(),
        mTransaction.getDay()));
  }

  private void updateCategory() {
    int position = findCategoryPosition(mTransaction.getIdCategory());
    if (position > -1) {
      mView.category.setText(mCategories.get(position).getTitle());
    }
  }

  private void updateContainer() {
    int position = findContainerPosition(mTransaction.getIdContainer());
    if (position > -1) {
      mView.container.setText(mContainers.get(position).getTitle());
    }
  }

  private void setupListener() {
    mView.dateLayout.setOnClickListener(v -> {
      AlertDialog dialog =
          new DatePickerDialog(this, this, mTransaction.getYear(), mTransaction.getMonth(),
              mTransaction.getDay());
      dialog.show();
    });

    mView.categoryLayout.setOnClickListener(v -> {
      int position = findCategoryPosition(mTransaction.getIdCategory());
      CategoryDialog.newInstance(mCategories, position).show(getFragmentManager(), null);
    });

    mView.containerLayout.setOnClickListener(v -> {
      int position = findContainerPosition(mTransaction.getIdContainer());
      ContainerPickerDialog.newInstance(mContainers, position).show(getFragmentManager(), null);
    });
  }

  private void updateTransactionFromForm() {
    mTransaction.setDescription(mView.description.getText().toString());

    try {
      double value = Double.parseDouble(mView.value.getText().toString());
      mTransaction.setValue(value);
    } catch (NumberFormatException e) {
      // Don't update transaction value;
    }
  }

  private boolean isEditMode() {
    return getIntent().hasExtra(Transaction.TRANSACTION)
        && getIntent().getExtras().getParcelable(Transaction.TRANSACTION) != null;
  }

  private boolean isFormValid() {
    boolean isValid = true;
    if (mView.description.getText().length() == 0) {
      mView.descriptionInputLayout.setErrorEnabled(true);
      mView.descriptionInputLayout.setError(getString(R.string.no_empty_field));
      isValid = false;
    } else {
      mView.descriptionInputLayout.setErrorEnabled(false);
    }

    if (mView.value.getText().length() == 0) {
      mView.valueInputLayout.setErrorEnabled(true);
      mView.valueInputLayout.setError(getString(R.string.no_empty_field));
      isValid = false;
    } else {
      mView.valueInputLayout.setErrorEnabled(false);
    }

    return isValid;
  }

  private boolean isCategoriesAndContainersLoaded(@Nullable Bundle savedInstanceState) {
    return savedInstanceState != null
        && savedInstanceState.getParcelableArray(Category.CATEGORY) != null
        && savedInstanceState.getParcelableArray(Container.CONTAINERS) != null;
  }

  @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    mTransaction.setYear(year);
    mTransaction.setMonth(month);
    mTransaction.setDay(dayOfMonth);
    updateDate();
  }

  @Override public void onCategorySet(@NonNull Category category) {
    mTransaction.setIdCategory(category.getId());
    mTransaction.setIdContainer(category.getIdContainer());
    updateCategory();
    updateContainer();
  }

  @Override public void onContainerSet(@NonNull Container container) {
    mTransaction.setIdContainer(container.getId());
    mView.container.setText(container.getTitle());
  }

  @Override public void onCategoriesLoaded(ArrayList<Category> categories) {
    mCategories = categories;

    Category category;
    int positionCategory = findCategoryPosition(mTransaction.getIdCategory());
    if (positionCategory == -1) {
      category = categories.get(0);
      mTransaction.setIdCategory(category.getId());
    } else {
      category = mCategories.get(positionCategory);
    }

    if (!mTransaction.isContainerIdDefined()) {
      if (category.isContainerIdDefined()) {
        mTransaction.setIdContainer(category.getIdContainer());
      } else {
        mTransaction.setIdContainer(mContainers.get(0).getId());
      }
    }

    updateCategory();
    updateContainer();
  }

  @Override public void onContainersLoaded(ArrayList<Container> containers) {
    mContainers = containers;
  }

  @Override public void onError(String error) {
    Snackbar.make(mView.coordinator, error, Snackbar.LENGTH_LONG).show();
  }

  @NonNull @Override public Context getContext() {
    return this;
  }

  private int findCategoryPosition(long selected) {
    for (int index = 0; index < mCategories.size(); index++) {
      if (mCategories.get(index).getId() == selected) {
        return index;
      }
    }
    return -1;
  }

  private int findContainerPosition(long selected) {
    for (int index = 0; index < mContainers.size(); index++) {
      if (mContainers.get(index).getId() == selected) {
        return index;
      }
    }
    return -1;
  }
}
