package com.example.trial1tmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FilteredMoviesActivity extends AppCompatActivity {
    ArrayList<MovieEntity>filteredMovies;
    SearchResultMoviesAdapter searchResultMoviesAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    RequestQueue filterRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_movies);
        filteredMovies=new ArrayList<>();
        recyclerView=findViewById(R.id.filtered_movies_recyc);
        linearLayoutManager=new LinearLayoutManager(FilteredMoviesActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        filterRequest= Volley.newRequestQueue(this);
        parseSearchResultMoviesJson();

    }
    void parseSearchResultMoviesJson()
    {

        final String basepath = "https://image.tmdb.org/t/p/w300";
        final String url=getIntent().getStringExtra("url_formed");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    filteredMovies.clear();
                    JSONArray jsonArray = response.getJSONArray("results");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject item=jsonArray.getJSONObject(i);
                        int movieId=item.getInt("id");
                        String originalTitle=item.getString("original_title");
                        String movieOverview=item.getString("overview");
                        int rating;
                        rating = item.getInt("vote_average");
                        String posterImagePath=basepath+item.getString("poster_path");
                        JSONArray genres=item.getJSONArray("genre_ids");

                        ArrayList<Integer> generes=new ArrayList<>();
                        for(int j=0;j<genres.length();j++)
                        {
                            generes.add((Integer) genres.get(j));
                        }
                        String backdropImagePath=basepath+item.getString("backdrop_path");

                        filteredMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,generes,backdropImagePath));
                    }
                    if(searchResultMoviesAdapter!=null)
                        searchResultMoviesAdapter.notifyDataSetChanged();
                    else {
                        searchResultMoviesAdapter = new SearchResultMoviesAdapter(filteredMovies, FilteredMoviesActivity.this);
                        recyclerView.setAdapter(searchResultMoviesAdapter);
                        searchResultMoviesAdapter.setOnItemClickListener(new SearchResultMoviesAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent=new Intent(FilteredMoviesActivity.this,SingleMovieDetail.class);
                                ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(FilteredMoviesActivity.this,linearLayoutManager.findViewByPosition(position).findViewById(R.id.search_movie_poster),"my_transition");
                                intent.putExtra("backdrop_url",filteredMovies.get(position).getBackdropImagePath());
                                intent.putExtra("poster_url",filteredMovies.get(position).getPosterImagePath());
                                intent.putExtra("overview_txt",filteredMovies.get(position).getMovieOverview());
                                intent.putExtra("rating",Integer.toString(filteredMovies.get(position).getRating()));
                                intent.putExtra("movie_title",filteredMovies.get(position).getOriginalTitle());
                                intent.putExtra("movie_id",filteredMovies.get(position).getMovieId()+"");
                                startActivity(intent,option.toBundle());
                            }
                        });
                    }

                } catch (JSONException e) {
                    // Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("datafetched",e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        filterRequest.add(jsonObjectRequest);
    }
}
