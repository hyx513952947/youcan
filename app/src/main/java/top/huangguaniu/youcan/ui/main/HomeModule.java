package top.huangguaniu.youcan.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import top.huangguaniu.youcan.di.FragmentScoped;

/**
 * Created by 侯延旭 on 2018/6/29.
 */
@Module
public abstract class HomeModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract HomeFragment homeFragment();
}
