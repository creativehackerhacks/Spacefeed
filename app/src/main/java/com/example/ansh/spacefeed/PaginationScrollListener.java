package com.example.ansh.spacefeed;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

    LinearLayoutManager mLayoutManager;

    public PaginationScrollListener(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    // Method to tell if we're scrolling or not
    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    // Method to show the next fetched data + progress bar
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        /**
         * Current scroll status
         * and check if it's last page
         */
        if(!isLoading() && !isLastPage()) {
            if((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
        }
    }

    protected abstract boolean isLoading();
    protected abstract boolean isLastPage();
    protected abstract void loadMoreItems();
    protected abstract int getTotalPageCount();


}
