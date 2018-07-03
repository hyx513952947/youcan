package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;

import top.huangguaniu.youcan.R;

/**
 * Created by 侯延旭 on 2018/7/1.
 */
public class DragMenuItemView extends View implements IMenuItemView{

    public DragMenuItemView(Context context) {
        super(context);
    }

    public DragMenuItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (null == mPaint) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DragMenuItemView);

        int defaultTextColor = Color.parseColor("#000000");
        int defaultTextSelectColor = Color.parseColor("#ffffff");
        int defaultDrawable = R.mipmap.ic_launcher;
        int defaultUnDrawable = R.mipmap.ic_launcher;

        mTextColor = array.getColor(R.styleable.DragMenuItemView_textColor, defaultTextColor);
        mSelectTextColor = array.getColor(R.styleable.DragMenuItemView_textSelectColor, defaultTextSelectColor);
        mUnSelectDrawable = array.getResourceId(R.styleable.DragMenuItemView_unSelectDrawable, defaultUnDrawable);
        mSelectDrawable = array.getResourceId(R.styleable.DragMenuItemView_selectDrawable, defaultDrawable);
        mTitle = array.getString(R.styleable.DragMenuItemView_title);
        mTextSize = array.getDimensionPixelSize(R.styleable.DragMenuItemView_android_textSize,mTextSize);
        array.recycle();
    }

    public DragMenuItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    private Paint mPaint = null;
    private int mSelectDrawable = -1;
    private int mUnSelectDrawable = -1;
    private int mTextColor = -1;
    private int mSelectTextColor = -1;
    private String mTitle = "";
    private int mTextSize = 20;
    private Rect mDrawableRect = new Rect();

    private float mTextX,mTextY;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBitmap(canvas);
        drawText(canvas);
    }


    private void drawText(Canvas canvas) {
        if (null == mTitle){
            return;
        }
        if (isSelected()){
            mPaint.setColor(mSelectTextColor);
        }else {
            mPaint.setColor(mTextColor);
        }
        canvas.drawText(mTitle,mTextX,mTextY,mPaint);
    }

    private void drawBitmap(Canvas canvas) {
        Bitmap bitmap;
        if (isSelected()) {
            bitmap = BitmapFactory.decodeResource(getResources(), mSelectDrawable);
        } else {
            bitmap = BitmapFactory.decodeResource(getResources(), mUnSelectDrawable);
        }
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Rect rect = new Rect(0, 0, w, h);
        canvas.drawBitmap(bitmap, rect, mDrawableRect, mPaint);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        sizeWidth = Math.min(sizeHeight, sizeWidth);
        setMeasuredDimension(sizeWidth, sizeWidth);

        mDrawableRect.left = sizeWidth / 3;
        mDrawableRect.top = sizeWidth / 3 - 20;
        mDrawableRect.right = sizeWidth * 2 / 3;
        mDrawableRect.bottom = sizeWidth * 2 / 3 - 20;

        if (null != mTitle) {
            mPaint.setTextSize(mTextSize);
            float textWidth = mPaint.measureText(mTitle);
            mTextX = (getMeasuredWidth()- textWidth)/2;
            mTextY = getMeasuredHeight()*2/3+mTextSize;
        }
    }

    private void log(String msg) {
        Log.i("DragMenuItemView", msg);
    }
}
