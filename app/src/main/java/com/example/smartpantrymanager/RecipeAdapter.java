package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private ArrayList<Recipe> recipeList;
    private ArrayList<String> missingList;

    public RecipeAdapter(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
        this.missingList = null;
    }

    public RecipeAdapter(ArrayList<Recipe> recipeList, ArrayList<String> missingList) {
        this.recipeList = recipeList;
        this.missingList = missingList;
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeNameText;
        TextView ingredientCountText;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeNameText = itemView.findViewById(R.id.recipeNameText);
            ingredientCountText = itemView.findViewById(R.id.ingredientCountText);
        }
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);

        // Show recipe details
        holder.recipeNameText.setText(recipe.getName());
        showSecondLine(holder, recipe, position);

        // Open detail screen
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra("recipe_id", recipe.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    private void showSecondLine(RecipeViewHolder holder, Recipe recipe, int position) {
        // Missing ingredient
        if (missingList != null) {
            holder.ingredientCountText.setText("Missing: " + missingList.get(position));
        } else {
            holder.ingredientCountText.setText(makeCountText(recipe.getIngredients().size()));
        }
    }

    private String makeCountText(int count) {
        // Singular or plural
        if (count == 1) {
            return "1 ingredient";
        }
        return count + " ingredients";
    }
}
