package com.example.smartpantrymanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    // database info
    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 1;

    // pantry table
    private static final String TABLE_PANTRY = "pantry_items";
    private static final String PANTRY_ID = "id";
    private static final String PANTRY_NAME = "name";
    private static final String PANTRY_QUANTITY = "quantity";
    private static final String PANTRY_UNIT = "unit";
    private static final String PANTRY_EXPIRY = "expiry_date";

    // recipe table
    private static final String TABLE_RECIPES = "recipes";
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String RECIPE_STEPS = "steps";

    // recipe ingredients table
    private static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";
    private static final String INGREDIENT_ID = "id";
    private static final String INGREDIENT_RECIPE_ID = "recipe_id";
    private static final String INGREDIENT_NAME = "ingredient_name";
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_UNIT = "unit";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating pantry table
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PANTRY_NAME + " TEXT NOT NULL, " +
                PANTRY_QUANTITY + " REAL NOT NULL, " +
                PANTRY_UNIT + " TEXT NOT NULL, " +
                PANTRY_EXPIRY + " TEXT)";

        // creating recipe table
        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " TEXT NOT NULL, " +
                RECIPE_STEPS + " TEXT NOT NULL)";

        // creating recipe ingredients table
        String createRecipeIngredientsTable = "CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " (" +
                INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                INGREDIENT_NAME + " TEXT NOT NULL, " +
                INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                INGREDIENT_UNIT + " TEXT NOT NULL)";

        db.execSQL(createPantryTable);
        db.execSQL(createRecipeTable);
        db.execSQL(createRecipeIngredientsTable);
    }
    // adding pantry item
    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PANTRY_NAME, item.getName());
        values.put(PANTRY_QUANTITY, item.getQuantity());
        values.put(PANTRY_UNIT, item.getUnit());
        values.put(PANTRY_EXPIRY, item.getExpiryDate());

        long result = db.insert(TABLE_PANTRY, null, values);
        db.close();

        return result;
    }

    // get all pantry items
    public ArrayList<PantryItem> getAllPantryItems() {
        ArrayList<PantryItem> pantryItems = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PANTRY,
                null,
                null,
                null,
                null,
                null,
                PANTRY_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(PANTRY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(PANTRY_NAME));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(PANTRY_QUANTITY));
                String unit = cursor.getString(cursor.getColumnIndexOrThrow(PANTRY_UNIT));
                String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(PANTRY_EXPIRY));

                PantryItem item = new PantryItem(id, name, quantity, unit, expiryDate);
                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pantryItems;
    }
    // updates the pantry item
    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PANTRY_NAME, item.getName());
        values.put(PANTRY_QUANTITY, item.getQuantity());
        values.put(PANTRY_UNIT, item.getUnit());
        values.put(PANTRY_EXPIRY, item.getExpiryDate());

        int result = db.update(
                TABLE_PANTRY,
                values,
                PANTRY_ID + " = ?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();
        return result;
    }

    // delete pantry item
    public int deletePantryItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        int result = db.delete(
                TABLE_PANTRY,
                PANTRY_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}