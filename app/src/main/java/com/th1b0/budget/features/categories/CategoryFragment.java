package com.th1b0.budget.features.categories;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.th1b0.budget.R;
import com.th1b0.budget.features.categoryform.CategoryFormActivity;
import com.th1b0.budget.features.drawer.Toolbar;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.ConfirmDeletionDialog;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.FragmentRecycler;
import java.util.ArrayList;

/**
 * Created by 7h1b0
 */

public final class CategoryFragment extends FragmentRecycler<CategoryPresenter, CategoryAdapter>
    implements CategoryView, CategoryAdapter.OnCategoryClick {

  public static CategoryFragment newInstance() {
    return new CategoryFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new CategoryPresenterImpl(DataManager.getInstance(getActivity()));
    mAdapter = new CategoryAdapter(this);
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initializeRecycler();
    initializeFAB();
    initializeToolbar();

    mPresenter.attach(this);
    mPresenter.loadCategory();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case CONFIRM_DELETE:
        if (resultCode == Activity.RESULT_OK
            && data.hasExtra(ConfirmDeletionDialog.PARCELABLE)
            && !mAdapter.hasOnlyOneElement()) {
          Category category = data.getParcelableExtra(ConfirmDeletionDialog.PARCELABLE);
          mPresenter.deleteCategory(category);
        }
        break;
    }
  }

  @Override public void onCategoryLoaded(@NonNull ArrayList<Category> categories) {
    mAdapter.addAll(categories);
  }

  @Override public void onError(String error) {
    super.onError(error);
  }

  @Override public void onCategoryClick(@NonNull Category category) {
    View view = View.inflate(getActivity(), R.layout.bottomsheet_edit, null);
    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
    dialog.setContentView(view);
    dialog.show();

    view.findViewById(R.id.edit).setOnClickListener(v -> {
      startActivity(CategoryFormActivity.newInstance(getActivity(), category));
      dialog.dismiss();
    });

    if (mAdapter.hasOnlyOneElement()) {
      view.findViewById(R.id.delete).setVisibility(View.GONE);
    } else {
      view.findViewById(R.id.delete).setOnClickListener(v -> {
        String title = getString(R.string.confirm_category_deletion_title);
        String msg = getString(R.string.confirm_category_deletion);
        ConfirmDeletionDialog.newInstance(title, msg, category, this, CONFIRM_DELETE)
            .show(getFragmentManager(), null);
        dialog.dismiss();
      });
    }
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.setAdapter(mAdapter);
  }

  private void initializeFAB() {
    mView.fab.setOnClickListener(
        v -> startActivity(CategoryFormActivity.newInstance(getActivity())));
  }

  private void initializeToolbar() {
    Toolbar toolbar = (Toolbar) getActivity();
    toolbar.setToolbarTitle(getString(R.string.categories));
  }
}
