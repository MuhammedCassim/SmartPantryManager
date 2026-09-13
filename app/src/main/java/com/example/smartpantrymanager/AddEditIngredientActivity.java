package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editQuantity;
    private Spinner editUnit;
    private EditText editExpiry;

    private DatabaseHelper databaseHelper;

    private int itemId = -1;
    private String currentUnit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);

        Button btnSaveIngredient =
                findViewById(R.id.btnSaveIngredient);

        databaseHelper = new DatabaseHelper(this);

        // load item if editing
        if (getIntent().hasExtra("id")) {

            itemId =
                    getIntent().getIntExtra("id", -1);

            String name =
                    getIntent().getStringExtra("name");

            double quantity =
                    getIntent().getDoubleExtra(
                            "quantity",
                            0
                    );

            currentUnit =
                    getIntent().getStringExtra("unit");

            String expiry =
                    getIntent().getStringExtra("expiry");

            editName.setText(name);
            editQuantity.setText(
                    String.valueOf(quantity)
            );

            if (expiry != null) {
                editExpiry.setText(expiry);
            }

            btnSaveIngredient.setText(
                    "Update Ingredient"
            );

            updateUnitOptions(
                    name,
                    currentUnit
            );

        } else {

            updateUnitOptions(
                    "",
                    ""
            );
        }

        // change units when ingredient changes
        editName.addTextChangedListener(
                new TextWatcher() {

                    @Override
                    public void beforeTextChanged(
                            CharSequence s,
                            int start,
                            int count,
                            int after) {
                    }

                    @Override
                    public void onTextChanged(
                            CharSequence s,
                            int start,
                            int before,
                            int count) {

                        updateUnitOptions(
                                s.toString(),
                                ""
                        );
                    }

                    @Override
                    public void afterTextChanged(
                            Editable s) {
                    }
                }
        );

        // choose expiry date
        editExpiry.setOnClickListener(v -> {

            Calendar calendar =
                    Calendar.getInstance();

            int year =
                    calendar.get(Calendar.YEAR);

            int month =
                    calendar.get(Calendar.MONTH);

            int day =
                    calendar.get(
                            Calendar.DAY_OF_MONTH
                    );

            DatePickerDialog datePickerDialog =
                    new DatePickerDialog(
                            this,
                            (view,
                             selectedYear,
                             selectedMonth,
                             selectedDay) -> {

                                String date =
                                        selectedDay
                                                + "/"
                                                + (selectedMonth + 1)
                                                + "/"
                                                + selectedYear;

                                editExpiry.setText(date);
                            },
                            year,
                            month,
                            day
                    );

            datePickerDialog.show();
        });

        // save ingredient
        btnSaveIngredient.setOnClickListener(v -> {

            String name =
                    editName
                            .getText()
                            .toString()
                            .trim();

            String quantityText =
                    editQuantity
                            .getText()
                            .toString()
                            .trim();

            String expiry =
                    editExpiry
                            .getText()
                            .toString()
                            .trim();

            if (name.isEmpty()) {

                editName.setError(
                        "enter ingredient name"
                );

                return;
            }

            if (quantityText.isEmpty()) {

                editQuantity.setError(
                        "enter quantity"
                );

                return;
            }

            double quantity;

            try {

                quantity =
                        Double.parseDouble(
                                quantityText
                        );

            } catch (NumberFormatException e) {

                editQuantity.setError(
                        "enter a valid quantity"
                );

                return;
            }

            if (quantity <= 0) {

                editQuantity.setError(
                        "quantity must be more than 0"
                );

                return;
            }

            if (editUnit.getSelectedItem()
                    == null) {

                Toast.makeText(
                        this,
                        "select a unit",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            String unit =
                    editUnit
                            .getSelectedItem()
                            .toString();

            PantryItem pantryItem =
                    new PantryItem(
                            itemId,
                            name,
                            quantity,
                            unit,
                            expiry
                    );

            if (itemId == -1) {

                databaseHelper.addPantryItem(
                        pantryItem
                );

                Toast.makeText(
                        this,
                        "ingredient saved",
                        Toast.LENGTH_SHORT
                ).show();

            } else {

                databaseHelper.updatePantryItem(
                        pantryItem
                );

                Toast.makeText(
                        this,
                        "ingredient updated",
                        Toast.LENGTH_SHORT
                ).show();
            }

            finish();
        });
    }

    private void updateUnitOptions(
            String ingredientName,
            String selectedUnit) {

        // get units for ingredient
        ArrayList<String> units =
                databaseHelper
                        .getUnitsForIngredient(
                                ingredientName
                        );

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout
                                .simple_spinner_item,
                        units
                );

        adapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item
        );

        editUnit.setAdapter(adapter);

        // keep saved unit when editing
        if (selectedUnit != null
                && !selectedUnit.isEmpty()) {

            int position =
                    units.indexOf(
                            selectedUnit
                    );

            if (position >= 0) {
                editUnit.setSelection(position);
            }
        }
    }
}