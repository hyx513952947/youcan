package top.huangguaniu.youcan.data;


import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import top.huangguaniu.youcan.data.local.Sql;
import top.huangguaniu.youcan.data.remote.Api;
import top.huangguaniu.youcan.data.remote.RemoteClient;
import top.huangguaniu.youcan.data.remote.bean.WeatherBean;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * @author 侯延旭
 * @date 2018/7/11
 */

public class Depository {
    private Api api;
    private Sql sql;

    @Inject
    public Depository(Api api,Sql sql){
        this.sql = sql;
        this.api = api;
        Logger.i("创建咯");
    }

    public Observable<WeatherBean> queryWeather(String city){
        return api.queryWeather(Api.URL_WEATHER,city);
    }
}
