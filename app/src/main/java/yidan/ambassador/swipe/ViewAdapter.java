package yidan.ambassador.swipe;

/**
 * Created by xyd on 16/4/14.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

/**
 * Interface for a given UI element to help extend the swipe-to-dismiss-undo pattern to other
 * elements.
 */
public interface ViewAdapter {
    Context getContext();
    int getWidth();
    int getChildCount();
    void getLocationOnScreen(int[] locations);
    View getChildAt(int index);
    int getChildPosition(View position);
    void requestDisallowInterceptTouchEvent(boolean disallowIntercept);
    void onTouchEvent(MotionEvent e);
    Object makeScrollListener(AbsListView.OnScrollListener listener);


}

