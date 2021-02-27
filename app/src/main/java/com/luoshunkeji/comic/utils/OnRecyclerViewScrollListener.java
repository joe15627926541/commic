package com.luoshunkeji.comic.utils;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OnRecyclerViewScrollListener extends RecyclerView.OnScrollListener {



    /**
     * 滑动中回调
     *
     * @param recyclerView 控件对象
     * @param dx           [距离][X轴]
     * @param dy           [距离][Y轴][正数代表向下][负数代表向上]
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    /**
     * 滑动状态发生改变的时候回调
     *
     * @param recyclerView 控件对象
     * @param newState     [状态值][每次滑动时候三个状态都会回调一次][手指拖动(惯性滑动)静止]
     */
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        switch (newState) {
            case RecyclerView.SCROLL_STATE_IDLE:// 静止
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {// 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    int totalItemCount = layoutManager.getItemCount();// 当前RecyclerView的所有子项个数
                    int firstItemPosition = linearLayoutManager.findFirstVisibleItemPosition();// 获取第一个可见view的位置
                    int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();// 获取最后一个可见view的位置
                    View childView = linearLayoutManager.findViewByPosition(firstItemPosition);// 获取显示的第一个 View
                    int childViewWidth= childView.getWidth();
                    int childViewLeft = childView.getLeft();
                    if (Math.abs(childViewLeft) >= childViewWidth / 2.0f) {// 定位下一个
                        int nextPosition = firstItemPosition + 1;
                        smoothMoveToPosition(recyclerView, nextPosition);
                    } else {// 定位当前
                        recyclerView.smoothScrollToPosition(firstItemPosition);
                    }
//                    int childViewHeight = childView.getHeight();
//                    int childViewTop = childView.getTop();
//                    if (Math.abs(childViewTop) >= childViewHeight / 2.0f) {// 定位下一个
//                        int nextPosition = firstItemPosition + 1;
//                        smoothMoveToPosition(recyclerView, nextPosition);
//                    } else {// 定位当前
//                        recyclerView.smoothScrollToPosition(firstItemPosition);
//                    }


                }
                break;
            case RecyclerView.SCROLL_STATE_DRAGGING:// 手指拖动

                break;
            case RecyclerView.SCROLL_STATE_SETTLING:// 惯性滑动

                break;
        }
    }

    /**
     * 缓慢移动到指定的位置
     *
     * @param position
     */
    private void smoothMoveToPosition(RecyclerView recyclerView, int position) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {// 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            int firstItem = linearLayoutManager.findFirstVisibleItemPosition();// 先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
            int lastItem = linearLayoutManager.findLastVisibleItemPosition();
            if (position <= firstItem) {// 当要置顶的项在当前显示的第一个项的前面时
                recyclerView.smoothScrollToPosition(position);
            } else if (position <= lastItem) {// 当要置顶的项已经在屏幕上显示时
                int top = recyclerView.getChildAt(position - firstItem).getLeft();
                recyclerView.smoothScrollBy(top, 0);
            } else {// 当要置顶的项在当前显示的最后一项的后面时
                recyclerView.smoothScrollToPosition(position);
            }
        }
    }

}
