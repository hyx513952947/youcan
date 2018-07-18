package top.huangguaniu.youcan.ui;

import android.os.Bundle;

import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        onLayoutInit();
    }

    protected abstract void onLayoutInit();
    /**
     * 获取layout id
     * @return int
     */
    private int getLayoutId(){
        Layout layout = getClass().getAnnotation(Layout.class);
        if (null == layout){
            throw new NullPointerException("未设置页面布局id");
        }
        return layout.layout();
    }
}
