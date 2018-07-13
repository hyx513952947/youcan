package top.huangguaniu.youcan.data.remote;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 *
 * @author 侯延旭
 * @date 2018/7/11
 */
public class RemoteClient {

    public static OkHttpClient getHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.retryOnConnectionFailure(true)
                .writeTimeout(100,TimeUnit.SECONDS)
                .readTimeout(100,TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .addInterceptor(new LoggingInterceptor())
                .addNetworkInterceptor(new LoggingInterceptor())
                .build();
        return builder.build();
    }
    public static Retrofit getRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Api.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getHttpClient())
                .build();
    }
   static class LoggingInterceptor implements Interceptor {
        @Override public Response intercept(Interceptor.Chain chain) {
            Request request = chain.request();
            long t1 = System.nanoTime();
            Logger.i(String.format("Sending request %s on %s%n%s",
                    request.url(), chain.connection(), request.headers()));
            Response response =null;
            try {
                response = chain.proceed(request);
                long t2 = System.nanoTime();
                Logger.i(String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));
            }catch (Exception e){
                Logger.i(e.toString());
                Request errRequest = new Request.Builder()
                        .url("http://127.0.0.1:8080")
                        .build();
                response = new Response.Builder()
                        .request(errRequest)
                        .code(500).build();
            }
            return response;
        }
    }

}
