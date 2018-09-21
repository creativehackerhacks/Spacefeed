package com.example.ansh.spacefeed.adapter;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.ansh.spacefeed.R;
import com.example.ansh.spacefeed.interfaces.SimpleOnItemClickListener;
import com.example.ansh.spacefeed.pojos.UnSplashResponse;
import com.github.florent37.glidepalette.BitmapPalette.Profile;
import com.github.florent37.glidepalette.BitmapPalette.Swatch;
import com.github.florent37.glidepalette.GlidePalette;

public class RecyclerViewPagedListAdapter extends PagedListAdapter<UnSplashResponse, RecyclerViewPagedListAdapter.ItemViewHolder> {

    private Context mContext;

    private SimpleOnItemClickListener mListener;

    public RecyclerViewPagedListAdapter(Context mCtx, SimpleOnItemClickListener simpleOnItemClickListener) {
        super(DIFF_CALLBACK);
        this.mContext = mCtx;
        this.mListener = simpleOnItemClickListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        UnSplashResponse item = getItem(position);

        if (item != null) {
            holder.mName.setText(item.getUser().getName());
            holder.mNumOfLikes.setText(String.valueOf(item.getNumOfLikes()));

            Glide.with(mContext).load(item.getUrls().getSmallUrl())
                    .thumbnail(0.1f)
                    .apply(new RequestOptions()
                            .override(400, 400)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                    )
                    .listener(GlidePalette.with(item.getUrls().getSmallUrl())
                            .use(Profile.MUTED_DARK)
                            .intoBackground(holder.mLinearLayout)

                            .use(Profile.MUTED_DARK)
                            .intoTextColor(holder.mName, Swatch.BODY_TEXT_COLOR)
                            .intoTextColor(holder.mNumOfLikes, Swatch.BODY_TEXT_COLOR)
                            .crossfade(true)
                    )
                    .into(holder.mImageView);
        }else{
            Toast.makeText(mContext, "Item is null", Toast.LENGTH_LONG).show();
        }
    }

    private static DiffUtil.ItemCallback<UnSplashResponse> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UnSplashResponse>() {
                @Override
                public boolean areItemsTheSame(UnSplashResponse oldItem, UnSplashResponse newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(UnSplashResponse oldItem, UnSplashResponse newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ItemViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        LinearLayout mLinearLayout;
        ImageView mImageView;
        TextView mName, mNumOfLikes;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mLinearLayout = itemView.findViewById(R.id.row_content_bg);
            mImageView = itemView.findViewById(R.id.image);
            mName = itemView.findViewById(R.id.user_name);
            mNumOfLikes = itemView.findViewById(R.id.num_of_likes);

            mImageView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(v, getAdapterPosition());
        }
    }

}
