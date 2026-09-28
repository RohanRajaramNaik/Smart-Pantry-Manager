package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_PANTRY = "pantry";
    public static final String TABLE_RECIPES = "recipes";
    public static final String TABLE_RECIPE_INGREDIENTS = "recipe_ingredients";

    // Column names
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_QUANTITY = "quantity";
    public static final String COL_UNIT = "unit";
    public static final String COL_EXPIRY_DATE = "expiry_date";
    public static final String COL_STEPS = "steps";
    public static final String COL_RECIPE_ID = "recipe_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        createPantryTable(db);
        createRecipesTable(db);
        createIngredientsTable(db);

        // Seed recipes
        RecipeSeeder.seedRecipes(db);
    }

    private void createPantryTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_PANTRY + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_QUANTITY + " REAL, "
                + COL_UNIT + " TEXT, "
                + COL_EXPIRY_DATE + " TEXT)");
    }

    private void createRecipesTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_RECIPES + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_NAME + " TEXT, "
                + COL_STEPS + " TEXT)");
    }

    private void createIngredientsTable(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_RECIPE_INGREDIENTS + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_RECIPE_ID + " INTEGER, "
                + COL_NAME + " TEXT, "
                + COL_QUANTITY + " REAL, "
                + COL_UNIT + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop recipe tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE_INGREDIENTS);

        // Rebuild recipes
        createRecipesTable(db);
        createIngredientsTable(db);
        RecipeSeeder.seedRecipes(db);
    }

    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = makePantryValues(item);
        return db.insert(TABLE_PANTRY, null, values);
    }

    public ArrayList<PantryItem> getAllPantryItems() {
        ArrayList<PantryItem> pantryList = new ArrayList<PantryItem>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, null, null, null, null, COL_NAME + " ASC");

        // Read all rows
        while (cursor.moveToNext()) {
            pantryList.add(readPantryItem(cursor));
        }
        cursor.close();
        return pantryList;
    }

    public PantryItem getPantryItem(int id) {
        PantryItem item = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_PANTRY, null, COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        // Read one row
        if (cursor.moveToFirst()) {
            item = readPantryItem(cursor);
        }
        cursor.close();
        return item;
    }

    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = makePantryValues(item);
        return db.update(TABLE_PANTRY, values, COL_ID + " = ?",
                new String[]{String.valueOf(item.getId())});
    }

    public int deletePantryItem(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_PANTRY, COL_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public ArrayList<Recipe> getAllRecipes() {
        ArrayList<Recipe> recipeList = new ArrayList<Recipe>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, null, null, null, null, COL_ID + " ASC");

        // Read all rows
        while (cursor.moveToNext()) {
            recipeList.add(readRecipe(cursor));
        }
        cursor.close();

        // Load ingredients
        for (int i = 0; i < recipeList.size(); i++) {
            Recipe recipe = recipeList.get(i);
            recipe.setIngredients(getIngredientsForRecipe(recipe.getId()));
        }
        return recipeList;
    }

    public Recipe getRecipe(int id) {
        Recipe recipe = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPES, null, COL_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        // Read one row
        if (cursor.moveToFirst()) {
            recipe = readRecipe(cursor);
        }
        cursor.close();

        // Load ingredients
        if (recipe != null) {
            recipe.setIngredients(getIngredientsForRecipe(id));
        }
        return recipe;
    }

    private ArrayList<RecipeIngredient> getIngredientsForRecipe(int recipeId) {
        ArrayList<RecipeIngredient> ingredientList = new ArrayList<RecipeIngredient>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECIPE_INGREDIENTS, null, COL_RECIPE_ID + " = ?",
                new String[]{String.valueOf(recipeId)}, null, null, COL_ID + " ASC");

        // Read all rows
        while (cursor.moveToNext()) {
            ingredientList.add(readIngredient(cursor));
        }
        cursor.close();
        return ingredientList;
    }

    private Recipe readRecipe(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
        String steps = cursor.getString(cursor.getColumnIndexOrThrow(COL_STEPS));
        return new Recipe(id, name, steps);
    }

    private RecipeIngredient readIngredient(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
        int recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RECIPE_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
        double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANTITY));
        String unit = cursor.getString(cursor.getColumnIndexOrThrow(COL_UNIT));
        return new RecipeIngredient(id, recipeId, name, quantity, unit);
    }

    private ContentValues makePantryValues(PantryItem item) {
        ContentValues values = new ContentValues();
        values.put(COL_NAME, item.getName());
        values.put(COL_QUANTITY, item.getQuantity());
        values.put(COL_UNIT, item.getUnit());
        values.put(COL_EXPIRY_DATE, item.getExpiryDate());
        return values;
    }

    private PantryItem readPantryItem(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
        double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COL_QUANTITY));
        String unit = cursor.getString(cursor.getColumnIndexOrThrow(COL_UNIT));
        String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow(COL_EXPIRY_DATE));
        return new PantryItem(id, name, quantity, unit, expiryDate);
    }
}
