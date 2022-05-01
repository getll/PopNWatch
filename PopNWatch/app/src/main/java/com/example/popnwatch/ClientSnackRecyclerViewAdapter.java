package com.example.popnwatch;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClientSnackRecyclerViewAdapter extends RecyclerView.Adapter<ClientSnackRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();

    Context mContext;
    LayoutInflater minflator;

    public ClientSnackRecyclerViewAdapter(ArrayList<String> names, ArrayList<String> imgs, ArrayList<String> prices, ArrayList<String> genres, Context mContext) {
        this.names = names;
        this.imgs = imgs;
        this.prices = prices;
        this.genres = genres;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflator.from( parent.getContext() ).inflate( R.layout.client_snack_row, parent, false );

        return new ClientSnackRecyclerViewAdapter.ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(imgs.get(position))
                .into(holder.itemImg);
        holder.title.setText(names.get( position ));
        holder.info.setText(prices.get( position ));


        holder.add.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder( mContext );
                alertDialog.setView( R.layout.add_to_cart_dialog )
                        .setTitle( "Select Quantity" )
                        .setPositiveButton( "Add to cart", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        } )
                        .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        } );
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView itemImg;
        TextView title, info;
        Button add;


        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            itemImg = itemView.findViewById( R.id.citemImg );
            title = itemView.findViewById( R.id.clientTitleTextView );
            info = itemView.findViewById( R.id.cinfoTextView );
            add = itemView.findViewById( R.id.addToCartButton);

        }
    }
}
