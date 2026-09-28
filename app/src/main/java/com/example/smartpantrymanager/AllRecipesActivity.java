package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllRecipesActivity extends AppCompatActivity {

    private RecyclerView allRecipesRecyclerView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_recipes);

        // Set up list
        allRecipesRecyclerView = findViewById(R.id.allRecipesRecyclerView);
        allRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        // Close screen
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipes();
    }

    private void loadRecipes() {
        try {
            // Load from database
            ArrayList<PantryItem> pantryList = databaseHelper.getAllPantryItems();
            ArrayList<Recipe> recipes = databaseHelper.getAllRecipes();

            // Count and sort
            int[] missing = countMissingForAll(recipes, pantryList);
            sortByMissing(recipes, missing);
            allRecipesRecyclerView.setAdapter(new AllRecipesAdapter(recipes, missing));
        } catch (Exception e) {
            Toast.makeText(this, "Could not load recipes", Toast.LENGTH_SHORT).show();
        }
    }

    private int[] countMissingForAll(ArrayList<Recipe> recipes, ArrayList<PantryItem> pantryList) {
        int[] missing = new int[recipes.size()];

        // One count each
        for (int i = 0; i < recipes.size(); i++) {
            missing[i] = IngredientMatcher.countMissing(recipes.get(i), pantryList);
        }
        return missing;
    }

    private void sortByMissing(ArrayList<Recipe> recipes, int[] missing) {
        // Insertion sort
        for (int i = 1; i < recipes.size(); i++) {
            Recipe recipe = recipes.get(i);
            int recipeMissing = missing[i];
            int j = i - 1;

            // Shift larger counts
            while (j >= 0 && missing[j] > recipeMissing) {
                recipes.set(j + 1, recipes.get(j));
                missing[j + 1] = missing[j];
                j--;
            }
            recipes.set(j + 1, recipe);
            missing[j + 1] = recipeMissing;
        }
    }
}
