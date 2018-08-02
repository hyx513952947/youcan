package top.huangguaniu.youcan.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import top.huangguaniu.youcan.YouCanApplication;
import top.huangguaniu.youcan.ui.main.layouts.editor.DiaryEditorModule;
import top.huangguaniu.youcan.ui.main.HomeModule;

@Singleton
@Component(modules = {
        DiaryEditorModule.class,
        AppModule.class,
        HomeModule.class,
        PresenterModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<YouCanApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
