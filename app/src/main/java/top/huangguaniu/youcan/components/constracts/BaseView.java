package top.huangguaniu.youcan.components.constracts;

import android.content.Context;

/**
 * Created by hyx on 18-1-15.
 */

public interface BaseView<T extends BasePresenter> {
    void setUpPresenter(T r);
    T getPresenter();
    Context getContext();
}
