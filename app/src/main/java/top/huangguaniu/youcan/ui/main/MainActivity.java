package top.huangguaniu.youcan.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.views.DragRightFrameLayout;
import top.huangguaniu.youcan.ui.main.views.Logger;
import top.huangguaniu.youcan.ui.main.views.MenuButton;
import top.huangguaniu.youcan.ui.main.views.MenuLayout;

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
        Log.i("菜单点击：","id:"+v.getId());
        layoutDrag.closeMenu();
    }

    @OnClick(R.id.button_menu_closed)
    public void onViewClicked() {
        layoutDrag.closeMenu();
    }
}
