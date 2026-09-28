package com.example.smartpantrymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SettingsActivity extends AppCompatActivity {

    private ToggleButton expiryAlertsToggle;
    private ToggleButton almostThereToggle;
    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Find views
        expiryAlertsToggle = findViewById(R.id.expiryAlertsToggle);
        almostThereToggle = findViewById(R.id.almostThereToggle);
        settings = getSharedPreferences("pantry_settings", MODE_PRIVATE);

        // Set up screen
        loadSettings();
        setUpToggles();
        setUpNavigation();
    }

    private void loadSettings() {
        expiryAlertsToggle.setChecked(settings.getBoolean("expiry_alerts", true));
        almostThereToggle.setChecked(settings.getBoolean("show_almost_there", true));
    }

    private void setUpToggles() {
        // Save expiry alerts
        expiryAlertsToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetting("expiry_alerts", expiryAlertsToggle.isChecked());
            }
        });

        // Save almost there
        almostThereToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSetting("show_almost_there", almostThereToggle.isChecked());
            }
        });
    }

    private void saveSetting(String key, boolean value) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    private void setUpNavigation() {
        // Mark current screen
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_settings);

        // Open other screens
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_home) {
                    openScreen(new Intent(SettingsActivity.this, HomeActivity.class));
                } else if (itemId == R.id.nav_pantry) {
                    openScreen(new Intent(SettingsActivity.this, MainActivity.class));
                } else if (itemId == R.id.nav_recipes) {
                    openScreen(new Intent(SettingsActivity.this, SuggestedRecipesActivity.class));
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
}
