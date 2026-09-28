package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class AddEditIngredientActivity extends AppCompatActivity {

    private static final String[] ALLOWED_UNITS = {"g", "kg", "ml", "l", "cup", "tbsp", "tsp",
            "pcs", "piece", "pieces", "item", "whole"};

    private static final double MAX_QUANTITY = 100000;

    private TextView titleText;
    private EditText nameEdit;
    private EditText quantityEdit;
    private EditText unitEdit;
    private ToggleButton expiryToggle;
    private DatePicker expiryPicker;
    private Button saveButton;
    private Button deleteButton;
    private DatabaseHelper databaseHelper;
    private int pantryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_ingredient);

        // Set up screen
        findViews();
        databaseHelper = new DatabaseHelper(this);
        pantryId = getIntent().getIntExtra("pantry_id", -1);
        setUpMode();
        setUpListeners();
    }

    private void findViews() {
        titleText = findViewById(R.id.titleText);
        nameEdit = findViewById(R.id.nameEdit);
        quantityEdit = findViewById(R.id.quantityEdit);
        unitEdit = findViewById(R.id.unitEdit);
        expiryToggle = findViewById(R.id.expiryToggle);
        expiryPicker = findViewById(R.id.expiryPicker);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.deleteButton);
    }

    private void setUpMode() {
        // Add mode
        if (pantryId == -1) {
            titleText.setText("Add Ingredient");
            return;
        }

        // Edit mode
        titleText.setText("Edit Ingredient");
        deleteButton.setVisibility(View.VISIBLE);
        loadItem();
    }

    private void setUpListeners() {
        // Show or hide date
        expiryToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPicker(expiryToggle.isChecked());
            }
        });
        setUpButtons();
    }

    private void setUpButtons() {
        // Save item
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIngredient();
            }
        });

        // Delete item
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteIngredient();
            }
        });
    }

    private void loadItem() {
        try {
            // Get item
            PantryItem item = databaseHelper.getPantryItem(pantryId);
            if (item == null) {
                Toast.makeText(this, "Ingredient not found", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Fill fields
            nameEdit.setText(item.getName());
            quantityEdit.setText(PantryAdapter.formatQuantity(item.getQuantity()));
            unitEdit.setText(item.getUnit());
            showExpiry(item.getExpiryDate());
        } catch (Exception e) {
            Toast.makeText(this, "Could not load ingredient", Toast.LENGTH_SHORT).show();
        }
    }

    private void showExpiry(String expiryDate) {
        // No date
        if (expiryDate == null || expiryDate.isEmpty()) {
            expiryToggle.setChecked(false);
            showPicker(false);
            return;
        }

        // Read date parts
        try {
            String[] parts = expiryDate.split("-");
            int year = Integer.parseInt(parts[0]);
            int month = Integer.parseInt(parts[1]);
            int day = Integer.parseInt(parts[2]);
            expiryPicker.updateDate(year, month - 1, day);
            expiryToggle.setChecked(true);
            showPicker(true);
        } catch (Exception e) {
            expiryToggle.setChecked(false);
            showPicker(false);
        }
    }

    private void showPicker(boolean show) {
        if (show) {
            expiryPicker.setVisibility(View.VISIBLE);
        } else {
            expiryPicker.setVisibility(View.GONE);
        }
    }

    private void saveIngredient() {
        // Check all fields
        boolean nameOk = checkName();
        boolean quantityOk = checkQuantity();
        boolean unitOk = checkUnit();
        if (!nameOk || !quantityOk || !unitOk) {
            return;
        }

        // Save to database
        PantryItem item = buildItem();
        saveToDatabase(item);
    }

    private void saveToDatabase(PantryItem item) {
        try {
            if (pantryId == -1) {
                databaseHelper.addPantryItem(item);
                Toast.makeText(this, "Ingredient added", Toast.LENGTH_SHORT).show();
            } else {
                item.setId(pantryId);
                databaseHelper.updatePantryItem(item);
                Toast.makeText(this, "Ingredient updated", Toast.LENGTH_SHORT).show();
            }
            saveButton.setEnabled(false);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Could not save ingredient", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteIngredient() {
        try {
            databaseHelper.deletePantryItem(pantryId);
            Toast.makeText(this, "Ingredient deleted", Toast.LENGTH_SHORT).show();
            deleteButton.setEnabled(false);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Could not delete ingredient", Toast.LENGTH_SHORT).show();
        }
    }

    private PantryItem buildItem() {
        String name = nameEdit.getText().toString().trim();
        double quantity = parseQuantity(quantityEdit.getText().toString().trim());
        String unit = unitEdit.getText().toString().trim().toLowerCase(Locale.ROOT);
        return new PantryItem(0, name, quantity, unit, getExpiryDate());
    }

    private String getExpiryDate() {
        // No date
        if (!expiryToggle.isChecked()) {
            return "";
        }

        // Format yyyy-MM-dd
        int year = expiryPicker.getYear();
        int month = expiryPicker.getMonth() + 1;
        int day = expiryPicker.getDayOfMonth();
        return String.format(Locale.US, "%04d-%02d-%02d", year, month, day);
    }

    private boolean checkName() {
        String name = nameEdit.getText().toString().trim();

        // Check empty
        if (name.isEmpty()) {
            nameEdit.setError("Enter a name");
            return false;
        }

        // Check letters
        for (int i = 0; i < name.length(); i++) {
            char letter = name.charAt(i);
            if (!Character.isLetter(letter) && letter != ' ') {
                nameEdit.setError("Use letters and spaces only");
                return false;
            }
        }
        return true;
    }

    private boolean checkQuantity() {
        String text = quantityEdit.getText().toString().trim();

        // Check empty
        if (text.isEmpty()) {
            quantityEdit.setError("Enter a quantity");
            return false;
        }

        // Check number
        double number = parseQuantity(text);
        if (number <= 0) {
            quantityEdit.setError("Enter a number above 0");
            return false;
        }

        // Check size
        if (number > MAX_QUANTITY) {
            quantityEdit.setError("Enter a number up to 100000");
            return false;
        }
        return true;
    }

    private double parseQuantity(String text) {
        try {
            double number = Double.parseDouble(text);
            if (Double.isNaN(number) || Double.isInfinite(number)) {
                return -1;
            }
            return number;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean checkUnit() {
        String unit = unitEdit.getText().toString().trim().toLowerCase(Locale.ROOT);

        // Check empty
        if (unit.isEmpty()) {
            unitEdit.setError("Enter a unit");
            return false;
        }

        // Check allowed units
        for (int i = 0; i < ALLOWED_UNITS.length; i++) {
            if (unit.equals(ALLOWED_UNITS[i])) {
                return true;
            }
        }
        unitEdit.setError("Use g, kg, ml, l, cup, tbsp, tsp or pcs");
        return false;
    }
}
