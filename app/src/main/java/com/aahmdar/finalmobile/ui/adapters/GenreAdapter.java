package com.aahmdar.finalmobile.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aahmdar.finalmobile.R;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<com.aahmdar.finalmobile.ui.adapters.GenreAdapter.ViewHolder>
{
    private ArrayList<String> genres;
    private Context mContext;

    public GenreAdapter(ArrayList<String> genres, Context context){
        this.genres = genres;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public com.aahmdar.finalmobile.ui.adapters.GenreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_genre_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.aahmdar.finalmobile.ui.adapters.GenreAdapter.ViewHolder holder, int position) {
        final String genre = genres.get(position);

        holder.setGenre(genre);
    }

    @Override
    public int getItemCount() {
        return genres == null ? 0 : genres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvGenre;

        public ViewHolder(View itemView){
            super(itemView);

            tvGenre = itemView.findViewById(R.id.tv_genre_rv);
        }

        public void setGenre(String genre){
            tvGenre.setText(genre);
        }
    }
}