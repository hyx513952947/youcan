package top.huangguaniu.youcan.ui.main.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import top.huangguaniu.youcan.ui.main.draw.dialogs.DrawSelectShapeDialog;

/**
 *  简单的弹出选择框
 * @author 侯延旭
 * @date 2018/7/12
 */
public class SelectItemDialog extends DialogFragment {

    private DrawSelectShapeDialog.OnChoiceDialogListener onChoiceDialogListener;

    private static final String CHOICES_TITLE ="CHOICES_TITLE";
    private static final String CHOICES_ARRAY ="CHOICES_ARRAY";

    public SelectItemDialog() {}

    public static DrawSelectShapeDialog newInstance(String title, String... choices){
        if (choices.length == 0){
            throw new RuntimeException("Be sure to add at least one choise to the dialog!");
        }

        DrawSelectShapeDialog selectChoiceDialog = new DrawSelectShapeDialog();
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
                .setItems(getArguments().getStringArray(CHOICES_ARRAY), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onChoiceDialogListener != null){
                            onChoiceDialogListener.onChoiceSelected(i);
                        }
                        dismiss();

                    }
                });
        return builder.create();
    }

    // INTERFACE
    public void setOnChoiceDialogListener(DrawSelectShapeDialog.OnChoiceDialogListener onChoiceDialogListener){
        this.onChoiceDialogListener = onChoiceDialogListener;
    }

    public interface OnChoiceDialogListener{
        void onChoiceSelected(int position);
    }
}
