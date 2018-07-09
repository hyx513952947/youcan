package top.huangguaniu.youcan.ui.main;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.views.DragRightFrameLayout;
import top.huangguaniu.youcan.ui.main.views.Logger;
import top.huangguaniu.youcan.ui.main.views.MenuButton;
import top.huangguaniu.youcan.ui.main.views.MenuLayout;

/**
 * @author hyx
 */
@RuntimePermissions
public class MainActivity extends DaggerAppCompatActivity implements MenuLayout.OnMenuItemClickListener {
    @BindView(R.id.button_menu_closed)
    MenuButton buttonMenuClosed;
    @BindView(R.id.layout_menu)
    MenuLayout layoutMenu;
    @BindView(R.id.layout_drag)
    DragRightFrameLayout layoutDrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        layoutMenu.setOnMenuItemClickListener(this);
    }

    @Override
    public void onItemClick(View v) {
        Log.i("菜单点击：", "id:" + v.getId());
        layoutDrag.closeMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @OnClick(R.id.button_menu_closed)
    public void onViewClicked() {
        layoutDrag.closeMenu();
    }

    @NeedsPermission({Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void requestPermission() {

    }

    @OnShowRationale({Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void onPermissionResult(final PermissionRequest request) {
        Logger.i("结果："+request.toString());
    }

}
