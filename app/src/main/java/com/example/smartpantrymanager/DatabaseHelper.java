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
    private static final String TABLE_RECIPE_INGREDIENTS =
            "recipe_ingredients";

    private static final String INGREDIENT_ID = "id";
    private static final String INGREDIENT_RECIPE_ID = "recipe_id";
    private static final String INGREDIENT_NAME = "ingredient_name";
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_UNIT = "unit";

    public DatabaseHelper(Context context) {

        super(
                context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create pantry table
        String createPantryTable =
                "CREATE TABLE "
                        + TABLE_PANTRY
                        + " ("
                        + PANTRY_ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + PANTRY_NAME
                        + " TEXT NOT NULL, "
                        + PANTRY_QUANTITY
                        + " REAL NOT NULL, "
                        + PANTRY_UNIT
                        + " TEXT NOT NULL, "
                        + PANTRY_EXPIRY
                        + " TEXT)";

        // create recipe table
        String createRecipeTable =
                "CREATE TABLE "
                        + TABLE_RECIPES
                        + " ("
                        + RECIPE_ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + RECIPE_NAME
                        + " TEXT NOT NULL, "
                        + RECIPE_STEPS
                        + " TEXT NOT NULL)";

        // create recipe ingredients table
        String createRecipeIngredientsTable =
                "CREATE TABLE "
                        + TABLE_RECIPE_INGREDIENTS
                        + " ("
                        + INGREDIENT_ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + INGREDIENT_RECIPE_ID
                        + " INTEGER NOT NULL, "
                        + INGREDIENT_NAME
                        + " TEXT NOT NULL, "
                        + INGREDIENT_QUANTITY
                        + " REAL NOT NULL, "
                        + INGREDIENT_UNIT
                        + " TEXT NOT NULL)";

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

        addRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "butter",
                10,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Cheese Toast",
                "Place the cheese on the bread and toast until the cheese melts"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "cheese",
                50,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Avocado Toast",
                "Toast the bread then smash and spread the avocado on top"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "avocado",
                1,
                "pieces"
        );

        recipeId = addRecipe(
                db,
                "Cheese and Tomato Toastie",
                "Add cheese and sliced tomato to the bread then toast until the cheese melts"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "cheese",
                50,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "tomato",
                1,
                "pieces"
        );

        recipeId = addRecipe(
                db,
                "Beans on Toast",
                "Heat the beans and serve them over toasted bread"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "beans",
                150,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Chicken Mayo Sandwich",
                "Mix the chicken with mayonnaise and place it between the bread slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "chicken",
                100,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "mayonnaise",
                30,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Egg and Atchar Sandwich",
                "Cook the egg and place it on the bread with atchar"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "egg",
                1,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "atchar",
                30,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Pap and Tomato Relish",
                "Cook the maize meal into pap and serve it with tomato relish"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "maize meal",
                150,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "tomato",
                2,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "pieces"
        );

        recipeId = addRecipe(
                db,
                "Boerewors and Pap",
                "Cook the boerewors and prepare the pap then serve them together"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "boerewors",
                200,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "maize meal",
                150,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Chakalaka Toast",
                "Toast the bread and put warm chakalaka over it"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "bread",
                2,
                "slices"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "chakalaka",
                100,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Mielie Pap with Milk",
                "Cook the maize meal until soft then serve it with milk"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "maize meal",
                100,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "milk",
                200,
                "ml"
        );

        recipeId = addRecipe(
                db,
                "Potato Curry",
                "Cook the onion and potato with curry powder until the potato is soft"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "potato",
                3,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "curry powder",
                5,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Chicken Curry and Rice",
                "Cook the chicken with onion and curry powder then serve it with cooked rice"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "chicken",
                150,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "rice",
                100,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "curry powder",
                5,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Samp and Beans",
                "Cook the samp and beans together until soft"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "samp",
                150,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "beans",
                100,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Tomato Bredie",
                "Cook the beef with tomato onion and potato until everything is soft"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "beef",
                200,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "tomato",
                2,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "onion",
                1,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "potato",
                2,
                "pieces"
        );

        recipeId = addRecipe(
                db,
                "Boiled Eggs",
                "Boil the eggs until cooked then let them cool"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "egg",
                2,
                "pieces"
        );

        recipeId = addRecipe(
                db,
                "Mashed Potato",
                "Boil the potato until soft then mash it with butter and salt"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "potato",
                2,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "butter",
                20,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "salt",
                1,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Cheese Pasta",
                "Cook the pasta then mix in the cheese until melted"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "pasta",
                100,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "cheese",
                50,
                "g"
        );

        recipeId = addRecipe(
                db,
                "Tomato Rice",
                "Cook the rice then mix it with chopped tomato"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "rice",
                100,
                "g"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "tomato",
                1,
                "pieces"
        );

        recipeId = addRecipe(
                db,
                "Banana Milk",
                "smash the banana and mix it with the milk"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "banana",
                1,
                "pieces"
        );

        addRecipeIngredient(
                db,
                recipeId,
                "milk",
                250,
                "ml"
        );
    }

    // add recipe
    private long addRecipe(
            SQLiteDatabase db,
            String name,
            String steps) {

        ContentValues values =
                new ContentValues();

        values.put(
                RECIPE_NAME,
                name
        );

        values.put(
                RECIPE_STEPS,
                steps
        );

        return db.insert(
                TABLE_RECIPES,
                null,
                values
        );
    }

    // add ingredient needed for recipe
    private void addRecipeIngredient(
            SQLiteDatabase db,
            long recipeId,
            String name,
            double quantity,
            String unit) {

        ContentValues values =
                new ContentValues();

        values.put(
                INGREDIENT_RECIPE_ID,
                recipeId
        );

        values.put(
                INGREDIENT_NAME,
                name
        );

        values.put(
                INGREDIENT_QUANTITY,
                quantity
        );

        values.put(
                INGREDIENT_UNIT,
                unit
        );

        db.insert(
                TABLE_RECIPE_INGREDIENTS,
                null,
                values
        );
    }

    // add pantry item
    public long addPantryItem(
            PantryItem item) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                PANTRY_NAME,
                item.getName()
        );

        values.put(
                PANTRY_QUANTITY,
                item.getQuantity()
        );

        values.put(
                PANTRY_UNIT,
                item.getUnit()
        );

        values.put(
                PANTRY_EXPIRY,
                item.getExpiryDate()
        );

        long result =
                db.insert(
                        TABLE_PANTRY,
                        null,
                        values
                );

        db.close();

        return result;
    }

    // get all pantry items
    public ArrayList<PantryItem>
    getAllPantryItems() {

        ArrayList<PantryItem> pantryItems =
                new ArrayList<>();

        SQLiteDatabase db =
                this.getReadableDatabase();

        Cursor cursor =
                db.query(
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

                int id =
                        cursor.getInt(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_ID
                                )
                        );

                String name =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_NAME
                                )
                        );

                double quantity =
                        cursor.getDouble(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_QUANTITY
                                )
                        );

                String unit =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_UNIT
                                )
                        );

                String expiryDate =
                        cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                        PANTRY_EXPIRY
                                )
                        );

                PantryItem item =
                        new PantryItem(
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
    public int updatePantryItem(
            PantryItem item) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        ContentValues values =
                new ContentValues();

        values.put(
                PANTRY_NAME,
                item.getName()
        );

        values.put(
                PANTRY_QUANTITY,
                item.getQuantity()
        );

        values.put(
                PANTRY_UNIT,
                item.getUnit()
        );

        values.put(
                PANTRY_EXPIRY,
                item.getExpiryDate()
        );

        int result =
                db.update(
                        TABLE_PANTRY,
                        values,
                        PANTRY_ID + " = ?",
                        new String[]{
                                String.valueOf(
                                        item.getId()
                                )
                        }
                );

        db.close();

        return result;
    }

    // delete pantry item
    public int deletePantryItem(int id) {

        SQLiteDatabase db =
                this.getWritableDatabase();

        int result =
                db.delete(
                        TABLE_PANTRY,
                        PANTRY_ID + " = ?",
                        new String[]{
                                String.valueOf(id)
                        }
                );

        db.close();

        return result;
    }

    // get all recipes
    public ArrayList<Recipe> getAllRecipes() {

        ArrayList<Recipe> recipes =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_RECIPES,
                        null,
                        null,
                        null,
                        null,
                        null,
                        RECIPE_NAME + " ASC"
                );

        while (cursor.moveToNext()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    RECIPE_ID
                            )
                    );

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    RECIPE_NAME
                            )
                    );

            String steps =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    RECIPE_STEPS
                            )
                    );

            Recipe recipe =
                    new Recipe(
                            id,
                            name,
                            steps
                    );

            recipes.add(recipe);
        }

        cursor.close();
        db.close();

        return recipes;
    }

    // get recipe ingredients
    public ArrayList<RecipeIngredient>
    getRecipeIngredients(int recipeId) {

        ArrayList<RecipeIngredient> ingredients =
                new ArrayList<>();

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_RECIPE_INGREDIENTS,
                        null,
                        INGREDIENT_RECIPE_ID + " = ?",
                        new String[]{
                                String.valueOf(
                                        recipeId
                                )
                        },
                        null,
                        null,
                        null
                );

        while (cursor.moveToNext()) {

            int id =
                    cursor.getInt(
                            cursor.getColumnIndexOrThrow(
                                    INGREDIENT_ID
                            )
                    );

            String name =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    INGREDIENT_NAME
                            )
                    );

            double quantity =
                    cursor.getDouble(
                            cursor.getColumnIndexOrThrow(
                                    INGREDIENT_QUANTITY
                            )
                    );

            String unit =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    INGREDIENT_UNIT
                            )
                    );

            RecipeIngredient ingredient =
                    new RecipeIngredient(
                            id,
                            recipeId,
                            name,
                            quantity,
                            unit
                    );

            ingredients.add(ingredient);
        }

        cursor.close();
        db.close();

        return ingredients;
    }

    // get suggested recipes
    public ArrayList<Recipe>
    getSuggestedRecipes() {

        ArrayList<Recipe> suggestedRecipes =
                new ArrayList<>();

        ArrayList<Recipe> recipes =
                getAllRecipes();

        ArrayList<PantryItem> pantryItems =
                getAllPantryItems();

        for (Recipe recipe : recipes) {

            ArrayList<RecipeIngredient>
                    recipeIngredients =
                    getRecipeIngredients(
                            recipe.getId()
                    );

            boolean canMakeRecipe = true;

            for (RecipeIngredient needed
                    : recipeIngredients) {

                double availableQuantity = 0;

                String neededName =
                        normalizeIngredientName(
                                needed.getIngredientName()
                        );

                String neededUnit =
                        getBaseUnit(
                                needed.getUnit()
                        );

                double neededQuantity =
                        getBaseQuantity(
                                needed.getQuantity(),
                                needed.getUnit()
                        );

                for (PantryItem pantryItem
                        : pantryItems) {

                    String pantryName =
                            normalizeIngredientName(
                                    pantryItem.getName()
                            );

                    String pantryUnit =
                            getBaseUnit(
                                    pantryItem.getUnit()
                            );

                    if (pantryName.equals(
                            neededName)
                            && pantryUnit.equals(
                            neededUnit)) {

                        availableQuantity +=
                                getBaseQuantity(
                                        pantryItem
                                                .getQuantity(),
                                        pantryItem
                                                .getUnit()
                                );
                    }
                }

                if (availableQuantity
                        < neededQuantity) {

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

    // get units used for ingredient
    public ArrayList<String>
    getUnitsForIngredient(
            String ingredientName) {

        ArrayList<String> units =
                new ArrayList<>();

        String name =
                normalizeIngredientName(
                        ingredientName
                );

        SQLiteDatabase db =
                getReadableDatabase();

        Cursor cursor =
                db.query(
                        TABLE_RECIPE_INGREDIENTS,
                        new String[]{
                                INGREDIENT_NAME,
                                INGREDIENT_UNIT
                        },
                        null,
                        null,
                        null,
                        null,
                        null
                );

        while (cursor.moveToNext()) {

            String recipeIngredient =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    INGREDIENT_NAME
                            )
                    );

            String recipeUnit =
                    cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                    INGREDIENT_UNIT
                            )
                    );

            if (normalizeIngredientName(
                    recipeIngredient
            ).equals(name)) {

                String baseUnit =
                        getBaseUnit(
                                recipeUnit
                        );

                if (baseUnit.equals("g")) {

                    if (!units.contains("g")) {
                        units.add("g");
                    }

                    if (!units.contains("kg")) {
                        units.add("kg");
                    }

                } else if (
                        baseUnit.equals("ml")) {

                    if (!units.contains("ml")) {
                        units.add("ml");
                    }

                    if (!units.contains("L")) {
                        units.add("L");
                    }

                } else if (
                        baseUnit.equals("pieces")) {

                    if (!units.contains(
                            "pieces")) {

                        units.add("pieces");
                    }

                } else if (
                        baseUnit.equals("slices")) {

                    if (!units.contains(
                            "slices")) {

                        units.add("slices");
                    }
                }
            }
        }

        cursor.close();
        db.close();

        // show all units for unknown ingredient
        if (units.isEmpty()) {

            units.add("g");
            units.add("kg");
            units.add("ml");
            units.add("L");
            units.add("pieces");
            units.add("slices");
        }

        return units;
    }

    // clean ingredient names
    private String normalizeIngredientName(
            String name) {

        if (name == null) {
            return "";
        }

        String cleaned =
                name.trim()
                        .toLowerCase();

        if (cleaned.equals("tomatoes")) {
            return "tomato";
        }

        if (cleaned.equals("potatoes")) {
            return "potato";
        }

        if (cleaned.equals("eggs")) {
            return "egg";
        }

        if (cleaned.equals("avocados")) {
            return "avocado";
        }

        if (cleaned.equals("bananas")) {
            return "banana";
        }

        if (cleaned.equals("onions")) {
            return "onion";
        }

        return cleaned;
    }

    // get matching unit
    private String getBaseUnit(
            String unit) {

        if (unit == null) {
            return "";
        }

        String cleaned =
                unit.trim()
                        .toLowerCase();

        if (cleaned.equals("g")
                || cleaned.equals("gram")
                || cleaned.equals("grams")
                || cleaned.equals("kg")
                || cleaned.equals("kilogram")
                || cleaned.equals("kilograms")) {

            return "g";
        }

        if (cleaned.equals("ml")
                || cleaned.equals("millilitre")
                || cleaned.equals("millilitres")
                || cleaned.equals("milliliter")
                || cleaned.equals("milliliters")
                || cleaned.equals("l")
                || cleaned.equals("litre")
                || cleaned.equals("litres")
                || cleaned.equals("liter")
                || cleaned.equals("liters")) {

            return "ml";
        }

        if (cleaned.equals("piece")
                || cleaned.equals("pieces")
                || cleaned.equals("pc")
                || cleaned.equals("pcs")) {

            return "pieces";
        }

        if (cleaned.equals("slice")
                || cleaned.equals("slices")) {

            return "slices";
        }

        return cleaned;
    }

    // convert quantity
    private double getBaseQuantity(
            double quantity,
            String unit) {

        if (unit == null) {
            return quantity;
        }

        String cleaned =
                unit.trim()
                        .toLowerCase();

        if (cleaned.equals("kg")
                || cleaned.equals("kilogram")
                || cleaned.equals("kilograms")) {

            return quantity * 1000;
        }

        if (cleaned.equals("l")
                || cleaned.equals("litre")
                || cleaned.equals("litres")
                || cleaned.equals("liter")
                || cleaned.equals("liters")) {

            return quantity * 1000;
        }

        return quantity;
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion) {

    }
}