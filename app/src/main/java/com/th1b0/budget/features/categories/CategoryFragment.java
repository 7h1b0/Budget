package com.th1b0.budget.features.categories;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.th1b0.budget.R;
import com.th1b0.budget.databinding.FragmentRecyclerViewBinding;
import com.th1b0.budget.features.categoryform.CategoryFormActivity;
import com.th1b0.budget.model.Category;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.DividerItemDecoration;
import java.util.ArrayList;

/**
 * Created by 7h1b0
 */

public final class CategoryFragment extends Fragment
    implements CategoryView, CategoryAdapter.OnCategoryClick {

  public static final int CONFIRM_DELETE = 2;

  private CategoryPresenter mPresenter;
  private CategoryAdapter mAdapter;
  private FragmentRecyclerViewBinding mView;

  public static CategoryFragment newInstance() {
    return new CategoryFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new CategoryPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new CategoryAdapter(getActivity(), this);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    mView = DataBindingUtil.inflate(inflater, R.layout.fragment_recycler_view, container, false);
    return mView.getRoot();
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initializeRecycler();
    initializeFAB();

    mPresenter.loadCategory();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case CONFIRM_DELETE:
        if (resultCode == Activity.RESULT_OK && data.hasExtra(Category.CATEGORY)) {
          Category category = data.getParcelableExtra(Category.CATEGORY);
          mPresenter.deleteCategory(category);
        }
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detach();
  }

  @Override public void onCategoryLoaded(ArrayList<Category> categories) {
    mAdapter.addAll(categories);
  }

  @Override public void onError(String error) {
    Snackbar.make(mView.coordinator, error, Snackbar.LENGTH_LONG).show();
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

    view.findViewById(R.id.delete).setOnClickListener(v -> {
      ConfirmCategoryDeletionDialog.newInstance(category, this, CONFIRM_DELETE)
          .show(getFragmentManager(), null);
      dialog.dismiss();
    });
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.addItemDecoration(new DividerItemDecoration(getActivity()));
    mView.recycler.setAdapter(mAdapter);
  }

  private void initializeFAB() {
    mView.fab.setOnClickListener(
        v -> startActivity(CategoryFormActivity.newInstance(getActivity())));
  }
}
