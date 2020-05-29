package com.example.trial1tmdb;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.AppBarLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {
    AppBarLayout appBarLayout;
    LottieAnimationView lottieAnimationView;
    UpcomingMovieAdapter upcomingMovieAdapter;
    NowPlayingMovieAdapter nowPlayingMovieAdapter;
    PopularMovieAdapter popularMovieAdapter;
    TopRatedMovieAdapter topRatedMovieAdapter;
    ArrayList<MovieEntity> upcomingMovies;
    ArrayList<MovieEntity>nowPlayingMovies;
    ArrayList<MovieEntity>popularMovies;
    ArrayList<MovieEntity>topRatedMovies;
    RecyclerView upMoviesRec;
    RecyclerView npMoviesRec;
    RecyclerView popMoviesRec;
    RecyclerView topMoviesRec;
    RequestQueue upcomingMovieRequest;
    RequestQueue nowPlayingMovieRequest;
    RequestQueue popularMovieRequest;
    RequestQueue topRatedMovieRequest;
    RelativeLayout wrapper;
    Toolbar toolbar;
    LinearLayout drawer;
    Animation fromTop,fromBottom;
    ImageView imageView;
    Button searchNav,aboutUsNav,favNav,filterNav;
    LinearLayoutManager linearLayoutManager1,linearLayoutManager2,linearLayoutManager3,linearLayoutManager4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchNav=findViewById(R.id.search_nav_btn);
        aboutUsNav=findViewById(R.id.aboutus_nav_btn);
        favNav=findViewById(R.id.fav_nav_btn);
        filterNav=findViewById(R.id.filter_search_nav_btn);
        setNavigationListners();
        imageView=findViewById(R.id.userpicbig);
        fromBottom= AnimationUtils.loadAnimation(this,R.anim.from_bottom);
        fromTop=AnimationUtils.loadAnimation(this,R.anim.from_top);
         appBarLayout=findViewById(R.id.app_bar_layout);
         drawer=findViewById(R.id.my_drawer);
         drawer.bringToFront();
        lottieAnimationView=findViewById(R.id.lottie_image);
        wrapper=findViewById(R.id.wrapper_lottie_image);
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              drawer.animate().translationX(0);
              appBarLayout.animate().translationX(800);
              imageView.startAnimation(fromBottom);

              searchNav.startAnimation(fromTop);
              aboutUsNav.startAnimation(fromTop);
              favNav.startAnimation(fromTop);
              filterNav.startAnimation(fromTop);
            }
        });
        drawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.animate().translationX(-800);
                appBarLayout.animate().translationX(0);
            }
        });
        upcomingMovieRequest= Volley.newRequestQueue(this);
        nowPlayingMovieRequest=Volley.newRequestQueue(this);
        popularMovieRequest=Volley.newRequestQueue(this);
        topRatedMovieRequest=Volley.newRequestQueue(this);
        upcomingMovies = new ArrayList<>();
        nowPlayingMovies=new ArrayList<>();
        popularMovies =new ArrayList<>();
        topRatedMovies=new ArrayList<>();
        upMoviesRec = findViewById(R.id.upcoming_rcv);
        npMoviesRec=findViewById(R.id.nowplaying_rcv);
        popMoviesRec=findViewById(R.id.popular_rcv);
        topMoviesRec=findViewById(R.id.top_rated_rcv);
         linearLayoutManager1 = new LinearLayoutManager(this);
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        upMoviesRec.setHasFixedSize(true);
        upMoviesRec.setLayoutManager(linearLayoutManager1);
         linearLayoutManager2 = new LinearLayoutManager(this);
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        npMoviesRec.setHasFixedSize(true);
        npMoviesRec.setLayoutManager(linearLayoutManager2);
         linearLayoutManager3 = new LinearLayoutManager(this);
        linearLayoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        popMoviesRec.setHasFixedSize(true);
        popMoviesRec.setLayoutManager(linearLayoutManager3);
       linearLayoutManager4 = new LinearLayoutManager(this);
        linearLayoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        topMoviesRec.setHasFixedSize(true);
        topMoviesRec.setLayoutManager(linearLayoutManager4);

        parseUpcominJson();
        parseNowPlayingJson();
        parsePopularJson();
        parseTopRatedJson();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 lottieAnimationView.setVisibility(View.GONE);
                 wrapper.setVisibility(View.GONE);
            }
        },1500);


    }

    private void setNavigationListners() {
        searchNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchNav.setTextColor(getResources().getColor(R.color.white));
                Intent i1=new Intent(MainActivity.this,SearchMovieActivity.class);
                startActivity(i1);
            }
        });
        aboutUsNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutUsNav.setTextColor(getResources().getColor(R.color.white));
            }
        });
        filterNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterNav.setTextColor(getResources().getColor(R.color.white));
                Intent intent=new Intent(MainActivity.this,FilterActivity.class);
                startActivity(intent);
            }
        });
        favNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favNav.setTextColor(getResources().getColor(R.color.white));
                Toast.makeText(MainActivity.this,"Hang tight feature will be added soon",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void parseUpcominJson() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        upcomingMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,generes,backdropImagePath));
                    }
                    Collections.shuffle(upcomingMovies);
                    upcomingMovieAdapter=new UpcomingMovieAdapter(MainActivity.this,upcomingMovies);
                    upMoviesRec.setAdapter(upcomingMovieAdapter);
                    upcomingMovieAdapter.setOnItemClickListener(new UpcomingMovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent=new Intent(MainActivity.this,SingleMovieDetail.class);
                            ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,linearLayoutManager1.findViewByPosition(position).findViewById(R.id.movie_poster_image),"my_transition");
                            intent.putExtra("backdrop_url",upcomingMovies.get(position).getBackdropImagePath());
                            intent.putExtra("poster_url",upcomingMovies.get(position).getPosterImagePath());
                            intent.putExtra("overview_txt",upcomingMovies.get(position).getMovieOverview());
                            intent.putExtra("rating",Integer.toString(upcomingMovies.get(position).getRating()));
                            intent.putExtra("movie_title",upcomingMovies.get(position).getOriginalTitle());
                            intent.putExtra("movie_id",upcomingMovies.get(position).getMovieId()+"");
                            startActivity(intent,option.toBundle());
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                   // Log.d("datafetched",e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volleyeror", error.getMessage());
            }
        });
        upcomingMovieRequest.add(jsonObjectRequest);
    }
    private void parseNowPlayingJson() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        nowPlayingMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,generes,backdropImagePath));
                    }
                    Collections.shuffle(nowPlayingMovies);
                    nowPlayingMovieAdapter=new NowPlayingMovieAdapter(MainActivity.this,nowPlayingMovies);
                    npMoviesRec.setAdapter(nowPlayingMovieAdapter);
                    nowPlayingMovieAdapter.setOnItemClickListener(new NowPlayingMovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent=new Intent(MainActivity.this,SingleMovieDetail.class);
                            ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,linearLayoutManager2.findViewByPosition(position).findViewById(R.id.movie_poster_image),"my_transition");
                            intent.putExtra("backdrop_url",nowPlayingMovies.get(position).getBackdropImagePath());
                            intent.putExtra("poster_url",nowPlayingMovies.get(position).getPosterImagePath());
                            intent.putExtra("overview_txt",nowPlayingMovies.get(position).getMovieOverview());
                            intent.putExtra("rating",Integer.toString(nowPlayingMovies.get(position).getRating()));
                            intent.putExtra("movie_title",nowPlayingMovies.get(position).getOriginalTitle());
                            intent.putExtra("movie_id",nowPlayingMovies.get(position).getMovieId()+"");
                            startActivity(intent,option.toBundle());
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("datafetched",e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volleyeror", error.getMessage());
            }
        });
        nowPlayingMovieRequest.add(jsonObjectRequest);
    }
    private void parsePopularJson() {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                        //Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        popularMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,generes,backdropImagePath));
                    }


                    popularMovieAdapter=new PopularMovieAdapter(MainActivity.this,popularMovies);
                    popMoviesRec.setAdapter(popularMovieAdapter);
                    popularMovieAdapter.setOnItemClickListener(new PopularMovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {


                            Intent intent=new Intent(MainActivity.this,SingleMovieDetail.class);
                            ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,linearLayoutManager3.findViewByPosition(position).findViewById(R.id.movie_poster_image),"my_transition");
                            intent.putExtra("backdrop_url",popularMovies.get(position).getBackdropImagePath());
                            intent.putExtra("poster_url",popularMovies.get(position).getPosterImagePath());
                            intent.putExtra("overview_txt",popularMovies.get(position).getMovieOverview());
                            intent.putExtra("rating",Integer.toString(popularMovies.get(position).getRating()));
                            intent.putExtra("movie_title",popularMovies.get(position).getOriginalTitle());
                            intent.putExtra("movie_id",popularMovies.get(position).getMovieId()+"");
                            startActivity(intent,option.toBundle());
                        }
                    });
                } catch (JSONException e) {
                    //Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("datafetched",e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.d("volleyeror", error.getMessage());
            }
        });
        nowPlayingMovieRequest.add(jsonObjectRequest);
    }
    private void parseTopRatedJson()
    {
        final String basepath = "https://image.tmdb.org/t/p/w300";
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&page=1";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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
                        Log.d("datafetched",movieOverview+originalTitle+posterImagePath);
                        topRatedMovies.add(new MovieEntity(movieId,originalTitle,movieOverview,rating,posterImagePath,generes,backdropImagePath));
                    }
                   // upcomingMovieAdapter=new UpcomingMovieAdapter(MainActivity.this,upcomingMovies);
                    topRatedMovieAdapter=new TopRatedMovieAdapter(MainActivity.this,topRatedMovies);
                    topMoviesRec.setAdapter(topRatedMovieAdapter);
                    topRatedMovieAdapter.setOnItemClickListener(new TopRatedMovieAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            Intent intent=new Intent(MainActivity.this,SingleMovieDetail.class);
                            ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,linearLayoutManager4.findViewByPosition(position).findViewById(R.id.movie_poster_image),"my_transition");
                            intent.putExtra("backdrop_url",topRatedMovies.get(position).getBackdropImagePath());
                            intent.putExtra("poster_url",topRatedMovies.get(position).getPosterImagePath());
                            intent.putExtra("overview_txt",topRatedMovies.get(position).getMovieOverview());
                            intent.putExtra("rating",Integer.toString(topRatedMovies.get(position).getRating()));
                            intent.putExtra("movie_title",topRatedMovies.get(position).getOriginalTitle());
                            intent.putExtra("movie_id",topRatedMovies.get(position).getMovieId()+"");
                            startActivity(intent,option.toBundle());
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    Log.d("datafetched",e.getMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("volleyeror", error.getMessage());
            }
        });
        topRatedMovieRequest.add(jsonObjectRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        drawer.animate().translationX(-800);
        appBarLayout.animate().translationX(0);
    }
}
