package com.example.ansh.spacefeed.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.pojos.UnSplashResponse;
import com.github.florent37.glidepalette.BitmapPalette.Profile;
import com.github.florent37.glidepalette.BitmapPalette.Swatch;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.List;

public class UnSplashAdapter extends RecyclerView.Adapter<UnSplashAdapter.UnSplashViewHolder> {

//    private static final int ITEM = 0;
//    private static final int LOADING = 1;
//    private boolean isLoadingAdded = false;

    private List<UnSplashResponse> mUnSplashResponseList;
    private Context mContext;
    private int rowLayout;

    // Instance of interface
    private SimpleOnItemClickListener mOnItemClickListener;

    /**
     * Key Point : we pass the interface to the constructor to apply clicking events.
     * @param unSplashResponseList
     * @param context
     * @param rowLayout
     * @param onItemClickListener
     */
    public UnSplashAdapter(List<UnSplashResponse> unSplashResponseList, Context context, int rowLayout, SimpleOnItemClickListener onItemClickListener) {
        mUnSplashResponseList = unSplashResponseList;
        mContext = context;
        this.rowLayout = rowLayout;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public UnSplashAdapter.UnSplashViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new UnSplashViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnSplashViewHolder unSplashViewHolder, int pos) {

        // Setting the name and numOfLikes for the items of recyclerView.
        unSplashViewHolder.mName.setText(mUnSplashResponseList.get(pos).getUser().getName());
        unSplashViewHolder.mNumOfLikes.setText(String.valueOf(mUnSplashResponseList.get(pos).getNumOfLikes()));

        /**
         * This was used to load only the image
         */
//        Glide.with(mContext)
//                .load(mUnSplashResponseList.get(pos).getUrls().getFullUrl())
//                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
//                .into(unSplashViewHolder.mImageView);

        /**
         * Using this to set the background color of the linearLayout using the
         * Glide Palette library to get the 'Colors' from the images
         * and to apply the 'thumbnail' until image loads.
         * */
        Glide.with(mContext).load(mUnSplashResponseList.get(pos).getUrls().getFullUrl())
                .listener(GlidePalette.with(mUnSplashResponseList.get(pos).getUrls().getFullUrl())
                        .use(Profile.MUTED_DARK)
                        .intoBackground(unSplashViewHolder.mLinearLayout)

                        .use(Profile.MUTED_DARK)
                        .intoTextColor(unSplashViewHolder.mName, Swatch.BODY_TEXT_COLOR)
                        .intoTextColor(unSplashViewHolder.mNumOfLikes, Swatch.BODY_TEXT_COLOR)
                        .crossfade(true)
                )
                .thumbnail(0.1f)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .override(800, 800)
                )
                .into(unSplashViewHolder.mImageView);


    }

    @Override
    public int getItemCount() {
        return mUnSplashResponseList.size();
    }



//    // --------------------------------------
//    public void add(UnSplashResponse mUSR) {
//        mUnSplashResponseList.add(mUSR);
//        // Position - where we should add data
//        notifyItemInserted(mUnSplashResponseList.size() -1);
//    }
//
//    public void addALL(List<UnSplashResponse> mUSR) {
//        for(UnSplashResponse usr : mUSR) {
//            add(usr);
//        }
//    }
//
//    public void remove(UnSplashResponse mUSR) {
//        int position = mUnSplashResponseList.indexOf(mUSR);
//        if (position > -1) {
//            mUnSplashResponseList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public void clear() {
//        isLoadingAdded = false;
//        while (getItemCount() > 0) {
//            remove(getItem(0));
//        }
//    }
//
//    public boolean isEmpty() {
//        return getItemCount() == 0;
//    }
//
//    public void addLoadingFooter() {
//        isLoadingAdded = true;
//        add(new UnSplashResponse());
//    }
//
//    public void removeLoadingFooter() {
//        isLoadingAdded = false;
//
//        int position = mUnSplashResponseList.size() - 1;
//        UnSplashResponse item = getItem(position);
//
//        if (item != null) {
//            mUnSplashResponseList.remove(position);
//            notifyItemRemoved(position);
//        }
//    }
//
//    public UnSplashResponse getItem(int position) {
//        return mUnSplashResponseList.get(position);
//    }
//
//    // -------------------------------


    /**
     * Inner class
     * Viewholder for the single items per row.
     */
    public class UnSplashViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mName;
        private TextView mNumOfLikes;
        private ImageView mImageView;
        private LinearLayout mLinearLayout;

        public UnSplashViewHolder(@NonNull View itemView) {
            super(itemView);

            mLinearLayout = itemView.findViewById(R.id.row_content_bg);
            mImageView = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.user_name);
            mNumOfLikes = itemView.findViewById(R.id.num_of_likes);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Implementation of the interface.
            mOnItemClickListener.onClick(v, getAdapterPosition());
        }
    }


}
