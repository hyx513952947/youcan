package top.huangguaniu.youcan.ui.main.draw.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;

import top.huangguaniu.youcan.R;

/**
 * @author Ing. Oscar G. Medina Cruz
 * @date 09/11/2016
 */

public class RequestTextDialog extends DialogFragment {

    private static final String REQ_TEXT = "REQ_TEXT";

    private OnRequestTextListener onRequestTextListener;

    // VARS
    private Bitmap mPreviewBitmap;

    public RequestTextDialog() {
    }

    public static RequestTextDialog newInstance(String currentText) {
        RequestTextDialog requestTextDialog = new RequestTextDialog();
        Bundle bundle = new Bundle();
        bundle.putString(REQ_TEXT, currentText);
        requestTextDialog.setArguments(bundle);
        return requestTextDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.layout_request_text, null);
        final TextInputEditText textInputEditText = view.findViewById(R.id.et_req_text);

        if (!getArguments().getString(REQ_TEXT).equals("")){
            textInputEditText.setText(getArguments().getShort(REQ_TEXT));
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onRequestTextListener != null){
                            onRequestTextListener.onRequestTextConfirmed(textInputEditText.getText().toString());
                        }
                        dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (onRequestTextListener != null) {
                            onRequestTextListener.onRequestTextCancelled();
                        }
                        dismiss();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    // METHODS

    // LISTENER
    public void setOnRequestTextListener(OnRequestTextListener onRequestTextListener) {
        this.onRequestTextListener = onRequestTextListener;
    }

    public interface OnRequestTextListener {
        void onRequestTextConfirmed(String requestedText);

        void onRequestTextCancelled();
    }
}
