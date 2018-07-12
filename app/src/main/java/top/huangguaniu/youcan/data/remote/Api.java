package top.huangguaniu.youcan.data.remote;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;
import top.huangguaniu.youcan.data.remote.bean.WeatherBean;

/**
 *
 * @author 侯延旭
 * @date 2018/7/11
 */
public interface Api {
    String URL = "http://www.baidu.com/";
    String URL_WEATHER = "https://www.sojson.com/open/api/weather/json.shtml";
    String URL_DATE = "https://www.sojson.com/open/api/lunar/json.shtml";

    /**
     * 查询天气
     */
    @GET
    Observable<WeatherBean> queryWeather(@Url String url, @Query("city") String city);

    @POST
    Observable<Object> test(@Query("id")String id);
}
