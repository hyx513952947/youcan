package top.huangguaniu.youcan.ui.main.layouts.editor;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.Toast;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.components.location.LocationEvent;
import top.huangguaniu.youcan.components.location.LocationManager;
import top.huangguaniu.youcan.components.media.FileIo;
import top.huangguaniu.youcan.components.media.FileUtil;
import top.huangguaniu.youcan.components.media.RecordManager;
import top.huangguaniu.youcan.data.Depository;
import top.huangguaniu.youcan.ui.BaseFragment;
import top.huangguaniu.youcan.ui.editor.dialogs.LabelManageDialog;
import top.huangguaniu.youcan.ui.editor.dialogs.SelectImageDialog;
import top.huangguaniu.youcan.ui.editor.dialogs.SelectItemDialog;
import top.huangguaniu.youcan.ui.main.draw.DrawViewActivity;
import top.huangguaniu.youcan.ui.main.views.HappyToast;
import top.huangguaniu.youcan.ui.main.views.Logger;
import top.huangguaniu.youcan.ui.main.views.labels.Label;
import top.huangguaniu.youcan.ui.main.views.labels.LabelManager;
import top.huangguaniu.youcan.ui.main.views.labels.LabelViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author css
 */
@RuntimePermissions
public class DiaryEditorFragment extends BaseFragment implements DiaryEditorConstract.View {

    Unbinder unbinder;
    @BindView(R.id.layout_label_select)
    LabelViewGroup layoutLabelSelect;
    @BindView(R.id.layout_label_all)
    LabelViewGroup layoutLabelAll;
    LabelManager labelManager;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;

    LocationManager locationManager;

    @Inject
    Depository depository;

    private LabelViewGroup.OnLabelStateListener selectedLabelListener = (label, isRemoved, isTouched) -> {

    };

    private LabelViewGroup.OnLabelStateListener allLabelListener = new LabelViewGroup.OnLabelStateListener() {
        @Override
        public void onLabelChanged(Label label, boolean isRemoved, boolean isTouched) {
            Label select = labelManager.createDeletableLabel(label.getTitle());
            layoutLabelSelect.addView(select);
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationEvent(LocationEvent event) {
        Label label = labelManager.createDeletableLabel("地点");
        label.setTitle(event.getaMapLocation().getCity());
        layoutLabelSelect.addView(label);

        Disposable disposable = depository.queryWeather(event.getaMapLocation().getCity())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(bean -> {
                    Label labelWeather = labelManager.createDeletableLabel("天气");
                    labelWeather.setTitle(bean.getData().getForecast().get(0).getType());
                    layoutLabelSelect.addView(labelWeather);
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_editor, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void onViewCreate(View root) {
        labelManager = LabelManager.newInstance(getContext());
        for (Label label : labelManager.getSelectableLabel()) {
            layoutLabelAll.addView(label);
        }
        layoutLabelAll.setOnLabelStateListener(allLabelListener);
        layoutLabelSelect.setOnLabelStateListener(selectedLabelListener);
    }

    @OnClick(R.id.appCompatImageView_back)
    public void onAppCompatImageViewBackClicked() {

    }

    @OnClick(R.id.text_label_manage)
    public void onTextLabelManageClicked() {
        LabelManageDialog labelManageDialog = new LabelManageDialog();
        labelManageDialog.show(getChildFragmentManager(), "labels");
    }


    @NeedsPermission({Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void needLocationPermission() {
        locationManager = LocationManager.newInstance(getContext());
        locationManager.startLocation();
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
        HappyToast.makeText(getContext(), "权限不给我自己填去吧", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.imageView_image)
    public void onImageViewImageClicked() {
        SelectImageDialog selectImageDialog = SelectImageDialog.newInstance("没标题", (String[]) null);
        selectImageDialog.setOnImageSelectedListener(results -> {
            // todo  选择完照片

        });
        selectImageDialog.show(getChildFragmentManager(), "select");
    }

    @OnClick(R.id.imageView_draw)
    public void onImageViewDrawClicked() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DrawViewActivity.CODE_REQUEST && resultCode == Activity.RESULT_OK) {
            byte[] draw = data.getByteArrayExtra(DrawViewActivity.KEY_DRAW);
            String end = data.getStringExtra(DrawViewActivity.FILE_END);
            File file = FileUtil.getNewDrawFile(String.format("%s.%s", System.currentTimeMillis(), end));
            FileIo.byteToFile(draw, file);
        }
    }

    @OnClick(R.id.appCompatImageView_back)
    public void onViewClicked() {

    }

    @OnClick(R.id.appCompatImageView)
    public void onAppCompatImageView() {
        String[] arr = getResources().getStringArray(R.array.array_default_edit);
        SelectItemDialog selectItemDialog = SelectItemDialog.newInstance("更多选项", arr);
        selectItemDialog.setOnChoiceDialogListener(new SelectItemDialog.OnChoiceDialogListener() {
            @Override
            public void onChoiceSelected(int position) {
                Logger.i("索引:" + position);
            }
        });
        selectItemDialog.show(getChildFragmentManager(), "菜单");
    }

    @OnClick(R.id.imageView_draw)
    public void onImageView_draw() {
        Intent intent = new Intent(getContext(), DrawViewActivity.class);
        startActivityForResult(intent, DrawViewActivity.CODE_REQUEST);
    }

    @OnClick(R.id.imageView_textSize)
    public void ontextSized() {
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
    }
    private RecordManager recordManager;
    @NeedsPermission(Manifest.permission.RECORD_AUDIO)
    void onRecord() {
    }

    @OnClick(R.id.imageView_voice)
    public void onVoiceViewClicked() {
    }
}