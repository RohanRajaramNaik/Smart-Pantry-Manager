package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddEditIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        // Show received id
        int pantryId = getIntent().getIntExtra("pantry_id", -1);
        TextView placeholderText = findViewById(R.id.placeholderText);
        placeholderText.setText("Add or edit ingredient (pantry_id = " + pantryId + ")");
    }
}
