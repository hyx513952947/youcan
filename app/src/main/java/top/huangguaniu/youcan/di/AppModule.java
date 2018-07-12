package top.huangguaniu.youcan.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import top.huangguaniu.youcan.YouCanApplication;
import top.huangguaniu.youcan.data.Depository;
import top.huangguaniu.youcan.data.local.Sql;
import top.huangguaniu.youcan.data.remote.Api;
import top.huangguaniu.youcan.data.remote.RemoteClient;

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
}
