package com.example.ansh.spacefeed.adapter;

import android.animation.ObjectAnimator;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.pojos.CollectionPhoto;

import de.hdodenhof.circleimageview.CircleImageView;

public class CollectionsPagedListAdapter extends PagedListAdapter<CollectionPhoto, CollectionsPagedListAdapter.CollectionViewHolder> {

    private Context mContext;
    private SimpleOnItemClickListener mSimpleOnItemClickListener;
    ViewPropertyTransition.Animator animationObject;

    public CollectionsPagedListAdapter(Context ctx, SimpleOnItemClickListener simpleOnItemClickListener) {
        super(DIFF_CALLBACK);
        this.mContext = ctx;
        this.mSimpleOnItemClickListener = simpleOnItemClickListener;
    }

    @NonNull
    @Override
    public CollectionsPagedListAdapter.CollectionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.collection_item, viewGroup, false);

        animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);

                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };

        return new CollectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionsPagedListAdapter.CollectionViewHolder collectionViewHolder, int pos) {
        collectionViewHolder.bind(collectionViewHolder, pos);
    }

    private static DiffUtil.ItemCallback<CollectionPhoto> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<CollectionPhoto>() {
                @Override
                public boolean areItemsTheSame(CollectionPhoto oldItem, CollectionPhoto newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(CollectionPhoto oldItem, CollectionPhoto newItem) {
                    return oldItem.equals(newItem);
                }
            };

    public class CollectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mCollectionImage;
        private CircleImageView mCollectionUserImage;
        private TextView mUserName, mNumOfPhotos;

        public CollectionViewHolder(@NonNull View itemView) {
            super(itemView);

            mCollectionImage = itemView.findViewById(R.id.collection_image);
            mCollectionUserImage = itemView.findViewById(R.id.collection_user_image);
            mUserName = itemView.findViewById(R.id.collection_user_name);
            mNumOfPhotos = itemView.findViewById(R.id.collection_numOfPhotos);

            mCollectionImage.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mSimpleOnItemClickListener.onClick(v, getAdapterPosition());
        }

        public void bind(CollectionViewHolder collectionViewHolder, int pos) {
            CollectionPhoto collectionPhoto = getItem(pos);

            Glide.with(mContext).load(collectionPhoto.getCoverPhoto().getUrls().getRegularUrl())
                    .into(collectionViewHolder.mCollectionImage);
            Glide.with(mContext).load(collectionPhoto.getUser().getProfileImage().getMedium()).into(mCollectionUserImage);
            mUserName.setText(collectionPhoto.getUser().getName());
            String numOfPhotos = String.valueOf(collectionPhoto.getTotalPhotos());
            mNumOfPhotos.setText(numOfPhotos);
        }
    }
}
