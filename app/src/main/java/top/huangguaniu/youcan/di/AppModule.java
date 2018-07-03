package top.huangguaniu.youcan.di;

import android.content.Context;

import dagger.Binds;
import dagger.Module;
import top.huangguaniu.youcan.YouCanApplication;

/**
 * Created by 侯延旭 on 2018/6/28.
 */
@Module
public abstract class AppModule {
    @Binds
    abstract Context bindContext(YouCanApplication youCanApplication);
}
