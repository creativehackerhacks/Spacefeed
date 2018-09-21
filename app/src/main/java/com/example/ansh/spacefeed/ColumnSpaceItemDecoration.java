package com.example.ansh.spacefeed;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

public class ColumnSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int mColumnSpace;

    public ColumnSpaceItemDecoration(int columnSpace) {
        this.mColumnSpace = columnSpace;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull State state) {
        int position = parent.getChildAdapterPosition(view); // item position
        int spanCount = 2;
        int spacing = mColumnSpace;//spacing between views in grid

        if (position >= 0) {
            int column = position % spanCount; // item column

            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = 0;
            outRect.right = 0;
            outRect.top = 0;
            outRect.bottom = 0;
        }
    }


}
