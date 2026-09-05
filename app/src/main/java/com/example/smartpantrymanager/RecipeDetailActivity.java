package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeTitle;
    private TextView txtRecipeIngredients;
    private TextView txtRecipeSteps;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        txtRecipeTitle = findViewById(R.id.txtRecipeTitle);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeSteps = findViewById(R.id.txtRecipeSteps);

        databaseHelper = new DatabaseHelper(this);

        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        String recipeName = getIntent().getStringExtra("recipe_name");
        String recipeSteps = getIntent().getStringExtra("recipe_steps");

        txtRecipeTitle.setText(recipeName);
        txtRecipeSteps.setText(recipeSteps);

        loadIngredients(recipeId);
    }

    private void loadIngredients(int recipeId) {

        ArrayList<RecipeIngredient> ingredients =
                databaseHelper.getRecipeIngredients(recipeId);

        StringBuilder ingredientText = new StringBuilder();

        for (RecipeIngredient ingredient : ingredients) {

            ingredientText.append("• ")
                    .append(ingredient.getIngredientName())
                    .append(" - ")
                    .append(ingredient.getQuantity())
                    .append(" ")
                    .append(ingredient.getUnit())
                    .append("\n");
        }

        txtRecipeIngredients.setText(ingredientText.toString());
    }
}