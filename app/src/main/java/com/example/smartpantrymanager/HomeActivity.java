package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private TextView itemsCountText;
    private TextView canMakeCountText;
    private TextView expiringCountText;
    private LinearLayout recipesContainer;
    private TextView recipesEmptyText;
    private LinearLayout expiringContainer;
    private TextView expiringEmptyText;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Set up screen
        findViews();
        databaseHelper = new DatabaseHelper(this);
        showToday();
        setUpClicks();
        setUpNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDashboard();
    }

    private void findViews() {
        itemsCountText = findViewById(R.id.itemsCountText);
        canMakeCountText = findViewById(R.id.canMakeCountText);
        expiringCountText = findViewById(R.id.expiringCountText);
        recipesContainer = findViewById(R.id.recipesContainer);
        recipesEmptyText = findViewById(R.id.recipesEmptyText);
        expiringContainer = findViewById(R.id.expiringContainer);
        expiringEmptyText = findViewById(R.id.expiringEmptyText);
    }

    private void showToday() {
        TextView dateText = findViewById(R.id.dateText);

        // Format date
        SimpleDateFormat format = new SimpleDateFormat("EEEE, d MMMM", Locale.getDefault());
        dateText.setText(format.format(new Date()));
    }

    private void setUpClicks() {
        // One listener for all
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClick(view.getId());
            }
        };
        findViewById(R.id.statItems).setOnClickListener(listener);
        findViewById(R.id.statCanMake).setOnClickListener(listener);
        findViewById(R.id.statExpiring).setOnClickListener(listener);
        findViewById(R.id.recipesSeeAll).setOnClickListener(listener);
        findViewById(R.id.expiringSeeAll).setOnClickListener(listener);
    }

    private void handleClick(int viewId) {
        // Pick screen
        if (viewId == R.id.statItems) {
            openPantry(false);
        } else if (viewId == R.id.statCanMake || viewId == R.id.recipesSeeAll) {
            openScreen(new Intent(this, SuggestedRecipesActivity.class));
        } else if (viewId == R.id.statExpiring || viewId == R.id.expiringSeeAll) {
            openPantry(true);
        }
    }

    private void openPantry(boolean expiringOnly) {
        Intent intent = new Intent(this, MainActivity.class);

        // Add filter
        if (expiringOnly) {
            intent.putExtra("filter", "expiring");
        }
        openScreen(intent);
    }

    private void openScreen(Intent intent) {
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    private void loadDashboard() {
        try {
            // Load from database
            ArrayList<PantryItem> pantryList = databaseHelper.getAllPantryItems();
            ArrayList<Recipe> allRecipes = databaseHelper.getAllRecipes();

            // Work out lists
            ArrayList<Recipe> suggested = IngredientMatcher.getSuggestedRecipes(allRecipes, pantryList);
            ArrayList<PantryItem> expiring = ExpiryHelper.getExpiringItems(pantryList);

            // Show everything
            itemsCountText.setText(String.valueOf(pantryList.size()));
            canMakeCountText.setText(String.valueOf(suggested.size()));
            expiringCountText.setText(String.valueOf(expiring.size()));
            showRecipes(suggested);
            showExpiring(expiring);
        } catch (Exception e) {
            Toast.makeText(this, "Could not load home", Toast.LENGTH_SHORT).show();
        }
    }

    private void showRecipes(ArrayList<Recipe> suggested) {
        recipesContainer.removeAllViews();

        // First three
        int count = Math.min(3, suggested.size());
        for (int i = 0; i < count; i++) {
            addRecipeRow(suggested.get(i));
        }

        // Empty message
        if (suggested.isEmpty()) {
            recipesEmptyText.setVisibility(View.VISIBLE);
        } else {
            recipesEmptyText.setVisibility(View.GONE);
        }
    }

    private void addRecipeRow(final Recipe recipe) {
        TextView row = (TextView) getLayoutInflater().inflate(R.layout.item_home_recipe,
                recipesContainer, false);
        row.setText(recipe.getName());

        // Open detail screen
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, RecipeDetailActivity.class);
                intent.putExtra("recipe_id", recipe.getId());
                startActivity(intent);
            }
        });
        recipesContainer.addView(row);
    }

    private void showExpiring(ArrayList<PantryItem> expiring) {
        expiringContainer.removeAllViews();

        // First three
        int count = Math.min(3, expiring.size());
        for (int i = 0; i < count; i++) {
            addExpiringRow(expiring.get(i));
        }

        // Empty message
        if (expiring.isEmpty()) {
            expiringEmptyText.setVisibility(View.VISIBLE);
        } else {
            expiringEmptyText.setVisibility(View.GONE);
        }
    }

    private void addExpiringRow(PantryItem item) {
        View row = getLayoutInflater().inflate(R.layout.item_home_expiring,
                expiringContainer, false);
        TextView nameText = row.findViewById(R.id.expiringNameText);
        TextView statusText = row.findViewById(R.id.expiringStatusText);

        // Name and days left
        nameText.setText(item.getName());
        statusText.setText(ExpiryHelper.makeDaysLeftText(item.getExpiryDate()));

        // Warning or danger
        if (ExpiryHelper.getStatus(item.getExpiryDate()) == ExpiryHelper.EXPIRY_PASSED) {
            statusText.setTextColor(getColor(R.color.alert_red));
        } else {
            statusText.setTextColor(getColor(R.color.pill_need_text));
        }
        expiringContainer.addView(row);
    }

    private void setUpNavigation() {
        // Mark current screen
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        // Open other screens
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_pantry) {
                    openPantry(false);
                } else if (itemId == R.id.nav_recipes) {
                    openScreen(new Intent(HomeActivity.this, SuggestedRecipesActivity.class));
                } else if (itemId == R.id.nav_settings) {
                    openScreen(new Intent(HomeActivity.this, SettingsActivity.class));
                }
                return true;
            }
        });
    }
}
