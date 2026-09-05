package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

        // create pantry table
        String createPantryTable = "CREATE TABLE " + TABLE_PANTRY + " (" +
                PANTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PANTRY_NAME + " TEXT NOT NULL, " +
                PANTRY_QUANTITY + " REAL NOT NULL, " +
                PANTRY_UNIT + " TEXT NOT NULL, " +
                PANTRY_EXPIRY + " TEXT)";

        // create recipe table
        String createRecipeTable = "CREATE TABLE " + TABLE_RECIPES + " (" +
                RECIPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RECIPE_NAME + " TEXT NOT NULL, " +
                RECIPE_STEPS + " TEXT NOT NULL)";

        // create recipe ingredients table
        String createRecipeIngredientsTable = "CREATE TABLE " +
                TABLE_RECIPE_INGREDIENTS + " (" +
                INGREDIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                INGREDIENT_RECIPE_ID + " INTEGER NOT NULL, " +
                INGREDIENT_NAME + " TEXT NOT NULL, " +
                INGREDIENT_QUANTITY + " REAL NOT NULL, " +
                INGREDIENT_UNIT + " TEXT NOT NULL)";

        db.execSQL(createPantryTable);
        db.execSQL(createRecipeTable);
        db.execSQL(createRecipeIngredientsTable);

        seedRecipes(db);
    }

    // add the starting recipes
    private void seedRecipes(SQLiteDatabase db) {

        long recipeId;

        recipeId = addRecipe(
                db,
                "Scrambled Eggs",
                "Beat the eggs and cook them in a pan with butter until done"
        );
        addRecipeIngredient(db, recipeId, "egg", 2, "pieces");
        addRecipeIngredient(db, recipeId, "butter", 10, "g");

        recipeId = addRecipe(
                db,
                "Cheese Toast",
                "Place the cheese on the bread and toast until the cheese melts"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "cheese", 50, "g");

        recipeId = addRecipe(
                db,
                "Avocado Toast",
                "Toast the bread then smash and spread the avocado on top"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "avocado", 1, "pieces");

        recipeId = addRecipe(
                db,
                "Cheese and Tomato Toastie",
                "Add cheese and sliced tomato to the bread then toast until the cheese melts"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "cheese", 50, "g");
        addRecipeIngredient(db, recipeId, "tomato", 1, "pieces");

        recipeId = addRecipe(
                db,
                "Beans on Toast",
                "Heat the beans and serve them over toasted bread"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "beans", 150, "g");

        recipeId = addRecipe(
                db,
                "Chicken Mayo Sandwich",
                "Mix the chicken with mayonnaise and place it between the bread slices"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "chicken", 100, "g");
        addRecipeIngredient(db, recipeId, "mayonnaise", 30, "g");

        recipeId = addRecipe(
                db,
                "Egg and Atchar Sandwich",
                "Cook the egg and place it on the bread with atchar"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "egg", 1, "pieces");
        addRecipeIngredient(db, recipeId, "atchar", 30, "g");

        recipeId = addRecipe(
                db,
                "Pap and Tomato Relish",
                "Cook the maize meal into pap and serve it with tomato relish"
        );
        addRecipeIngredient(db, recipeId, "maize meal", 150, "g");
        addRecipeIngredient(db, recipeId, "tomato", 2, "pieces");
        addRecipeIngredient(db, recipeId, "onion", 1, "pieces");

        recipeId = addRecipe(
                db,
                "Boerewors and Pap",
                "Cook the boerewors and prepare the pap then serve them together"
        );
        addRecipeIngredient(db, recipeId, "boerewors", 200, "g");
        addRecipeIngredient(db, recipeId, "maize meal", 150, "g");

        recipeId = addRecipe(
                db,
                "Chakalaka Toast",
                "Toast the bread and put warm chakalaka over it"
        );
        addRecipeIngredient(db, recipeId, "bread", 2, "slices");
        addRecipeIngredient(db, recipeId, "chakalaka", 100, "g");

        recipeId = addRecipe(
                db,
                "Mielie Pap with Milk",
                "Cook the maize meal until soft then serve it with milk"
        );
        addRecipeIngredient(db, recipeId, "maize meal", 100, "g");
        addRecipeIngredient(db, recipeId, "milk", 200, "ml");

        recipeId = addRecipe(
                db,
                "Potato Curry",
                "Cook the onion and potato with curry powder until the potato is soft"
        );
        addRecipeIngredient(db, recipeId, "potato", 3, "pieces");
        addRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        addRecipeIngredient(db, recipeId, "curry powder", 5, "g");

        recipeId = addRecipe(
                db,
                "Chicken Curry and Rice",
                "Cook the chicken with onion and curry powder then serve it with cooked rice"
        );
        addRecipeIngredient(db, recipeId, "chicken", 150, "g");
        addRecipeIngredient(db, recipeId, "rice", 100, "g");
        addRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        addRecipeIngredient(db, recipeId, "curry powder", 5, "g");

        recipeId = addRecipe(
                db,
                "Samp and Beans",
                "Cook the samp and beans together until soft"
        );
        addRecipeIngredient(db, recipeId, "samp", 150, "g");
        addRecipeIngredient(db, recipeId, "beans", 100, "g");

        recipeId = addRecipe(
                db,
                "Tomato Bredie",
                "Cook the beef with tomato onion and potato until everything is soft"
        );
        addRecipeIngredient(db, recipeId, "beef", 200, "g");
        addRecipeIngredient(db, recipeId, "tomato", 2, "pieces");
        addRecipeIngredient(db, recipeId, "onion", 1, "pieces");
        addRecipeIngredient(db, recipeId, "potato", 2, "pieces");

        recipeId = addRecipe(
                db,
                "Boiled Eggs",
                "Boil the eggs until cooked then let them cool"
        );
        addRecipeIngredient(db, recipeId, "egg", 2, "pieces");

        recipeId = addRecipe(
                db,
                "Mashed Potato",
                "Boil the potato until soft then mash it with butter and salt"
        );
        addRecipeIngredient(db, recipeId, "potato", 2, "pieces");
        addRecipeIngredient(db, recipeId, "butter", 20, "g");
        addRecipeIngredient(db, recipeId, "salt", 1, "g");

        recipeId = addRecipe(
                db,
                "Cheese Pasta",
                "Cook the pasta then mix in the cheese until melted"
        );
        addRecipeIngredient(db, recipeId, "pasta", 100, "g");
        addRecipeIngredient(db, recipeId, "cheese", 50, "g");

        recipeId = addRecipe(
                db,
                "Tomato Rice",
                "Cook the rice then mix it with chopped tomato"
        );
        addRecipeIngredient(db, recipeId, "rice", 100, "g");
        addRecipeIngredient(db, recipeId, "tomato", 1, "pieces");

        recipeId = addRecipe(
                db,
                "Banana Milk",
                "smash the banana and mix it with the milk"
        );
        addRecipeIngredient(db, recipeId, "banana", 1, "pieces");
        addRecipeIngredient(db, recipeId, "milk", 250, "ml");
    }

    // add recipe
    private long addRecipe(SQLiteDatabase db, String name, String steps) {

        ContentValues values = new ContentValues();

        values.put(RECIPE_NAME, name);
        values.put(RECIPE_STEPS, steps);

        return db.insert(TABLE_RECIPES, null, values);
    }

    // add ingredient needed for recipe
    private void addRecipeIngredient(SQLiteDatabase db, long recipeId,
                                     String name, double quantity, String unit) {

        ContentValues values = new ContentValues();

        values.put(INGREDIENT_RECIPE_ID, recipeId);
        values.put(INGREDIENT_NAME, name);
        values.put(INGREDIENT_QUANTITY, quantity);
        values.put(INGREDIENT_UNIT, unit);

        db.insert(TABLE_RECIPE_INGREDIENTS, null, values);
    }

    // add pantry item
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

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(PANTRY_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(PANTRY_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(PANTRY_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(PANTRY_UNIT)
                );

                String expiryDate = cursor.getString(
                        cursor.getColumnIndexOrThrow(PANTRY_EXPIRY)
                );

                PantryItem item = new PantryItem(
                        id,
                        name,
                        quantity,
                        unit,
                        expiryDate
                );

                pantryItems.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return pantryItems;
    }

    // update pantry item
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

    // get all recipes
    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPES,
                null,
                null,
                null,
                null,
                null,
                RECIPE_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(RECIPE_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_NAME)
                );

                String steps = cursor.getString(
                        cursor.getColumnIndexOrThrow(RECIPE_STEPS)
                );

                recipes.add(new Recipe(id, name, steps));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recipes;
    }

    // get ingredients for one recipe
    public ArrayList<RecipeIngredient> getRecipeIngredients(int recipeId) {

        ArrayList<RecipeIngredient> ingredients = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_RECIPE_INGREDIENTS,
                null,
                INGREDIENT_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {

            do {

                int id = cursor.getInt(
                        cursor.getColumnIndexOrThrow(INGREDIENT_ID)
                );

                int recipe = cursor.getInt(
                        cursor.getColumnIndexOrThrow(INGREDIENT_RECIPE_ID)
                );

                String name = cursor.getString(
                        cursor.getColumnIndexOrThrow(INGREDIENT_NAME)
                );

                double quantity = cursor.getDouble(
                        cursor.getColumnIndexOrThrow(INGREDIENT_QUANTITY)
                );

                String unit = cursor.getString(
                        cursor.getColumnIndexOrThrow(INGREDIENT_UNIT)
                );

                ingredients.add(
                        new RecipeIngredient(
                                id,
                                recipe,
                                name,
                                quantity,
                                unit
                        )
                );

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ingredients;
    }

    // get recipes that match the pantry
    public ArrayList<Recipe> getSuggestedRecipes() {

        ArrayList<Recipe> suggestedRecipes = new ArrayList<>();
        ArrayList<Recipe> allRecipes = getAllRecipes();
        ArrayList<PantryItem> pantryItems = getAllPantryItems();

        for (Recipe recipe : allRecipes) {

            ArrayList<RecipeIngredient> neededIngredients =
                    getRecipeIngredients(recipe.getId());

            boolean canMakeRecipe = true;

            for (RecipeIngredient needed : neededIngredients) {

                double availableQuantity = 0;

                for (PantryItem pantryItem : pantryItems) {

                    String pantryName =
                            normalizeIngredientName(pantryItem.getName());

                    String neededName =
                            normalizeIngredientName(needed.getIngredientName());

                    if (pantryName.equals(neededName)) {

                        String pantryUnit =
                                getBaseUnit(pantryItem.getUnit());

                        String neededUnit =
                                getBaseUnit(needed.getUnit());

                        if (pantryUnit.equals(neededUnit)) {

                            availableQuantity += getBaseQuantity(
                                    pantryItem.getQuantity(),
                                    pantryItem.getUnit()
                            );
                        }
                    }
                }

                double requiredQuantity = getBaseQuantity(
                        needed.getQuantity(),
                        needed.getUnit()
                );

                if (availableQuantity < requiredQuantity) {
                    canMakeRecipe = false;
                    break;
                }
            }

            if (canMakeRecipe) {
                suggestedRecipes.add(recipe);
            }
        }

        return suggestedRecipes;
    }

    // clean ingredient names before matching
    private String normalizeIngredientName(String name) {

        if (name == null) {
            return "";
        }

        String cleanedName = name
                .trim()
                .toLowerCase();

        if (cleanedName.equals("tomatoes")) {
            return "tomato";
        }

        if (cleanedName.equals("potatoes")) {
            return "potato";
        }

        if (cleanedName.equals("eggs")) {
            return "egg";
        }

        if (cleanedName.equals("avocados")) {
            return "avocado";
        }

        if (cleanedName.equals("bananas")) {
            return "banana";
        }

        if (cleanedName.equals("onions")) {
            return "onion";
        }

        return cleanedName;
    }

    // put units into the same format
    private String getBaseUnit(String unit) {

        if (unit == null) {
            return "";
        }

        String cleanedUnit = unit
                .trim()
                .toLowerCase();

        if (cleanedUnit.equals("gram") ||
                cleanedUnit.equals("grams") ||
                cleanedUnit.equals("g") ||
                cleanedUnit.equals("kg") ||
                cleanedUnit.equals("kilogram") ||
                cleanedUnit.equals("kilograms")) {

            return "g";
        }

        if (cleanedUnit.equals("ml") ||
                cleanedUnit.equals("millilitre") ||
                cleanedUnit.equals("millilitres") ||
                cleanedUnit.equals("milliliter") ||
                cleanedUnit.equals("milliliters") ||
                cleanedUnit.equals("l") ||
                cleanedUnit.equals("litre") ||
                cleanedUnit.equals("litres") ||
                cleanedUnit.equals("liter") ||
                cleanedUnit.equals("liters")) {

            return "ml";
        }

        if (cleanedUnit.equals("piece") ||
                cleanedUnit.equals("pieces") ||
                cleanedUnit.equals("pc") ||
                cleanedUnit.equals("pcs")) {

            return "pieces";
        }

        if (cleanedUnit.equals("slice") ||
                cleanedUnit.equals("slices")) {

            return "slices";
        }

        return cleanedUnit;
    }

    // convert kg and litres when needed
    private double getBaseQuantity(double quantity, String unit) {

        if (unit == null) {
            return quantity;
        }

        String cleanedUnit = unit
                .trim()
                .toLowerCase();

        if (cleanedUnit.equals("kg") ||
                cleanedUnit.equals("kilogram") ||
                cleanedUnit.equals("kilograms")) {

            return quantity * 1000;
        }

        if (cleanedUnit.equals("l") ||
                cleanedUnit.equals("litre") ||
                cleanedUnit.equals("litres") ||
                cleanedUnit.equals("liter") ||
                cleanedUnit.equals("liters")) {

            return quantity * 1000;
        }

        return quantity;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}