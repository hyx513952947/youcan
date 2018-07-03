package top.huangguaniu.youcan.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import top.huangguaniu.youcan.ui.main.MainActivity;
import top.huangguaniu.youcan.ui.main.DiaryEditorModule;
import top.huangguaniu.youcan.ui.main.HomeModule;

/**
 * Created by 侯延旭 on 2018/6/28.
 */
@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = {DiaryEditorModule.class,HomeModule.class})
    abstract MainActivity mainActivity();
}
