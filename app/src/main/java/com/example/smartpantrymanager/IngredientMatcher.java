package com.example.smartpantrymanager;

import java.util.ArrayList;

public class IngredientMatcher {

    private static final double TOLERANCE = 0.000001;

    public static String normaliseName(String name) {
        if (name == null) {
            return "";
        }

        // Clean text
        String cleanName = name.toLowerCase().trim();
        cleanName = collapseSpaces(cleanName);

        // Make singular
        return makeSingular(cleanName);
    }

    private static String collapseSpaces(String text) {
        String result = "";
        boolean lastWasSpace = false;

        // Keep one space
        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            if (Character.isWhitespace(letter)) {
                if (!lastWasSpace) {
                    result = result + " ";
                }
                lastWasSpace = true;
            } else {
                result = result + letter;
                lastWasSpace = false;
            }
        }
        return result;
    }

    private static String makeSingular(String name) {
        int length = name.length();
        if (length <= 3) {
            return name;
        }

        // Endings
        if (name.endsWith("ies")) {
            return name.substring(0, length - 3) + "y";
        }
        if (name.endsWith("oes") || name.endsWith("ches")
                || name.endsWith("shes") || name.endsWith("xes")) {
            return name.substring(0, length - 2);
        }
        if (name.endsWith("s") && !name.endsWith("ss")) {
            return name.substring(0, length - 1);
        }
        return name;
    }

    private static String cleanUnit(String unit) {
        if (unit == null) {
            return "";
        }
        return unit.trim().toLowerCase();
    }

    public static String unitGroup(String unit) {
        switch (cleanUnit(unit)) {
            case "g":
            case "kg":
                return "weight";
            case "ml":
            case "l":
            case "cup":
            case "tbsp":
            case "tsp":
                return "volume";
            case "pcs":
            case "piece":
            case "pieces":
            case "item":
            case "whole":
                return "count";
            default:
                return "unknown";
        }
    }

    public static double toBaseAmount(double quantity, String unit) {
        switch (cleanUnit(unit)) {
            case "kg":
            case "l":
                return quantity * 1000;
            case "cup":
                return quantity * 250;
            case "tbsp":
                return quantity * 15;
            case "tsp":
                return quantity * 5;
            default:
                return quantity;
        }
    }

    public static double pantryAmountFor(RecipeIngredient ingredient, ArrayList<PantryItem> pantry) {
        String name = normaliseName(ingredient.getName());
        String group = unitGroup(ingredient.getUnit());
        double total = 0;

        // Unknown unit
        if (group.equals("unknown")) {
            return 0;
        }

        // Add matching items
        for (int i = 0; i < pantry.size(); i++) {
            PantryItem item = pantry.get(i);
            boolean sameName = normaliseName(item.getName()).equals(name);
            boolean sameGroup = unitGroup(item.getUnit()).equals(group);
            if (sameName && sameGroup) {
                total = total + toBaseAmount(item.getQuantity(), item.getUnit());
            }
        }
        return total;
    }

    public static double getPantryAmountInUnit(RecipeIngredient ingredient,
                                               ArrayList<PantryItem> pantry) {
        double baseAmount = pantryAmountFor(ingredient, pantry);
        double oneUnit = toBaseAmount(1, ingredient.getUnit());

        // Back to recipe unit
        if (oneUnit == 0) {
            return 0;
        }
        return baseAmount / oneUnit;
    }

    public static boolean hasIngredient(RecipeIngredient ingredient, ArrayList<PantryItem> pantry) {
        double needed = toBaseAmount(ingredient.getQuantity(), ingredient.getUnit());
        double have = pantryAmountFor(ingredient, pantry);

        // Small tolerance
        return have + TOLERANCE >= needed;
    }

    public static int countMissing(Recipe recipe, ArrayList<PantryItem> pantry) {
        int missing = 0;
        ArrayList<RecipeIngredient> ingredients = recipe.getIngredients();

        // Count short ingredients
        for (int i = 0; i < ingredients.size(); i++) {
            if (!hasIngredient(ingredients.get(i), pantry)) {
                missing++;
            }
        }
        return missing;
    }

    public static String getMissingIngredientName(Recipe recipe, ArrayList<PantryItem> pantry) {
        ArrayList<RecipeIngredient> ingredients = recipe.getIngredients();

        // Find first short ingredient
        for (int i = 0; i < ingredients.size(); i++) {
            if (!hasIngredient(ingredients.get(i), pantry)) {
                return ingredients.get(i).getName();
            }
        }
        return "";
    }

    public static boolean canMake(Recipe recipe, ArrayList<PantryItem> pantry) {
        return countMissing(recipe, pantry) == 0;
    }

    public static ArrayList<Recipe> getSuggestedRecipes(ArrayList<Recipe> recipes,
                                                        ArrayList<PantryItem> pantry) {
        ArrayList<Recipe> suggested = new ArrayList<Recipe>();

        // Missing none
        for (int i = 0; i < recipes.size(); i++) {
            if (canMake(recipes.get(i), pantry)) {
                suggested.add(recipes.get(i));
            }
        }
        return suggested;
    }

    public static ArrayList<Recipe> getAlmostThereRecipes(ArrayList<Recipe> recipes,
                                                          ArrayList<PantryItem> pantry) {
        ArrayList<Recipe> almostThere = new ArrayList<Recipe>();

        // Missing exactly one
        for (int i = 0; i < recipes.size(); i++) {
            if (countMissing(recipes.get(i), pantry) == 1) {
                almostThere.add(recipes.get(i));
            }
        }
        return almostThere;
    }
}
