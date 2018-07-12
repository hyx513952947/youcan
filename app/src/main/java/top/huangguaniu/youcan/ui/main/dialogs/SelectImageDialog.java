package top.huangguaniu.youcan.ui.main.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.test.suitebuilder.annotation.Smoke;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.qingmei2.rximagepicker.core.RxImagePicker;
import com.qingmei2.rximagepicker.entity.Result;
import com.qingmei2.rximagepicker_extension.MimeType;
import com.qingmei2.rximagepicker_extension_wechat.WechatConfigrationBuilder;
import com.qingmei2.rximagepicker_extension_wechat.ui.WechatImagePickerActivity;

import java.io.File;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;
import io.reactivex.functions.Consumer;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.components.glide.GlideApp;
import top.huangguaniu.youcan.components.media.FileUtil;
import top.huangguaniu.youcan.components.media.ImagePicker;
import top.huangguaniu.youcan.ui.main.dialogs.select.RecycleViewDivider;
import top.huangguaniu.youcan.ui.main.dialogs.select.SelectImageAdapter;
import top.huangguaniu.youcan.ui.main.views.Logger;

import static android.app.Activity.RESULT_OK;
import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;

/**
 * 文本编辑添加图片附件
 *
 * @author 侯延旭
 * @date 2018/7/10
 */
public class SelectImageDialog extends DialogFragment implements SelectImageAdapter.OnSelectItemListener {

    private static final String TITLE = "SELECT_TITLE";
    private static final String ARRAY = "SELECT_ARRAY";
    private static final int CODE_REQUEST_CAMERA = 100;
    private SelectImageAdapter selectImageAdapter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        selectImageAdapter = new SelectImageAdapter(null);
        selectImageAdapter.setOnSelectItemListener(this);

        RecyclerView recyclerView = (RecyclerView) LayoutInflater.from(getContext()).inflate(R.layout.layout_select_image, null);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.addItemDecoration(new RecycleViewDivider(getContext(), RecycleViewDivider.BOUND));
        recyclerView.setAdapter(selectImageAdapter);

        builder.setView(recyclerView);
        return builder.create();
    }

    public static SelectImageDialog newInstance(String title, String... array) {
        SelectImageDialog selectImageDialog = new SelectImageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        bundle.putStringArray(ARRAY, array);
        selectImageDialog.setArguments(bundle);
        return selectImageDialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_REQUEST_CAMERA && resultCode == Activity.RESULT_OK){
            selectImageAdapter.addNewImage(cameraFile);
        }
    }
    private File cameraFile;
    @Override
    public void onCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        cameraFile = FileUtil.getNewImageFile(Math.random()+".png");
        Uri uri = FileProvider.getUriForFile(getContext(), "top.huanggguaniu.youcan.fileprovider", cameraFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,CODE_REQUEST_CAMERA);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        compositeDisposable.dispose();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void onSelect() {
        WechatConfigrationBuilder builder = new WechatConfigrationBuilder(MimeType.ofImage(), true)
                .maxSelectable(9)    //最大可选图片数
                .spanCount(3)        //每行展示四张图片
                .countable(false)    //关闭计数模式
                .theme(R.style.Wechat);    //微信主题
        Disposable disposable = new RxImagePicker.Builder().with(this)
                .addCustomGallery(ImagePicker.KEY_WECHAT_PICKER_ACTIVITY, WechatImagePickerActivity.class, builder.build())
                .build()
                .create(ImagePicker.class)
                .openGallery()
                .subscribe(result -> {
                    Uri uri = result.getUri();
                    selectImageAdapter.addNewImage(uri);
                });
        compositeDisposable.add(disposable);
    }
}
