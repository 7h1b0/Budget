package com.th1b0.budget.features.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.th1b0.budget.R;
import com.th1b0.budget.util.DataManager;

import static com.th1b0.budget.util.Preferences.PREF_BUDGET_VALUE;

/**
 * Created by 7h1b0.
 */

public final class SettingsFragment extends PreferenceFragment
    implements SharedPreferences.OnSharedPreferenceChangeListener {

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    addPreferencesFromResource(R.xml.preferences);

    initializeBudgetValue();
  }

  @Override public void onResume() {
    super.onResume();
    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
  }

  @Override public void onPause() {
    super.onPause();
    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
  }

  private void initializeBudgetValue() {
    final EditTextPreference prefBudgetValue =
        (EditTextPreference) findPreference(PREF_BUDGET_VALUE);
    prefBudgetValue.setSummary(prefBudgetValue.getText() + "€");
    prefBudgetValue.setOnPreferenceChangeListener((Preference preference, Object newValue) -> {
      prefBudgetValue.setSummary(String.valueOf(newValue) + "€");
      return true;
    });
  }

  @Override public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    DataManager.getInstance(getActivity()).preferenceChange(key);
  }
}
