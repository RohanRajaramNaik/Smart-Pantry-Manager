package com.example.smartpantrymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<PantryItem> pantryList;

    public PantryAdapter(ArrayList<PantryItem> pantryList) {
        this.pantryList = pantryList;
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView nameText;
        TextView quantityText;
        TextView expiryText;

        public PantryViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            quantityText = itemView.findViewById(R.id.quantityText);
            expiryText = itemView.findViewById(R.id.expiryText);
        }
    }

    @Override
    public PantryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PantryViewHolder holder, int position) {
        final PantryItem item = pantryList.get(position);

        // Show item details
        holder.nameText.setText(item.getName());
        holder.quantityText.setText(formatQuantity(item.getQuantity()) + " " + item.getUnit());
        showExpiry(holder, item);

        // Open edit screen
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context, AddEditIngredientActivity.class);
                intent.putExtra("pantry_id", item.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pantryList.size();
    }

    private void showExpiry(PantryViewHolder holder, PantryItem item) {
        String expiryDate = item.getExpiryDate();

        // Hide if empty
        if (expiryDate == null || expiryDate.isEmpty()) {
            holder.expiryText.setVisibility(View.GONE);
        } else {
            holder.expiryText.setVisibility(View.VISIBLE);
            holder.expiryText.setText("Expires: " + expiryDate);
        }
    }

    public static String formatQuantity(double quantity) {
        // Whole numbers without decimals
        if (quantity == (int) quantity) {
            return String.valueOf((int) quantity);
        }
        return String.valueOf(quantity);
    }
}
