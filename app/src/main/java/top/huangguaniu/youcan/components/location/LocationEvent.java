package top.huangguaniu.youcan.components.location;

import com.amap.api.location.AMapLocation;

/**
 * @author 侯延旭
 * @date 2018/7/18
 */
public class LocationEvent {
    /**
     * 错误
     * 无定位权限
     * 无法定位
     */
    public static int ERROR_VERIFY = -1;
    private String message;
    private int code;
    private AMapLocation aMapLocation;
    public LocationEvent(String msg,int code,AMapLocation aMapLocation){
        this.message = msg;
        this.code = code;
        this.aMapLocation = aMapLocation;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }
}
