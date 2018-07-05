package top.huangguaniu.youcan.ui.main.views.behaviors;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * Created by 侯延旭 on 2018/7/5.
 */
public class HomeHeadBehavior extends CoordinatorLayout.Behavior<RelativeLayout> {
    public HomeHeadBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        return dependency instanceof NestedScrollView;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RelativeLayout child, int layoutDirection) {
        CoordinatorLayout.LayoutParams layoutParamsParent = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        child.layout(layoutParamsParent.leftMargin,layoutParamsParent.topMargin,parent.getMeasuredWidth()-
        layoutParamsParent.rightMargin,layoutParamsParent.topMargin+child.getMeasuredHeight());
        return true;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RelativeLayout child, View dependency) {
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) dependency.getLayoutParams();
        dependency.layout(layoutParams.leftMargin, (int) (child.getMeasuredHeight()+layoutParams.topMargin),layoutParams.leftMargin+dependency.getMeasuredWidth(),
                parent.getMeasuredHeight());
        return true;
    }

}
