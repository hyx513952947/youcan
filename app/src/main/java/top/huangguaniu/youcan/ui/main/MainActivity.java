package top.huangguaniu.youcan.ui.main;

import android.Manifest;
import android.support.annotation.NonNull;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.BaseActivity;
import top.huangguaniu.youcan.ui.Layout;
import top.huangguaniu.youcan.ui.main.views.DragRightFrameLayout;
import top.huangguaniu.youcan.ui.main.views.Logger;
import top.huangguaniu.youcan.ui.main.views.MenuButton;
import top.huangguaniu.youcan.ui.main.views.MenuLayout;

/**
 * @author hyx
 */
@RuntimePermissions
@Layout(layout = R.layout.main_activity)
public class MainActivity extends BaseActivity implements MenuLayout.OnMenuItemClickListener
        ,MainActivityContract.View {
    @BindView(R.id.button_menu_closed)
    MenuButton buttonMenuClosed;
    @BindView(R.id.layout_menu)
    MenuLayout layoutMenu;
    @BindView(R.id.layout_drag)
    DragRightFrameLayout layoutDrag;

    @Override
    protected void onLayoutInit() {
        layoutMenu.setOnMenuItemClickListener(this);
    }

    @Inject
    protected void injectPresenter(MainActivityContract.Presenter presenter) {
        Logger.i("啊哈哈的好哇"+presenter.toString());
    }

    @Override
    public void onItemClick(View v) {
        layoutDrag.closeMenu();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
