package top.huangguaniu.youcan.ui.main.draw;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.byox.drawview.enums.DrawingMode;
import com.byox.drawview.enums.DrawingTool;
import com.byox.drawview.views.DrawView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.DaggerAppCompatActivity;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.draw.dialogs.DrawAttribsDialog;
import top.huangguaniu.youcan.ui.main.draw.dialogs.DrawSelectShapeDialog;
import top.huangguaniu.youcan.ui.main.draw.dialogs.RequestTextDialog;

/**
 * @author hyx
 */
public class DrawViewActivity extends DaggerAppCompatActivity {

    @BindView(R.id.draw_view)
    DrawView drawView;
    @BindView(R.id.imageView_undo)
    ImageView imageViewUndo;
    @BindView(R.id.imageView_redo)
    ImageView imageViewRedo;
    @BindView(R.id.imageView_eraser)
    ImageView imageViewEraser;
    @BindView(R.id.linearLayout)
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_view);
        ButterKnife.bind(this);

        imageViewRedo.setEnabled(false);
        imageViewUndo.setEnabled(false);

        drawView.setOnDrawViewListener(new DrawView.OnDrawViewListener() {
            @Override
            public void onStartDrawing() {

            }

            @Override
            public void onEndDrawing() {
                canUndoRedo();
            }

            @Override
            public void onClearDrawing() {
                canUndoRedo();
            }

            @Override
            public void onRequestText() {
                inputText();
            }

            @Override
            public void onAllMovesPainted() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        canUndoRedo();
                    }
                }, 300);
            }
        });

    }

    private void inputText() {
        RequestTextDialog requestTextDialog =
                RequestTextDialog.newInstance("");
        requestTextDialog.setOnRequestTextListener(new RequestTextDialog.OnRequestTextListener() {
            @Override
            public void onRequestTextConfirmed(String requestedText) {
                drawView.refreshLastText(requestedText);
            }

            @Override
            public void onRequestTextCancelled() {
                drawView.cancelTextRequest();
            }
        });
        requestTextDialog.show(getSupportFragmentManager(), "requestText");
    }

    private void canUndoRedo() {
        imageViewUndo.setEnabled(drawView.canUndo());
        imageViewRedo.setEnabled(drawView.canRedo());
    }

    @OnClick(R.id.imageView_attrs)
    public void onImageViewAttrsClicked() {
        DrawAttribsDialog drawAttribsDialog = DrawAttribsDialog.newInstance();
        drawAttribsDialog.setPaint(drawView.getCurrentPaintParams());
        drawAttribsDialog.setOnCustomViewDialogListener(new DrawAttribsDialog.OnCustomViewDialogListener() {
            @Override
            public void onRefreshPaint(Paint newPaint) {
                drawView.setDrawColor(newPaint.getColor())
                        .setPaintStyle(newPaint.getStyle())
                        .setDither(newPaint.isDither())
                        .setDrawWidth((int) newPaint.getStrokeWidth())
                        .setDrawAlpha(newPaint.getAlpha())
                        .setAntiAlias(newPaint.isAntiAlias())
                        .setLineCap(newPaint.getStrokeCap())
                        .setFontFamily(newPaint.getTypeface())
                        .setFontSize(newPaint.getTextSize());
            }
        });
        drawAttribsDialog.show(getSupportFragmentManager(), "drawAttribs");
    }

    @OnClick(R.id.imageView_round)
    public void onImageViewRoundClicked() {
        DrawSelectShapeDialog selectShapeDialog = DrawSelectShapeDialog.newInstance("选择形状",
                getResources().getStringArray(R.array.array_draw_shape));
        selectShapeDialog.setOnChoiceDialogListener(new DrawSelectShapeDialog.OnChoiceDialogListener() {
            @Override
            public void onChoiceSelected(int position) {
                drawView.setDrawingTool(DrawingTool.values()[position]);
            }
        });
        selectShapeDialog.show(getSupportFragmentManager(), "selects");
    }

    @OnClick(R.id.imageView_eraser)
    public void onImageViewEraserClicked() {
        DrawSelectShapeDialog selectShapeDialog = DrawSelectShapeDialog.newInstance("选择画笔模式",
                getResources().getStringArray(R.array.array_draw_mode));
        selectShapeDialog.setOnChoiceDialogListener(new DrawSelectShapeDialog.OnChoiceDialogListener() {
            @Override
            public void onChoiceSelected(int position) {
                imageViewEraser.setImageLevel(position*10);
                drawView.setDrawingMode(DrawingMode.values()[position]);
            }
        });
        selectShapeDialog.show(getSupportFragmentManager(), "selects");
    }

    @OnClick(R.id.imageView_undo)
    public void onImageViewUndoClicked() {
        if (drawView.canUndo()) {
            drawView.undo();
            canUndoRedo();
        }
    }

    @OnClick(R.id.imageView_redo)
    public void onImageViewRedoClicked() {
        if (drawView.canRedo()) {
            drawView.redo();
            canUndoRedo();
        }
    }
}
