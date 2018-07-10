package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by 侯延旭 on 2018/7/10.
 */
public class EditorEditTextView extends AppCompatEditText {
    public EditorEditTextView(Context context) {
        super(context);
    }

    public EditorEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EditorEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                log("落下");
                break;
            case MotionEvent.ACTION_MOVE:
                log("移动");
                break;
            case MotionEvent.ACTION_UP:
                log("抬起");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        log("是否消费了："+ super.dispatchTouchEvent(event));
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onScrollChanged(int horiz, int vert, int oldHoriz, int oldVert) {
        super.onScrollChanged(horiz, vert, oldHoriz, oldVert);
        log("滚动了");
    }

    private void log(String info) {
        Log.i("输入控件:", info);
    }
}
