# Smart Pantry Manager

Smart Pantry Manager is a java android application created for my Mobile App Development 700 assignment.

The purpose of the app is to help users keep track of the ingredients they currently have at home and suggest recipes that can be made from those ingredients

Users can add, edit and delete pantry items. Each pantry item stores the ingredient name, quantity, unit and an optional expiry date field.

The app also has a set of recipes. A recipe is only shown in the suggested recipes screen if all the ingredients that are needed for that recipe is available in the pantry and there is enough quantity available.

The app also includes expiry alerts, recipe details, settings and bottom navigation between the main screens.

## Database

I used SQLite with SQLiteOpenHelper for the database.

I chose SQLite because the app only needs to store its data locally on the device and does not need an internet connection.

The database stores the pantry items, recipes and the ingredients needed for each recipe.

The main tables used are:

- pantry_items
- recipes
- recipe_ingredients

SharedPreferences is also used to save the expiry alert setting and to help prevent the same expiry alert from showing repeatedly.

## Main features

- add pantry ingredients
- edit pantry ingredients
- delete pantry ingredients
- view pantry items
- view suggested recipes
- strict recipe matching
- view recipe details
- expiry alerts
- settings screen
- bottom navigation

## How to run the app

1. Download or clone the project from GitHub.
2. Open the project in Android Studio.
3. Wait for Gradle to finish syncing.
4. Start an Android emulator or connect an Android device.
5. Run the app from Android Studio.

## Requirements

- Android Studio
- Java
- Android SDK
- minimum SDK 24