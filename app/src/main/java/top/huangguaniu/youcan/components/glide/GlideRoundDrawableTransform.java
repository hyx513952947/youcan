package top.huangguaniu.youcan.components.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * Created by 侯延旭 on 2018/7/3.
 */
public class GlideRoundDrawableTransform extends BitmapTransformation {

    private float radius ;

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap result = pool.get(toTransform.getWidth(), toTransform.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, toTransform.getWidth(), toTransform.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        Logger.i("便咯？？？？");
        return result;
    }

    public GlideRoundDrawableTransform(float radius){
        this.radius = radius;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(new String(getClass().getName()+Math.random()).getBytes());
    }
}
