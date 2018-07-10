package top.huangguaniu.youcan.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.dialogs.LabelManageDialog;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author css
 */
public class DiaryEditorFragment extends DaggerFragment {

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

    @OnClick(R.id.appCompatImageView_back)
    public void onAppCompatImageViewBackClicked() {
        NavController controller = Navigation.findNavController(getView());

    }

    @OnClick(R.id.text_label_manage)
    public void onTextLabelManageClicked() {
        LabelManageDialog labelManageDialog = new LabelManageDialog();
        labelManageDialog.show(getChildFragmentManager(),"labels");
    }
}