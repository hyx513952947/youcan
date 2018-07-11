package top.huangguaniu.youcan.ui.main.views.labels;

import android.content.Context;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import top.huangguaniu.youcan.R;
import top.huangguaniu.youcan.ui.main.views.Logger;

/**
 * Created by 侯延旭 on 2018/7/11.
 */
public class LabelManager {
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static LabelManager newInstance(Context context) {
        LabelManager labelManager = new LabelManager();
        labelManager.setContext(context);
        return labelManager;
    }

    /**
     * @return 可供选择的所有可用标签
     */
    public List<Label> getSelectableLabel() {
        String[] defaults = context.getResources().getStringArray(R.array.array_default_labels);
        List<Label> labels = new ArrayList<>();
        for (String title : defaults) {
            int src = getLabelIcon(title);
            Label label = Label.createSelectableLabel(context, title, src);
            labels.add(label);
        }
        return labels;
    }

    /**
     * @return
     */
    public List<Label> getDefaultLabel() {
        String[] defaults = context.getResources().getStringArray(R.array.array_default_labels);
        List<Label> labels = new ArrayList<>();
        for (String title : defaults) {
            int src = getLabelIcon(title);
            Label label = Label.createLabel(context, title, src);
            labels.add(label);
        }
        return labels;
    }

    /**
     * 可被删除的
     */
    public Label createDeletableLabel(String title) {
        int src = getLabelIcon(title);
        title = queryLabelInfo(title);
        return Label.createDeletableLabel(context, title, src);
    }

    /**
     * @return 根据标签名称查询
     * 现在能够查询 日期 天气 地点 心情
     */
    private String queryLabelInfo(String label) {
        String info = label;
        if ("天气".equals(label)) {
            info = "大暴雨";
        }
        if ("心情".equals(label)) {
            info = "伐开心";
        }
        if ("地点".equals(label)) {
            info = "北京";
        }
        if ("日期".equals(label)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            Date date = new Date(System.currentTimeMillis());
            info = simpleDateFormat.format(date);
        }
        return info;
    }

    private int getLabelIcon(String label) {
        if ("天气".equals(label)) {
            return R.drawable.icon_weather;
        }
        if ("心情".equals(label)) {
            return R.drawable.icon_heart;
        }
        if ("地点".equals(label)) {
            return R.drawable.icon_location;
        }
        if ("日期".equals(label)) {
            return R.drawable.icon_date;
        }
        return Label.INVALID;
    }


}
