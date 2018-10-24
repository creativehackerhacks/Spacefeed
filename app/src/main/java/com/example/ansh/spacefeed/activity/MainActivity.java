package com.example.ansh.spacefeed.activity;

import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemReselectedListener;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * I WILL CHANGE THIS CLASS LATER
 * AND REFACTOR THE NAVIGATION LISTENER
 * and to implement "BACKSTACK"
 *
 * --------
 * OR Probably I'll shift to TabLayout and implement the code
 * which I starred on Github.
 */
public class MainActivity extends AppCompatActivity {

    private static final int INDEX_PHOTOS = 1;
    private static final int INDEX_TRENDINGS = 2;
    private static final int INDEX_COLLECTIONS = 3;
    private static final int INDEX_FAVOURITES = 4;

    private static final long MOVE_DEFAULT_TIME = 1000;
    private static final long FADE_DEFAULT_TIME = 300;

    private String mPhotoSortOrder;
    private String mCollectionsSortOrder;
    private String mTrendingsSortOrder;

    private List<Fragment> mFragmentList;
    private FragNavController.Builder mBuilder;
    private FragNavController mFragNavController;

    private BottomNavigationView mBottomNavigationView;
    private Toolbar mToolbar;

    private Menu mMenu;
    private MenuItem mMenuPhotoSearch,
            mMenuPhotoLatest, mMenuPhotoOldest, mMenuPhotoPopular,
            mMenuTrendingLatest, mMenuTrendingOldest, mMenuTrendingPopular,
            mMenuCollectionAll, mMenuCollectionCurated, mMenuCollectionFeatured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFragmentList = new ArrayList<>(4);

        mBottomNavigationView = findViewById(R.id.main_navigation);
        mPhotoSortOrder = "latest";
        mCollectionsSortOrder = "";
        mTrendingsSortOrder = "latest";

        mBuilder = FragNavController
                .newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.main_frame_layout);
//                .transactionListener(this),
//                .rootFragmentListener(this, 4);
//                .switchController(mFragNavSwitchController);

        initializeFragmentList();

        mFragNavController = mBuilder
