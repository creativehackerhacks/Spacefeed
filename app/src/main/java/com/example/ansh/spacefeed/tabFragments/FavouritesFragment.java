package com.example.ansh.spacefeed.tabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ansh.spacefeed.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavouritesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavouritesFragment extends Fragment {

    private LottieAnimationView mLottieAnimationView;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public static FavouritesFragment newInstance() {
        FavouritesFragment fragment = new FavouritesFragment();
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
        View view = inflater.inflate(R.layout.fragment_favourites, container, false);
        setHasOptionsMenu(true);

        mLottieAnimationView = view.findViewById(R.id.f_f_lottie_empty_list);

        mLottieAnimationView.setAnimation("empty_list.json");
        mLottieAnimationView.playAnimation();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mLottieAnimationView.pauseAnimation();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        getActivity().invalidateOptionsMenu();
        menu.clear();
    }
}
