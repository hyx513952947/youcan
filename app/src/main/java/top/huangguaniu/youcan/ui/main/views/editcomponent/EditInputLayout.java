package top.huangguaniu.youcan.ui.main.views.editcomponent;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class EditInputLayout extends LinearLayout implements EditInputManager{

    public static int TYPE_INPUT_NORMAL = 0;
    public static int TYPE_INPUT_CHECK = 1;

    public EditInputLayout(@NonNull Context context) {
        this(context,null);
    }

    public EditInputLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initGroup();
    }

    private void initGroup() {

    }

    public EditInputLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    public void addNormalEditView() {

    }

    @Override
    public void addCheckEditView(boolean isChecked) {

    }
}
