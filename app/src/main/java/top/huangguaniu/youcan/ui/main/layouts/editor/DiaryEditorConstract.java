package top.huangguaniu.youcan.ui.main.layouts.editor;

import top.huangguaniu.youcan.components.constracts.BasePresenter;
import top.huangguaniu.youcan.components.constracts.BaseView;

/**
 * Created by 侯延旭 on 2018/7/18.
 */
public interface DiaryEditorConstract {
    interface View extends BaseView<DiaryEditorPresenter>{

    }
    interface Presenter<V extends BaseView<DiaryEditorPresenter>> extends BasePresenter<View> {
    }

}
