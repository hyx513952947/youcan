package top.huangguaniu.youcan.ui.main;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import javax.inject.Inject;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.data.Depository;
import top.huangguaniu.youcan.ui.main.dialogs.LabelManageDialog;
import top.huangguaniu.youcan.ui.main.dialogs.SelectImageDialog;
import top.huangguaniu.youcan.ui.main.views.HappyToast;
import top.huangguaniu.youcan.ui.main.views.labels.Label;
import top.huangguaniu.youcan.ui.main.views.labels.LabelManager;
import top.huangguaniu.youcan.ui.main.views.labels.LabelViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author css
 */
@RuntimePermissions
public class DiaryEditorFragment extends DaggerFragment {

    Unbinder unbinder;
    @BindView(R.id.layout_label_select)
    LabelViewGroup layoutLabelSelect;
    @BindView(R.id.layout_label_all)
    LabelViewGroup layoutLabelAll;
    LabelManager labelManager;

    private AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption = null;

    @Inject
    Depository depository;

    AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation.getErrorCode() == 0) {
                Label label = labelManager.createDeletableLabel("地点");
                label.setTitle(aMapLocation.getCity());
                layoutLabelSelect.addView(label);
                Disposable disposable = depository.queryWeather(aMapLocation.getCity())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(bean -> {
                            Label labelWeather = labelManager.createDeletableLabel("天气");
                            labelWeather.setTitle(bean.getData().getForecast().get(0).getType());
                            layoutLabelSelect.addView(labelWeather);
                        });
            }
        }
    };


    private LabelViewGroup.OnLabelStateListener selectedLabelListener = new LabelViewGroup.OnLabelStateListener() {
        @Override
        public void onLabelChanged(Label label, boolean isRemoved, boolean isTouched) {
        }
    };

    private LabelViewGroup.OnLabelStateListener allLabelListener = new LabelViewGroup.OnLabelStateListener() {
        @Override
        public void onLabelChanged(Label label, boolean isRemoved, boolean isTouched) {
            Label select = labelManager.createDeletableLabel(label.getTitle());
            layoutLabelSelect.addView(select);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_editor, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        labelManager = LabelManager.newInstance(getContext());
        for (Label label : labelManager.getSelectableLabel()) {
            layoutLabelAll.addView(label);
        }
        layoutLabelAll.setOnLabelStateListener(allLabelListener);
        layoutLabelSelect.setOnLabelStateListener(selectedLabelListener);
        DiaryEditorFragmentPermissionsDispatcher.needLocationPermissionWithPermissionCheck(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mLocationClient.unRegisterLocationListener(mLocationListener);
    }

    @OnClick(R.id.appCompatImageView_back)
    public void onAppCompatImageViewBackClicked() {
        NavController controller = Navigation.findNavController(getView());

    }

    @OnClick(R.id.text_label_manage)
    public void onTextLabelManageClicked() {
        LabelManageDialog labelManageDialog = new LabelManageDialog();
        labelManageDialog.show(getChildFragmentManager(), "labels");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        DiaryEditorFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @NeedsPermission({Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needLocationPermission() {
        mLocationClient = new AMapLocationClient(getContext());
        mLocationClient.setLocationListener(mLocationListener);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationCacheEnable(true);
        mLocationOption.setOnceLocation(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
    }

    @OnShowRationale({Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showLocationRationale(final PermissionRequest request) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("关于定位")
                .setMessage("获取你的定位用于自动填写笔记地理位置、天气状况，拒绝该权限后位置以及天气需要手动填写")
                .setNegativeButton("知道咯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("我自己填", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    @OnPermissionDenied({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void showLocationDenied() {
        HappyToast.makeText(getContext(),"权限不给我自己填去吧",Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.imageView_image)
    public void onImageViewImageClicked() {
        SelectImageDialog selectImageDialog = SelectImageDialog.newInstance("没标题",null);
        selectImageDialog.show(getChildFragmentManager(),"select");
    }

    @OnClick(R.id.imageView_draw)
    public void onImageViewDrawClicked() {
    }
}