# Smart Pantry Manager

## Description

Smart Pantry Manager is an Android app that helps you keep track of the food you have at home. You add each item to your pantry with its amount and expiry date. The app then suggests recipes you can cook using only what you already have.

Many households throw away food every week. Some of it goes off before it is used. Some is bought twice because people forget what is in the cupboard. This app helps by showing what you own and what you can make with it. That means less waste and fewer trips to the shop.

## Features

- Add, view, edit and delete pantry items
- Save the name, amount, unit and expiry date of each item
- Keep your data after the app is closed
- Get recipe suggestions from 40 built-in recipes
- See a recipe only when you have every ingredient in the right amount
- See an "Almost There" list of recipes that are missing just one ingredient
- View the ingredients and steps for each recipe
- See which ingredients you have, need more of, or are missing
- Browse all recipes, sorted by how close you are to making each one
- Turn expiry alerts and the Almost There list on or off in Settings
- Move between screens with a bottom navigation bar
- Get a clear message when a form has empty or wrong input

## Database choice

This app uses SQLite with SQLiteOpenHelper. Here are three reasons:

1. It works offline. The app does not need the internet.
2. It needs no account or server. The data stays on the phone.
3. It is the database taught in the module.

## Setup and run

1. Clone the project:
   `git clone https://github.com/RohanRajaramNaik/Smart-Pantry-Manager.git`
2. Open Android Studio.
3. Choose File, then Open, and select the project folder.
4. Wait for Gradle to sync. Click "Sync Project with Gradle Files" if it does not start.
5. Create an emulator with API 24 or higher in Device Manager.
6. Press the Run button to start the app on the emulator.

## Screens

- **Pantry List (MainActivity):** shows all your pantry items. You can add, edit or delete an item from here.
- **Add or Edit Ingredient (AddEditIngredientActivity):** a form to add a new item or change an existing one.
- **Suggested Recipes (SuggestedRecipesActivity):** shows the recipes you can cook right now.
- **Recipe Detail (RecipeDetailActivity):** shows the ingredients and steps for one recipe.
- **Settings (SettingsActivity):** lets you change your app options.