//                .defaultTransactionOptions(FragNavTransactionOptions.newBuilder()
//                        .transition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN) // for all transitions
//                        .build())
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
                    if (!mFragNavController.isRootFragment()) {
                        mFragNavController.clearStack();
                    } else {
//                        Fragment f = mFragNavController.getCurrentFrag();
//                        if (f != null) {
//                            View fragmentView = f.getView();
//                            RecyclerView mRecyclerView = fragmentView.findViewById(R.id.f_p_recyclerView);//mine one is RecyclerView
//                            if (mRecyclerView != null)
//                                mRecyclerView.smoothScrollToPosition(0);
//                        }
                    }
                }
            };

    private void initializeFragmentList() {
        mFragmentList.add(PhotosFragment.newInstance(mPhotoSortOrder));
        mFragmentList.add(TrendingsFragment.newInstance(mTrendingsSortOrder));
        mFragmentList.add(CollectionsFragment.newInstance(mCollectionsSortOrder));
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
        FragNavTransactionOptions options = FragNavTransactionOptions.newBuilder()
                .transition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//                .customAnimations(R.anim.enter_from_right,
//                        R.anim.exit_to_left
//                )
                .build();
        mFragNavController.pushFragment(fragment, options);
    }

    public void pushFragment(Fragment fragment, View view, String name) {
        FragNavTransactionOptions d = FragNavTransactionOptions.newBuilder()
                .addSharedElement(new Pair<View, String>(view, name))
                .build();
        mFragNavController.pushFragment(fragment, d);
    }

    @Override
    public void onBackPressed() {
        if (mFragNavController.getCurrentStack().size() > 1) {
            mFragNavController.popFragment(
                    FragNavTransactionOptions.newBuilder()
                            .transition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
//                            .customAnimations(R.anim.enter_from_left,
//                                    R.anim.exit_to_right)
                            .build()
            );
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public Fragment getRootFragment(int index) {
//        switch (index) {
//            case INDEX_PHOTOS:
//                return PhotosFragment.newInstance(mPhotoSortOrder);
//            case INDEX_TRENDINGS:
//                return TrendingsFragment.newInstance();
//            case INDEX_COLLECTIONS:
//                return CollectionsFragment.newInstance();
//            case INDEX_FAVOURITES:
//                return FavouritesFragment.newInstance();
//        }
//        throw new IllegalStateException("Need to send an index that we know");
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu = menu;

        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);

        mMenuPhotoSearch = menu.findItem(R.id.menu_search_by);
        mMenuPhotoLatest = menu.findItem(R.id.menu_photo_latest);
        mMenuPhotoOldest = menu.findItem(R.id.menu_photo_oldest);
        mMenuPhotoPopular = menu.findItem(R.id.menu_photo_popular);
        mMenuTrendingLatest = menu.findItem(R.id.menu_trending_latest);
        mMenuTrendingOldest = menu.findItem(R.id.menu_trending_oldest);
        mMenuTrendingPopular = menu.findItem(R.id.menu_trending_popular);
        mMenuCollectionAll = menu.findItem(R.id.menu_collection_all);
        mMenuCollectionCurated = menu.findItem(R.id.menu_collection_curated);
        mMenuCollectionFeatured = menu.findItem(R.id.menu_collection_featured);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        Fragment fragment = mFragNavController.getCurrentFrag();
        if(fragment instanceof PhotosFragment) {
            mMenuPhotoLatest.setVisible(true);
            mMenuPhotoOldest.setVisible(true);
            mMenuPhotoPopular.setVisible(true);
            mMenuTrendingLatest.setVisible(false);
            mMenuTrendingOldest.setVisible(false);
            mMenuTrendingPopular.setVisible(false);
            mMenuCollectionAll.setVisible(false);
            mMenuCollectionCurated.setVisible(false);
            mMenuCollectionFeatured.setVisible(false);
        } else if(fragment instanceof TrendingsFragment) {
            mMenuPhotoLatest.setVisible(false);
            mMenuPhotoOldest.setVisible(false);
            mMenuPhotoPopular.setVisible(false);
            mMenuTrendingLatest.setVisible(true);
            mMenuTrendingOldest.setVisible(true);
            mMenuTrendingPopular.setVisible(true);
            mMenuCollectionAll.setVisible(false);
            mMenuCollectionCurated.setVisible(false);
            mMenuCollectionFeatured.setVisible(false);
        } else if(fragment instanceof CollectionsFragment) {
            mMenuPhotoLatest.setVisible(false);
            mMenuPhotoOldest.setVisible(false);
            mMenuPhotoPopular.setVisible(false);
            mMenuTrendingLatest.setVisible(false);
            mMenuTrendingOldest.setVisible(false);
            mMenuTrendingPopular.setVisible(false);
            mMenuCollectionAll.setVisible(true);
            mMenuCollectionCurated.setVisible(true);
            mMenuCollectionFeatured.setVisible(true);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_photo_latest:
                Toast.makeText(this, "Latest Clicked", Toast.LENGTH_SHORT).show();
                mPhotoSortOrder = "latest";
                mFragNavController.replaceFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                return true;
            case R.id.menu_photo_oldest:
                Toast.makeText(this, "Oldest Clicked", Toast.LENGTH_SHORT).show();
                mPhotoSortOrder = "oldest";
                mFragNavController.replaceFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                return true;
            case R.id.menu_photo_popular:
                Toast.makeText(this, "Popular Clicked", Toast.LENGTH_SHORT).show();
                mPhotoSortOrder = "popular";
                mFragNavController.replaceFragment(PhotosFragment.newInstance(mPhotoSortOrder));
                return true;
            case R.id.menu_trending_latest:
                Toast.makeText(this, "Latest Clicked", Toast.LENGTH_SHORT).show();
                mTrendingsSortOrder = "latest";
                mFragNavController.replaceFragment(TrendingsFragment.newInstance(mTrendingsSortOrder));
                return true;
            case R.id.menu_trending_oldest:
                Toast.makeText(this, "Oldest Clicked", Toast.LENGTH_SHORT).show();
                mTrendingsSortOrder = "oldest";
                mFragNavController.replaceFragment(TrendingsFragment.newInstance(mTrendingsSortOrder));
                return true;
            case R.id.menu_trending_popular:
                Toast.makeText(this, "Popular Clicked", Toast.LENGTH_SHORT).show();
                mTrendingsSortOrder = "popular";
                mFragNavController.replaceFragment(TrendingsFragment.newInstance(mTrendingsSortOrder));
                return true;
            case R.id.menu_collection_all:
                Toast.makeText(this, "All Clicked", Toast.LENGTH_SHORT).show();
                mCollectionsSortOrder = "";
                mFragNavController.replaceFragment(CollectionsFragment.newInstance(mCollectionsSortOrder));
                return true;
            case R.id.menu_collection_curated:
                Toast.makeText(this, "Curated Clicked", Toast.LENGTH_SHORT).show();
                mCollectionsSortOrder = "curated";
                mFragNavController.replaceFragment(CollectionsFragment.newInstance(mCollectionsSortOrder));
                return true;
            case R.id.menu_collection_featured:
                Toast.makeText(this, "Featured Clicked", Toast.LENGTH_SHORT).show();
                mCollectionsSortOrder = "featured";
                mFragNavController.replaceFragment(CollectionsFragment.newInstance(mCollectionsSortOrder));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void SetNavigationVisibility(boolean opt) {
        if (opt) {
            mBottomNavigationView.setVisibility(View.VISIBLE);
        } else {
            mBottomNavigationView.setVisibility(View.GONE);
        }
    }

    public void showOverFlowMenu(boolean menuState) {
        if(mMenu == null) {
            return;
        }
        mMenu.setGroupVisible(R.id.all_menu, menuState);
    }


}
