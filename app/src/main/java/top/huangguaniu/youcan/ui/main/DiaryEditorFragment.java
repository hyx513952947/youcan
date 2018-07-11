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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.dialogs.LabelManageDialog;
import top.huangguaniu.youcan.ui.main.views.Logger;
import top.huangguaniu.youcan.ui.main.views.labels.Label;
import top.huangguaniu.youcan.ui.main.views.labels.LabelManager;
import top.huangguaniu.youcan.ui.main.views.labels.LabelViewGroup;

/**
 * A simple {@link Fragment} subclass.
 *
 * @author css
 */
public class DiaryEditorFragment extends DaggerFragment{

    Unbinder unbinder;
    @BindView(R.id.layout_label_select)
    LabelViewGroup layoutLabelSelect;
    @BindView(R.id.layout_label_all)
    LabelViewGroup layoutLabelAll;
    LabelManager labelManager;
    private LabelViewGroup.OnLabelStateListener selectedLabelListener = new LabelViewGroup.OnLabelStateListener() {
        @Override
        public void onLabelChanged(Label label, boolean isRemoved, boolean isTouched) {
            Logger.i("已选择的发生了改变");
        }
    };

    private LabelViewGroup.OnLabelStateListener allLabelListener = new LabelViewGroup.OnLabelStateListener() {
        @Override
        public void onLabelChanged(Label label, boolean isRemoved, boolean isTouched) {
            Logger.i("全部的标签发生了改变");
            Label select = labelManager.createDeletableLabel(label.getTitle());
            layoutLabelSelect.addView(select);
        }
    };
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
        labelManager = LabelManager.newInstance(getContext());
        for (Label label : labelManager.getSelectableLabel()) {
            layoutLabelAll.addView(label);
        }
        layoutLabelAll.setOnLabelStateListener(allLabelListener);
        layoutLabelSelect.setOnLabelStateListener(selectedLabelListener);
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
        labelManageDialog.show(getChildFragmentManager(), "labels");
    }
}