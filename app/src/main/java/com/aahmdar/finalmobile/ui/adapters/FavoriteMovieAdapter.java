package com.aahmdar.finalmobile.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.aahmdar.finalmobile.ImageSize;
import com.aahmdar.finalmobile.R;
import com.aahmdar.finalmobile.ui.adapters.clicklistener.OnItemClickListener;
import com.aahmdar.finalmobile.data.local.FavoriteMovie;

import java.util.List;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<com.aahmdar.finalmobile.ui.adapters.FavoriteMovieAdapter.ViewHolder> {
    private List<FavoriteMovie> movieList;
    private OnItemClickListener clickListener;

    public FavoriteMovieAdapter(List<FavoriteMovie> movieList) {
        this.movieList = movieList;
    }

    public void setClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_favorite, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBindItemView(movieList.get(i));
    }

//    public void appendList(List<TvShow> tvShowListToAppend, List<Movie> movieListToAppend) {
//        if (tvShowListToAppend != null) {
//            tvShowList.addAll(tvShowListToAppend);
//        } else {
//            movieList.addAll(movieListToAppend);
//        }
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FavoriteMovie movie;
        ImageView ivPoster;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvName = itemView.findViewById(R.id.tv_title);
            itemView.setOnClickListener(this);
        }


        void onBindItemView(FavoriteMovie movie) {
            this.movie = movie;
            String imageUri = movie.getPosterPath(ImageSize.W154);
            String title = movie.getTitle();
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .into(ivPoster);
            tvName.setText(title);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(movie);
        }
    }
}
