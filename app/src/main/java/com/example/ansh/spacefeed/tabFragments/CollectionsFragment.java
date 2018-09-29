package com.example.ansh.spacefeed.tabFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.ansh.spacefeed.activity.DetailActivity;
import com.example.ansh.spacefeed.adapter.CollectionPhotoPagedListAdapter;
import com.example.ansh.spacefeed.fragments.PhotoDetailFragment;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CollectionPhotoViewModel;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.CollectionTag;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.recyclerViewUtils.ItemOffsetDecoration;

import java.util.ListIterator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionsFragment extends Fragment {

    /**
     * Private member variables
     */
    private Context mContext = getActivity();
    public static final String TAG = "FUCK";

    // Layout related instances.
    private Toolbar mToolbar;

    // Private Member Variables
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    //    private GridLayoutManager mGridLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private CollectionPhotoPagedListAdapter mCollectionPhotoPagedListAdapter;
    private CollectionPhotoViewModel mCollectionPhotoViewModel;

    public CollectionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CollectionsFragment.
     */
    public static CollectionsFragment newInstance() {
        CollectionsFragment fragment = new CollectionsFragment();
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
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        // set up toolbar
        mToolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Collection");

        // Initialize views
        mRecyclerView = view.findViewById(R.id.collectionRecyclerView);

        // getting our PhotoViewModel
        mCollectionPhotoViewModel = ViewModelProviders.of(this).get(CollectionPhotoViewModel.class);

        // SetUp
        setUpRecyclerView();

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
//                Toast.makeText(mContext, "FUCK! I got called.", Toast.LENGTH_SHORT).show();
                CollectionPhoto splashResponse = mCollectionPhotoViewModel.getCollectionPhotoPagedList().getValue().get(pos);
                for(CollectionTag collectionTag : splashResponse.getCollectionTags()) {
                    Log.i(TAG, "onClick: " + collectionTag.getTitle());
                }


            }
        };

        // Creating the Adapter
        mCollectionPhotoPagedListAdapter = new CollectionPhotoPagedListAdapter(getContext(), simpleOnItemClickListener);
        // observing the mCollectionPhotoPagedList from view model
        mCollectionPhotoViewModel.mCollectionPhotoPagedList.observe(this, new Observer<PagedList<CollectionPhoto>>() {
            @Override
            public void onChanged(@Nullable PagedList<CollectionPhoto> unSplashRespons) {
                // in case of any changes submitting the items to adapter
                mCollectionPhotoPagedListAdapter.submitList(unSplashRespons);
                Log.i(TAG, "LALALA: " + unSplashRespons.size());
            }
        });
        // Setting the adapter
        mRecyclerView.setAdapter(mCollectionPhotoPagedListAdapter);

        return view;
    }

    private void setUpRecyclerView() {
//        int columnSpacingInPixels = 16;
//        mRecyclerView.addItemDecoration(new ColumnSpaceItemDecoration(columnSpacingInPixels));

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        mGridLayoutManager = new GridLayoutManager(this, 2);
//        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
//        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.item_offset);
//        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(null);

//        mRecyclerView.setItemViewCacheSize(20);
//        mRecyclerView.setDrawingCacheEnabled(true);
//        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
    }

}