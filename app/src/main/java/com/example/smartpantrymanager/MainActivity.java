package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
        setUpNavigation();
        showExpiryToast();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryList();
    }

    private void setUpNavigation() {
        // Mark current screen
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_pantry);

        // Open other screens
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_recipes) {
                    openScreen(new Intent(MainActivity.this, SuggestedRecipesActivity.class));
                } else if (itemId == R.id.nav_settings) {
                    openScreen(new Intent(MainActivity.this, SettingsActivity.class));
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

    private void loadPantryList() {
        try {
            // Load from database
            ArrayList<PantryItem> pantryList = databaseHelper.getAllPantryItems();
            pantryRecyclerView.setAdapter(new PantryAdapter(pantryList, alertsAreOn()));
            showEmptyMessage(pantryList.size());
        } catch (Exception e) {
            Toast.makeText(this, "Could not load pantry", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean alertsAreOn() {
        SharedPreferences settings = getSharedPreferences("pantry_settings", MODE_PRIVATE);
        return settings.getBoolean("expiry_alerts", true);
    }

    private void showExpiryToast() {
        // Alerts off
        if (!alertsAreOn()) {
            return;
        }

        try {
            // One message
            int soonCount = countExpiringSoon(databaseHelper.getAllPantryItems());
            if (soonCount > 0) {
                Toast.makeText(this, soonCount + " item(s) expiring soon", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Could not check expiry dates", Toast.LENGTH_SHORT).show();
        }
    }

    private int countExpiringSoon(ArrayList<PantryItem> pantryList) {
        int soonCount = 0;

        // Count soon items
        for (int i = 0; i < pantryList.size(); i++) {
            if (pantryList.get(i).getExpiryStatus() == PantryItem.EXPIRY_SOON) {
                soonCount++;
            }
        }
        return soonCount;
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
