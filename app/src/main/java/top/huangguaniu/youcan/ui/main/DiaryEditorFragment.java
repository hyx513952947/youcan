package top.huangguaniu.youcan.ui.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.draw.DrawViewActivity;
import top.huangguaniu.youcan.ui.main.views.Logger;

import static android.icu.text.DateTimePatternGenerator.PatternInfo.OK;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author css
 */
public class DiaryEditorFragment extends DaggerFragment {

    public static int CODE_DRAW_VIEW_REQUEST = 241;
    public static int CODE_DRAW_VIEW_RESULT = 240;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary_editor, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imageView_back)
    public void onImageViewBackClicked() {
        Logger.i("返回");
    }

    @OnClick(R.id.text_input_title)
    public void onTextInputTitleClicked() {
        Logger.i("日记标题");
    }

    @OnClick(R.id.imageView_image)
    public void onImageViewImageClicked() {
        Logger.i("选择照片");
    }

    @OnClick(R.id.imageView_textSize)
    public void onImageViewTextSizeClicked(View view) {
        Logger.i("文字大小");
        view.setSelected(!view.isSelected());
    }

    @OnClick(R.id.imageView_draw)
    public void onImageViewDrawClicked() {
        Logger.i("日记涂鸦");
        Intent intent = new Intent(getContext(),DrawViewActivity.class);
        startActivityForResult(intent,CODE_DRAW_VIEW_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_DRAW_VIEW_REQUEST && resultCode == CODE_DRAW_VIEW_RESULT){
            Logger.i("有结果咯？？？："+data.getByteArrayExtra("draw"));
        }
    }

    @OnClick(R.id.imageView_voice)
    public void onImageViewVoiceClicked() {
        Logger.i("语音输入");
    }
}
