package com.example.ansh.spacefeed.activity;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemReselectedListener;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ansh.spacefeed.tabFragments.CollectionsFragment;
import com.example.ansh.spacefeed.tabFragments.FavouritesFragment;
import com.example.ansh.spacefeed.tabFragments.PhotosFragment;
import com.example.ansh.spacefeed.tabFragments.TrendingsFragment;
import com.example.ansh.spacefeed.R;
import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavController.TransactionType;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;

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
public class MainActivity extends AppCompatActivity implements FragNavController.TransactionListener, FragNavController.RootFragmentListener {

    private static final int INDEX_PHOTOS = 1;
    private static final int INDEX_TRENDINGS = 2;
    private static final int INDEX_COLLECTIONS = 3;
    private static final int INDEX_FAVOURITES = 4;

    private String mPhotoSortOrder;


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
        mPhotoSortOrder = "latest";

        mBuilder = FragNavController
                .newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_frame_layout)
                .transactionListener(this)
                .rootFragmentListener(this, 4);
//                .switchController(mFragNavSwitchController);

        initializeFragmentList();

        mFragNavController = mBuilder
                .defaultTransactionOptions(FragNavTransactionOptions.newBuilder()
                        .transition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .build())
                .build();


        mBottomNavigationView.setOnNavigationItemSelectedListener(bottomNavItemSelected);
        mBottomNavigationView.setOnNavigationItemReselectedListener(bottomNavItemReselected);


    }

    private FragNavSwitchController mFragNavSwitchController = new FragNavSwitchController() {
        @Override
        public void switchTab(int i, FragNavTransactionOptions fragNavTransactionOptions) {
            mBottomNavigationView.setSelectedItemId(i);
        }
    };

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavItemSelected =
            new OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_photos:
                            mFragNavController.switchTab(FragNavController.TAB1, FragNavTransactionOptions.newBuilder().transition(FragmentTransaction.TRANSIT_NONE).build());
                            return true;
                        case R.id.navigation_trendings:
                            mFragNavController.switchTab(FragNavController.TAB2, FragNavTransactionOptions.newBuilder().transition(FragmentTransaction.TRANSIT_NONE).build());
                            return true;
                        case R.id.navigation_collections:
                            mFragNavController.switchTab(FragNavController.TAB3, FragNavTransactionOptions.newBuilder().transition(FragmentTransaction.TRANSIT_NONE).build());
                            return true;
                        case R.id.navigation_likes:
                            mFragNavController.switchTab(FragNavController.TAB4, FragNavTransactionOptions.newBuilder().transition(FragmentTransaction.TRANSIT_NONE).build());
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
                    if (!mFragNavController.isRootFragment()) {
                        mFragNavController.clearStack();
                    } else {
                        Fragment f = mFragNavController.getCurrentFrag();
                        if (f != null) {
                            View fragmentView = f.getView();
                            RecyclerView mRecyclerView = fragmentView.findViewById(R.id.f_p_recyclerView);//mine one is RecyclerView
                            if (mRecyclerView != null)
                                mRecyclerView.smoothScrollToPosition(0);
                        }
                    }
                }
            };

    private void initializeFragmentList() {
        mFragmentList.add(PhotosFragment.newInstance(mPhotoSortOrder));
        mFragmentList.add(TrendingsFragment.newInstance());
        mFragmentList.add(CollectionsFragment.newInstance());
        mFragmentList.add(FavouritesFragment.newInstance());

        mBuilder.rootFragments(mFragmentList);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
        if (mFragNavController != null) {
            mFragNavController.onSaveInstanceState(outState);
        }
    }

    public void pushFragment(Fragment fragment) {
        mFragNavController.pushFragment(fragment);
    }

    public void pushFragment(Fragment fragment, FragNavTransactionOptions fragNavTransactionOptions) {
        mFragNavController.pushFragment(fragment, fragNavTransactionOptions);
    }

    @Override
    public void onBackPressed() {
        if (mFragNavController.getCurrentStack().size() > 1) {
            mFragNavController.popFragment(
                    FragNavTransactionOptions.newBuilder()
                            .transition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).build()
            );
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_PHOTOS:
                return PhotosFragment.newInstance(mPhotoSortOrder);
            case INDEX_TRENDINGS:
                return TrendingsFragment.newInstance();
            case INDEX_COLLECTIONS:
                return CollectionsFragment.newInstance();
            case INDEX_FAVOURITES:
                return FavouritesFragment.newInstance();
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public void onTabTransaction(@Nullable Fragment fragment, int i) {

    }

    @Override
    public void onFragmentTransaction(Fragment fragment, TransactionType transactionType) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.latest:
                Toast.makeText(this, "Latest Clicked", Toast.LENGTH_SHORT).show();
                mPhotoSortOrder = "latest";
//                mFragNavController.pushFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                mFragNavController.replaceFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                return true;
            case R.id.oldest:
                Toast.makeText(this, "Oldest Clicked", Toast.LENGTH_SHORT).show();
                mPhotoSortOrder = "oldest";
                mFragNavController.clearStack();
//                mFragNavController.pushFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                mFragNavController.replaceFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                return true;
            case R.id.popular:
                Toast.makeText(this, "Popular Clicked", Toast.LENGTH_SHORT).show();
                mPhotoSortOrder = "popular";
                mFragNavController.clearStack();
//                mFragNavController.pushFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                mFragNavController.replaceFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
