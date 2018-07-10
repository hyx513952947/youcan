package top.huangguaniu.youcan.ui.main;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import dagger.android.support.DaggerFragment;
import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.components.glide.GlideApp;
import top.huangguaniu.youcan.components.glide.GlideRoundDrawableTransform;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends DaggerFragment {


    Unbinder unbinder;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.imageView_head)
    AppCompatImageView imageViewHead;
    @BindView(R.id.imageView_notify)
    AppCompatImageView imageViewNotify;
    @BindView(R.id.imageView_more)
    AppCompatImageView imageViewMore;
    @BindView(R.id.imageView_search)
    AppCompatImageView imageViewSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
                .transform(new GlideRoundDrawableTransform(20))
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

    @OnClick({R.id.imageView_head, R.id.imageView_notify})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_head:
                break;
            case R.id.imageView_notify:
                break;
        }
    }

    @OnClick(R.id.imageView_image)
    public void onImageViewImageClicked() {
    }

    @OnClick(R.id.imageView_textSize)
    public void onImageViewTextSizeClicked() {
        Toast.makeText(getContext(),"新建笔记",Toast.LENGTH_SHORT).show();
        NavController controller = NavHostFragment.findNavController(this);
        controller.navigate(R.id.action_fragment_home_to_fragment_diary);
    }

    @OnClick(R.id.imageView_draw)
    public void onImageViewDrawClicked() {
    }

    @OnClick(R.id.imageView_voice)
    public void onImageViewVoiceClicked() {
    }
}
