package com.example.ansh.spacefeed;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.pojos.UnSplashResponse;

import java.util.ArrayList;
import java.util.List;

public class UnSplashAdapter extends RecyclerView.Adapter<UnSplashAdapter.UnSplashViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private List<UnSplashResponse> mUnSplashResponseList;
    private Context mContext;
    private int rowLayout;

    public UnSplashAdapter(List<UnSplashResponse> unSplashResponseList, Context context, int rowLayout) {
        mUnSplashResponseList = unSplashResponseList;
        mContext = context;
        this.rowLayout = rowLayout;
    }

    @NonNull
    @Override
    public UnSplashAdapter.UnSplashViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(rowLayout, viewGroup, false);

        return new UnSplashViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnSplashViewHolder unSplashViewHolder, int pos) {
        unSplashViewHolder.mName.setText(mUnSplashResponseList.get(pos).getUser().getName());
        unSplashViewHolder.mNumOfLikes.setText(String.valueOf(mUnSplashResponseList.get(pos).getNumOfLikes()));

        Glide.with(mContext)
                .load(mUnSplashResponseList.get(pos).getUrls().getFullUrl())
                .apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
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
     * VIEWHOLDER
     */
    public static class UnSplashViewHolder extends RecyclerView.ViewHolder {

        private TextView mName;
        private TextView mNumOfLikes;
        private ImageView mImageView;

        public UnSplashViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.user_name);
            mNumOfLikes = itemView.findViewById(R.id.num_of_likes);
        }
    }


}
