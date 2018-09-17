package com.adityathakker.egyaan.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.adityathakker.egyaan.R;
import com.adityathakker.egyaan.ui.activities.LoginActivity;
import com.adityathakker.egyaan.utils.AppConst;
import com.adityathakker.egyaan.utils.CommonTasks;

public class SettingsActivity extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {

        private static final String TAG = MainPreferenceFragment.class.getSimpleName();
        Preference preferenceLogout;
        SwitchPreference switchPreferenceToggle;

        public MainPreferenceFragment() {
            super();
        }

        public static void setDefaults(String key, String value, Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(key, value);
            editor.apply();
        }

        public static String getDefaults(String key, Context context) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            return preferences.getString(key, null);
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting_preferences);

            switchPreferenceToggle = (SwitchPreference) findPreference("switch_toggle");
            setDefaults("switchPreference", "true", getActivity());
            switchPreferenceToggle.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object status) {
                    if (status.equals(true)) {
//                        Toast.makeText(getActivity(), "True", Toast.LENGTH_SHORT).show();
                        setDefaults("switchPreference", status.toString(), getActivity());
                    } else {
//                        Toast.makeText(getActivity(), "False", Toast.LENGTH_SHORT).show();
                        setDefaults("switchPreference", status.toString(), getActivity());
                    }
                    return true;
                }
            });

            preferenceLogout = findPreference(getString(R.string.key_log_out));
            preferenceLogout.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    if (logoutChecking()) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        Toast.makeText(getActivity(), AppConst.Messages.SUCCESSFUL_LOG_OUT, Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    } else {
                        Toast.makeText(getActivity(), AppConst.Messages.ERROR_SUCCESSFUL_LOG_OUT, Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
        }

        public boolean logoutChecking() {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences(AppConst.Extras.PROJ_NAME, MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            getActivity().finish();
            return true;
        }
    }
}
