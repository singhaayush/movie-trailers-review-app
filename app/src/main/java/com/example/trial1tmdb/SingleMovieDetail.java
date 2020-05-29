package com.example.trial1tmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.trial1tmdb.myfragments.TrailerFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleMovieDetail extends AppCompatActivity {
  ImageView poster,backdrop;
  TextView overview,rating,movieTitle;
  LinearLayout linearLayout;
   ArrayList<TrailerEntity>trailersList;
  TrailerAdapter trailerAdapter;
  RecyclerView castRec,trailerRec;
  ArrayList<Cast>castsList;
  CastAdapter castAdapter;
  RequestQueue requestQueue;
    TrailerFragment trailerFragment;
    LinearLayoutManager linearLayoutManager1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_movie_detail);
        Fade fade=new Fade();
        castsList=new ArrayList<>();
        trailersList=new ArrayList<>();
        View decor=getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container),true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);
        requestQueue= Volley.newRequestQueue(this);
        poster=findViewById(R.id.poster_movie_detail);
        poster.bringToFront();
        backdrop=findViewById(R.id.backdrop_image);
        overview=findViewById(R.id.search_movie_overview_detail);
        rating=findViewById(R.id.search_movie_ratings_detail);
        movieTitle=findViewById(R.id.search_movie_title_detail);
        setUpUi(getIntent());
        castRec=findViewById(R.id.cast_rcv);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        castRec.setLayoutManager(linearLayoutManager);
        getAllCasts(getIntent().getStringExtra("movie_id"));
        getAllTrailers(getIntent().getStringExtra("movie_id"));
        trailerRec=findViewById(R.id.trailers_rcv);
        linearLayoutManager1=new LinearLayoutManager(this);
        trailerRec.setLayoutManager(linearLayoutManager1);



    }

    private void getAllCasts(String movie_id) {
      //  String url="https://api.themoviedb.org/3/movie/"+movie_id+"?api_key=6bab6920aae24c6f79377a7786622ab6&&append_to_response=videos,credits,images";
        String url="https://api.themoviedb.org/3/movie/"+movie_id+"/credits?api_key=6bab6920aae24c6f79377a7786622ab6";
        Log.d("url_generated",url);
        final String basepath = "https://image.tmdb.org/t/p/w300";
        final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("cast");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String name=jsonObject.getString("name");
                        int id=jsonObject.getInt("cast_id");
                        String role=jsonObject.getString("character");
                        String imagepath=basepath+jsonObject.getString("profile_path");
                        castsList.add(new Cast(id,name,role,imagepath));
                    }
                    castAdapter=new CastAdapter(castsList,SingleMovieDetail.this);
                    castRec.setAdapter(castAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TMDB_Errior",error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
      private void getAllTrailers(String movie_id)
      {
          String url="https://api.themoviedb.org/3/movie/"+movie_id+"/videos?api_key=6bab6920aae24c6f79377a7786622ab6";
          JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
              @Override
              public void onResponse(JSONObject response) {
                  try {
                      JSONArray jsonArray=response.getJSONArray("results");
                      for(int i=0;i<jsonArray.length();i++)
                      {
                          JSONObject jsonObject=jsonArray.getJSONObject(i);
                           String name=jsonObject.getString("name");
                           String key=jsonObject.getString("key");
                           String type=jsonObject.getString("type");
                           trailersList.add(new TrailerEntity(name,key,type));
                      }
                      trailerAdapter=new TrailerAdapter(trailersList,SingleMovieDetail.this);
                      trailerRec.setAdapter(trailerAdapter);
                      trailerAdapter.setOnItemClickListener(new TrailerAdapter.OnItemClickListener() {
                          @Override
                          public void onItemClick(int position) {
                             Intent intent=new Intent(SingleMovieDetail.this,VideoPlayActivity.class);
                              ActivityOptionsCompat option=ActivityOptionsCompat.makeSceneTransitionAnimation(SingleMovieDetail.this,linearLayoutManager1.findViewByPosition(position).findViewById(R.id.trailer_img),"video_transition");
                              intent.putExtra("key",trailersList.get(position).getKey());
                              startActivity(intent,option.toBundle());
                          }
                      });

                  } catch (JSONException e) {
                      e.printStackTrace();
                  }

              }
          }, new Response.ErrorListener() {
              @Override
              public void onErrorResponse(VolleyError error) {
                  Log.d("Samasya",error.getMessage());

              }
          });
          requestQueue.add(jsonObjectRequest);
      }
    private void setUpUi(Intent intent) {
        try {
            String url=intent.getStringExtra("backdrop_url").replace("w300","w780");
            Log.d("MyURl",url);
            Picasso.with(SingleMovieDetail.this).load(url).fit().into(backdrop);
            Picasso.with(SingleMovieDetail.this).load(intent.getStringExtra("poster_url")).fit().into(poster);
            overview.setText(intent.getStringExtra("overview_txt"));
            movieTitle.setText(intent.getStringExtra("movie_title"));
            rating.setText(intent.getStringExtra("rating"));

        }
        catch (Exception e)
        {
            Toast.makeText(SingleMovieDetail.this,e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
}
