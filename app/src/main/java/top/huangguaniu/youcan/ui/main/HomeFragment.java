package top.huangguaniu.youcan.ui.main;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.components.glide.GlideApp;
import top.huangguaniu.youcan.components.glide.GlideAppMudule;
import top.huangguaniu.youcan.components.glide.GlideRoundDrawableTransform;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends DaggerFragment {


    @BindView(R.id.imageView_head)
    AppCompatImageView imageViewHead;
    @BindView(R.id.spinner_history)
    AppCompatSpinner spinnerHistory;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.spinner2)
    AppCompatSpinner spinner2;
    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.i("onActivityCreated");
        GlideApp.with(this)
                .load(R.mipmap.icon_default_head)
                .transform(new GlideRoundDrawableTransform(10))
                .into(imageViewHead);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i("onViewCreated");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
