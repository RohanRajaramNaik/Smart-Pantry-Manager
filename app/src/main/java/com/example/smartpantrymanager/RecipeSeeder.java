package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class RecipeSeeder {

    public static void seedRecipes(SQLiteDatabase db) {
        // Original recipes
        seedCoreRecipes(db);

        // Extra recipes
        seedBreakfasts(db);
        seedLunches(db);
        seedDinners(db);
        seedSnacks(db);
    }

    private static void seedBreakfasts(SQLiteDatabase db) {
        seedBananaOatPancakes(db);
        seedPorridge(db);
        seedYoghurtBananaBowl(db);
        seedMushroomOmelette(db);
        seedSpinachScramble(db);
        seedBreakfastSandwich(db);
    }

    private static void seedLunches(SQLiteDatabase db) {
        seedTomatoSoup(db);
        seedMushroomToast(db);
        seedCheeseTomatoSandwich(db);
        seedPepperRice(db);
        seedPotatoSoup(db);
        seedSpinachPasta(db);
    }

    private static void seedDinners(SQLiteDatabase db) {
        seedSpaghettiBolognese(db);
        seedChickenAndPeppers(db);
        seedLemonChicken(db);
        seedMinceAndPotatoes(db);
        seedCreamyMushroomPasta(db);
        seedBeanSpinachStew(db);
    }

    private static void seedSnacks(SQLiteDatabase db) {
        seedBananaCake(db);
        seedLemonYoghurtPots(db);
        seedOatCookies(db);
        seedGarlicBread(db);
    }

    private static void seedCoreRecipes(SQLiteDatabase db) {
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

    private static void seedBananaOatPancakes(SQLiteDatabase db) {
        String steps = "1. Mash the bananas in a bowl.\n"
                + "2. Mix in the oats, eggs and milk.\n"
                + "3. Pour small rounds into a hot pan.\n"
                + "4. Cook until golden on both sides.";
        long id = addRecipe(db, "Banana Oat Pancakes", steps);
        addIngredient(db, id, "bananas", 2, "pcs");
        addIngredient(db, id, "oats", 1, "cup");
        addIngredient(db, id, "eggs", 2, "pcs");
        addIngredient(db, id, "milk", 100, "ml");
    }

    private static void seedPorridge(SQLiteDatabase db) {
        String steps = "1. Put the oats and milk in a pot.\n"
                + "2. Stir over low heat for five minutes.\n"
                + "3. Slice the banana on top.\n"
                + "4. Sprinkle with sugar and serve.";
        long id = addRecipe(db, "Porridge", steps);
        addIngredient(db, id, "oats", 1, "cup");
        addIngredient(db, id, "milk", 250, "ml");
        addIngredient(db, id, "sugar", 1, "tbsp");
        addIngredient(db, id, "banana", 1, "pcs");
    }

    private static void seedYoghurtBananaBowl(SQLiteDatabase db) {
        String steps = "1. Spoon the yoghurt into a bowl.\n"
                + "2. Slice the banana on top.\n"
                + "3. Sprinkle with the oats.";
        long id = addRecipe(db, "Yoghurt Banana Bowl", steps);
        addIngredient(db, id, "yoghurt", 200, "g");
        addIngredient(db, id, "banana", 1, "pcs");
        addIngredient(db, id, "oats", 3, "tbsp");
    }

    private static void seedMushroomOmelette(SQLiteDatabase db) {
        String steps = "1. Slice the mushrooms.\n"
                + "2. Fry them in butter until soft.\n"
                + "3. Beat the eggs with salt and pour over.\n"
                + "4. Add the cheese and fold.";
        long id = addRecipe(db, "Mushroom Omelette", steps);
        addIngredient(db, id, "eggs", 3, "pcs");
        addIngredient(db, id, "mushrooms", 100, "g");
        addIngredient(db, id, "butter", 10, "g");
        addIngredient(db, id, "cheese", 30, "g");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedSpinachScramble(SQLiteDatabase db) {
        String steps = "1. Melt the butter in a pan.\n"
                + "2. Wilt the spinach for one minute.\n"
                + "3. Add the beaten eggs and salt.\n"
                + "4. Stir until just set.";
        long id = addRecipe(db, "Spinach Scramble", steps);
        addIngredient(db, id, "eggs", 3, "pcs");
        addIngredient(db, id, "spinach", 50, "g");
        addIngredient(db, id, "butter", 10, "g");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedBreakfastSandwich(SQLiteDatabase db) {
        String steps = "1. Toast the bread and spread with butter.\n"
                + "2. Fry the egg in a pan.\n"
                + "3. Put the egg and cheese between the bread.";
        long id = addRecipe(db, "Breakfast Sandwich", steps);
        addIngredient(db, id, "bread", 2, "pieces");
        addIngredient(db, id, "egg", 1, "pcs");
        addIngredient(db, id, "cheese", 30, "g");
        addIngredient(db, id, "butter", 10, "g");
    }

    private static void seedTomatoSoup(SQLiteDatabase db) {
        String steps = "1. Fry the onion and garlic in butter.\n"
                + "2. Add the chopped tomatoes and salt.\n"
                + "3. Add a cup of water and simmer for 15 minutes.\n"
                + "4. Blend until smooth.";
        long id = addRecipe(db, "Tomato Soup", steps);
        addIngredient(db, id, "tomatoes", 4, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "butter", 20, "g");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedMushroomToast(SQLiteDatabase db) {
        String steps = "1. Slice the mushrooms and garlic.\n"
                + "2. Fry them in butter for five minutes.\n"
                + "3. Toast the bread.\n"
                + "4. Pile the mushrooms on the toast.";
        long id = addRecipe(db, "Mushroom Toast", steps);
        addIngredient(db, id, "bread", 2, "pieces");
        addIngredient(db, id, "mushrooms", 150, "g");
        addIngredient(db, id, "garlic", 1, "pcs");
        addIngredient(db, id, "butter", 20, "g");
    }

    private static void seedCheeseTomatoSandwich(SQLiteDatabase db) {
        String steps = "1. Butter the bread.\n"
                + "2. Layer the cheese and sliced tomato.\n"
                + "3. Close the sandwich and toast it in a pan.";
        long id = addRecipe(db, "Cheese and Tomato Sandwich", steps);
        addIngredient(db, id, "bread", 2, "pieces");
        addIngredient(db, id, "cheese", 40, "g");
        addIngredient(db, id, "tomato", 1, "pcs");
        addIngredient(db, id, "butter", 10, "g");
    }

    private static void seedPepperRice(SQLiteDatabase db) {
        String steps = "1. Chop the peppers and onion.\n"
                + "2. Fry them in oil until soft.\n"
                + "3. Stir in the rice, salt and two cups of water.\n"
                + "4. Cover and cook until the rice is soft.";
        long id = addRecipe(db, "Pepper Rice", steps);
        addIngredient(db, id, "rice", 1, "cup");
        addIngredient(db, id, "peppers", 2, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedPotatoSoup(SQLiteDatabase db) {
        String steps = "1. Peel and chop the potatoes and onion.\n"
                + "2. Fry the onion in butter.\n"
                + "3. Add the potatoes and water and boil until soft.\n"
                + "4. Blend with the milk and salt.";
        long id = addRecipe(db, "Potato Soup", steps);
        addIngredient(db, id, "potatoes", 400, "g");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "butter", 20, "g");
        addIngredient(db, id, "milk", 200, "ml");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedSpinachPasta(SQLiteDatabase db) {
        String steps = "1. Boil the pasta until soft.\n"
                + "2. Fry the garlic in oil.\n"
                + "3. Add the spinach and cook until it wilts.\n"
                + "4. Toss with the pasta and cheese.";
        long id = addRecipe(db, "Spinach Pasta", steps);
        addIngredient(db, id, "pasta", 200, "g");
        addIngredient(db, id, "spinach", 100, "g");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "oil", 2, "tbsp");
        addIngredient(db, id, "cheese", 30, "g");
    }

    private static void seedSpaghettiBolognese(SQLiteDatabase db) {
        String steps = "1. Fry the onion and garlic in oil.\n"
                + "2. Add the mince and cook until brown.\n"
                + "3. Add the chopped tomatoes and simmer for 20 minutes.\n"
                + "4. Boil the pasta and top with the sauce.";
        long id = addRecipe(db, "Spaghetti Bolognese", steps);
        addIngredient(db, id, "pasta", 250, "g");
        addIngredient(db, id, "mince", 300, "g");
        addIngredient(db, id, "tomatoes", 3, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
    }

    private static void seedChickenAndPeppers(SQLiteDatabase db) {
        String steps = "1. Slice the chicken, peppers and onion.\n"
                + "2. Fry the chicken in oil until cooked.\n"
                + "3. Add the peppers, onion and salt.\n"
                + "4. Cook for five more minutes.";
        long id = addRecipe(db, "Chicken and Peppers", steps);
        addIngredient(db, id, "chicken", 300, "g");
        addIngredient(db, id, "peppers", 2, "pcs");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedLemonChicken(SQLiteDatabase db) {
        String steps = "1. Rub the chicken with salt and garlic.\n"
                + "2. Fry it in butter until golden.\n"
                + "3. Squeeze the lemon over the top.\n"
                + "4. Cook for five more minutes.";
        long id = addRecipe(db, "Lemon Chicken", steps);
        addIngredient(db, id, "chicken", 400, "g");
        addIngredient(db, id, "lemon", 1, "pcs");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "butter", 20, "g");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedMinceAndPotatoes(SQLiteDatabase db) {
        String steps = "1. Peel, chop and boil the potatoes.\n"
                + "2. Fry the onion in oil.\n"
                + "3. Add the mince and salt and cook until brown.\n"
                + "4. Serve the mince over the potatoes.";
        long id = addRecipe(db, "Mince and Potatoes", steps);
        addIngredient(db, id, "mince", 300, "g");
        addIngredient(db, id, "potatoes", 400, "g");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
        addIngredient(db, id, "salt", 1, "tsp");
    }

    private static void seedCreamyMushroomPasta(SQLiteDatabase db) {
        String steps = "1. Boil the pasta until soft.\n"
                + "2. Fry the mushrooms and garlic in butter.\n"
                + "3. Pour in the milk and simmer until thick.\n"
                + "4. Mix in the pasta and cheese.";
        long id = addRecipe(db, "Creamy Mushroom Pasta", steps);
        addIngredient(db, id, "pasta", 200, "g");
        addIngredient(db, id, "mushrooms", 150, "g");
        addIngredient(db, id, "garlic", 2, "pcs");
        addIngredient(db, id, "milk", 100, "ml");
        addIngredient(db, id, "butter", 20, "g");
        addIngredient(db, id, "cheese", 30, "g");
    }

    private static void seedBeanSpinachStew(SQLiteDatabase db) {
        String steps = "1. Fry the chopped onion in oil.\n"
                + "2. Add the tomatoes and cook until soft.\n"
                + "3. Stir in the beans and simmer for 15 minutes.\n"
                + "4. Add the spinach and cook until it wilts.";
        long id = addRecipe(db, "Bean and Spinach Stew", steps);
        addIngredient(db, id, "beans", 300, "g");
        addIngredient(db, id, "spinach", 100, "g");
        addIngredient(db, id, "onion", 1, "pcs");
        addIngredient(db, id, "tomatoes", 2, "pcs");
        addIngredient(db, id, "oil", 1, "tbsp");
    }

    private static void seedBananaCake(SQLiteDatabase db) {
        String steps = "1. Mash the bananas and mix in the melted butter.\n"
                + "2. Stir in the eggs, sugar and flour.\n"
                + "3. Pour into a tin.\n"
                + "4. Bake at 180 degrees for 40 minutes.";
        long id = addRecipe(db, "Banana Cake", steps);
        addIngredient(db, id, "bananas", 3, "pcs");
        addIngredient(db, id, "flour", 2, "cup");
        addIngredient(db, id, "sugar", 0.5, "cup");
        addIngredient(db, id, "eggs", 2, "pcs");
        addIngredient(db, id, "butter", 100, "g");
    }

    private static void seedLemonYoghurtPots(SQLiteDatabase db) {
        String steps = "1. Grate a little lemon zest and squeeze the juice.\n"
                + "2. Stir the juice and sugar into the yoghurt.\n"
                + "3. Chill for one hour.";
        long id = addRecipe(db, "Lemon Yoghurt Pots", steps);
        addIngredient(db, id, "yoghurt", 300, "g");
        addIngredient(db, id, "lemon", 1, "pcs");
        addIngredient(db, id, "sugar", 2, "tbsp");
    }

    private static void seedOatCookies(SQLiteDatabase db) {
        String steps = "1. Cream the butter and sugar together.\n"
                + "2. Stir in the oats and flour.\n"
                + "3. Roll into small balls and flatten on a tray.\n"
                + "4. Bake at 180 degrees for 12 minutes.";
        long id = addRecipe(db, "Oat Cookies", steps);
        addIngredient(db, id, "oats", 1, "cup");
        addIngredient(db, id, "flour", 0.5, "cup");
        addIngredient(db, id, "sugar", 0.25, "cup");
        addIngredient(db, id, "butter", 100, "g");
    }

    private static void seedGarlicBread(SQLiteDatabase db) {
        String steps = "1. Mash the garlic into the butter.\n"
                + "2. Spread it on the bread.\n"
                + "3. Bake at 200 degrees for ten minutes.";
        long id = addRecipe(db, "Garlic Bread", steps);
        addIngredient(db, id, "bread", 4, "pieces");
        addIngredient(db, id, "butter", 50, "g");
        addIngredient(db, id, "garlic", 3, "pcs");
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
