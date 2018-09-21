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
import android.widget.Toast;

import com.example.ansh.spacefeed.ItemViewModel;
import com.example.ansh.spacefeed.adapter.RecyclerViewPagedListAdapter;
import com.example.ansh.spacefeed.client.ApiClient;
import com.example.ansh.spacefeed.apis.ApiInterface;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.adapter.UnSplashAdapter;
import com.example.ansh.spacefeed.pojos.UnSplashResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Context mContext = MainActivity.this;
    public static final String TAG = "FUCK";

    // Layout related instances.
    private Toolbar mToolbar;

    // API related instances
    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";
    private ApiInterface mApiService;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private UnSplashAdapter mUnSplashAdapter;

    ItemViewModel itemViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mRecyclerView = findViewById(R.id.mainRecyclerView);

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Toast.makeText(mContext, "FUCK! I got called.", Toast.LENGTH_SHORT).show();

                UnSplashResponse splashResponse = itemViewModel.getItemPagedList().getValue().get(pos);
                Log.i(TAG, "onClick: " + splashResponse);

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("imageUrl", splashResponse.getUrls().getFullUrl());

                startActivity(intent);
            }
        };

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        // getting our ItemViewModel
        itemViewModel = ViewModelProviders.of(this).get(ItemViewModel.class);

        // Creating the Adapter
        final RecyclerViewPagedListAdapter adapter = new RecyclerViewPagedListAdapter(this, simpleOnItemClickListener);

        // observing the itemPagedList from view model
        itemViewModel.itemPagedList.observe(this, new Observer<PagedList<UnSplashResponse>>() {
            @Override
            public void onChanged(@Nullable PagedList<UnSplashResponse> unSplashResponses) {
                // in case of any changes
                // submitting the items to adapter
                adapter.submitList(unSplashResponses);
                Log.i(TAG, "MAIN ACTIVITY: " + unSplashResponses.size());
            }
        });

        // Setting the adapter
        mRecyclerView.setAdapter(adapter);

    }

}
