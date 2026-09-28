package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSuggestedRecipes();
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
