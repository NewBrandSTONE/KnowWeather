package com.silencedut.knowweather.ui.recyclerview;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * 基础封装，实现RecyclerView的侧滑删除
 *
 * @author gaohuang_yangzi@dahuatech.com
 * @version 2018/3/15
 */

public class OzItemTouchHepler extends ItemTouchHelper.Callback {

    private OnItemTouchCallbackListener mListener;

    /**
     * 是否可以拖拽
     */
    private boolean isDrag = false;

    /**
     * 是否可以被滑动
     */
    private boolean isSwipe = false;

    public OzItemTouchHepler(OnItemTouchCallbackListener listener) {
        this.mListener = listener;
    }

    public void setmListener(OnItemTouchCallbackListener mListener) {
        this.mListener = mListener;
    }

    public void setItemDrag(boolean drag) {
        isDrag = drag;
    }

    public void setItemSwipe(boolean swipe) {
        isSwipe = swipe;
    }

    public boolean itemDrag() {
        return isDrag;
    }

    public boolean itemSwipe() {
        return isSwipe;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // GridLayoutManager
            // flag如果值是0，相当于这个功能被关闭
            int dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlag = 0;
            // create make
            return makeMovementFlags(dragFlag, swipeFlag);
        } else if (layoutManager instanceof LinearLayoutManager) {
            // LinearLayoutManager
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int orientation = linearLayoutManager.getOrientation();
            int dragFlag = 0;
            int swipeFlag = 0;
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                swipeFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                dragFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            } else if (orientation == LinearLayoutManager.VERTICAL) {
                dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                swipeFlag = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            }
            return makeMovementFlags(dragFlag, swipeFlag);
        }
        return 0;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (mListener != null) {
            return mListener.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        }
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (mListener != null) {
            mListener.onSwiped(viewHolder.getAdapterPosition());
        }
    }

    public interface OnItemTouchCallbackListener {
        /**
         * 当某个Item被滑动删除的时候
         *
         * @param adapterPosition item的位置
         */
        void onSwiped(int adapterPosition);

        /**
         * 当两个Item位置互换的时候被回调
         *
         * @param srcPosition    拖拽的item的position
         * @param targetPosition 目的地的Item的position
         * @return 开发者处理了操作应该返回true，开发者没有处理就返回false
         */
        boolean onMove(int srcPosition, int targetPosition);
    }
}
