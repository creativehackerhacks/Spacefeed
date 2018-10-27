package com.example.ansh.spacefeed.childFragments;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.example.ansh.spacefeed.adapter.PhotoPagedListAdapter;
import com.example.ansh.spacefeed.fragments.PhotoDetailFragment;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.modal.CustomViewModel;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.recyclerViewUtils.ItemOffsetDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserLikesFragment extends Fragment {

    /**
     * Private member variables
     */
    private Context mContext = getActivity();
    public static final String TAG = "FUCK";

    // Private Member Variables
    private RecyclerView mRecyclerView;
    private TextView mEmptyText;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

    private PhotoPagedListAdapter mPhotoPagedListAdapter;
    private CustomViewModel mCustomViewModel;
    private String mUsername, mUserLikesSortOrder;

    private PhotoDetailFragment mPhotoDetailFragment;

    public static final String CLIENT_ID = "63002cc7718cea591dcf5a661065713e4a353d49090dce8df8c7680af2cb78e4";


    public UserLikesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mUsername = getArguments().getString("username");
            mUserLikesSortOrder = getArguments().getString("userLikesSortOrder");
        }

        mCustomViewModel = ViewModelProviders.of(this).get(CustomViewModel.class);
        mCustomViewModel.setUserLikesOptions(mUsername, mUserLikesSortOrder);

        mPhotoDetailFragment = new PhotoDetailFragment();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_likes, container, false);

        mRecyclerView = view.findViewById(R.id.f_u_p_l_recyclerView);
        mEmptyText = view.findViewById(R.id.f_u_p_l_empty_text);

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

        mPhotoPagedListAdapter = new PhotoPagedListAdapter(simpleOnItemClickListener);

        mCustomViewModel.mUserLikesPagedList.observe(this, new Observer<PagedList<Photo>>() {
            @Override
            public void onChanged(@Nullable PagedList<Photo> photos) {
                Log.i(TAG, "onChanged: SUCCESSFULL and Photos = " + photos + " and Username = " + mUsername + " --- SortOrder = " + mUserLikesSortOrder);
                mPhotoPagedListAdapter.submitList(photos);
            }
        });

        mRecyclerView.setAdapter(mPhotoPagedListAdapter);

        Log.i(TAG, "onCreateView: YAY I AM RUNNING!");

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

}
