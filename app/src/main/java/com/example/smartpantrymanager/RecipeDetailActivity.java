package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeNameText;
    private TextView summaryText;
    private TextView stepsText;
    private LinearLayout ingredientsContainer;
    private DatabaseHelper databaseHelper;
    private int recipeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Find views
        recipeNameText = findViewById(R.id.recipeNameText);
        summaryText = findViewById(R.id.summaryText);
        stepsText = findViewById(R.id.stepsText);
        ingredientsContainer = findViewById(R.id.ingredientsContainer);

        // Set up screen
        databaseHelper = new DatabaseHelper(this);
        setUpBackButton();
        checkRecipeId();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (recipeId != -1) {
            loadRecipe();
        }
    }

    private void setUpBackButton() {
        Button backButton = findViewById(R.id.backButton);

        // Close screen
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkRecipeId() {
        // Get recipe id
        recipeId = getIntent().getIntExtra("recipe_id", -1);
        if (recipeId == -1) {
            Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void loadRecipe() {
        try {
            // Load from database
            Recipe recipe = databaseHelper.getRecipe(recipeId);
            if (recipe == null) {
                Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            showRecipe(recipe, databaseHelper.getAllPantryItems());
        } catch (Exception e) {
            Toast.makeText(this, "Could not load recipe", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRecipe(Recipe recipe, ArrayList<PantryItem> pantry) {
        recipeNameText.setText(recipe.getName());
        stepsText.setText(recipe.getSteps());
        showIngredients(recipe.getIngredients(), pantry);
    }

    private void showIngredients(ArrayList<RecipeIngredient> ingredients,
                                 ArrayList<PantryItem> pantry) {
        ingredientsContainer.removeAllViews();
        int haveCount = 0;

        // One row each
        for (int i = 0; i < ingredients.size(); i++) {
            if (addIngredientRow(ingredients.get(i), pantry)) {
                haveCount++;
            }
        }
        summaryText.setText("You have " + haveCount + " of " + ingredients.size()
                + " ingredients");
    }

    private boolean addIngredientRow(RecipeIngredient ingredient, ArrayList<PantryItem> pantry) {
        View row = getLayoutInflater().inflate(R.layout.item_ingredient_status,
                ingredientsContainer, false);
        TextView ingredientText = row.findViewById(R.id.ingredientText);
        TextView statusText = row.findViewById(R.id.statusText);

        // Name and amount
        String amount = PantryAdapter.formatQuantity(ingredient.getQuantity());
        ingredientText.setText(capitalise(ingredient.getName()) + "  " + amount + " "
                + ingredient.getUnit());

        // Status tag
        boolean haveEnough = showStatus(statusText, ingredient, pantry);
        ingredientsContainer.addView(row);
        return haveEnough;
    }

    private boolean showStatus(TextView tag, RecipeIngredient ingredient,
                               ArrayList<PantryItem> pantry) {
        double haveAmount = IngredientMatcher.getPantryAmountInUnit(ingredient, pantry);

        // Have enough
        if (IngredientMatcher.hasIngredient(ingredient, pantry)) {
            setTag(tag, "Have", R.drawable.tag_accent, R.color.tag_accent_text);
            return true;
        }

        // Some but not enough
        if (haveAmount > 0) {
            setTag(tag, makeNeedText(ingredient, haveAmount), R.drawable.tag_warning,
                    R.color.tag_warning_text);
            return false;
        }

        // None
        setTag(tag, "Missing", R.drawable.tag_danger, R.color.tag_danger_text);
        return false;
    }

    private void setTag(TextView tag, String text, int background, int textColor) {
        tag.setText(text);
        tag.setBackgroundResource(background);
        tag.setTextColor(getColor(textColor));
    }

    private String makeNeedText(RecipeIngredient ingredient, double haveAmount) {
        double missing = ingredient.getQuantity() - haveAmount;
        missing = Math.round(missing * 100) / 100.0;
        String amount = PantryAdapter.formatQuantity(missing);

        // Count items skip unit
        if (IngredientMatcher.unitGroup(ingredient.getUnit()).equals("count")) {
            return "Need " + amount + " more";
        }
        return "Need " + amount + " " + ingredient.getUnit() + " more";
    }

    private String capitalise(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1);
    }
}
