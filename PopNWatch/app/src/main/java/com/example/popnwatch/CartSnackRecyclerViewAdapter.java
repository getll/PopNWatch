package com.example.popnwatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartSnackRecyclerViewAdapter extends RecyclerView.Adapter<CartSnackRecyclerViewAdapter.ViewHolder> {
    List<CartSnack> cartSnacks;
    Context context;
    LayoutInflater layoutInflater;

    public CartSnackRecyclerViewAdapter(List<CartSnack> cartSnacks, Context context) {
        this.cartSnacks = cartSnacks;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.client_cart_snack_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartSnack cartSnack = cartSnacks.get(position);

        Glide.with(context).asBitmap().load(cartSnack.getImg()).into(holder.snackImgImageView);

        holder.snackTitleTextView.setText(cartSnack.getQuantity() + " x " + cartSnack.getName());
        holder.snackInfoTextView.setText(cartSnack.getPrice() + "");
    }

    @Override
    public int getItemCount() {
        return cartSnacks.size();
    }

    public double calcPrice() {
        double total = 0;

        for (CartSnack snack : cartSnacks) {
            int quantity = snack.getQuantity();
            double price = snack.getPrice();

            total += quantity * price;
        }

        return total;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView snackImgImageView;
        TextView snackTitleTextView, snackInfoTextView;
        Button editCartSnackButton, deleteSnackFromCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            snackImgImageView = itemView.findViewById(R.id.snackImgImageView);
            snackTitleTextView = itemView.findViewById(R.id.snackTitleTextView);
            snackInfoTextView = itemView.findViewById(R.id.snackInfoTextView);
            editCartSnackButton = itemView.findViewById(R.id.editCartSnackButton);
            deleteSnackFromCartButton = itemView.findViewById(R.id.deleteSnackFromCartButton);

            editCartSnackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

            deleteSnackFromCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}
