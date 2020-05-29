package com.example.trial1tmdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchResultMoviesAdapter extends RecyclerView.Adapter<SearchResultMoviesAdapter.SearchResultMoviesViewHolder> {
    private ArrayList<MovieEntity>movieList;
    private Context mContext;
    private OnItemClickListener mListner;
    ImageView myImage;
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        mListner=listener;
    }

    public SearchResultMoviesAdapter(ArrayList<MovieEntity> movieList, Context mContext) {
        this.movieList = movieList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SearchResultMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view=inflater.inflate(R.layout.search_result_movies_layout,parent,false);
        return new SearchResultMoviesViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultMoviesViewHolder holder, int position) {
        Picasso.with(mContext).load(movieList.get(position).getPosterImagePath()).fit().into(holder.poster);
    holder.mRatings.setText(movieList.get(position).getRating()+"");
        holder.mOverview.setText(movieList.get(position).getMovieOverview());
        holder.mTitle.setText(movieList.get(position).getOriginalTitle());
        myImage=holder.poster;
    }
    public View getImageView()
    {
        return myImage;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class SearchResultMoviesViewHolder extends RecyclerView.ViewHolder
    {
         ImageView poster;
         TextView mTitle;
         TextView mRatings;
         TextView mOverview;
        public SearchResultMoviesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            poster=itemView.findViewById(R.id.search_movie_poster);
            mTitle=itemView.findViewById(R.id.search_movie_title);
            mRatings=itemView.findViewById(R.id.search_movie_ratings);
            mOverview=itemView.findViewById(R.id.search_movie_overview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  if(listener!=null)
                  {
                      int position =getAdapterPosition();
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
