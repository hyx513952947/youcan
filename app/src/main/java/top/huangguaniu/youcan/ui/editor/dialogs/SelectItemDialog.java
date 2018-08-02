package top.huangguaniu.youcan.ui.editor.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 *  简单的弹出选择框
 * @author 侯延旭
 * @date 2018/7/12
 */
public class SelectItemDialog extends DialogFragment {

    private OnChoiceDialogListener onChoiceDialogListener;

    private static final String CHOICES_TITLE ="CHOICES_TITLE";
    private static final String CHOICES_ARRAY ="CHOICES_ARRAY";

    public SelectItemDialog() {}

    public static SelectItemDialog newInstance(String title, String... choices){
        if (choices.length == 0){
            throw new RuntimeException("Be sure to add at least one choise to the dialog!");
        }

        SelectItemDialog selectChoiceDialog = new SelectItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString(CHOICES_TITLE, title);
        bundle.putStringArray(CHOICES_ARRAY, choices);
        selectChoiceDialog.setArguments(bundle);
        return selectChoiceDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getArguments().getString(CHOICES_TITLE))
                .setItems(getArguments().getStringArray(CHOICES_ARRAY), (dialogInterface, i) -> {
                    if (onChoiceDialogListener != null){
                        onChoiceDialogListener.onChoiceSelected(i);
                    }
                    dismiss();
                });
        return builder.create();
    }

    // INTERFACE
    public void setOnChoiceDialogListener(OnChoiceDialogListener onChoiceDialogListener){
        this.onChoiceDialogListener = onChoiceDialogListener;
    }

    public interface OnChoiceDialogListener{
        void onChoiceSelected(int position);
    }
}
