package com.th1b0.budget.features.pager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Created by 7h1b0.
 */
final class PagerAdapter extends FragmentPagerAdapter {

  private ArrayList<Fragment> mPages;
  private ArrayList<String> mTitles;

  PagerAdapter(FragmentManager fm) {
    super(fm);
    mPages = new ArrayList<>(3);
    mTitles = new ArrayList<>(3);
  }

  public void add(Fragment fragment, String title) {
    mTitles.add(title);
    mPages.add(fragment);
  }

  @Override public int getCount() {
    return mPages.size();
  }

  @Override public Fragment getItem(int position) {
    return mPages.get(position);
  }

  @Override public CharSequence getPageTitle(int position) {
    return mTitles.get(position);
  }
}

