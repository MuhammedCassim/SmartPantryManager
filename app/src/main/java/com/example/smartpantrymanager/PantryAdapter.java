package com.example.smartpantrymanager;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private ArrayList<PantryItem> pantryItems;
    private Context context;
    private DatabaseHelper databaseHelper;

    public PantryAdapter(Context context, ArrayList<PantryItem> pantryItems) {
        this.context = context;
        this.pantryItems = pantryItems;
        databaseHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {
        PantryItem item = pantryItems.get(position);

        holder.txtItemName.setText(item.getName());

        String quantityText = item.getQuantity() + " " + item.getUnit();
        holder.txtItemQuantity.setText(quantityText);

        if (item.getExpiryDate() == null || item.getExpiryDate().isEmpty()) {
            holder.txtItemExpiry.setText("no expiry date");
        } else {
            holder.txtItemExpiry.setText("expiry " + item.getExpiryDate());
        }

        // edit item
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, AddEditIngredientActivity.class);

            intent.putExtra("id", item.getId());
            intent.putExtra("name", item.getName());
            intent.putExtra("quantity", item.getQuantity());
            intent.putExtra("unit", item.getUnit());
            intent.putExtra("expiry", item.getExpiryDate());

            context.startActivity(intent);
        });

        // delete item
        holder.btnDelete.setOnClickListener(v -> {

            // stop double clicks
            holder.btnDelete.setEnabled(false);

            AlertDialog dialog = new AlertDialog.Builder(context)
                    .setTitle("delete ingredient")
                    .setMessage("are you sure you want to delete this item")
                    .setPositiveButton("yes", (dialogInterface, which) -> {

                        int result = databaseHelper.deletePantryItem(item.getId());

                        if (result > 0) {
                            int currentPosition = holder.getAdapterPosition();

                            if (currentPosition != RecyclerView.NO_POSITION) {
                                pantryItems.remove(currentPosition);
                                notifyItemRemoved(currentPosition);
                            }

                            Toast.makeText(context, "ingredient deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            holder.btnDelete.setEnabled(true);
                        }
                    })
                    .setNegativeButton("no", (dialogInterface, which) -> {
                        holder.btnDelete.setEnabled(true);
                    })
                    .create();

            dialog.setOnCancelListener(dialogInterface ->
                    holder.btnDelete.setEnabled(true)
            );

            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView txtItemName;
        TextView txtItemQuantity;
        TextView txtItemExpiry;
        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemQuantity = itemView.findViewById(R.id.txtItemQuantity);
            txtItemExpiry = itemView.findViewById(R.id.txtItemExpiry);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}