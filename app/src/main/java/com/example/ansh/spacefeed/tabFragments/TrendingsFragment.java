package com.example.ansh.spacefeed.tabFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrendingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendingsFragment extends Fragment {

    /**
     * Private member variables
     */
    public static final String TAG_TRENDINGS_FRAGMENT = TrendingsFragment.class.getSimpleName();

    // Layout related instances.
    @BindView(R.id.f_p_toolbar) Toolbar mToolbar;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    // Private Member Variables
    @BindView(R.id.f_p_recyclerView) RecyclerView mRecyclerView;

    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    public PhotoPagedListAdapter mPhotoPagedListAdapter;
    private CustomViewModel mPhotoViewModel;

    private Fragment mPhotoDetailFragment;

    private Unbinder mUnbinder;

    public TrendingsFragment() {
        // Required empty public constructor
    }

    public static TrendingsFragment newInstance() {
        TrendingsFragment fragment = new TrendingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPhotoDetailFragment = new PhotoDetailFragment();

        // getting our CustomViewModel
        mPhotoViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);

        Log.i(TAG_TRENDINGS_FRAGMENT, "onCreate: called");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photos, container, false);

        mUnbinder = ButterKnife.bind(this, view);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Trending");

        Log.i(TAG_TRENDINGS_FRAGMENT, "onCreateView: called");

        // SetUp
        setUpRecyclerView(container.getContext());

        //OnClickImplementation
        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Photo splashResponse = mPhotoViewModel.getTrendingPagedList().getValue().get(pos);
                Log.i(TAG_TRENDINGS_FRAGMENT, "onClick: " + splashResponse);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Photo", splashResponse);
                mPhotoDetailFragment.setArguments(bundle);
                ((MainActivity)getActivity()).pushFragment(mPhotoDetailFragment);
            }
        };

        // Creating the Adapter
        mPhotoPagedListAdapter = new PhotoPagedListAdapter(simpleOnItemClickListener);

        // observing the mCollectionsPagedList from view model
        mPhotoViewModel.mTrendingPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> unSplashResponse) {
                // in case of any changes submitting the items to adapter
                mPhotoPagedListAdapter.submitList(unSplashResponse);
                Log.i(TAG_TRENDINGS_FRAGMENT, "onChanged: called " + unSplashResponse.size());
            }
        });
        // Setting the adapter
        mRecyclerView.setAdapter(mPhotoPagedListAdapter);

        return view;
    }


    private void setUpRecyclerView(Context context) {
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(context, R.dimen.item_offset);
        mRecyclerView.addItemDecoration(itemDecoration);

        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG_TRENDINGS_FRAGMENT, "onActivityCreated: called");
    }
}
