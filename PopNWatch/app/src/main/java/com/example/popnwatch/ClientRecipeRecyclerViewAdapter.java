package com.example.popnwatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ClientRecipeRecyclerViewAdapter extends RecyclerView.Adapter<ClientRecipeRecyclerViewAdapter.ViewHolder> {


    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    ArrayList<String> eta = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    Context mContext;
    LayoutInflater minflator;

    public ClientRecipeRecyclerViewAdapter(ArrayList<String> names, ArrayList<String> imgs, ArrayList<String> desc, ArrayList<String> eta, ArrayList<String> genre, Context mContext) {
        this.names = names;
        this.imgs = imgs;
        this.desc = desc;
        this.eta = eta;
        this.genre = genre;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ClientRecipeRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflator.from(parent.getContext()).inflate( R.layout.client_recipe_row, parent, false);
        return new ClientRecipeRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientRecipeRecyclerViewAdapter.ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(imgs.get(position))
                .into(holder.recipeImg);
        holder.name.setText(names.get( position ));


        holder.recipeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ClientRecipeActivity.class );
                i.putExtra("names", String.valueOf(names.get(holder.getAdapterPosition())));
                i.putExtra("imgs", String.valueOf(imgs.get(holder.getAdapterPosition())));
                i.putExtra("desc", String.valueOf(desc.get(holder.getAdapterPosition())));
                i.putExtra("eta", String.valueOf(eta.get(holder.getAdapterPosition())));
                i.putExtra("genre", String.valueOf(genre.get(holder.getAdapterPosition())));
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

        ImageView recipeImg;
        TextView name;
        ConstraintLayout recipeLayout;

        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            recipeImg = itemView.findViewById( R.id.clientRecipeImageView );
            name = itemView.findViewById( R.id.clientRecipeNameTextView );
            recipeLayout = itemView.findViewById( R.id.clientRecipeLayout );
        }
    }
}
