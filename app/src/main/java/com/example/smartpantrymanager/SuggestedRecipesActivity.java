package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;
    private TextView emptyText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        // Find views
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        emptyText = findViewById(R.id.emptyText);

        // Set up list
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        setUpNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
    }

    private void setUpNavigation() {
        // Mark current screen
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_recipes);

        // Open other screens
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_pantry) {
                    openScreen(new Intent(SuggestedRecipesActivity.this, MainActivity.class));
                } else if (itemId == R.id.nav_settings) {
                    openScreen(new Intent(SuggestedRecipesActivity.this, SettingsActivity.class));
                }
                return true;
            }
        });
    }

    private void openScreen(Intent intent) {
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private void loadSuggestedRecipes() {
        try {
            // Load from database
            ArrayList<PantryItem> pantryList = databaseHelper.getAllPantryItems();
            ArrayList<Recipe> allRecipes = databaseHelper.getAllRecipes();

            // Match recipes
            ArrayList<Recipe> suggested =
                    IngredientMatcher.getSuggestedRecipes(allRecipes, pantryList);
            recipeRecyclerView.setAdapter(new RecipeAdapter(suggested));
            showEmptyMessage(suggested.size());
        } catch (Exception e) {
            Toast.makeText(this, "Could not load recipes", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmptyMessage(int recipeCount) {
        // Swap list and message
        if (recipeCount == 0) {
            recipeRecyclerView.setVisibility(View.GONE);
            emptyText.setVisibility(View.VISIBLE);
        } else {
            recipeRecyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        }
    }
}
