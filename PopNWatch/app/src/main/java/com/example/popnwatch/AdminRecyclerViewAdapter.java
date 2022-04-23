package com.example.popnwatch;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdminRecyclerViewAdapter extends RecyclerView.Adapter<AdminRecyclerViewAdapter.ViewHolder>  {
    //Movies

    //Snacks
    ArrayList<String> names = new ArrayList<>();
    ArrayList<byte[]> imgs = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> genre = new ArrayList<>();

    //Recipes

    Context context;
    LayoutInflater minflator;
    String currentSelect;

    public AdminRecyclerViewAdapter(ArrayList<String> names, ArrayList<byte[]> imgs, ArrayList<String> price, ArrayList<String> genre, Context context, String currentSelect) {
        this.names = names;
        this.imgs = imgs;
        this.price = price;
        this.genre = genre;
        this.context = context;
        this.currentSelect = currentSelect;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(currentSelect == "Movie" || currentSelect == "Snack"){
            View view = minflator.from(parent.getContext()).inflate(R.layout.admin_movie_snack_row, parent, false);

            return new ViewHolder(view);
        }else

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(currentSelect == "Snack"){
            Glide.with(context)
                    .asBitmap()
                    .load(imgs.get(position))
                    .into(holder.itemImg);
            holder.title.setText(names.get(position));
            holder.info.setText(price.get(position));
            holder.delete.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            } );

            holder.mainLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AddSnackActivity.class);
                    context.startActivity(i);
                }
            } );


        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Button delete;
        TextView title, info;
        CircleImageView itemImg;
        ConstraintLayout mainLayout;

        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            delete = itemView.findViewById(R.id.deleteButton );
            title = itemView.findViewById(R.id.titleTextView);
            info = itemView.findViewById(R.id.infoTextView);
            itemImg = itemView.findViewById(R.id.itemImg);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
