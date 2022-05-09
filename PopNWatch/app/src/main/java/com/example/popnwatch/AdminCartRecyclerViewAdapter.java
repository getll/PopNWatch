package com.example.popnwatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdminCartRecyclerViewAdapter extends RecyclerView.Adapter<AdminCartRecyclerViewAdapter.ViewHolder> {
    List<Cart> carts;
    Context context;
    LayoutInflater layoutInflater;

    public AdminCartRecyclerViewAdapter(List<Cart> carts, Context context) {
        this.carts = carts;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminCartRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.from(parent.getContext()).inflate(R.layout.receipt_row, parent, false);

        return new AdminCartRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminCartRecyclerViewAdapter.ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.textView.setText("User ID: " + cart.getUserId() + "\n" + cart.toString());
    }

    @Override
    public int getItemCount() {
        return carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.textView2);
        }
    }
}
