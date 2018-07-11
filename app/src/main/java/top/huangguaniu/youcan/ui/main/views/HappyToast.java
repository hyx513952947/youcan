package top.huangguaniu.youcan.ui.main.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import top.huangguaniu.youcan.R;

/**
 * 土司
 * @author 侯延旭
 * @date 2018/7/11
 */
public class HappyToast{
    private Toast mToast;
    private HappyToast(Context context, CharSequence text, int duration) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_toast_warn, null);
        TextView textView = v.findViewById(R.id.text_title);
        textView.setText(text);
        mToast = new Toast(context);
        mToast.setDuration(duration);
        mToast.setView(v);
    }

    public static HappyToast makeText(Context context, CharSequence text, int duration) {
        return new HappyToast(context, text, duration);
    }
    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
    public void setGravity(int gravity, int xOffset, int yOffset) {
        if (mToast != null) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }
    }
}
