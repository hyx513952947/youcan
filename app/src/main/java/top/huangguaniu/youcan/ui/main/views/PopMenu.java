package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 *
 * @author 侯延旭
 * @date 2018/7/4
 */
public class PopMenu extends FrameLayout {
    public PopMenu(@NonNull Context context) {
        super(context);
    }

    public PopMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    public PopMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    DisplayMetrics displayMetrics = new DisplayMetrics();
    private int childNormalLeft, childNormalRight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    }
}
