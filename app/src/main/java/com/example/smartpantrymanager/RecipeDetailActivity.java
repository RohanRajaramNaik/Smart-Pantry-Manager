package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeNameText;
    private TextView ingredientsText;
    private TextView stepsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Find views
        recipeNameText = findViewById(R.id.recipeNameText);
        ingredientsText = findViewById(R.id.ingredientsText);
        stepsText = findViewById(R.id.stepsText);
        Button backButton = findViewById(R.id.backButton);

        // Close screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Get recipe id
        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId == -1) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        loadRecipe(recipeId);
    }

    private void loadRecipe(int recipeId) {
        try {
            // Load from database
            DatabaseHelper databaseHelper = new DatabaseHelper(this);
            Recipe recipe = databaseHelper.getRecipe(recipeId);
            if (recipe == null) {
                Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            showRecipe(recipe);
        } catch (Exception e) {
            Toast.makeText(this, "Could not load recipe", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRecipe(Recipe recipe) {
        recipeNameText.setText(recipe.getName());
        ingredientsText.setText(makeIngredientText(recipe.getIngredients()));
        stepsText.setText(recipe.getSteps());
    }

    private String makeIngredientText(ArrayList<RecipeIngredient> ingredients) {
        String text = "";

        // One line each
        for (int i = 0; i < ingredients.size(); i++) {
            RecipeIngredient ingredient = ingredients.get(i);
            String amount = PantryAdapter.formatQuantity(ingredient.getQuantity());
            text = text + "- " + amount + " " + ingredient.getUnit() + " " + ingredient.getName();
            if (i < ingredients.size() - 1) {
                text = text + "\n";
            }
        }
        return text;
    }
}
