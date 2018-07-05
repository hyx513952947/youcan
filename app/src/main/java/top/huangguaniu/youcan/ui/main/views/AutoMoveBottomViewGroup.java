package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 能自动显示、隐藏的控件
 * @author 侯延旭
 * @date 2018/7/4
 */
public class AutoMoveBottomViewGroup extends View {

    private Path mPath;
    private float xCircle;
    private float yCircle;
    private float radius;
    private int circleOutBoundWidth = 10;
    private float roundRadius = 10f;
    private float width,height;
    private float heightPercent = 0.4f;
    private float circleHeightPercent = 0.8f;
    private Paint mPaint;

    private Scroller mScroller;
    private VelocityTracker mVelocityTracker;
    private int minSlop;
    private float minVSlop;
    private float maxFlingDistance;
    public AutoMoveBottomViewGroup(Context context) {
        super(context);
    }

    public AutoMoveBottomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        setClickable(true);
        mScroller = new Scroller(context);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        minSlop = viewConfiguration.getScaledPagingTouchSlop();
        minVSlop = viewConfiguration.getScaledMinimumFlingVelocity();
        maxFlingDistance = 1000;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public AutoMoveBottomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.parseColor("#63e2c2"));
        mPaint.setShadowLayer(5,5,5,Color.parseColor("#ed76c0c6"));
        canvas.drawPath(path,mPaint);
        mPaint.setColor(Color.parseColor("#25ccfb"));
        canvas.drawCircle(xCircle,yCircle,radius,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        log("这个有响应咯");
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        return super.onTouchEvent(event);
    }
    private void log(String Info){
        Log.i("滑动隐藏View",Info);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
        xCircle = width/2;
        yCircle = circleHeightPercent*height/2;
        radius = yCircle;
        initPath();
    }

    private void initPath() {
        path = new Path();
        RectF rectF = new RectF(10,height*heightPercent,width-10,height-10);
        path.addRoundRect(rectF,roundRadius,roundRadius,Path.Direction.CCW);
        RectF rectFCircleOutBound = new RectF(xCircle - radius -circleOutBoundWidth,yCircle-radius-circleOutBoundWidth,
                xCircle + radius + circleOutBoundWidth,yCircle+radius+circleOutBoundWidth);
        path.addArc(rectFCircleOutBound,0,180);
    }

    private Path path;
}
