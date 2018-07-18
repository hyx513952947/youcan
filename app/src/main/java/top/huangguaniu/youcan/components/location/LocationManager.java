package top.huangguaniu.youcan.components.location;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * @author 侯延旭
 * @date 2018/7/18
 */
public class LocationManager {

    public static LocationManager newInstance(Context context){
        LocationManager locationManager = new LocationManager();
        locationManager.setContext(context);
        locationManager.init();
        return locationManager;
    }

    private void init() {
        mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        handleClientOption(mLocationOption);
        mLocationClient.setLocationOption(mLocationOption);
    }

    public void startLocation(){
        mLocationClient.disableBackgroundLocation(false);
        mLocationClient.startLocation();
    }

    public AMapLocation getLastLocation(){
        return mLocationClient.getLastKnownLocation();
    }


    private LocationManager(){}
    private AMapLocationListener aMapLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation.getErrorCode() != 0){
                if (mLocationClient.getLastKnownLocation()!=null){
                    EventBus.getDefault().post(new LocationEvent("",0,mLocationClient.getLastKnownLocation()));
                }else {
                    EventBus.getDefault().post(new LocationEvent("",LocationEvent.ERROR_VERIFY,null));
                }
            }else {
                EventBus.getDefault().post(new LocationEvent("",0,aMapLocation));
            }
        }
    };

    public void handleClientOption(AMapLocationClientOption clientOption){
        mLocationOption.setLocationCacheEnable(true);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setGeoLanguage(AMapLocationClientOption.GeoLanguage.ZH);
        mLocationOption.setWifiScan(true);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        mLocationClient.setLocationListener(aMapLocationListener);
    }
    private Context context;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    public void setContext(Context context) {
        this.context = context;
    }
    public void release(){
        mLocationClient.unRegisterLocationListener(aMapLocationListener);
        mLocationClient.stopLocation();
    }
}
