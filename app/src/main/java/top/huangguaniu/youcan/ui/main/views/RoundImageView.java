package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import top.huangguaniu.youcan.R;

/**
 *  圆角图片
 * @author 侯延旭
 * @date 2018/7/3
 */
public class RoundImageView extends AppCompatImageView {
    public RoundImageView(Context context) {
        super(context);
    }

    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        array.recycle();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }
    private Paint mPaint;
    @Override
    protected void onDraw(Canvas canvas) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        Logger.i("宽高："+sizeWidth+"--"+sizeHeight);
    }
}
