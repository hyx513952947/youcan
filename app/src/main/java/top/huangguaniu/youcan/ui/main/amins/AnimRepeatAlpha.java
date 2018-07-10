package top.huangguaniu.youcan.ui.main.amins;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

/**
 *  动画 透明度来回变化的
 * @author 侯延旭
 * @date 2018/7/10
 */
public class AnimRepeatAlpha {
    private long duration = 1000;
    private boolean isRepeat = true;
    private AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f,0.7f);
    public void start(View target){
        alphaAnimation.setDuration(duration);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        if (isRepeat){
            alphaAnimation.setRepeatCount(ValueAnimator.INFINITE);
        }
        target.startAnimation(alphaAnimation);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public AlphaAnimation getAlphaAnimation() {
        return alphaAnimation;
    }

    public void setAlphaAnimation(AlphaAnimation alphaAnimation) {
        this.alphaAnimation = alphaAnimation;
    }

    public static class Builder{
        private AnimRepeatAlpha animRepeatAlpha = new AnimRepeatAlpha();
        public Builder setDuration(long duration){
            animRepeatAlpha.setDuration(duration);
            return this;
        }
        public Builder setRepeatable(boolean repeat){
            animRepeatAlpha.setRepeat(repeat);
            return this;
        }
        public Builder setBounds(float s,float e){
            AlphaAnimation alphaAnimation = new AlphaAnimation(s,e);
            animRepeatAlpha.setAlphaAnimation(alphaAnimation);
            return this;
        }
        public AnimRepeatAlpha build(){
            return animRepeatAlpha;
        }
    }
}
