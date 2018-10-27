package com.example.ansh.spacefeed.childFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.activity.MainActivity;
import com.example.ansh.spacefeed.adapter.CollectionsPagedListAdapter;
import com.example.ansh.spacefeed.adapter.PhotoPagedListAdapter;
import com.example.ansh.spacefeed.fragments.PhotoDetailFragment;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CustomViewModel;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.recyclerViewUtils.ItemOffsetDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserCollectionsFragment extends Fragment {

    /**
     * Private member variables
     */
    private Context mContext = getActivity();
    public static final String TAG = "FUCK";

    // Private Member Variables
    private RecyclerView mRecyclerView;
    private TextView mEmptyText;
    private LinearLayoutManager mLinearLayoutManager;

    private CollectionsPagedListAdapter mCollectionsPagedListAdapter;
    private CustomViewModel mCustomViewModel;
    private String mUsername;

    private PhotoDetailFragment mPhotoDetailFragment;

    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";


    public UserCollectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mUsername = getArguments().getString("username");
        }

        mCustomViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        mCustomViewModel.setUserCollectionsOptions(mUsername);

        mPhotoDetailFragment = new PhotoDetailFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_collections, container, false);

        mRecyclerView = view.findViewById(R.id.f_u_p_c_recyclerView);
        mEmptyText = view.findViewById(R.id.f_u_p_c_empty_text);

        setUpRecyclerView(container.getContext());

        final SimpleOnItemClickListener simpleOnItemClickListener = new SimpleOnItemClickListener() {
            @Override
            public void onClick(View v, int pos) {
                Photo splashResponse = mCustomViewModel.getUserPhotoPagedList().getValue().get(pos);
                Log.i(TAG, "onClick: " + splashResponse);

                Bundle bundle = new Bundle();
                bundle.putParcelable("Photo", splashResponse);
                bundle.putString("Transition", "trans"+pos);
                mPhotoDetailFragment.setArguments(bundle);

                ((MainActivity) getActivity()).pushFragment(mPhotoDetailFragment);
            }
        };

        mCollectionsPagedListAdapter = new CollectionsPagedListAdapter(simpleOnItemClickListener);

        mCustomViewModel.mUserCollectionsPagedList.observe(this, new Observer<PagedList<CollectionPhoto>>() {
            @Override
            public void onChanged(@Nullable PagedList<CollectionPhoto> collectionPhotos) {
                Log.i(TAG, "onChanged: USER COLLECTION and Photos = " + collectionPhotos + " and Username = " + mUsername);
                mCollectionsPagedListAdapter.submitList(collectionPhotos);
            }
        });

        mRecyclerView.setAdapter(mCollectionsPagedListAdapter);

        Log.i(TAG, "onCreateView: YAY I AM RUNNING!");

        return view;
    }

    private void setUpRecyclerView(Context context) {
        mLinearLayoutManager = new LinearLayoutManager(context);
        mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setItemAnimator(null);

    }

}
