package com.aahmdar.finalmobile.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import com.aahmdar.finalmobile.R;
import com.aahmdar.finalmobile.ui.adapters.clicklistener.OnFavoriteClickListener;
import com.aahmdar.finalmobile.data.models.Favorite;
//import com.aahmdar.finalmobile.data.models.Favorite;
//import com.aahmdar.finalmobile.ui.adapters.clicklistener.OnFavoriteClickListener;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private List<Favorite> favoriteList;
    private OnFavoriteClickListener clickListener;

    public FavoriteAdapter(List<Favorite> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public void setClickListener(OnFavoriteClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBindItem(favoriteList.get(i));
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Favorite favorite;
        ImageView ivPoster;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvName = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(this);
        }

        void onBindItem(Favorite favorite) {
            this.favorite = favorite;
            Glide.with(itemView.getContext())
                    .load(favorite.getPosterPath())
                    .into(ivPoster);
            tvName.setText(favorite.getName());
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(favorite);
        }
    }
}
