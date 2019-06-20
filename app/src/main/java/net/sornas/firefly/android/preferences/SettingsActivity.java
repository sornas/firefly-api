package net.sornas.firefly.android.preferences;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import net.sornas.firefly.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("SettingsActivity", "onCreate");
        setContentView(R.layout.activity_settings);

        Log.v("SettingsActivity", "Creating SettingsFragment");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.preference_container, new SettingsFragment())
                .commit();
    }
}
