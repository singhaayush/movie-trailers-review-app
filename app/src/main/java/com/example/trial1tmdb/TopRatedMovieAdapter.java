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

public class TopRatedMovieAdapter extends RecyclerView.Adapter<TopRatedMovieAdapter.TopRatedMovieViewHolder> {
    private Context mContext;
    private ArrayList<MovieEntity> movieList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener
    {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListener=listener;
    }
    public TopRatedMovieAdapter(Context mContext, ArrayList<MovieEntity> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public TopRatedMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.horizontal_movie_list_layout,parent,false);
        return new TopRatedMovieViewHolder(view,mListener);

    }

    @Override
    public void onBindViewHolder(@NonNull TopRatedMovieViewHolder holder, int position) {
        holder.movieTitle.setText(movieList.get(position).getOriginalTitle());
        holder.rating.setText(movieList.get(position).getRating()+"");
        Picasso.with(mContext).load(movieList.get(position).getPosterImagePath()).fit().placeholder(R.color.boxes_interior_color).into(holder.posterImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class TopRatedMovieViewHolder extends RecyclerView.ViewHolder
    {
        ImageView posterImage;
        TextView rating;
        TextView movieTitle;
        public TopRatedMovieViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            posterImage=itemView.findViewById(R.id.movie_poster_image);
            rating=itemView.findViewById(R.id.rating_number);
            movieTitle=itemView.findViewById(R.id.original_move_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    {
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION)
                        {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}