package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private TextView txtEmptyPantry;
    private DatabaseHelper databaseHelper;
    private PantryAdapter pantryAdapter;

    private static final String PREFS_NAME = "pantry_settings";
    private static final String EXPIRY_ALERTS = "expiry_alerts";
    private static final String LAST_EXPIRY_ALERT = "last_expiry_alert";
    private static final String LAST_EXPIRY_ITEMS = "last_expiry_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup screen items
        Button btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        recyclerPantry =
                findViewById(R.id.recyclerPantry);

        txtEmptyPantry =
                findViewById(R.id.txtEmptyPantry);

        BottomNavigationView bottomNavigation =
                findViewById(R.id.bottomNavigation);

        databaseHelper = new DatabaseHelper(this);

        // open add ingredient screen
        btnAddIngredient.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    AddEditIngredientActivity.class
            );

            startActivity(intent);
        });

        bottomNavigation.setSelectedItemId(R.id.navPantry);

        // bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navPantry) {
                return true;
            }

            if (itemId == R.id.navRecipes) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SuggestedRecipesActivity.class
                );

                startActivity(intent);
                return true;
            }

            if (itemId == R.id.navSettings) {

                Intent intent = new Intent(
                        MainActivity.this,
                        SettingsActivity.class
                );

                startActivity(intent);
                return true;
            }

            return false;
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // refresh pantry when coming back
        loadPantryItems();

        // check expiry dates
        checkExpiryAlerts();
    }

    private void loadPantryItems() {

        ArrayList<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        // show empty pantry message
        if (pantryItems.isEmpty()) {

            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);

        } else {

            txtEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }

        pantryAdapter =
                new PantryAdapter(this, pantryItems);

        recyclerPantry.setAdapter(pantryAdapter);
    }

    private void checkExpiryAlerts() {

        // get saved alert settings
        SharedPreferences preferences =
                getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        boolean alertsEnabled =
                preferences.getBoolean(EXPIRY_ALERTS, true);

        if (!alertsEnabled) {
            return;
        }

        ArrayList<PantryItem> pantryItems =
                databaseHelper.getAllPantryItems();

        StringBuilder expiringItems = new StringBuilder();

        SimpleDateFormat dateFormat =
                new SimpleDateFormat("d/M/yyyy", Locale.getDefault());

        dateFormat.setLenient(false);

        // remove time from todays date
        Calendar todayCalendar = Calendar.getInstance();

        todayCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayCalendar.set(Calendar.MINUTE, 0);
        todayCalendar.set(Calendar.SECOND, 0);
        todayCalendar.set(Calendar.MILLISECOND, 0);

        Date today = todayCalendar.getTime();

        // find items expiring soon
        for (PantryItem item : pantryItems) {

            String expiryDate = item.getExpiryDate();

            if (expiryDate == null || expiryDate.isEmpty()) {
                continue;
            }

            try {

                Date expiry = dateFormat.parse(expiryDate);

                if (expiry == null) {
                    continue;
                }

                // remove time from expiry date
                Calendar expiryCalendar = Calendar.getInstance();
                expiryCalendar.setTime(expiry);

                expiryCalendar.set(Calendar.HOUR_OF_DAY, 0);
                expiryCalendar.set(Calendar.MINUTE, 0);
                expiryCalendar.set(Calendar.SECOND, 0);
                expiryCalendar.set(Calendar.MILLISECOND, 0);

                expiry = expiryCalendar.getTime();

                long difference =
                        expiry.getTime() - today.getTime();

                long daysLeft =
                        TimeUnit.MILLISECONDS.toDays(difference);

                if (daysLeft >= 0 && daysLeft <= 3) {

                    expiringItems
                            .append("• ")
                            .append(item.getName())
                            .append(" - ")
                            .append(expiryDate)
                            .append("\n");
                }

            } catch (ParseException e) {
                // skip bad dates
            }
        }

        if (expiringItems.length() == 0) {
            return;
        }

        SimpleDateFormat alertDateFormat =
                new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        String todayValue =
                alertDateFormat.format(new Date());

        String lastAlert =
                preferences.getString(LAST_EXPIRY_ALERT, "");

        String lastItems =
                preferences.getString(LAST_EXPIRY_ITEMS, "");

        String currentItems =
                expiringItems.toString();

        // stop same alert showing again
        if (todayValue.equals(lastAlert)
                && currentItems.equals(lastItems)) {
            return;
        }

        // save last alert
        preferences.edit()
                .putString(LAST_EXPIRY_ALERT, todayValue)
                .putString(LAST_EXPIRY_ITEMS, currentItems)
                .apply();

        new AlertDialog.Builder(this)
                .setTitle("expiring soon")
                .setMessage(
                        "these items are expiring soon\n\n"
                                + expiringItems
                )
                .setPositiveButton("ok", null)
                .show();
    }
}