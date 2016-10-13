package com.th1b0.budget.features.wizard;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityTransactionWizardBinding;
import com.th1b0.budget.model.Transaction;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DateUtil;

/**
 * Created by 7h1b0.
 */

public class TransactionFormActivity extends AppCompatActivity
    implements DatePickerDialog.OnDateSetListener, CategoryDialog.OnCategorySet {

  private ActivityTransactionWizardBinding mView;
  private Transaction mTransaction;
  private WizardTransactionPresenter mPresenter;

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
    mView = DataBindingUtil.setContentView(this, R.layout.activity_transaction_wizard);
    mPresenter = new WizardTransactionPresenterImpl(this, DataManager.getInstance(this));

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
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.wizard_transaction, menu);
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

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putParcelable(Transaction.TRANSACTION, mTransaction);
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
    updateCategory();
  }

  private void updateDate() {
    mView.date.setText(DateUtil.formatDate(mTransaction.getYear(), mTransaction.getMonth(),
        mTransaction.getDay()));
  }

  private void updateCategory() {
    String[] categories = getResources().getStringArray(R.array.categories_title);
    mView.categorie.setText(categories[mTransaction.getCategory()]);
  }

  private void setupListener() {
    mView.dateLayout.setOnClickListener(v -> {
      AlertDialog dialog =
          new DatePickerDialog(this, this, mTransaction.getYear(), mTransaction.getMonth(),
              mTransaction.getDay());
      dialog.show();
    });

    mView.categorieLayout.setOnClickListener(
        v -> CategoryDialog.newInstance(mTransaction.getCategory())
            .show(getFragmentManager(), null));
  }

  private boolean isFormValid() {
    boolean isValid = true;
    if (mView.description.getText().length() == 0) {
      mView.descriptionInputLayout.setErrorEnabled(true);
      mView.descriptionInputLayout.setError(getString(R.string.no_empty_field));
      isValid = false;
    } else {
      mView.valueInputLayout.setErrorEnabled(false);
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

  @Override public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
    mTransaction.setYear(year);
    mTransaction.setMonth(month);
    mTransaction.setDay(dayOfMonth);
    updateDate();
  }

  @Override public void onCategorySet(int category) {
    mTransaction.setCategory(category);
    updateCategory();
  }
}
