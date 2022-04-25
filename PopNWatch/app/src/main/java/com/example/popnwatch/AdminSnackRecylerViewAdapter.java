package com.example.popnwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminSnackRecylerViewAdapter extends RecyclerView.Adapter<AdminSnackRecylerViewAdapter.ViewHolder> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();

    Context mContext;
    LayoutInflater minflator;

    public AdminSnackRecylerViewAdapter(ArrayList<String> names, ArrayList<String> prices, ArrayList<String> genres, Context mContext) {
        this.names = names;
        this.prices = prices;
        this.genres = genres;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public AdminSnackRecylerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflator.from(parent.getContext()).inflate(R.layout.admin_movie_snack_row, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminSnackRecylerViewAdapter.ViewHolder holder, int position) {
        holder.title.setText(names.get( position ));
        holder.info.setText(prices.get( position ));

        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackDB db = new SnackDB( mContext );
                db.deleteData( names.get( holder.getAdapterPosition() ) );
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
        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            itemImg = itemView.findViewById( R.id.itemImg );
            title = itemView.findViewById( R.id.titleTextView );
            info = itemView.findViewById( R.id.infoTextView );
            delete = itemView.findViewById( R.id.deleteButton );

        }
    }
}
