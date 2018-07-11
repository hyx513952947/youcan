package top.huangguaniu.youcan.ui.main.views.labels;

import android.annotation.SuppressLint;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import top.huangguaniu.youcan.R;

/**
 * Created by 侯延旭 on 2018/7/11.
 */
public class Label extends View {
    private boolean leftEnable = false;
    private boolean rightEnable = false;
    private float textSize;
    private int textColor;
    private Bitmap iconLeft, iconRight;
    private String title = "";
    private int textPadding;

    public boolean isLeftEnable() {
        return leftEnable;
    }

    public void setLeftEnable(boolean leftEnable) {
        this.leftEnable = leftEnable;
    }

    public boolean isRightEnable() {
        return rightEnable;
    }

    public void setRightEnable(boolean rightEnable) {
        this.rightEnable = rightEnable;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        paint.setColor(textColor);
        postInvalidate();
    }

    public Bitmap getIconLeft() {
        return iconLeft;
    }

    public void setIconLeft(Bitmap iconLeft) {
        this.iconLeft = iconLeft;
        postInvalidate();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        postInvalidate();
    }

    private Rect rectSrcLeft, rectSrcRight;
    private RectF rectLeftDst, rectRightDst;
    private int mSlopScroll;
    private Paint paint;

    public Label(Context context) {
        this(context, null);
    }

    public Label(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Label);

        leftEnable = a.getBoolean(R.styleable.Label_left_enable, false);
        rightEnable = a.getBoolean(R.styleable.Label_right_enable, false);

        textSize = a.getDimensionPixelSize(R.styleable.Label_text_size, 20);
        textColor = a.getColor(R.styleable.Label_text_color, Color.BLACK);
        textPadding = a.getDimensionPixelSize(R.styleable.Label_text_padding, 10);
        if (leftEnable) {
            int srcLeft = a.getResourceId(R.styleable.Label_src_left, R.drawable.icon_note_add);
            iconLeft = BitmapFactory.decodeResource(getResources(), srcLeft);
            rectSrcLeft = new Rect(0, 0, iconLeft.getWidth(), iconLeft.getHeight());
        }
        if (rightEnable) {
            int srcRight = a.getResourceId(R.styleable.Label_src_right, R.drawable.icon_delete);
            iconRight = BitmapFactory.decodeResource(getResources(), srcRight);
            rectSrcRight = new Rect(0, 0, iconRight.getWidth(), iconRight.getHeight());
        }
        title = a.getString(R.styleable.Label_text);

        a.recycle();

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(textColor);
        paint.setTextSize(textSize);

        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mSlopScroll = viewConfiguration.getScaledTouchSlop();
    }

    public Label(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int textX;
        if (leftEnable) {
            canvas.drawBitmap(iconLeft, rectSrcLeft, rectLeftDst, paint);
            textX = (int) (textPadding / 4 + rectLeftDst.right + getPaddingLeft());
        } else {
            textX = textPadding / 4;
        }
        if (rightEnable) {
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawBitmap(iconRight, rectSrcRight, rectRightDst, paint);
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float fontHeight = fontMetrics.bottom - fontMetrics.top;
        float textBaseY = getMeasuredHeight() - (getMeasuredHeight() - fontHeight) / 2 - fontMetrics.bottom;
        canvas.drawText(title, textX, textBaseY, paint);
    }

    private float xDown, yDown, xLast, yLast;
    private boolean touchDelete = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getX();
                yDown = event.getY();
                if (xDown >= rectRightDst.left && xDown <= rectRightDst.right
                        && yDown >= rectRightDst.top && yDown <= rectRightDst.bottom) {
                    touchDelete = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                xLast = event.getX();
                yLast = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (Math.abs(xLast - xDown) <= mSlopScroll && Math.abs(yDown - yLast) <= mSlopScroll) {
                    if (touchDelete){
                        if (null != onDeleteClickListener){
                            onDeleteClickListener.onDelete(this);
                        }
                    }else {
                        if (null != onTouchClickListener){
                            onTouchClickListener.onClick(this);
                        }
                    }
                }
                touchDelete = false;
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);
        int sizeW,sizeH;
        sizeW = (int) paint.measureText(title);
        sizeH = (int) textSize;
        boolean haveMeaLeft = false;
        if (leftEnable) {
            haveMeaLeft = true;
            sizeH += textPadding * 2;
            sizeW += (textPadding / 2 + sizeH);
            if (rectLeftDst == null) {
                rectLeftDst = new RectF(getPaddingLeft(), getPaddingTop(), sizeH - getPaddingRight(), sizeH - getPaddingBottom());
            }
        }
        if (rightEnable) {
            if (haveMeaLeft) {
                sizeW += sizeH;
            } else {
                sizeH += textPadding * 2;
                sizeW += (textPadding / 2 + sizeH);
            }
            if (rectRightDst == null) {
                rectRightDst = new RectF(sizeW - sizeH + getPaddingRight(), getPaddingTop(), sizeW - getPaddingRight(), sizeH - getPaddingBottom());
            }
        }
        int w = MeasureSpec.makeMeasureSpec(sizeW, modeW);
        int h = MeasureSpec.makeMeasureSpec(sizeH, modeH);
        setMeasuredDimension(w, h);
    }

    public void setOnTouchClickListener(OnTouchClickListener onTouchClickListener) {
        this.onTouchClickListener = onTouchClickListener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    private OnTouchClickListener onTouchClickListener;
    private OnDeleteClickListener onDeleteClickListener;
    public interface OnTouchClickListener{
        /**
         * 点击返回
         * @param view d
         */
        void onClick(Label view);
    }
    public interface OnDeleteClickListener{
        /**删除
         * @param view d
         */
        void onDelete(Label view);
    }
}
