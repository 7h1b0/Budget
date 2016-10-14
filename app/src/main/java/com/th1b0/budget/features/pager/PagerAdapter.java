package com.th1b0.budget.features.pager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.NonNull;
import android.support.v13.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */

final class PagerAdapter extends FragmentPagerAdapter {

  private ArrayList<String> mTitles;
  private ArrayList<Fragment> mFragments;

  PagerAdapter(@NonNull FragmentManager fm) {
    super(fm);
    mFragments = new ArrayList<>(2);
    mTitles = new ArrayList<>(2);
  }

  public void add(@NonNull Fragment fragment, @NonNull String title) {
    mTitles.add(title);
    mFragments.add(fragment);
  }

  @Override public int getCount() {
    return mFragments.size();
  }

  @Override public Fragment getItem(int position) {
    return mFragments.get(position);
  }

  @Override public CharSequence getPageTitle(int position) {
    return mTitles.get(position);
  }
}
