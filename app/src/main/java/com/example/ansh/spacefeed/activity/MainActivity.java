package com.example.ansh.spacefeed.activity;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemReselectedListener;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
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
import com.ncapdevi.fragnav.FragNavController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * I WILL CHANGE THIS CLASS LATER
 * AND REFACTOR THE NAVIGATION LISTENER
 * and to implement "BACKSTACK"
 *
 * --------
 * OR Probably I'll shift to TabLayout and implement the code
 * which I starred on Github.
 */
public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private FragNavController.Builder mBuilder;
    private FragNavController mFragNavController;

    private BottomNavigationView mBottomNavigationView;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBottomNavigationView = findViewById(R.id.main_navigation);

        mBuilder = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_frame_layout);
        initializeFragmentList();
        mFragNavController = mBuilder.build();

        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomNavItemSelected);
        mBottomNavigationView.setOnNavigationItemReselectedListener(bottomNavItemReselected);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavItemSelected =
            new OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_photos:
                            mFragNavController.switchTab(FragNavController.TAB1);
                            return true;
                        case R.id.navigation_trendings:
                            mFragNavController.switchTab(FragNavController.TAB2);
                            return true;
                        case R.id.navigation_collections:
                            mFragNavController.switchTab(FragNavController.TAB3);
                            return true;
                        case R.id.navigation_likes:
                            mFragNavController.switchTab(FragNavController.TAB4);
                            return true;
                        default:
                            return false;
                    }
                }
            };

    private BottomNavigationView.OnNavigationItemReselectedListener bottomNavItemReselected =
            new OnNavigationItemReselectedListener() {
                @Override
                public void onNavigationItemReselected(@NonNull MenuItem menuItem) {
                    if(!mFragNavController.isRootFragment()) {
                        mFragNavController.clearStack();
                    }
                }
            };

    private void initializeFragmentList() {
        mFragmentList.add(PhotosFragment.newInstance());
        mFragmentList.add(TrendingsFragment.newInstance());
        mFragmentList.add(CollectionsFragment.newInstance());
        mFragmentList.add(FavouritesFragment.newInstance());

        mBuilder.rootFragments(mFragmentList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        if(mFragNavController != null) {
            mFragNavController.onSaveInstanceState(outState);
        }
    }

    public void pushFragment(Fragment fragment) {
        mFragNavController.pushFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        if(mFragNavController.isRootFragment()) {
            super.onBackPressed();
        } else {
            mFragNavController.popFragment();
        }
    }

    @Override
    public Fragment getRootFragment(int i) {
        return null;
    }
}
