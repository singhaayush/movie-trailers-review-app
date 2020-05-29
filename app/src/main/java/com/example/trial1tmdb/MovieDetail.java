package com.example.trial1tmdb;

import android.os.Bundle;

import com.example.trial1tmdb.myfragments.DescriptonFragment;
import com.example.trial1tmdb.myfragments.ThirdFragment;
import com.example.trial1tmdb.myfragments.TrailersSectionFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.ImageView;

public class MovieDetail extends AppCompatActivity {
    ViewPager pager;
    TabLayout tabLayout;
    DescriptonFragment descriptonFragment;
    TrailersSectionFragment trailersSectionFragment;
    ThirdFragment thirdFragment;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Just Trailers");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        pager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tab_layout);
        descriptonFragment=new DescriptonFragment();
        trailersSectionFragment=new TrailersSectionFragment();
        thirdFragment =new ThirdFragment();
         MyPagerAdapter myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager(),FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
         tabLayout.setupWithViewPager(pager);
         pager.setAdapter(myPagerAdapter);

    }
   class MyPagerAdapter extends FragmentPagerAdapter {
        String[]names={"Description","Trailers","Third"};
       public MyPagerAdapter(@NonNull FragmentManager fm, int behavior) {
           super(fm, behavior);
       }

       @NonNull
       @Override
       public Fragment getItem(int position) {
           switch (position)
           {
               case 0:
                   return descriptonFragment;
               case 1:
                   return trailersSectionFragment;
               case 2:
                   return thirdFragment;

           }
           return null;
       }

       @Nullable
       @Override
       public CharSequence getPageTitle(int position) {
           return names[position];
       }

       @Override
       public int getCount() {
           return names.length;
       }
   }
}
