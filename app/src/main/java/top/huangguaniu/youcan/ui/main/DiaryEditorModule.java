package top.huangguaniu.youcan.ui.main;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import top.huangguaniu.youcan.di.FragmentScoped;

/**
 * Created by 侯延旭 on 2018/6/28.
 */
@Module
public abstract class DiaryEditorModule {
    @FragmentScoped
    @ContributesAndroidInjector
    abstract DiaryEditorFragment diaryEditorFragment();
}
