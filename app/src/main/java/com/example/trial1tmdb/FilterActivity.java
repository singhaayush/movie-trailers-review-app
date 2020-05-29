package com.example.trial1tmdb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.Date;

public class FilterActivity extends AppCompatActivity {
     Spinner s1,s2,s3;
     Button b1;
     ArrayList<String>ratings,years,genre;
     ArrayList<Integer>genreIds;
     ArrayAdapter<String>ratingAdapter,yearAdapter,genreAdapter;
     String queryRating,queryGenres,queryYear;
     String base_url="https://api.themoviedb.org/3/discover/movie?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        s1=findViewById(R.id.genre_dropdown);
        s2=findViewById(R.id.ratings_dropdown);
        s3=findViewById(R.id.year_dropdown);
        b1=findViewById(R.id.filter_btn);
        ratings=new ArrayList<>();
        years=new ArrayList<>();
        genre=getAllGenres();
        genreIds=new ArrayList<>();
        setUpSpinnersData();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FilterActivity.this,FilteredMoviesActivity.class);
                intent.putExtra("url_formed",base_url);
                startActivity(intent);
            }
        });
       registerSpinnerLisners();
    }

    private void setUpSpinnersData() {
        for(int i=9;i>=1;i--)
        {
            ratings.add(i+"+");
        }
        ratingAdapter=new ArrayAdapter<>(FilterActivity.this,android.R.layout.simple_spinner_dropdown_item,ratings);
        s2.setAdapter(ratingAdapter);
        for(int i=2020;i>1980;i--)
        {
            years.add(i+"");
        }
        yearAdapter=new ArrayAdapter<>(FilterActivity.this,android.R.layout.simple_spinner_dropdown_item,years);
        s3.setAdapter(yearAdapter);
        genreAdapter=new ArrayAdapter<>(FilterActivity.this,android.R.layout.simple_spinner_dropdown_item,genre);
        s1.setAdapter(genreAdapter);

    }
   ArrayList<String> getAllGenres()
    {
        String url="https://api.themoviedb.org/3/genre/movie/list?api_key=6bab6920aae24c6f79377a7786622ab6&language=en-US";
        final ArrayList<String>res=new ArrayList<>();
        RequestQueue requestQueue= Volley.newRequestQueue(FilterActivity.this);
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray=response.getJSONArray("genres");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        res.add(jsonObject.getString("name"));
                        genreIds.add(jsonObject.getInt("id"));
                        Log.d("ayush",jsonObject.getString("name"));
                    }
                    genreAdapter=new ArrayAdapter(FilterActivity.this,android.R.layout.simple_spinner_dropdown_item,res);
                    s1.setAdapter(genreAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
        return res;
    }
    private void registerSpinnerLisners() {
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               base_url= base_url.concat("&with_genres="+genreIds.get(position));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              base_url=base_url.concat("&vote_average.gte="+ratings.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                base_url=base_url.concat("&release_date.gte="+years.get(position));
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
