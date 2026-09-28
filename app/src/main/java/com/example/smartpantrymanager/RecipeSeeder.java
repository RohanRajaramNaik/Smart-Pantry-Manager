package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class RecipeSeeder {

    public static void seedRecipes(SQLiteDatabase db) {
        seedVeggieOmelette(db);
        seedScrambledEggs(db);
        seedFrenchToast(db);
        seedCheeseToast(db);
        seedTomatoPasta(db);
        seedGarlicButterPasta(db);
        seedMacAndCheese(db);
        seedFriedRice(db);
        seedTomatoRice(db);
        seedRiceAndBeans(db);
        seedMashedPotatoes(db);
        seedRoastPotatoes(db);
        seedPotatoOmelette(db);
        seedChickenAndRice(db);
        seedGarlicChicken(db);
        seedChickenStirFry(db);
        seedPancakes(db);
        seedBeanStew(db);
    }

    private static long addRecipe(SQLiteDatabase db, String name, String steps) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_NAME, name);
        values.put(DatabaseHelper.COL_STEPS, steps);
        return db.insert(DatabaseHelper.TABLE_RECIPES, null, values);
    }

    private static void addIngredient(SQLiteDatabase db, long recipeId, String name,
                                      double quantity, String unit) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_RECIPE_ID, recipeId);
        values.put(DatabaseHelper.COL_NAME, name);
        values.put(DatabaseHelper.COL_QUANTITY, quantity);
        values.put(DatabaseHelper.COL_UNIT, unit);
        db.insert(DatabaseHelper.TABLE_RECIPE_INGREDIENTS, null, values);
    }

    private static void seedVeggieOmelette(SQLiteDatabase db) {
        String steps = "1. Chop the tomato and onion.\n"
                + "2. Beat the eggs with the milk.\n"
                + "3. Cook the onion and tomato in a pan.\n"
                + "4. Pour in the eggs and add the cheese.\n"
                + "5. Fold the omelette and serve hot.";
        long id = addRecipe(db, "Veggie Omelette", steps);
        addIngredient(db, id, "egg", 3, "pcs");
        addIngredient(db, id, "tomato", 1, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "cheese", 50, "g");
        addIngredient(db, id, "milk", 50, "ml");
    }

    private static void seedScrambledEggs(SQLiteDatabase db) {
        String steps = "1. Beat the eggs with the milk and salt.\n"
                + "2. Melt the butter in a pan.\n"
                + "3. Pour in the eggs and stir slowly until soft.";
        long id = addRecipe(db, "Scrambled Eggs", steps);
        addIngredient(db, id, "eggs", 3, "pcs");
        addIngredient(db, id, "butter", 10, "g");
        addIngredient(db, id, "milk", 30, "ml");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedFrenchToast(SQLiteDatabase db) {
        String steps = "1. Mix the eggs, milk and sugar in a bowl.\n"
                + "2. Dip each slice of bread in the mix.\n"
                + "3. Melt the butter in a pan.\n"
                + "4. Fry the bread until golden on both sides.";
        long id = addRecipe(db, "French Toast", steps);
        addIngredient(db, id, "bread", 4, "pieces");
        addIngredient(db, id, "eggs", 2, "pcs");
        addIngredient(db, id, "milk", 100, "ml");
        addIngredient(db, id, "butter", 10, "g");
        addIngredient(db, id, "sugar", 1, "tbsp");
    }

    private static void seedCheeseToast(SQLiteDatabase db) {
        String steps = "1. Spread the butter on the bread.\n"
                + "2. Cover the bread with cheese.\n"
                + "3. Grill until the cheese melts and bubbles.";
        long id = addRecipe(db, "Cheese Toast", steps);
        addIngredient(db, id, "bread", 2, "pieces");
        addIngredient(db, id, "cheese", 60, "g");
        addIngredient(db, id, "butter", 10, "g");
    }

    private static void seedTomatoPasta(SQLiteDatabase db) {
        String steps = "1. Boil the pasta in salted water.\n"
                + "2. Fry the garlic in oil for one minute.\n"
                + "3. Add the chopped tomatoes and cook until soft.\n"
                + "4. Mix the pasta into the sauce and serve.";
        long id = addRecipe(db, "Tomato Pasta", steps);
        addIngredient(db, id, "pasta", 200, "g");
        addIngredient(db, id, "tomatoes", 3, "pcs");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "oil", 2, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedGarlicButterPasta(SQLiteDatabase db) {
        String steps = "1. Boil the pasta until soft.\n"
                + "2. Melt the butter and fry the garlic.\n"
                + "3. Toss the pasta in the garlic butter.\n"
                + "4. Sprinkle with cheese and salt.";
        long id = addRecipe(db, "Garlic Butter Pasta", steps);
        addIngredient(db, id, "pasta", 200, "g");
        addIngredient(db, id, "butter", 30, "g");
        addIngredient(db, id, "garlic", 3, "pcs");
        addIngredient(db, id, "cheese", 40, "g");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedMacAndCheese(SQLiteDatabase db) {
        String steps = "1. Boil the pasta and drain it.\n"
                + "2. Melt the butter and stir in the flour.\n"
                + "3. Slowly add the milk and stir until thick.\n"
                + "4. Add the cheese and mix in the pasta.";
        long id = addRecipe(db, "Mac and Cheese", steps);
        addIngredient(db, id, "pasta", 250, "g");
        addIngredient(db, id, "cheese", 150, "g");
        addIngredient(db, id, "milk", 200, "ml");
        addIngredient(db, id, "butter", 20, "g");
        addIngredient(db, id, "flour", 2, "tbsp");
    }

    private static void seedFriedRice(SQLiteDatabase db) {
        String steps = "1. Chop the onion and carrots.\n"
                + "2. Fry them in oil for three minutes.\n"
                + "3. Add the cooked rice and stir well.\n"
                + "4. Push the rice aside and scramble the eggs.\n"
                + "5. Mix everything together and add salt.";
        long id = addRecipe(db, "Fried Rice", steps);
        addIngredient(db, id, "rice", 2, "cup");
        addIngredient(db, id, "eggs", 2, "pcs");
        addIngredient(db, id, "carrots", 1, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedTomatoRice(SQLiteDatabase db) {
        String steps = "1. Fry the chopped onion in oil.\n"
                + "2. Add the tomatoes and cook until soft.\n"
                + "3. Stir in the rice, salt and two cups of water.\n"
                + "4. Cover and cook until the rice is soft.";
        long id = addRecipe(db, "Tomato Rice", steps);
        addIngredient(db, id, "rice", 1, "cup");
        addIngredient(db, id, "tomatoes", 2, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedRiceAndBeans(SQLiteDatabase db) {
        String steps = "1. Cook the rice in water until soft.\n"
                + "2. Fry the onion and garlic in oil.\n"
                + "3. Add the beans and salt and heat through.\n"
                + "4. Serve the beans over the rice.";
        long id = addRecipe(db, "Rice and Beans", steps);
        addIngredient(db, id, "rice", 1, "cup");
        addIngredient(db, id, "beans", 200, "g");
        addIngredient(db, id, "onion", 1, "piece");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedMashedPotatoes(SQLiteDatabase db) {
        String steps = "1. Peel and chop the potatoes.\n"
                + "2. Boil them until very soft and drain.\n"
                + "3. Mash with the butter, milk and salt.";
        long id = addRecipe(db, "Mashed Potatoes", steps);
        addIngredient(db, id, "potatoes", 500, "g");
        addIngredient(db, id, "butter", 30, "g");
        addIngredient(db, id, "milk", 100, "ml");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedRoastPotatoes(SQLiteDatabase db) {
        String steps = "1. Heat the oven to 200 degrees.\n"
                + "2. Cut the potatoes into chunks.\n"
                + "3. Toss with oil, garlic and salt on a tray.\n"
                + "4. Bake for 40 minutes until crispy.";
        long id = addRecipe(db, "Roast Potatoes", steps);
        addIngredient(db, id, "potatoes", 600, "g");
        addIngredient(db, id, "oil", 3, "tbsp");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedPotatoOmelette(SQLiteDatabase db) {
        String steps = "1. Slice the potato and onion thinly.\n"
                + "2. Fry them in oil until soft.\n"
                + "3. Beat the eggs with salt and pour over.\n"
                + "4. Cook until set and flip once.";
        long id = addRecipe(db, "Potato Omelette", steps);
        addIngredient(db, id, "potato", 2, "pcs");
        addIngredient(db, id, "eggs", 4, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 2, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedChickenAndRice(SQLiteDatabase db) {
        String steps = "1. Cut the chicken into pieces.\n"
                + "2. Fry the chicken, onion and garlic in oil.\n"
                + "3. Add the rice, salt and two cups of water.\n"
                + "4. Cover and cook until the rice is soft.";
        long id = addRecipe(db, "Chicken and Rice", steps);
        addIngredient(db, id, "chicken", 300, "g");
        addIngredient(db, id, "rice", 1, "cup");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedGarlicChicken(SQLiteDatabase db) {
        String steps = "1. Rub the chicken with salt.\n"
                + "2. Melt the butter in a pan.\n"
                + "3. Fry the chicken until golden.\n"
                + "4. Add the garlic and cook for five minutes.";
        long id = addRecipe(db, "Garlic Chicken", steps);
        addIngredient(db, id, "chicken", 400, "g");
        addIngredient(db, id, "garlic", 4, "pcs");
        addIngredient(db, id, "butter", 20, "g");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedChickenStirFry(SQLiteDatabase db) {
        String steps = "1. Slice the chicken, carrots and onion.\n"
                + "2. Heat the oil in a pan.\n"
                + "3. Fry the chicken until cooked.\n"
                + "4. Add the vegetables and salt and stir for five minutes.";
        long id = addRecipe(db, "Chicken Stir Fry", steps);
        addIngredient(db, id, "chicken", 250, "g");
        addIngredient(db, id, "carrots", 2, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedPancakes(SQLiteDatabase db) {
        String steps = "1. Mix the flour, sugar, egg and milk into a batter.\n"
                + "2. Melt a little butter in a pan.\n"
                + "3. Pour in some batter and cook until bubbles show.\n"
                + "4. Flip and cook the other side.";
        long id = addRecipe(db, "Pancakes", steps);
        addIngredient(db, id, "flour", 1, "cup");
        addIngredient(db, id, "milk", 250, "ml");
        addIngredient(db, id, "egg", 1, "pcs");
        addIngredient(db, id, "sugar", 2, "tbsp");
        addIngredient(db, id, "butter", 20, "g");
    }

    private static void seedBeanStew(SQLiteDatabase db) {
        String steps = "1. Chop the onion, carrots and tomatoes.\n"
                + "2. Fry the onion and carrots in oil.\n"
                + "3. Add the tomatoes, beans and salt.\n"
                + "4. Simmer for 20 minutes.";
        long id = addRecipe(db, "Bean Stew", steps);
        addIngredient(db, id, "beans", 300, "g");
        addIngredient(db, id, "tomatoes", 2, "pcs");
        addIngredient(db, id, "carrots", 2, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }
}
