package com.example.trial1tmdb.myfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trial1tmdb.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrailersSectionFragment extends Fragment {

    public TrailersSectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trailers_section, container, false);
    }
}
