package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllRecipesAdapter extends RecyclerView.Adapter<AllRecipesAdapter.AllRecipesViewHolder> {

    private ArrayList<Recipe> recipeList;
    private int[] missingList;

    public AllRecipesAdapter(ArrayList<Recipe> recipeList, int[] missingList) {
        this.recipeList = recipeList;
        this.missingList = missingList;
    }

    public static class AllRecipesViewHolder extends RecyclerView.ViewHolder {

        TextView recipeNameText;
        TextView ingredientCountText;
        TextView tagText;

        public AllRecipesViewHolder(View itemView) {
            super(itemView);
            recipeNameText = itemView.findViewById(R.id.recipeNameText);
            ingredientCountText = itemView.findViewById(R.id.ingredientCountText);
            tagText = itemView.findViewById(R.id.tagText);
        }
    }

    @Override
    public AllRecipesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_all_recipe, parent, false);
        return new AllRecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AllRecipesViewHolder holder, int position) {
        final Recipe recipe = recipeList.get(position);
        int total = recipe.getIngredients().size();
        int missing = missingList[position];

        // Show recipe details
        holder.recipeNameText.setText(recipe.getName());
        holder.ingredientCountText.setText((total - missing) + " of " + total + " ingredients");
        showTag(holder, missing);

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

    private void showTag(AllRecipesViewHolder holder, int missing) {
        Context context = holder.itemView.getContext();

        // Pick colours
        if (missing == 0) {
            holder.tagText.setText("Can make");
            holder.tagText.setBackgroundResource(R.drawable.bg_pill_have);
            holder.tagText.setTextColor(context.getColor(R.color.pill_have_text));
        } else if (missing == 1) {
            holder.tagText.setText("Missing 1");
            holder.tagText.setBackgroundResource(R.drawable.bg_pill_need);
            holder.tagText.setTextColor(context.getColor(R.color.pill_need_text));
        } else {
            holder.tagText.setText("Missing " + missing);
            holder.tagText.setBackgroundResource(R.drawable.bg_pill_missing);
            holder.tagText.setTextColor(context.getColor(R.color.pill_missing_text));
        }
    }
}
