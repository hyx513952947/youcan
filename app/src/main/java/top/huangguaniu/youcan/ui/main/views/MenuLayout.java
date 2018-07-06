package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import top.huangguaniu.youcan.R;

/**
 *
 * @author 侯延旭
 * @date 2018/7/1
 */
public class MenuLayout extends LinearLayout implements DragRightFrameLayout.OnMenuOffsetListener,View.OnClickListener {

    public MenuLayout(Context context) {
        super(context);
    }
    private float defaultX = -1;
    private int mGravity = Gravity.NO_GRAVITY;
    private int selectItemIndex = -1;
    public MenuLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MenuLayout);
        mGravity = typedArray.getInt(R.styleable.MenuLayout_android_layout_gravity,Gravity.NO_GRAVITY);
        typedArray.recycle();
        initClick();
    }

    private void initClick() {
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                for (int i = 0;i< getChildCount();i++){
                    if (getChildAt(i) instanceof IMenuItemView){
                        if (selectItemIndex == -1){
                            selectItemIndex = i;
                        }
                        getChildAt(i).setOnClickListener(MenuLayout.this);
                        getChildAt(i).setTag(i);
                        getChildAt(i).setSelected(i == selectItemIndex);
                    }
                }
            }
        });

    }

    public MenuLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private float offsetScale = 0.6f;

    @Override
    public void onOffset(float offset) {
        for (int i = 0;i<getChildCount();i++){
            View child = getChildAt(i);
            if (child instanceof IMenuItemView){
                int childWidth = child.getMeasuredWidth();
                float xoff = - (1-offset)*offsetScale*i*childWidth;
                child.setX(xoff+defaultX);
                if (child.isSelected()){
                    selectItemIndex = i;
                }
            }
            if (child instanceof DragRightFrameLayout.OnMenuOffsetListener){
                DragRightFrameLayout.OnMenuOffsetListener onMenuOffsetListener = (DragRightFrameLayout.OnMenuOffsetListener) child;
                onMenuOffsetListener.onOffset(offset);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mGravity == Gravity.CENTER_HORIZONTAL && getChildCount() != 0){
            View child = getChildAt(1);
            if (child instanceof IMenuItemView){
                int width = MeasureSpec.getSize(widthMeasureSpec);
                int childWidth = child.getMeasuredWidth();
                defaultX = (width-childWidth)/2;
            }
        }
    }

    private void log(String msg){
        Log.i("MenuLayout",msg);
    }

    @Override
    public void onClick(View v) {
        if (null != onMenuItemClickListener){
            onMenuItemClickListener.onItemClick(v);
        }
        if (v.getTag() != null){
            int index = (int) v.getTag();
            if (index != selectItemIndex){
                notifyAllChildState(index);
            }
        }
    }

    private void notifyAllChildState(int index){
        for (int i =0;i<getChildCount();i++){
            View child = getChildAt(i);
            if (child instanceof IMenuItemView){
                child.setSelected((int)child.getTag() == index);
            }
        }
        selectItemIndex = index;
    }

    private OnMenuItemClickListener onMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    public interface OnMenuItemClickListener{
        void onItemClick(View v);
    }
}
