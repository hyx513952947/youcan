package top.huangguaniu.youcan.ui.editor.dialogs.select;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.components.glide.GlideApp;
import top.huangguaniu.youcan.ui.main.views.WHImageView;

/**
 * Created by 侯延旭 on 2018/7/12.
 */
public class SelectImageAdapter extends RecyclerView.Adapter<SelectViewHoler> {
    private List arrays;
    private int TAG_ADD_CAMERA = 12;
    private int TAG_ADD_ALBUM = 13;

    public SelectImageAdapter(List arrays) {
        this.arrays = arrays;
    }

    public void addNewImage(Object o){
        if (null == arrays){
            arrays = new ArrayList();
        }
        arrays.add(o);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SelectViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        WHImageView whImageView = new WHImageView(parent.getContext());
        return new SelectViewHoler(whImageView);
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TAG_ADD_CAMERA;
        } else if (position == 1) {
            return TAG_ADD_ALBUM;
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHoler holder, int position) {
        if (position == 0) {
            holder.imageView.setOnClickListener(v -> {
                if (null != onSelectItemListener){
                    onSelectItemListener.onCamera();
                }
            });
            GlideApp.with(holder.imageView)
                    .load(R.drawable.icon_camera)
                    .centerInside()
                    .into(holder.imageView);
        } else if (position == 1) {
            holder.imageView.setOnClickListener(v -> {
                if (null != onSelectItemListener){
                    onSelectItemListener.onSelect();
                }
            });
            GlideApp.with(holder.imageView)
                    .load(R.drawable.icon_album)
                    .centerInside()
                    .into(holder.imageView);
        } else {
            GlideApp.with(holder.imageView)
                    .load(arrays.get(position-2))
                    .centerCrop()
                    .into(holder.imageView);
        }
    }

    @Override
    public int getItemCount() {
        if (arrays == null) {
            return 2;
        }
        return arrays.size() + 2;
    }
    private OnSelectItemListener onSelectItemListener;

    public void setOnSelectItemListener(OnSelectItemListener onSelectItemListener) {
        this.onSelectItemListener = onSelectItemListener;
    }

    public interface OnSelectItemListener{
        void onCamera();
        void onSelect();
    }
}

class SelectViewHoler extends RecyclerView.ViewHolder {
    WHImageView imageView;

    SelectViewHoler(View itemView) {
        super(itemView);
        imageView = (WHImageView) itemView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imageView.setElevation(3);
        }
    }
}
