package com.example.ansh.spacefeed.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.ansh.spacefeed.tabFragments.CollectionsFragment;
import com.example.ansh.spacefeed.tabFragments.FavouritesFragment;
import com.example.ansh.spacefeed.tabFragments.PhotosFragment;
import com.example.ansh.spacefeed.tabFragments.TrendingsFragment;
import com.example.ansh.spacefeed.R;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;
    private Toolbar mToolbar;

    final Fragment fragment1 = PhotosFragment.newInstance();
    final Fragment fragment2 = TrendingsFragment.newInstance();
    final Fragment fragment3 = CollectionsFragment.newInstance();
    final Fragment fragment4 = FavouritesFragment.newInstance();
    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadFragment(PhotosFragment.newInstance());

        mNavigationView = findViewById(R.id.main_navigation);
        mNavigationView.setOnNavigationItemSelectedListener(mBottomNavigationListener);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
//        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mBottomNavigationListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment fragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_photos:
//                    fm.beginTransaction().hide(active).show(fragment1).commit();
//                    active = fragment1;
                    loadFragment(PhotosFragment.newInstance());
                    return true;

                case R.id.navigation_trendings:
//                    fm.beginTransaction().hide(active).show(fragment2).commit();
//                    active = fragment2;
                    loadFragment(TrendingsFragment.newInstance());
                    return true;

                case R.id.navigation_collections:
//                    fm.beginTransaction().hide(active).show(fragment3).commit();
//                    active = fragment3;
                    loadFragment(CollectionsFragment.newInstance());
                    return true;

                case R.id.navigation_likes:
//                    fm.beginTransaction().hide(active).show(fragment3).commit();
//                    active = fragment3;
                    loadFragment(FavouritesFragment.newInstance());
                    return true;

            }
            return false;
        }
    };

}
