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


public class AdminRecipeRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecipeRecyclerViewAdapter.ViewHolder> {

    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> imgs = new ArrayList<>();
    ArrayList<String> desc = new ArrayList<>();
    ArrayList<String> eta = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    Context mContext;
    LayoutInflater minflator;

    public AdminRecipeRecyclerViewAdapter(ArrayList<String> ids, ArrayList<String> names, ArrayList<String> imgs, ArrayList<String> desc, ArrayList<String> eta, ArrayList<String> genre, Context mContext) {
        this.names = names;
        this.ids = ids;
        this.imgs = imgs;
        this.desc = desc;
        this.eta = eta;
        this.genre = genre;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = minflator.from(parent.getContext()).inflate( R.layout.admin_recipe_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(imgs.get(position))
                .into(holder.recipeImg);
        holder.name.setText(names.get( position ));
        holder.delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipesDb db = new RecipesDb( mContext );
                db.deleteData( names.get( holder.getAdapterPosition() ) );
                ((AdminActivity) mContext).getRecipes();
            }
        } );

        holder.recipeLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, EditRecipeActivity.class );
                i.putExtra("id", String.valueOf(ids.get(holder.getAdapterPosition())));
                i.putExtra("names", String.valueOf(names.get(holder.getAdapterPosition())));
                i.putExtra("imgs", String.valueOf(imgs.get(holder.getAdapterPosition())));
                i.putExtra("desc", String.valueOf(desc.get(holder.getAdapterPosition())));
                i.putExtra("eta", String.valueOf(eta.get(holder.getAdapterPosition())));
                i.putExtra("genre", String.valueOf(genre.get(holder.getAdapterPosition())));
                ((AdminActivity) mContext).startActivityForResult(i, 2);
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
        ImageButton delete;
        ConstraintLayout recipeLayout;

        public ViewHolder(@NonNull View itemView) {

            super( itemView );
            recipeImg = itemView.findViewById( R.id.recipeImageView );
            name = itemView.findViewById( R.id.recipeNameTextView );
            delete = itemView.findViewById( R.id.deleteRecipeButton );
            recipeLayout = itemView.findViewById( R.id.recipeLayout );
        }
    }
}
