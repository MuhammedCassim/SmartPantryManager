package com.example.smartpantrymanager;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AddEditIngredientActivity extends AppCompatActivity {

    private EditText editName;
    private EditText editQuantity;
    private EditText editUnit;
    private EditText editExpiry;

    private DatabaseHelper databaseHelper;

    private int itemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        // link the form fields
        editName = findViewById(R.id.editName);
        editQuantity = findViewById(R.id.editQuantity);
        editUnit = findViewById(R.id.editUnit);
        editExpiry = findViewById(R.id.editExpiry);
        Button btnSave = findViewById(R.id.btnSave);

        databaseHelper = new DatabaseHelper(this);

        // check if item is being edited
        if (getIntent().hasExtra("id")) {
            itemId = getIntent().getIntExtra("id", -1);

            String name = getIntent().getStringExtra("name");
            double quantity = getIntent().getDoubleExtra("quantity", 0);
            String unit = getIntent().getStringExtra("unit");
            String expiry = getIntent().getStringExtra("expiry");

            editName.setText(name);
            editQuantity.setText(String.valueOf(quantity));
            editUnit.setText(unit);
            editExpiry.setText(expiry);

            btnSave.setText("Update Ingredient");
        }

        // open date picker
        editExpiry.setOnClickListener(v -> showDatePicker());

        // save ingredient
        btnSave.setOnClickListener(v -> saveIngredient());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    editExpiry.setText(date);
                },
                year,
                month,
                day
        );

        dialog.show();
    }

    private void saveIngredient() {
        String name = editName.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();
        String unit = editUnit.getText().toString().trim();
        String expiryDate = editExpiry.getText().toString().trim();

        // basic validation
        if (name.isEmpty()) {
            editName.setError("enter ingredient name");
            return;
        }

        if (quantityText.isEmpty()) {
            editQuantity.setError("enter quantity");
            return;
        }

        if (unit.isEmpty()) {
            editUnit.setError("enter unit");
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            editQuantity.setError("enter a valid quantity");
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("quantity must be above 0");
            return;
        }

        PantryItem item = new PantryItem(
                itemId,
                name,
                quantity,
                unit,
                expiryDate
        );

        if (itemId == -1) {

            // add new item
            long result = databaseHelper.addPantryItem(item);

            if (result != -1) {
                Toast.makeText(this, "ingredient saved", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "could not save ingredient", Toast.LENGTH_SHORT).show();
            }

        } else {

            // update item
            int result = databaseHelper.updatePantryItem(item);

            if (result > 0) {
                Toast.makeText(this, "ingredient updated", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "could not update ingredient", Toast.LENGTH_SHORT).show();
            }
        }
    }
}