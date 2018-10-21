package com.example.ansh.spacefeed.tabFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.adapter.PhotoPagedListAdapter;
import com.example.ansh.spacefeed.fragments.PhotoDetailFragment;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CustomViewModel;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.recyclerViewUtils.ItemOffsetDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendingsFragment extends Fragment {

    /**
     * Private member variables
     */
    private Context mContext = getActivity();
    public static final String TAG = "FUCK";

    // Layout related instances.
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    // Private Member Variables
    public RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    //    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public PhotoPagedListAdapter mPhotoPagedListAdapter;
    private CustomViewModel mPhotoViewModel;

    private Fragment mPhotoDetailFragment;

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

        mPhotoDetailFragment = PhotoDetailFragment.newInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        mToolbar = view.findViewById(R.id.f_p_toolbar);
//        mCollapsingToolbarLayout = view.findViewById(R.id.f_p_collapsing_toolbar);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("LOL");
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Trending");

//        mCollapsingToolbarLayout.setTitle("Photos");

        mRecyclerView = view.findViewById(R.id.f_p_recyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.f_p_swipe_refresh_layout);

        // getting our CustomViewModel
        mPhotoViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);

        // SetUp
        setUpRecyclerView();

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
//                Toast.makeText(mContext, "FUCK! I got called.", Toast.LENGTH_SHORT).show();
                Photo splashResponse = mPhotoViewModel.getTrendingPagedList().getValue().get(pos);
                Log.i(TAG, "onClick: " + splashResponse);

//                Intent intent = new Intent(getContext(), DetailActivity.class);
//                intent.putExtra("Photo", splashResponse);
//
////                ActivityOptionsCompat options = ActivityOptionsCompat.
////                        makeSceneTransitionAnimation(MainActivity.this,
////                                v,
////                                ViewCompat.getTransitionName(v));
//                startActivity(intent);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Photo", splashResponse);
                mPhotoDetailFragment.setArguments(bundle);
                ((MainActivity)getActivity()).pushFragment(mPhotoDetailFragment);

//                simpleFragmentB.setArguments(photosBundle);
//                getFragmentManager()
//                        .beginTransaction()
////                        .addSharedElement(imageView, ViewCompat.getTransitionName(imageView))
//                        .addToBackStack(TAG)
//                        .replace(R.id.main_frame_layout, simpleFragmentB)
//                        .commit();

            }
        };

        // Creating the Adapter
        mPhotoPagedListAdapter = new PhotoPagedListAdapter(getContext(), simpleOnItemClickListener);
        // observing the mCollectionsPagedList from view model
        mPhotoViewModel.mTrendingPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> unSplashRespons) {
                // in case of any changes submitting the items to adapter
                mPhotoPagedListAdapter.submitList(unSplashRespons);
                mSwipeRefreshLayout.setRefreshing(false);
                Log.i(TAG, "MAIN ACTIVITY: " + unSplashRespons.size());
            }
        });
        // Setting the adapter
        mRecyclerView.setAdapter(mPhotoPagedListAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPhotoViewModel.onTrendingDataSourceRefresh();
            }
        });

        return view;
    }


    private void setUpRecyclerView() {
//        int columnSpacingInPixels = 16;
//        mRecyclerView.addItemDecoration(new ColumnSpaceItemDecoration(columnSpacingInPixels));

        mLinearLayoutManager = new LinearLayoutManager(getContext());
//        mGridLayoutManager = new GridLayoutManager(this, 2);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(null);

//        mRecyclerView.setItemViewCacheSize(20);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

}
