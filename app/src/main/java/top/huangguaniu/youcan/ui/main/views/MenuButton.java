package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by 侯延旭 on 2018/7/2.
 */
public class MenuButton extends android.support.v7.widget.AppCompatImageView implements DragRightFrameLayout.OnMenuOffsetListener{
    public MenuButton(Context context) {
        super(context);
    }

    public MenuButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MenuButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setPivotX(getMeasuredWidth()/2);
        setPivotY(getMeasuredHeight()/2);
    }

    @Override
    public void onOffset(float offset) {
        setRotation(360*offset);
    }
}
