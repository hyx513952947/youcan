package top.huangguaniu.youcan.ui.main.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import top.huangguaniu.youcan.R;

import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * @author 侯延旭
 * @date 2018/6/29
 */
public class DragRightFrameLayout extends FrameLayout {

    private int sizeMenuWidth = 180;
    private String TAG = "DragRightFramelayout";
    private boolean mMenuOpened = false;
    private float[] mLastDown = new float[2];
    private float[] mLastMove = new float[2];
    private Scroller mScroller = null;

    private boolean isEdgeTouch = false;
    private int mActivePointerId = INVALID_POINTER;
    private int mTouchSlop = 20;
    private int mEdgeSlop = 20;

    private VelocityTracker mVelocityTracker;
    private int mMinimumVelocity;
    private int mMaximumVelocity;

    private OnMenuOffsetListener onMenuOffsetListener;

    public DragRightFrameLayout(@NonNull Context context) {
        super(context);
    }

    public DragRightFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DragRightFrameLayout);
        init();
        array.recycle();
    }

    public DragRightFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public void setOnMenuOffsetListener(OnMenuOffsetListener onMenuOffsetListener) {
        this.onMenuOffsetListener = onMenuOffsetListener;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int count = getChildCount();
        int parentLeft = getPaddingLeft();
        int parentRight = getPaddingRight();
        int parentTop = getPaddingTop();
        int parentBottom = getPaddingBottom();
        if (!changed) {
            return;
        }
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) child.getLayoutParams();
            int childLeft = 0;
            int childTop = 0;
            int childRight = 0;
            int childBottom = 0;
            if (0 == i) {
                childLeft = -(parentLeft + layoutParams.leftMargin + parentRight + layoutParams.rightMargin + layoutParams.width);
                childTop = parentTop + layoutParams.topMargin;
                childRight = 0;
                childBottom = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin - parentBottom;
            } else {
                childLeft = layoutParams.leftMargin;
                childTop = parentTop + layoutParams.topMargin;
                childRight = child.getMeasuredWidth();
                childBottom = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin - parentBottom;
                log(childLeft+"--"+childTop+"--"+childRight+"--"+childBottom);
            }
            child.layout(childLeft, childTop, childRight, childBottom);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = Math.min(viewConfiguration.getScaledPagingTouchSlop(), mTouchSlop);
        mEdgeSlop = Math.min(viewConfiguration.getScaledEdgeSlop(), mEdgeSlop);
        mScroller = new Scroller(getContext());
        float density = getContext().getResources().getDisplayMetrics().density;
        mMinimumVelocity = (int) (400 * density);
        mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean touch = super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = event.getPointerId(0);
                mLastMove[0] = event.getX(event.findPointerIndex(mActivePointerId));
                mLastMove[1] = event.getY(event.findPointerIndex(mActivePointerId));
                if (event.getX(event.findPointerIndex(mActivePointerId)) > sizeMenuWidth && mMenuOpened) {
                    touch = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int pointerIndex = event.findPointerIndex(mActivePointerId);
                if (isEdgeTouch || mMenuOpened) {
                    int offX = (int) (event.getX(pointerIndex) - mLastMove[0]);
                    if (Math.abs(getScrollX() - offX) > sizeMenuWidth) {
                        offX = sizeMenuWidth + getScrollX();
                    }
                    if (getScrollX() - offX <= 0) {
                        scrollBy(-offX, 0);
                    }
                }
                mLastMove[0] = event.getX(pointerIndex);
                mLastMove[1] = event.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_UP:
                if (getScrollX() != 0 && sizeMenuWidth <= Math.abs(getScrollX())) {
                    mMenuOpened = true;
                }
                if (mMenuOpened && mLastDown[0] > sizeMenuWidth) {
                    closeMenu();
                }
                mVelocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                if (!mMenuOpened) {
                    float currentVX = (int) mVelocityTracker.getXVelocity(mActivePointerId);
                    if (currentVX > 0 && currentVX > mMinimumVelocity && Math.abs(getScrollX()) != sizeMenuWidth) {
                        float xt = sizeMenuWidth - Math.abs(getScrollX());
                        float duration = xt * 1000 / currentVX;
                        int cy = mScroller.getCurrY();
                        mScroller.abortAnimation();
                        int dx = sizeMenuWidth + Math.min(0, getScrollX());
                        int dy = 0 - cy;
                        mScroller.startScroll(getScrollX(), cy, -dx, dy, (int) duration);
                        ViewCompat.postInvalidateOnAnimation(this);
                        mMenuOpened = true;
                        if (null != mVelocityTracker) {
                            mVelocityTracker.clear();
                            mVelocityTracker.recycle();
                            mVelocityTracker = null;
                        }
                    } else {
                        closeMenu();
                    }
                }

                break;
            case MotionEvent.ACTION_POINTER_DOWN: {
                int index = event.getActionIndex();
                mActivePointerId = event.getPointerId(index);
                break;
            }
            case MotionEvent.ACTION_POINTER_UP:
                mLastMove[0] = event.getX(event.findPointerIndex(mActivePointerId));
                mLastMove[1] = event.getY(event.findPointerIndex(mActivePointerId));
                onSecondaryPointerUp(event);
                break;

        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        return touch;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != onMenuOffsetListener) {
            onMenuOffsetListener.onOffset(l * 1f / sizeMenuWidth);
        }
        notifyChildMove(this, l * 1f / sizeMenuWidth);
    }

    public void closeMenu() {
        mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 500);
        invalidate();
        mMenuOpened = false;
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        final int pointerIndex = ev.getActionIndex();
        final int pointerId = ev.getPointerId(pointerIndex);
        if (pointerId == mActivePointerId) {
            final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            mLastMove[0] = ev.getX(newPointerIndex);
            mLastMove[1] = ev.getX(newPointerIndex);
            mActivePointerId = ev.getPointerId(newPointerIndex);
            if (mVelocityTracker != null) {
                mVelocityTracker.clear();
            }
        }
    }

    private void notifyChildMove(ViewGroup root, float offset) {
        for (int i = 0;i<root.getChildCount();i++){
            View child = getChildAt(i);
            if (child instanceof OnMenuOffsetListener) {
                OnMenuOffsetListener listener = (OnMenuOffsetListener) child;
                listener.onOffset(-offset);
            }
        }
    }

    public interface OnMenuOffsetListener {
        void onOffset(float offset);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(ev);
        final int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mActivePointerId = ev.getPointerId(0);
                mLastDown[0] = ev.getX();
                mLastDown[1] = ev.getY();
                isEdgeTouch = mLastDown[0] < mEdgeSlop;
                if (ev.getX() > sizeMenuWidth && mMenuOpened) {
                    intercept = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int pointIndex = ev.findPointerIndex(mActivePointerId);
                mLastMove[0] = ev.getX(pointIndex);
                mLastMove[1] = ev.getY(pointIndex);
                int xx = (int) Math.abs(mLastMove[0] - mLastDown[0]);
                if (xx > mTouchSlop && isEdgeTouch && !mMenuOpened) {
                    intercept = true;
                }
                if (intercept) {
                    mLastDown[0] = mTouchSlop + mLastMove[0];
                    mLastDown[1] = mTouchSlop + mLastMove[1];
                }
                break;
            case MotionEvent.ACTION_POINTER_UP:
                onSecondaryPointerUp(ev);
                break;
        }

        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(ev);
        return intercept;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        if (childCount < 2) {
            throw new NullPointerException("视图的子view个数不对！！！：当前个数：" + childCount);
        }
        int reqMaxWidth = 0;
        int reqMaxHeight = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            childView.setClickable(true);
            if (childView.getVisibility() != GONE) {
                measureChild(childView, widthMeasureSpec, heightMeasureSpec);
                FrameLayout.LayoutParams layoutParams = (LayoutParams) childView.getLayoutParams();
                reqMaxHeight = Math.max(reqMaxHeight, layoutParams.height + layoutParams.bottomMargin + layoutParams.topMargin);
                reqMaxWidth += layoutParams.width + layoutParams.leftMargin + layoutParams.rightMargin;
            }
        }

        reqMaxHeight = Math.max(reqMaxHeight, heightSize);

        if (null != getChildAt(0) && getChildAt(0).getVisibility() != GONE) {
            View menuView = getChildAt(0);
            LayoutParams layoutParams = (LayoutParams) menuView.getLayoutParams();
            int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
            int childHeightMeasureSpec = getChildMeasureSpec(heightMeasureSpec, getPaddingTop() + getPaddingBottom(), layoutParams.height);

            if (layoutParams.width == LayoutParams.WRAP_CONTENT || layoutParams.width == LayoutParams.MATCH_PARENT) {
                childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeMenuWidth, MeasureSpec.EXACTLY);
            } else {
                sizeMenuWidth = Math.max(layoutParams.width, sizeMenuWidth);
            }
            if (layoutParams.height == LayoutParams.WRAP_CONTENT || layoutParams.width == LayoutParams.MATCH_PARENT) {
                childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(reqMaxHeight, MeasureSpec.EXACTLY);
            }
            menuView.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }

        reqMaxWidth = Math.max(reqMaxWidth, widthSize) + sizeMenuWidth;
        setMeasuredDimension(reqMaxWidth, reqMaxHeight);
    }


    private void log(String msg) {
        Log.i(TAG, msg);
    }
}
