package net.sornas.firefly.android.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.preference.PreferenceFragmentCompat;
import net.sornas.firefly.R;
import net.sornas.firefly.android.preferences.token.TokenChooserActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        findPreference("token").setOnPreferenceClickListener(preference -> {
            Intent tokenChooserIntent = new Intent(getContext(), TokenChooserActivity.class);
            startActivity(tokenChooserIntent);
            return true;
        });

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
