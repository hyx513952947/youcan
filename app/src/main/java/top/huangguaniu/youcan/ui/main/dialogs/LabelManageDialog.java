package top.huangguaniu.youcan.ui.main.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import java.util.zip.Inflater;

import top.huangguaniu.youcan.R;

/**
 *
 * @author 侯延旭
 * @date 2018/7/10
 */
public class LabelManageDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view =   LayoutInflater.from(getContext()).inflate(R.layout.fragment_label_manage,null);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
