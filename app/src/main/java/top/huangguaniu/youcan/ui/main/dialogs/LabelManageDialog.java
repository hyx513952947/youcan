package top.huangguaniu.youcan.ui.main.dialogs;

import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.views.EditorEditTextView;
import top.huangguaniu.youcan.ui.main.views.HappyToast;
import top.huangguaniu.youcan.ui.main.views.Logger;
import top.huangguaniu.youcan.ui.main.views.labels.Label;
import top.huangguaniu.youcan.ui.main.views.labels.LabelManager;
import top.huangguaniu.youcan.ui.main.views.labels.LabelViewGroup;

/**
 * @author 侯延旭
 * @date 2018/7/10
 */
public class LabelManageDialog extends DialogFragment implements View.OnClickListener {
    private LabelViewGroup labelViewGroup;
    private View addLabel;
    private AppCompatEditText editText;
    private int INVALID = -1;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_label_manage, null);
        addLabel = view.findViewById(R.id.btn_add_label);
        editText = view.findViewById(R.id.input_label);
        labelViewGroup = view.findViewById(R.id.layout_label);
        labelViewGroup.setHorizontalSpacing(8);
        addLabel.setOnClickListener(this);

        LabelManager labelManager = LabelManager.newInstance(getContext());
        for (Label label:labelManager.getDefaultLabel()){
            labelViewGroup.addView(label);
        }
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * @param labels 自定义的标签
     * @return 碎片
     */
    public static LabelManageDialog newInstance(String... labels) {
        LabelManageDialog labelManageDialog = new LabelManageDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArray("labels", labels);
        labelManageDialog.setArguments(bundle);
        return labelManageDialog;
    }

    @Override
    public void onClick(View v) {
        if (editText.getText() == null || "".equals(editText.getText().toString())) {
            HappyToast.makeText(getContext(), "你确定写字咯嘛？", Toast.LENGTH_LONG).show();
        }else {
            Label label = Label.createLabel(getContext(),editText.getText().toString());
            labelViewGroup.addView(label);
        }

    }
}
