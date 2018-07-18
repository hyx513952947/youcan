package top.huangguaniu.youcan.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.components.constracts.BasePresenter;
import top.huangguaniu.youcan.components.constracts.BaseView;

/**
 *
 * @author hyx
 * @date 18-1-16
 */

public abstract class BaseFragment<R extends BasePresenter> extends DaggerFragment implements BaseView<R> {

    Unbinder unbinder;
    R presenter;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (null == rootView){
            rootView = inflater.inflate(getClass().getAnnotation(Layout.class).layout(), container, false);
        }
        unbinder = ButterKnife.bind(this,rootView);
        onViewCreate(rootView);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    @Inject
    public void setUpPresenter(R r) {
        this.presenter = r;
        presenter.setView(this);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        getPresenter().release();
    }

    protected abstract void onViewCreate(View root);

    @Override
    public R getPresenter() {
        return presenter;
    }
}
