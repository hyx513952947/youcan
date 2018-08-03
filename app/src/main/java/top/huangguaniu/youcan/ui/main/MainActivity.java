package top.huangguaniu.youcan.ui.main;

import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.BaseActivity;
import top.huangguaniu.youcan.ui.Layout;
import top.huangguaniu.youcan.ui.main.views.DragRightFrameLayout;
import top.huangguaniu.youcan.ui.main.views.MenuButton;
import top.huangguaniu.youcan.ui.main.views.MenuLayout;

/**
 * @author hyx
 */
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
    }

    @Override
    public void onItemClick(View v) {
        layoutDrag.closeMenu();
    }

    @OnClick(R.id.button_menu_closed)
    public void onViewClicked() {
        layoutDrag.closeMenu();
    }

}
