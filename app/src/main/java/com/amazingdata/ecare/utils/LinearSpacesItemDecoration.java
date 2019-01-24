package com.amazingdata.ecare.utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Xiong
 * @date 2019/1/24 - 11:16
 */
// 偷来的RecycleView的Linearlayout的item间距辅助类
public class LinearSpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public LinearSpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.top = space;
    }
}