package com.example.ansh.spacefeed.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.ansh.spacefeed.ColumnSpaceItemDecoration;
import com.example.ansh.spacefeed.modal.ItemViewModel;
import com.example.ansh.spacefeed.adapter.RecyclerViewPagedListAdapter;
import com.example.ansh.spacefeed.apis.ApiInterface;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.adapter.UnSplashAdapter;
import com.example.ansh.spacefeed.pojos.Photo;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    public static final String TAG = "FUCK";

    // Layout related instances.
    private Toolbar mToolbar;

    // API related instances
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";
    private ApiInterface mApiService;

    private RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;

    private UnSplashAdapter mUnSplashAdapter;

    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView = findViewById(R.id.mainRecyclerView);
        int columnSpacingInPixels = 8;
        mRecyclerView.addItemDecoration(new ColumnSpaceItemDecoration(columnSpacingInPixels));

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
//                Toast.makeText(mContext, "FUCK! I got called.", Toast.LENGTH_SHORT).show();

//                Photo splashResponse = itemViewModel.getItemPagedList().getValue().get(pos);
                Photo splashResponse = itemViewModel.getItemPagedList().getValue().get(pos);
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

        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // getting our ItemViewModel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Creating the Adapter
        final RecyclerViewPagedListAdapter adapter = new RecyclerViewPagedListAdapter(this, simpleOnItemClickListener);

        // observing the itemPagedList from view model
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> unSplashRespons) {
                // in case of any changes
                // submitting the items to adapter
                adapter.submitList(unSplashRespons);
                Log.i(TAG, "MAIN ACTIVITY: " + unSplashRespons.size());
            }
        });

        // Setting the adapter
        mRecyclerView.setAdapter(adapter);

    }

}
