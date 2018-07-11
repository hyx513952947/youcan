package top.huangguaniu.youcan.ui.main.views.labels;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * Created by 侯延旭 on 2018/7/10.
 */
public class LabelViewGroup extends ViewGroup implements Label.OnDeleteClickListener,Label.OnTouchClickListener{
    // 水平间距，单位为dp
    private int horizontalSpacing = dp2px(15);
    // 垂直间距，单位为dp
    private int verticalSpacing = dp2px(15);
    // 行的集合
    private List<Line> lines = new ArrayList<Line>();
    // 当前的行
    private Line line;
    // 当前行使用的空间
    private int lineSize = 0;
    // 关键字大小，单位为sp
    private int textSize = sp2px(15);
    // 关键字颜色
    private int textColor = Color.BLACK;
    // 关键字背景
    private int backgroundResource = R.drawable.icon_round;
    // 关键字水平padding，单位为dp
    private int textPaddingH = dp2px(15);
    // 关键字垂直padding，单位为dp
    private int textPaddingV = dp2px(8);
    public LabelViewGroup(Context context) {
        super(context);
    }

    public LabelViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LabelViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            line.layout(left, top);
            top = top + line.getHeight() + verticalSpacing;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft() - getPaddingRight();
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        restoreLine();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child instanceof Label){
                Label label = (Label) child;
                label.setOnDeleteClickListener(this);
                label.setOnTouchClickListener(this);
            }
            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(width,
                    widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                    heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode);
            child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
            if (line == null) {
                line = new Line();
            }
            int measuredWidth = child.getMeasuredWidth();
            lineSize += measuredWidth;
            if (lineSize <= width) {
                line.addChild(child);
                lineSize += horizontalSpacing;
            } else {
                newLine();
                line.addChild(child);
                lineSize += child.getMeasuredWidth();
                lineSize += horizontalSpacing;
            }
        }
        if (line != null && !lines.contains(line)) {
            lines.add(line);
        }
        int totalHeight = 0;
        for (int i = 0; i < lines.size(); i++) {
            totalHeight += lines.get(i).getHeight();
        }
        totalHeight += verticalSpacing * (lines.size() - 1);
        totalHeight += getPaddingBottom();
        totalHeight += getPaddingTop();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),
                resolveSize(totalHeight, heightMeasureSpec));
    }

    private void restoreLine() {
        lines.clear();
        line = new Line();
        lineSize = 0;
    }

    private void newLine() {
        if (line != null) {
            lines.add(line);
        }
        line = new Line();
        lineSize = 0;
    }

    @Override
    public void onClick(Label view) {
        Logger.i("容器点击标签："+view.getTitle());
    }

    @Override
    public void onDelete(Label view) {
        Logger.i("容器删除标签："+view.getTitle());
        removeView(view);
    }

    class Line {
        private List<View> children = new ArrayList<View>();
        int height;
        public void addChild(View childView) {
            children.add(childView);
            if (height < childView.getMeasuredHeight()) {
                height = childView.getMeasuredHeight();
            }
        }
        public void layout(int left, int top) {
            int totalWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            int currentLeft = left;

            for (int i = 0; i < children.size(); i++) {
                View view = children.get(i);
                // 设置childView的绘制区域
                view.layout(currentLeft, top, currentLeft + view.getMeasuredWidth(),
                        top + view.getMeasuredHeight());
                // 计算下一个childView的位置
                currentLeft = currentLeft + view.getMeasuredWidth() + horizontalSpacing;
            }
        }

        public int getHeight() {
            return height;
        }

        public int getChildCount() {
            return children.size();
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String content);
    }

    /**
     * 设置文字水平间距
     *
     * @param horizontalSpacing 间距/dp
     */
    public void setHorizontalSpacing(int horizontalSpacing) {
        this.horizontalSpacing = dp2px(horizontalSpacing);
    }

    /**
     * 设置文字垂直间距
     *
     * @param verticalSpacing 间距/dp
     */
    public void setVerticalSpacing(int verticalSpacing) {
        this.verticalSpacing = dp2px(verticalSpacing);
    }

    /**
     * 设置文字大小
     *
     * @param textSize 文字大小/sp
     */
    public void setTextSize(int textSize) {
        this.textSize = sp2px(textSize);
    }

    /**
     * 设置文字颜色
     *
     * @param textColor 文字颜色
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置文字背景
     *
     * @param backgroundResource 文字背景
     */
    @Override
    public void setBackgroundResource(int backgroundResource) {
        this.backgroundResource = backgroundResource;
    }

    /**
     * 设置文字水平padding
     *
     * @param textPaddingH padding/dp
     */
    public void setTextPaddingH(int textPaddingH) {
        this.textPaddingH = dp2px(textPaddingH);
    }

    /**
     * 设置文字垂直padding
     *
     * @param textPaddingV padding/dp
     */
    public void setTextPaddingV(int textPaddingV) {
        this.textPaddingV = dp2px(textPaddingV);
    }

    /**
     * dp转px
     *
     * @param dp dp值
     * @return px值
     */
    private int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param sp sp值
     * @return px值
     */
    private int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                getResources().getDisplayMetrics());
    }
}
