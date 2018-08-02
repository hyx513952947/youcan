package top.huangguaniu.youcan.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.R;

/**
 *
 * @author hyx
 * @date 18-1-16
 */

public abstract class BaseFragment extends DaggerFragment {

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    protected abstract void onViewCreate(View root);

}
