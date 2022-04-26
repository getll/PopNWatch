package com.example.popnwatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminSnackRecylerViewAdapter extends RecyclerView.Adapter<AdminSnackRecylerViewAdapter.ViewHolder> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> prices = new ArrayList<>();
    ArrayList<String> genres = new ArrayList<>();

    Context mContext;
    LayoutInflater minflator;

    public AdminSnackRecylerViewAdapter(ArrayList<String> names ,ArrayList<String> imgs, ArrayList<String> prices, ArrayList<String> genres, Context mContext) {
        this.names = names;
        this.imgs = imgs;
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
        Glide.with(mContext)
                .asBitmap()
                .load(imgs.get(position))
                .into(holder.itemImg);
        holder.title.setText(names.get( position ));
        holder.info.setText(prices.get( position ));
        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SnackDB db = new SnackDB( mContext );
                db.deleteData( names.get( holder.getAdapterPosition() ) );
            }
        } );

        holder.mainLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, EditSnackActivity.class );
                i.putExtra("names", String.valueOf(names.get(holder.getAdapterPosition())));
                i.putExtra("imgs", String.valueOf(imgs.get(holder.getAdapterPosition())));
                i.putExtra("price", String.valueOf(prices.get(holder.getAdapterPosition())));
                i.putExtra("genre", String.valueOf(genres.get(holder.getAdapterPosition())));
                mContext.startActivity(i);
                Toast.makeText(mContext,"works", Toast.LENGTH_SHORT).show();
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
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            itemImg = itemView.findViewById( R.id.itemImg );
            title = itemView.findViewById( R.id.titleTextView );
            info = itemView.findViewById( R.id.infoTextView );
            delete = itemView.findViewById( R.id.deleteButton );
            mainLayout = itemView.findViewById( R.id.mainLayout );


        }
    }
}
