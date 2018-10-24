package com.example.ansh.spacefeed.tabFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
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
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.adapter.CollectionsPagedListAdapter;
import com.example.ansh.spacefeed.fragments.CollectionPhotoFragment;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CustomViewModel;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CollectionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CollectionsFragment extends Fragment {

    /* Private member variables */
    public static final String TAG_COLLECTION_FRAGMENT = CollectionsFragment.class.getSimpleName();

    // Layout related instances.
    @BindView(R.id.f_c_toolbar) Toolbar mToolbar;

    // Private Member Variables
    @BindView(R.id.f_c_recyclerView) RecyclerView mRecyclerView;

    private LinearLayoutManager mLinearLayoutManager;

    private CollectionsPagedListAdapter mCollectionsPagedListAdapter;
    private CustomViewModel mCollectionViewModel;

    private Fragment mCollectionPhotoFragment;

    private String mCollectionsSortOrder;

    private Unbinder mUnbinder;

    public CollectionsFragment() {
        // Required empty public constructor
    }

    public static CollectionsFragment newInstance(String sort) {
        CollectionsFragment fragment = new CollectionsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("sort", sort);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCollectionsSortOrder = getArguments().getString("sort");

        // getting our CollectionViewModel
        mCollectionViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        mCollectionViewModel.setCollectionSortOrder(mCollectionsSortOrder);

        Log.i(TAG_COLLECTION_FRAGMENT, "onCreate: called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);
        setHasOptionsMenu(true);

        mUnbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Collection");

        Log.i(TAG_COLLECTION_FRAGMENT, "onCreateView: called");

        // SetUp
        setUpRecyclerView(container.getContext());

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                CollectionPhoto splashResponse = mCollectionViewModel.getCollectionsPagedList().getValue().get(pos);
                mCollectionPhotoFragment = new CollectionPhotoFragment();

                Bundle bundle = new Bundle();
                bundle.putParcelable("collections", splashResponse);
                mCollectionPhotoFragment.setArguments(bundle);
                ((MainActivity)getActivity()).pushFragment(mCollectionPhotoFragment);
            }
        };

        // Creating the Adapter
        mCollectionsPagedListAdapter = new CollectionsPagedListAdapter(simpleOnItemClickListener);
        // observing the mCollectionsPagedList from view model
        mCollectionViewModel.mCollectionsPagedList.observe(this, new Observer<PagedList<CollectionPhoto>>() {
            @Override
            public void onChanged(@Nullable PagedList<CollectionPhoto> unSplashResponse) {
                // in case of any changes submitting the items to adapter
                mCollectionsPagedListAdapter.submitList(unSplashResponse);
                Log.i(TAG_COLLECTION_FRAGMENT, "onChanged: called " + unSplashResponse.size());
            }
        });
        // Setting the adapter
        mRecyclerView.setAdapter(mCollectionsPagedListAdapter);

        return view;
    }

    private void setUpRecyclerView(Context context) {
        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG_COLLECTION_FRAGMENT, "onActivityCreated: called");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
