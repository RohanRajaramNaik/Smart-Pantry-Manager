package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
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
    private RecyclerView almostRecyclerView;
    private TextView emptyText;
    private TextView almostEmptyText;
    private LinearLayout almostSection;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_recipes);

        // Find views
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);
        almostRecyclerView = findViewById(R.id.almostRecyclerView);
        emptyText = findViewById(R.id.emptyText);
        almostEmptyText = findViewById(R.id.almostEmptyText);
        almostSection = findViewById(R.id.almostSection);

        // Set up lists
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        almostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
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
            showAlmostThere(allRecipes, pantryList);
        } catch (Exception e) {
            Toast.makeText(this, "Could not load recipes", Toast.LENGTH_SHORT).show();
        }
    }

    private void showAlmostThere(ArrayList<Recipe> allRecipes, ArrayList<PantryItem> pantryList) {
        // Setting off
        SharedPreferences settings = getSharedPreferences("pantry_settings", MODE_PRIVATE);
        if (!settings.getBoolean("show_almost_there", true)) {
            almostSection.setVisibility(View.GONE);
            return;
        }

        // Match recipes
        almostSection.setVisibility(View.VISIBLE);
        ArrayList<Recipe> almostThere =
                IngredientMatcher.getAlmostThereRecipes(allRecipes, pantryList);
        ArrayList<String> missingList = makeMissingList(almostThere, pantryList);
        almostRecyclerView.setAdapter(new RecipeAdapter(almostThere, missingList));
        showAlmostEmptyMessage(almostThere.size());
    }

    private ArrayList<String> makeMissingList(ArrayList<Recipe> recipes,
                                              ArrayList<PantryItem> pantryList) {
        ArrayList<String> missingList = new ArrayList<String>();

        // One name per recipe
        for (int i = 0; i < recipes.size(); i++) {
            missingList.add(IngredientMatcher.getMissingIngredientName(recipes.get(i), pantryList));
        }
        return missingList;
    }

    private void showAlmostEmptyMessage(int recipeCount) {
        // Swap list and message
        if (recipeCount == 0) {
            almostRecyclerView.setVisibility(View.GONE);
            almostEmptyText.setVisibility(View.VISIBLE);
        } else {
            almostRecyclerView.setVisibility(View.VISIBLE);
            almostEmptyText.setVisibility(View.GONE);
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
