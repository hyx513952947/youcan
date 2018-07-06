package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.nio.channels.Selector;

import top.huangguaniu.youcan.R;

/**
 *
 * @author 侯延旭
 * @date 2018/7/4
 */
public class PopMenuButton extends View {

    public PopMenuButton(Context context) {
        super(context);
    }

    private int color;
    private int colorOpen;

    private Bitmap src;
    private Bitmap srcOpen;

    private int srcId,srcOpenId;

    private Paint mPaint;
    public PopMenuButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.PopMenuButton);
        srcId = array.getResourceId(R.styleable.PopMenuButton_src_default,R.drawable.icon_note_add);
        src = BitmapFactory.decodeResource(getResources(),srcId);
        srcOpenId = array.getResourceId(R.styleable.PopMenuButton_src_open,R.drawable.icon_note_add_selected);
        srcOpen =BitmapFactory.decodeResource(getResources(),srcOpenId);
        color = array.getColor(R.styleable.PopMenuButton_color_default,Color.parseColor("#f900e0d9"));
        colorOpen = array.getColor(R.styleable.PopMenuButton_color_open,Color.BLUE);
        array.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(color);
        srcRectF = new Rect(0,0,src.getWidth(),src.getHeight());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    public PopMenuButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isSelected()){
            mPaint.setColor(colorOpen);
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-4,mPaint);
            canvas.drawBitmap(srcOpen,srcRectF,tarRectF,mPaint);
        }else {
            mPaint.setColor(color);
            canvas.drawCircle(getWidth()/2,getHeight()/2,getWidth()/2-4,mPaint);
            canvas.drawBitmap(src,srcRectF,tarRectF,mPaint);
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected && srcOpen == null){
            srcOpen =BitmapFactory.decodeResource(getResources(),srcOpenId);
        }else if (src == null){
            src = BitmapFactory.decodeResource(getResources(),srcId);
        }
    }

    public int getColor() {
        return colorOpen;
    }

    private int xBitmap;
    private int yBitmap;
    private Rect tarRectF,srcRectF;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        Logger.i(sizeW+"---"+sizeH);
        xBitmap = sizeW/4;
        yBitmap = sizeH/4;
        if (null == tarRectF){
            tarRectF = new Rect(xBitmap,yBitmap,sizeW*3/4,sizeH*3/4);
        }
    }
}
