package top.huangguaniu.youcan.ui.main.views.editcomponent;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import top.huangguaniu.youcan.R;

/**
 * Created by 侯延旭 on 2018/7/10.
 */
public class TextViewColorPoint extends AppCompatTextView {
    public TextViewColorPoint(Context context) {
        super(context);
    }
    DisplayMetrics displayMetrics = new DisplayMetrics();
    private Paint paint;
    private RectF rect;
    public TextViewColorPoint(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPadding(30,0,0,0);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextViewColorPoint);
        paint.setColor(typedArray.getColor(R.styleable.TextViewColorPoint_color_point,Color.RED));
        typedArray.recycle();
    }

    public TextViewColorPoint(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(rect,10,10,paint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        getDisplay().getMetrics(displayMetrics);
        rect = new RectF(0,0,5*displayMetrics.density,getMeasuredHeight());
    }
}
