package com.example.trial1tmdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {
    private ArrayList<Cast>castArrayList;
    private Context mContext;

    public CastAdapter(ArrayList<Cast> castArrayList, Context mContext) {
        this.castArrayList = castArrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.cast_layout,parent,false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Picasso.with(mContext).load(castArrayList.get(position).getCastImageUrl()).fit().into(holder.castimg);
        holder.castname.setText(castArrayList.get(position).getCastName());
        holder.castrole.setText(castArrayList.get(position).getCharacterRole());

    }

    @Override
    public int getItemCount() {
        return castArrayList.size();
    }

    class CastViewHolder extends RecyclerView.ViewHolder
    {
        ImageView castimg;
        TextView castname;
        TextView castrole;
        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            castimg=itemView.findViewById(R.id.cast_image);
            castname=itemView.findViewById(R.id.cast_name);
            castrole=itemView.findViewById(R.id.cast_role);
        }
    }
}
