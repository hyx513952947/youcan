package top.huangguaniu.youcan.ui.main;

import javax.inject.Inject;

import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * Created by 侯延旭 on 2018/8/2.
 */
public class MainActivityPresenter implements MainActivityContract.Presenter {
    @Inject
    public MainActivityPresenter(){
        Logger.i("创建");
    }
}
