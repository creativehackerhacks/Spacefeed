package com.example.ansh.spacefeed.adapter;

import android.animation.ObjectAnimator;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.pojos.Photo;
import com.example.ansh.spacefeed.utils.NetworkState;
import com.example.ansh.spacefeed.utils.Status;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PhotoPagedListAdapter extends PagedListAdapter<Photo, RecyclerView.ViewHolder> {

    /* Private member variables */
    private Context mContext;
    private SimpleOnItemClickListener mListener;
    ViewPropertyTransition.Animator animationObject;

    private NetworkState mNetworkState;

    /* Constructor */
    public PhotoPagedListAdapter(SimpleOnItemClickListener simpleOnItemClickListener) {
        super(Photo.DIFF_CALLBACK);
        this.mListener = simpleOnItemClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view;
         if(viewType == R.layout.row_item) {
             view = layoutInflater.inflate(R.layout.row_item, parent, false);

             animationObject = new ViewPropertyTransition.Animator() {
                 @Override
                 public void animate(View view) {
                     view.setAlpha(0f);

                     ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0.4f, 1f);
                     fadeAnim.setDuration(500);
                     fadeAnim.start();
                 }
             };

             return new PhotoViewHolder(view);
         } else if(viewType == R.layout.network_state_item) {
             view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
             return new NetworkStateViewHolder(view);
         } else {
             throw new IllegalArgumentException("unknown type");
         }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.row_item:
                ((PhotoViewHolder) holder).bind(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateViewHolder) holder).bind(mNetworkState);
                break;
        }
    }


    private boolean hasExtraRow() {
        if (mNetworkState != null && mNetworkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.row_item;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.mNetworkState;
        boolean previousExtraRow = hasExtraRow();
        this.mNetworkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }


    // Inner ViewHolder class
    class PhotoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.row_content_bg) LinearLayout mLinearLayout;
        @BindView(R.id.row_image) ImageView mImageView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.row_image)
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }

        // binding the data to the views
        public void bind(Photo photo) {
                Glide.with(mContext).load(photo.getUrls().getRegularUrl())
                        .transition(GenericTransitionOptions.with(animationObject))
                        .apply(new RequestOptions()
                                .placeholder(new ColorDrawable(Color.parseColor(photo.getColor())))
                                .dontAnimate()
                        )
                        .into(mImageView);

        }
    }

    class NetworkStateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.progress_bar) ProgressBar mProgressBar;
        @BindView(R.id.error_msg) TextView mErrorMessage;
        @BindView(R.id.retry_button) Button mRetryButton;

        public NetworkStateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.retry_button)
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }

        public void bind(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == Status.RUNNING) {
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setIndeterminate(true);
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
            if (networkState != null && networkState.getStatus() == Status.FAILED) {
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText(networkState.getMsg());
            }else if (networkState!=null && networkState.getStatus() ==Status.MAX) {
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText("No More Page to Load");
            }
            else
            {
                mErrorMessage.setVisibility(View.GONE);
            }
        }

    }


}
