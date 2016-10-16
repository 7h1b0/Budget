package com.th1b0.budget.features.categories;

import com.th1b0.budget.model.Category;
import java.util.ArrayList;

/**
 * Created by 7h1b0
 */

interface CategoryView {

  void onCategoryLoaded(ArrayList<Category> categories);

  void onError(String error);
}
