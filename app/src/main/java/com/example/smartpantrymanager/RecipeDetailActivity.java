package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Show received id
        int recipeId = getIntent().getIntExtra("recipe_id", -1);
        TextView placeholderText = findViewById(R.id.placeholderText);
        placeholderText.setText("Recipe detail (recipe_id = " + recipeId + ")");
    }
}
