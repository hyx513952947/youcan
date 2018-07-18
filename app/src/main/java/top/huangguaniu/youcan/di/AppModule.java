package top.huangguaniu.youcan.di;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import top.huangguaniu.youcan.data.Depository;
import top.huangguaniu.youcan.data.local.Sql;
import top.huangguaniu.youcan.data.local.room.Database;
import top.huangguaniu.youcan.data.remote.Api;
import top.huangguaniu.youcan.data.remote.RemoteClient;
import top.huangguaniu.youcan.ui.main.layouts.editor.DiaryEditorPresenter;

/**
 * Created by 侯延旭 on 2018/6/28.
 */
@Module
public class AppModule {
    @Singleton
    @Provides
    Api api(){
        return RemoteClient.getRetrofit()
                .create(Api.class);
    }
    @Singleton
    @Provides
    Sql sql(){
        return new Sql();
    }
    @Singleton
    @Provides
    Depository depository(Api api,Sql sql){
        return new Depository(api,sql);
    }

    @Singleton
    @Provides
    Database database(Application application){
        return Room.databaseBuilder(application,Database.class,"youcan").build();
    }

    @Singleton
    @Provides
    DiaryEditorPresenter diaryEditorPresenter(Depository depository){
        return new DiaryEditorPresenter(depository);
    }
}
