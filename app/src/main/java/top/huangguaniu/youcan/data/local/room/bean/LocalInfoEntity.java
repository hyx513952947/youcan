package top.huangguaniu.youcan.data.local.room.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 *  保存到本地的数据
 *  天气 地点 是否自动添加 日常标签
 * @author 侯延旭
 * @date 2018/7/13
 */
@Entity(tableName = "local")
public class LocalInfoEntity {
    @PrimaryKey
    public Long id;
    public long time;
    public String info;
    public String city;
}
