package com.th1b0.budget.features.container;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.th1b0.budget.R;
import com.th1b0.budget.features.containerform.ContainerFormActivity;
import com.th1b0.budget.model.Container;
import com.th1b0.budget.util.ConfirmDeletionDialog;
import com.th1b0.budget.util.DataManager;
import com.th1b0.budget.util.FragmentRecycler;
import com.th1b0.budget.util.SimpleItemAdapter;
import java.util.ArrayList;
import rx.Subscription;

/**
 * Created by 7h1b0.
 */

public class ContainerFragment
    extends FragmentRecycler<ContainerPresenter, SimpleItemAdapter<Container>>
    implements ContainerView {

  private Subscription mSubscription;

  public static ContainerFragment newInstance() {
    return new ContainerFragment();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    mPresenter = new ContainerPresenterImpl(this, DataManager.getInstance(getActivity()));
    mAdapter = new SimpleItemAdapter<>(getActivity());
  }

  @Override public void onViewCreated(View view, Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    initializeRecycler();
    initializeFAB();

    mSubscription = mAdapter.onClick().subscribe(this::onContainerClick);
    mPresenter.loadContainers();
  }

  @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
    switch (requestCode) {
      case CONFIRM_DELETE:
        if (resultCode == Activity.RESULT_OK && data.hasExtra(ConfirmDeletionDialog.PARCELABLE)) {
          Container container = data.getParcelableExtra(ConfirmDeletionDialog.PARCELABLE);
          mPresenter.deleteContainer(container);
        }
        break;
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (mSubscription != null) {
      mSubscription.unsubscribe();
    }
  }

  private void initializeRecycler() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
    mView.recycler.setLayoutManager(layoutManager);
    mView.recycler.setAdapter(mAdapter);
  }

  private void initializeFAB() {
    mView.fab.setOnClickListener(
        v -> startActivity(ContainerFormActivity.newInstance(getActivity())));
  }

  @Override public void onContainersLoaded(ArrayList<Container> containers) {
    mAdapter.addAll(containers);
    if (containers.isEmpty()) {
      mView.included.text.setText(getString(R.string.no_budget));
      mView.included.noItem.setVisibility(View.VISIBLE);
    } else {
      mView.included.noItem.setVisibility(View.GONE);
    }
  }

  @Override public void onError(String error) {
    super.onError(error);
  }

  private void onContainerClick(@NonNull Container container) {
    View view = View.inflate(getActivity(), R.layout.bottomsheet_edit, null);
    BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
    dialog.setContentView(view);
    dialog.show();

    view.findViewById(R.id.edit).setOnClickListener(v -> {
      startActivity(ContainerFormActivity.newInstance(getActivity(), container));
      dialog.dismiss();
    });

    view.findViewById(R.id.delete).setOnClickListener(v -> {
      String title = getString(R.string.confirm_containers_deletion_title);
      String msg = getString(R.string.confirm_containers_deletion);
      ConfirmDeletionDialog.newInstance(title, msg, container, this, CONFIRM_DELETE)
          .show(getFragmentManager(), null);
      dialog.dismiss();
    });
  }
}
