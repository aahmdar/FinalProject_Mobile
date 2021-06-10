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
import com.aahmdar.finalmobile.data.local.FavoriteTv;

import java.util.List;

public class FavoriteTvAdapter extends RecyclerView.Adapter<com.aahmdar.finalmobile.ui.adapters.FavoriteTvAdapter.ViewHolder> {
    private List<FavoriteTv> tvList;
    private OnItemClickListener clickListener;

    public FavoriteTvAdapter(List<FavoriteTv> tvList) {
        this.tvList = tvList;
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
        viewHolder.onBindItemView(tvList.get(i));
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        FavoriteTv tv;
        ImageView ivPoster;
        TextView tvName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            itemView.setOnClickListener(this);
        }


        void onBindItemView(FavoriteTv tv) {
            this.tv = tv;
            String imageUri = tv.getPosterPath(ImageSize.W154);
            String title = tv.getTitle();
            Glide.with(itemView.getContext())
                    .load(imageUri)
                    .into(ivPoster);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(tv);
        }
    }
}
