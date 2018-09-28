package com.example.ansh.spacefeed.tabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ansh.spacefeed.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendingsFragment extends Fragment {

    public TrendingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TrendingsFragment.
     */
    public static TrendingsFragment newInstance() {
        TrendingsFragment fragment = new TrendingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trendings, container, false);
    }

}
