package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recyclerRecipes;
    private TextView txtNoRecipes;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        databaseHelper = new DatabaseHelper(this);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadSuggestedRecipes();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // refresh recipes when coming back
        loadSuggestedRecipes();
    }

    private void loadSuggestedRecipes() {

        ArrayList<Recipe> recipes =
                databaseHelper.getSuggestedRecipes();

        if (recipes.isEmpty()) {

            recyclerRecipes.setVisibility(View.GONE);
            txtNoRecipes.setVisibility(View.VISIBLE);

        } else {

            recyclerRecipes.setVisibility(View.VISIBLE);
            txtNoRecipes.setVisibility(View.GONE);

            RecipeAdapter adapter =
                    new RecipeAdapter(this, recipes);

            recyclerRecipes.setAdapter(adapter);
        }
    }
}