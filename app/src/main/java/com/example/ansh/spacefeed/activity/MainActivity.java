package com.example.ansh.spacefeed.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

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
    private List<UnSplashResponse> mUnSplashResponseList = new ArrayList<>();

    // These commented instances are not in use(meant for 'PAGINATION').
//    private boolean isScrolling = false;
//    public static final int PAGE_START = 1;
//    private boolean isLoading = false;
//    private boolean isLastPage = false;
//    private int TOTAL_PAGES = 20;
//    private int currentPage = PAGE_START;

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

                UnSplashResponse responseList = mUnSplashResponseList.get(pos);

                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra("imageUrl", responseList.getUrls().getFullUrl());

                startActivity(intent);
            }
        };
        mUnSplashAdapter = new UnSplashAdapter(mUnSplashResponseList, mContext, R.layout.row_item, simpleOnItemClickListener);


        mLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mUnSplashAdapter);

//        mRecyclerView.addOnScrollListener(new PaginationScrollListener(mLayoutManager) {
//            @Override
//            protected boolean isLoading() {
//                return isLoading;
//            }
//
//            @Override
//            protected boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//
//                loadNextPage();
//            }
//
//            @Override
//            protected int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//        });

        if(CLIENT_ID.isEmpty()) {
            Toast.makeText(mContext, "Please obtain your CLIENT ID", Toast.LENGTH_SHORT).show();
        }

        mApiService = ApiClient.getClient().create(ApiInterface.class);

//        loadFirstPage();

        Call<List<UnSplashResponse>> call = mApiService.getUnSplashResponse(CLIENT_ID, 30);
        // Asynchronous Call
        call.enqueue(new Callback<List<UnSplashResponse>>() {
            @Override
            public void onResponse(Call<List<UnSplashResponse>> call, Response<List<UnSplashResponse>> response) {
                List<String> mUser = new ArrayList<>();
                mUnSplashResponseList = response.body();
                mRecyclerView.setAdapter(new UnSplashAdapter(mUnSplashResponseList, mContext, R.layout.row_item, simpleOnItemClickListener));
            }

            @Override
            public void onFailure(Call<List<UnSplashResponse>> call, Throwable t) {
                Toast.makeText(mContext, "Something is seriously FUCKED UP!", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void loadFirstPage() {
//        Log.d(TAG, "loadFirsPage: ");
//
//        callUnSplashResponseApi().enqueue(new Callback<List<UnSplashResponse>>() {
//            @Override
//            public void onResponse(Call<List<UnSplashResponse>> call, Response<List<UnSplashResponse>> response) {
//                List<UnSplashResponse>  responseList = response.body();
//                mUnSplashAdapter.addALL(responseList);
//
//                if (currentPage <= TOTAL_PAGES) mUnSplashAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<List<UnSplashResponse>> call, Throwable t) {
//                Toast.makeText(mContext, "Something is seriously FUCKED UP!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private void loadNextPage() {
//        Log.d(TAG, "loadNextPage: " + currentPage);
//
//        callUnSplashResponseApi().enqueue(new Callback<List<UnSplashResponse>>() {
//            @Override
//            public void onResponse(Call<List<UnSplashResponse>> call, Response<List<UnSplashResponse>> response) {
//                mUnSplashAdapter.removeLoadingFooter();
//                isLoading = false;
//
//                List<UnSplashResponse> responseList = response.body();
//                mUnSplashAdapter.addALL(responseList);
//
//                if (currentPage != TOTAL_PAGES) mUnSplashAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<List<UnSplashResponse>> call, Throwable t) {
//                Toast.makeText(mContext, "Something is seriously FUCKED UP!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    private Call<List<UnSplashResponse>> callUnSplashResponseApi() {
//        return mApiService.getUnSplashResponse(
//          CLIENT_ID, currentPage
//        );
//    }

}
