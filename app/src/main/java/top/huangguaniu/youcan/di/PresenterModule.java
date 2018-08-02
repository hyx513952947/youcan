package top.huangguaniu.youcan.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import top.huangguaniu.youcan.ui.main.MainActivityContract;
import top.huangguaniu.youcan.ui.main.MainActivityPresenter;

/**
 * Created by 侯延旭 on 2018/8/2.
 */
@Module
public class PresenterModule {
    @Singleton
    @Provides
    public MainActivityContract.Presenter mainActivityPresenter(){
        return new MainActivityPresenter();
    }
}
