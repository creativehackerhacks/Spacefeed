package com.example.ansh.spacefeed.adapter;

import android.animation.ObjectAnimator;
import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.transition.ViewPropertyTransition;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.pojos.Photo;
import com.github.florent37.glidepalette.BitmapPalette.Profile;
import com.github.florent37.glidepalette.BitmapPalette.Swatch;
import com.github.florent37.glidepalette.GlidePalette;

public class PhotoPagedListAdapter extends PagedListAdapter<Photo, PhotoPagedListAdapter.ItemViewHolder> {

    // Private member variables
    private Context mContext;
    private SimpleOnItemClickListener mListener;
    ViewPropertyTransition.Animator animationObject;

    // Constructor
    public PhotoPagedListAdapter(SimpleOnItemClickListener simpleOnItemClickListener) {
        super(DIFF_CALLBACK);
//        this.mContext = mCtx;
        this.mListener = simpleOnItemClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mContext = recyclerView.getContext();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);

         animationObject = new ViewPropertyTransition.Animator() {
            @Override
            public void animate(View view) {
                view.setAlpha(0f);

                ObjectAnimator fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f);
                fadeAnim.setDuration(2500);
                fadeAnim.start();
            }
        };

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Calling bind method.
        holder.bind(holder, position);
    }

    private static DiffUtil.ItemCallback<Photo> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Photo>() {
                @Override
                public boolean areItemsTheSame(Photo oldItem, Photo newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(Photo oldItem, Photo newItem) {
                    return oldItem.equals(newItem);
                }
            };

    // Inner ViewHolder class
    class ItemViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        LinearLayout mLinearLayout;
        ImageView mImageView;
        TextView mName, mNumOfLikes;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.row_content_bg);
            mImageView = itemView.findViewById(R.id.image);
//            mName = itemView.findViewById(R.id.user_name);
//            mNumOfLikes = itemView.findViewById(R.id.num_of_likes);

            mImageView.setOnClickListener(this);
        }

        // onClick Listener for imageView
        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }

        // binding the data to the views
        public void bind(ItemViewHolder holder, int position) {
            Photo item = getItem(position);

                Glide.with(mContext).load(item.getUrls().getRegularUrl())
//                        .transition(GenericTransitionOptions.with(animationObject))
                        .apply(new RequestOptions()
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .override(1080, 1080)
                                .placeholder(new ColorDrawable(Color.parseColor(item.getColor())))
                        )
//                        .listener(GlidePalette.with(item.getUrls().getSmallUrl())
//                                .use(Profile.VIBRANT_DARK)
//                                .intoBackground(holder.mLinearLayout)
//
//                                .use(Profile.VIBRANT_DARK)
//                                .intoTextColor(holder.mName, Swatch.BODY_TEXT_COLOR)
//                                .intoTextColor(holder.mNumOfLikes, Swatch.BODY_TEXT_COLOR)
//                                .crossfade(true)
//                        )
                        .into(holder.mImageView);

        }
    }

}
