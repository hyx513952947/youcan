package top.huangguaniu.youcan.ui.main.layouts.editor;

import javax.inject.Inject;

import top.huangguaniu.youcan.data.Depository;

/**
 * Created by 侯延旭 on 2018/7/18.
 */
public class DiaryEditorPresenter implements DiaryEditorConstract.Presenter<DiaryEditorConstract.View> {
    private Depository depository;
    private DiaryEditorConstract.View view;
    @Inject
    public DiaryEditorPresenter(Depository depository) {
        this.depository = depository;
    }

    @Override
    public void setView(DiaryEditorConstract.View view) {
        this.view = view;
    }

    @Override
    public void release() {
        
    }
}
