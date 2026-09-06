package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

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
        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        databaseHelper = new DatabaseHelper(this);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        bottomNavigation.setSelectedItemId(R.id.navRecipes);

        // bottom navigation
        bottomNavigation.setOnItemSelectedListener(item -> {

            int itemId = item.getItemId();

            if (itemId == R.id.navPantry) {
                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        MainActivity.class
                );

                startActivity(intent);
                finish();
                return true;
            }

            if (itemId == R.id.navRecipes) {
                return true;
            }

            if (itemId == R.id.navSettings) {
                Intent intent = new Intent(
                        SuggestedRecipesActivity.this,
                        SettingsActivity.class
                );

                startActivity(intent);
                finish();
                return true;
            }

            return false;
        });

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