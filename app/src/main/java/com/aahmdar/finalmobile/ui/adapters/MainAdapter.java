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

import com.aahmdar.finalmobile.ImageSize;
import com.aahmdar.finalmobile.R;
import com.aahmdar.finalmobile.ui.adapters.clicklistener.OnItemClickListener;
import com.aahmdar.finalmobile.data.models.Movie;
import com.aahmdar.finalmobile.data.models.TvShow;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private List<TvShow> tvShowList;
    private List<Movie> movieList;
    private OnItemClickListener clickListener;

    public MainAdapter(List<TvShow> tvShowList, List<Movie> movieList) {
        this.tvShowList = tvShowList;
        this.movieList = movieList;
    }

    public void setClickListener(OnItemClickListener clickListener) {
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
        if (tvShowList != null) {
            viewHolder.onBindItemView(tvShowList.get(i));
        } else {
            viewHolder.onBindItemView(movieList.get(i));
        }

    }

    public void appendList(List<TvShow> tvShowListToAppend, List<Movie> movieListToAppend) {
        if (tvShowListToAppend != null) {
            tvShowList.addAll(tvShowListToAppend);
        } else {
            movieList.addAll(movieListToAppend);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tvShowList != null ? tvShowList.size() : movieList.size() ;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TvShow tvShow;
        Movie movie;
        ImageView ivPoster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }

        void onBindItemView(TvShow tvShow) {
            this.tvShow = tvShow;
            String imageUri = tvShow.getPosterPath(ImageSize.W154);
            String title = tvShow.getName();
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .into(ivPoster);
        }

        void onBindItemView(Movie movie) {
            this.movie = movie;
            String imageUri = movie.getPosterPath(ImageSize.W154);
            String title = movie.getTitle();
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .into(ivPoster);
        }

        @Override
        public void onClick(View view) {
            if (tvShow != null){
                clickListener.onClick(tvShow);
            } else {
                clickListener.onClick(movie);
            }

        }
    }
}
