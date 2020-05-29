package com.example.trial1tmdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class SearchMovieActivity extends AppCompatActivity {
   Spinner genre,rating,year,page;
   ArrayList<MovieEntity>resultMovies;
   //ArrayAdapter genreAdapter,ratingAdapter,yearAdapter,pageAdapter;
   ArrayList<Integer>genereIds;
   ArrayList<String>generes;
    RequestQueue searchRequest;
    ArrayList<Integer>ratings;
    ArrayList<Integer>years;
    ArrayList<Integer>pages;
    RecyclerView searchRecycler;
    SearchResultMoviesAdapter searchResultMoviesAdapter;
    EditText queryInput;
    Button button;
    LinearLayoutManager linearLayoutManager;
    MovieEntity deleted,favourite;
    LottieAnimationView lot_img;
    @Override
    protected void onStart() {
        super.onStart();

    }
     ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        Fade fade=new Fade();
        View decor=getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);

        scrollView=findViewById(R.id.scrollView);
        lot_img=findViewById(R.id.search_lottie_image);
        resultMovies=new ArrayList<>();
        ratings=new ArrayList<>();
        years=new ArrayList<>();
        genereIds=new ArrayList<>();
        pages=new ArrayList<>();




        //registerSpinnerLisners();

        searchRequest=Volley.newRequestQueue(this);
        searchRecycler=findViewById(R.id.search_result_rcv_);
        linearLayoutManager =new LinearLayoutManager(this);
        searchRecycler.setLayoutManager(linearLayoutManager);
        queryInput=findViewById(R.id.movie_query);
         button=findViewById(R.id.search_button);
         button.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 parseSearchResultMoviesJson(queryInput.getText().toString());
                 lot_img.setVisibility(View.GONE);
                 scrollView.scrollTo(0,scrollView.getBottom()*2);
                 queryInput.setActivated(false);

             }
         });

        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(searchRecycler);

    }




   void parseSearchResultMoviesJson(String query)
    {
        if(query.length()<1)
            return;
        final String basepath = "https://image.tmdb.org/t/p/w300";
        final String url="https://api.themoviedb.org/3/search/movie?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&query="+query+"&page=1&include_adult=false";
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                       resultMovies.clear();
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
                        Log.d("Ayush",url);
                        resultMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,generes,backdropImagePath));
                    }
                    if(searchResultMoviesAdapter!=null)
                    searchResultMoviesAdapter.notifyDataSetChanged();
                    else {
                        searchResultMoviesAdapter = new SearchResultMoviesAdapter(resultMovies, SearchMovieActivity.this);
                        searchRecycler.setAdapter(searchResultMoviesAdapter);
                        searchResultMoviesAdapter.setOnItemClickListener(new SearchResultMoviesAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent=new Intent(SearchMovieActivity.this,SingleMovieDetail.class);
                                ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(SearchMovieActivity.this,linearLayoutManager.findViewByPosition(position).findViewById(R.id.search_movie_poster),"my_transition");
                                intent.putExtra("backdrop_url",resultMovies.get(position).getBackdropImagePath());
                                intent.putExtra("poster_url",resultMovies.get(position).getPosterImagePath());
                                intent.putExtra("overview_txt",resultMovies.get(position).getMovieOverview());
                                intent.putExtra("rating",Integer.toString(resultMovies.get(position).getRating()));
                                intent.putExtra("movie_title",resultMovies.get(position).getOriginalTitle());
                                intent.putExtra("movie_id",resultMovies.get(position).getMovieId()+"");
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
        searchRequest.add(jsonObjectRequest);
    }
    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int position=viewHolder.getAdapterPosition();
           switch (direction)
           {
               case ItemTouchHelper.LEFT : deleted=resultMovies.get(position);
               resultMovies.remove(position);
               searchResultMoviesAdapter.notifyItemRemoved(position);
                   Snackbar.make(searchRecycler,deleted.getOriginalTitle()+" marked as already Seen",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           resultMovies.add(position,deleted);
                           searchResultMoviesAdapter.notifyItemInserted(position);
                       }
                   }).show();
                   break;
               case ItemTouchHelper.RIGHT:
                   searchResultMoviesAdapter.notifyDataSetChanged();
                   Snackbar.make(searchRecycler,resultMovies.get(position).getOriginalTitle()+" marked as favourite",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           searchResultMoviesAdapter.notifyDataSetChanged();
                          Toast.makeText(SearchMovieActivity.this,resultMovies.get(position).getOriginalTitle()+" removed from favourite list",Toast.LENGTH_LONG).show();
                       }
                   }).show();
                   break;

           }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(R.color.colorAccent)
                    .addSwipeLeftActionIcon(R.drawable.ic_remove_red_eye_black_24dp)
                    .addSwipeRightBackgroundColor(R.color.colorPrimary)
                    .addSwipeRightActionIcon(R.drawable.ic_favorite_black_24dp)
                    .create()
                    .decorate();

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };
}
