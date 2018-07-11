package top.huangguaniu.youcan.ui.main.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import java.util.zip.Inflater;

import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.views.EditorEditTextView;
import top.huangguaniu.youcan.ui.main.views.HappyToast;
import top.huangguaniu.youcan.ui.main.views.labels.LabelViewGroup;

/**
 * @author 侯延旭
 * @date 2018/7/10
 */
public class LabelManageDialog extends DialogFragment implements View.OnClickListener {
    private LabelViewGroup labelViewGroup;
    private View addLabel;
    private AppCompatEditText editText;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_label_manage, null);
        addLabel = view.findViewById(R.id.btn_add_label);
        editText = view.findViewById(R.id.input_label);
        labelViewGroup = view.findViewById(R.id.layout_label);

        addLabel.setOnClickListener(this);

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
        if (editText.getText() == null || editText.getText().toString().equals("")) {
            HappyToast.makeText(getContext(),"你确定写字咯嘛？",Toast.LENGTH_SHORT).show();
        }
    }
}
