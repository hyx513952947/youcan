package top.huangguaniu.youcan.components.media;

import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker.entity.sources.Camera;
import com.qingmei2.rximagepicker.entity.sources.Gallery;

import io.reactivex.Observable;

/**
 * Created by 侯延旭 on 2018/7/12.
 */
public interface ImagePicker {

    String KEY_WECHAT_PICKER_ACTIVITY = "key_wechat_picker";
    @Gallery(viewKey = KEY_WECHAT_PICKER_ACTIVITY)
    Observable<Result> openGallery();

    @Camera
    Observable<Result> openCamera();
}
