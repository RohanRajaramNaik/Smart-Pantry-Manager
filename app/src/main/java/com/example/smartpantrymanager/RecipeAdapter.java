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

    public RecipeAdapter(ArrayList<Recipe> recipeList) {
        this.recipeList = recipeList;
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
        holder.ingredientCountText.setText(makeCountText(recipe.getIngredients().size()));

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

    private String makeCountText(int count) {
        // Singular or plural
        if (count == 1) {
            return "1 ingredient";
        }
        return count + " ingredients";
    }
}
