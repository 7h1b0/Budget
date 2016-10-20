package com.th1b0.budget.features.containerform;

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
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.ActivityContainerFormBinding;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.DataManager;

/**
 * Created by 7h1b0.
 */

public class ContainerFormActivity extends AppCompatActivity {

  private Container mContainer;
  private ContainerFormPresenter mPresenter;
  private ActivityContainerFormBinding mView;

  public static Intent newInstance(@NonNull Context context) {
    return new Intent(context, ContainerFormActivity.class);
  }

  public static Intent newInstance(@NonNull Context context, @Nullable Container container) {
    Intent intent = new Intent(context, ContainerFormActivity.class);
    intent.putExtra(Container.CONTAINER, container);
    return intent;
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mView = DataBindingUtil.setContentView(this, R.layout.activity_container_form);
    mPresenter = new ContainerFormPresenterImpl(this, DataManager.getInstance(this));

    if (savedInstanceState != null) {
      mContainer = savedInstanceState.getParcelable(Container.CONTAINER);
    } else if (isEditMode()) {
      mContainer = getIntent().getExtras().getParcelable(Container.CONTAINER);
    } else {
      mContainer = new Container();
    }

    setupToolbar();
    fillForm();
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
          updateContainerFromForm();
          if (isEditMode()) {
            mPresenter.updateContainer(mContainer);
          } else {
            mPresenter.addContainer(mContainer);
          }
          finish();
        }
        return true;

      default:
        return false;
    }
  }

  private boolean isEditMode() {
    return getIntent().hasExtra(Container.CONTAINER)
        && getIntent().getExtras().getParcelable(Container.CONTAINER) != null;
  }

  private void setupToolbar() {
    setSupportActionBar(mView.included.toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_close);
      if (isEditMode()) {
        getSupportActionBar().setTitle(R.string.edit_container);
      } else {
        getSupportActionBar().setTitle(R.string.add_container);
      }
    }
  }

  private void fillForm() {
    mView.title.setText(mContainer.getTitle());
    if (mContainer.getValue() != 0) {
      mView.value.setText(String.valueOf(mContainer.getValue()));
    }
  }

  private boolean isFormValid() {
    boolean isValid = true;
    if (mView.title.getText().length() == 0) {
      mView.titleInputLayout.setErrorEnabled(true);
      mView.titleInputLayout.setError(getString(R.string.no_empty_field));
      isValid = false;
    } else {
      mView.titleInputLayout.setErrorEnabled(false);
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

  private void updateContainerFromForm() {
    mContainer.setTitle(mView.title.getText().toString());
    try {
      mContainer.setValue(Double.parseDouble(mView.value.getText().toString()));
    } catch (NumberFormatException e) {
      // Nothing
    }
  }
}

