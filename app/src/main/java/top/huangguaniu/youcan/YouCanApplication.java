package top.huangguaniu.youcan;

import android.app.Activity;

import javax.inject.Inject;

import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import top.huangguaniu.youcan.di.DaggerAppComponent;

/**
 * Created by 侯延旭 on 2018/6/28.
 */
public class YouCanApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
