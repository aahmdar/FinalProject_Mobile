package com.aahmdar.finalmobile.ui.adapters;

import android.content.Context;
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
import com.aahmdar.finalmobile.data.models.Cast;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<com.aahmdar.finalmobile.ui.adapters.CastAdapter.CastViewHolder> {

    private List<Cast> casts;
    private Context mContext;

    public CastAdapter(List<Cast> casts, Context context){
        this.casts = casts;
        this.mContext = context;
        System.out.println(casts.size());
    }
    @NonNull
    @Override
    public com.aahmdar.finalmobile.ui.adapters.CastAdapter.CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_cast_recycler, parent, false);
        return new com.aahmdar.finalmobile.ui.adapters.CastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull com.aahmdar.finalmobile.ui.adapters.CastAdapter.CastViewHolder holder, int position) {
        holder.tvCastName.setText(casts.get(position).getName());
        holder.tvCastCharacter.setText(casts.get(position).getCharacter());
        String imageUri = casts.get(position).getProfilePath(ImageSize.W342);
        Glide.with(holder.itemView.getContext()).load(imageUri).into(holder.ivCastPhoto);
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCastPhoto;
        TextView tvCastName;
        TextView tvCastCharacter;
        public CastViewHolder(View itemView) {
            super(itemView);
            ivCastPhoto = itemView.findViewById(R.id.iv_cast);
            tvCastName = itemView.findViewById(R.id.tv_cast_name);
            tvCastCharacter = itemView.findViewById(R.id.tv_cast_character);
        }
    }
}
