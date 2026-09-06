package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchExpiryAlerts;
    private SharedPreferences preferences;

    private static final String PREFS_NAME = "pantry_settings";
    private static final String EXPIRY_ALERTS = "expiry_alerts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        switchExpiryAlerts = findViewById(R.id.switchExpiryAlerts);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        // load saved settings
        preferences = getSharedPreferences(
                PREFS_NAME,
                MODE_PRIVATE
        );

        boolean alertsEnabled =
                preferences.getBoolean(EXPIRY_ALERTS, true);

        switchExpiryAlerts.setChecked(alertsEnabled);

        // save alert setting
        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) -> {

                    SharedPreferences.Editor editor =
                            preferences.edit();

                    editor.putBoolean(EXPIRY_ALERTS, isChecked);
                    editor.apply();

                    if (isChecked) {
                        Toast.makeText(
                                this,
                                "expiry alerts on",
                                Toast.LENGTH_SHORT
                        ).show();
                    } else {
                        Toast.makeText(
                                this,
                                "expiry alerts off",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                }
        );

        bottomNavigation.setSelectedItemId(R.id.navSettings);

        // bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navPantry) {
                Intent intent = new Intent(
                        SettingsActivity.this,
                        MainActivity.class
                );

                startActivity(intent);
                finish();
                return true;
            }

            if (itemId == R.id.navRecipes) {
                Intent intent = new Intent(
                        SettingsActivity.this,
                        SuggestedRecipesActivity.class
                );

                startActivity(intent);
                finish();
                return true;
            }

            if (itemId == R.id.navSettings) {
                return true;
            }

            return false;
        });
    }
}