package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView pantryRecyclerView;
    private TextView emptyText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views
        pantryRecyclerView = findViewById(R.id.pantryRecyclerView);
        emptyText = findViewById(R.id.emptyText);
        Button addButton = findViewById(R.id.addButton);
        Button suggestedButton = findViewById(R.id.suggestedButton);

        // Set up list
        pantryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);

        // Open add screen
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditIngredientActivity.class);
                startActivity(intent);
            }
        });

        // Open suggested screen
        suggestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SuggestedRecipesActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryList();
    }

    private void loadPantryList() {
        try {
            // Load from database
            ArrayList<PantryItem> pantryList = databaseHelper.getAllPantryItems();
            pantryRecyclerView.setAdapter(new PantryAdapter(pantryList));
            showEmptyMessage(pantryList.size());
        } catch (Exception e) {
            Toast.makeText(this, "Could not load pantry", Toast.LENGTH_SHORT).show();
        }
    }

    private void showEmptyMessage(int itemCount) {
        // Show when no items
        if (itemCount == 0) {
            emptyText.setVisibility(View.VISIBLE);
        } else {
            emptyText.setVisibility(View.GONE);
        }
    }
}
