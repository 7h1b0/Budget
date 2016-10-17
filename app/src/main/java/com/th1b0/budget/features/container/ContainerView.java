package com.th1b0.budget.features.container;

import com.th1b0.budget.model.Container;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

interface ContainerView {

  void onContainersLoaded(ArrayList<Container> containers);

  void onError(String error);
}
