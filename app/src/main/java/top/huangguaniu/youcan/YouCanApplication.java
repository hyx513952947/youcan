package top.huangguaniu.youcan;

import android.app.Activity;
import android.os.Build;
import android.os.StrictMode;

import javax.inject.Inject;

import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.rx.RxObservableFactory;
import top.huangguaniu.youcan.di.DaggerAppComponent;

/**
 * Created by 侯延旭 on 2018/6/28.
 */
public class YouCanApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);

    }
}
