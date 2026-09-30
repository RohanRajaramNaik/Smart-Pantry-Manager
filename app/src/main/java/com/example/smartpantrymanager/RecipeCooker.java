package com.example.smartpantrymanager;

import java.util.ArrayList;

public class RecipeCooker {

    private static final double TOLERANCE = 0.000001;

    public static ArrayList<PantryItem> findMatchingRows(RecipeIngredient ingredient,
                                                         ArrayList<PantryItem> pantry) {
        ArrayList<PantryItem> rows = new ArrayList<PantryItem>();
        String name = IngredientMatcher.normaliseName(ingredient.getName());
        String group = IngredientMatcher.unitGroup(ingredient.getUnit());

        // Same name and group
        for (int i = 0; i < pantry.size(); i++) {
            PantryItem item = pantry.get(i);
            boolean sameName = IngredientMatcher.normaliseName(item.getName()).equals(name);
            boolean sameGroup = IngredientMatcher.unitGroup(item.getUnit()).equals(group);
            if (sameName && sameGroup && !group.equals("unknown")) {
                rows.add(item);
            }
        }
        sortByExpiry(rows);
        return rows;
    }

    private static void sortByExpiry(ArrayList<PantryItem> rows) {
        // Insertion sort
        for (int i = 1; i < rows.size(); i++) {
            PantryItem row = rows.get(i);
            long rowDays = ExpiryHelper.getDaysLeft(row.getExpiryDate());
            int j = i - 1;

            // No date goes last
            while (j >= 0 && ExpiryHelper.getDaysLeft(rows.get(j).getExpiryDate()) > rowDays) {
                rows.set(j + 1, rows.get(j));
                j--;
            }
            rows.set(j + 1, row);
        }
    }

    public static void deductIngredient(DatabaseHelper db, RecipeIngredient ingredient,
                                        ArrayList<PantryItem> pantry) {
        double needed = IngredientMatcher.toBaseAmount(ingredient.getQuantity(),
                ingredient.getUnit());
        ArrayList<PantryItem> rows = findMatchingRows(ingredient, pantry);

        // Use rows in order
        for (int i = 0; i < rows.size() && needed > TOLERANCE; i++) {
            PantryItem row = rows.get(i);
            double rowAmount = IngredientMatcher.toBaseAmount(row.getQuantity(), row.getUnit());
            double taken = Math.min(rowAmount, needed);
            needed = needed - taken;
            saveLeftover(db, row, rowAmount - taken);
        }
    }

    private static void saveLeftover(DatabaseHelper db, PantryItem row, double leftBase) {
        double left = IngredientMatcher.fromBaseAmount(leftBase, row.getUnit());
        left = Math.round(left * 100) / 100.0;

        // Used up
        if (leftBase < TOLERANCE || left <= 0) {
            db.deletePantryItem(row.getId());
            return;
        }

        // Keep the rest
        row.setQuantity(left);
        db.updatePantryItem(row);
    }

    public static boolean cookRecipe(DatabaseHelper db, Recipe recipe) {
        // Check again
        if (!IngredientMatcher.canMake(recipe, db.getAllPantryItems())) {
            return false;
        }

        // Take every ingredient
        ArrayList<RecipeIngredient> ingredients = recipe.getIngredients();
        for (int i = 0; i < ingredients.size(); i++) {
            deductIngredient(db, ingredients.get(i), db.getAllPantryItems());
        }
        return true;
    }
}
