package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by 侯延旭 on 2018/7/12.
 */
public class WHImageView extends android.support.v7.widget.AppCompatImageView {
    public WHImageView(Context context) {
        super(context);
    }

    public WHImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WHImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.max(sizeH,sizeW),Math.max(sizeH,sizeW));
    }
}
