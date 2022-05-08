package com.example.popnwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SnackCartRecyclerViewAdapter extends RecyclerView.Adapter<SnackCartRecyclerViewAdapter.ViewHolder> {

    ArrayList<CartSnack> cartSnacks = new ArrayList<>();

    Context context;
    LayoutInflater inflater;

    public SnackCartRecyclerViewAdapter(ArrayList<CartSnack> cartSnacks, Context context) {
        this.cartSnacks = cartSnacks;
        this.context = context;
    }

    @NonNull
    @Override
    public SnackCartRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.from( parent.getContext() ).inflate( R.layout.client_cart_snack_row, parent, false );

        return new SnackCartRecyclerViewAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull SnackCartRecyclerViewAdapter.ViewHolder holder, int position) {
        CartSnack cartSnack = cartSnacks.get(position);

        Glide.with(context)
                .asBitmap()
                .load(cartSnack.getImg())
                .into(holder.snackImgImageView);

        holder.snackTitleTextView.setText(cartSnack.getQuantity() + " x " + cartSnack.getName());
        holder.snackInfoTextView.setText(cartSnack.getPrice() + " each");
    }

    @Override
    public int getItemCount() {
        return cartSnacks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView snackImgImageView;
        TextView snackTitleTextView;
        TextView snackInfoTextView;
        Button editCartSnackButton;
        Button deleteSnackFromCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            snackImgImageView = itemView.findViewById(R.id.snackImgImageView);
            snackTitleTextView = itemView.findViewById(R.id.snackTitleTextView);
            snackInfoTextView = itemView.findViewById(R.id.snackInfoTextView);
            editCartSnackButton = itemView.findViewById(R.id.editCartSnackButton);
            deleteSnackFromCartButton = itemView.findViewById(R.id.deleteSnackFromCartButton);

            SharedPreferences sharedPreferences = context.getSharedPreferences("MY_APP_PREFERENCES", Context.MODE_PRIVATE);
            String userId = sharedPreferences.getString("userId", "error");

            editCartSnackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    EditText quantityInput = new EditText(context);
                    quantityInput.setInputType(InputType.TYPE_CLASS_NUMBER);

                    AlertDialog.Builder alertDialog = new AlertDialog.Builder( context );
                    alertDialog.setView(quantityInput)
                            .setTitle( "Edit Quantity" )
                            .setPositiveButton( "Edit cart", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    CartDb cartDb = new CartDb(context.getApplicationContext());
                                    CartSnack cartSnack = cartSnacks.get(getAbsoluteAdapterPosition());

                                    //get the quantity
                                    String quantityStr = quantityInput.getText().toString();
                                    int quantity = 1;

                                    if (!quantityStr.isEmpty() || Integer.parseInt(quantityStr) > 0) {
                                        quantity = Integer.parseInt(quantityStr);
                                    }

                                    //input to the db
                                    if (cartDb.editSnackCart(cartSnack.getSnackCartId(), cartSnack.getId(), quantity, cartSnack.getCartId())) {
                                        Toast.makeText(context, "Edited snack quantity", Toast.LENGTH_SHORT).show();

                                        ((CartActivity) context).getSnacks(userId);
                                    }
                                    else {
                                        Toast.makeText(context, "Could not edit snack quantity", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            } );
                    AlertDialog dialog = alertDialog.create();
                    dialog.show();
                }
            });

            deleteSnackFromCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CartDb cartDb = new CartDb(context.getApplicationContext());
                    CartSnack cartSnack = cartSnacks.get(getAbsoluteAdapterPosition());

                    if (cartDb.deleteSnackCart(cartSnack.getSnackCartId())) {
                        Toast.makeText(context, "Deleted snack from cart", Toast.LENGTH_SHORT).show();
                        ((CartActivity) context).getSnacks(userId);
                    }
                    else {
                        Toast.makeText(context, "Could not delete snack from cart", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
