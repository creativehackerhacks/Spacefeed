package com.example.ansh.spacefeed.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.ansh.spacefeed.modal.PhotoViewModel;
import com.example.ansh.spacefeed.adapter.PhotoPagedListAdapter;
import com.example.ansh.spacefeed.apis.ApiInterface;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.pojos.Photo;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    public static final String TAG = "FUCK";

    // Layout related instances.
    private Toolbar mToolbar;

    // API related instances
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";
    private ApiInterface mApiService;

    // Private Member Variables
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
//    private GridLayoutManager mGridLayoutManager;
//    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private PhotoPagedListAdapter mPhotoPagedListAdapter;
//    private UnSplashAdapter mUnSplashAdapter;

    private PhotoViewModel mPhotoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = findViewById(R.id.mainRecyclerView);

        // getting our PhotoViewModel
        mPhotoViewModel = ViewModelProviders.of(this).get(PhotoViewModel.class);

        // SetUp
        setUpRecyclerView();

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
//                Toast.makeText(mContext, "FUCK! I got called.", Toast.LENGTH_SHORT).show();
                Photo splashResponse = mPhotoViewModel.getPhotoPagedList().getValue().get(pos);
                Log.i(TAG, "onClick: " + splashResponse);

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("Photo", splashResponse);

//                ActivityOptionsCompat options = ActivityOptionsCompat.
//                        makeSceneTransitionAnimation(MainActivity.this,
//                                v,
//                                ViewCompat.getTransitionName(v));
                startActivity(intent);
            }
        };

        // Creating the Adapter
        mPhotoPagedListAdapter = new PhotoPagedListAdapter(this, simpleOnItemClickListener);
        // observing the mPhotoPagedList from view model
        mPhotoViewModel.mPhotoPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> unSplashRespons) {
                // in case of any changes submitting the items to adapter
                mPhotoPagedListAdapter.submitList(unSplashRespons);
                Log.i(TAG, "MAIN ACTIVITY: " + unSplashRespons.size());
            }
        });
        // Setting the adapter
        mRecyclerView.setAdapter(mPhotoPagedListAdapter);

    }

    private void setUpRecyclerView() {
//        int columnSpacingInPixels = 16;
//        mRecyclerView.addItemDecoration(new ColumnSpaceItemDecoration(columnSpacingInPixels));

        mLinearLayoutManager = new LinearLayoutManager(this);
//        mGridLayoutManager = new GridLayoutManager(this, 2);
//        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setHasFixedSize(true);
    }

}
