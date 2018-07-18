package top.huangguaniu.youcan.data.local.room;

import android.arch.persistence.room.RoomDatabase;

import top.huangguaniu.youcan.data.local.room.bean.LocalInfoEntity;
import top.huangguaniu.youcan.data.local.room.dao.LocalInfoDao;

/**
 *
 * @author 侯延旭
 * @date 2018/7/18
 */
@android.arch.persistence.room.Database(entities = {LocalInfoEntity.class},version = 1)
public abstract class Database extends RoomDatabase {
    public abstract LocalInfoDao localInfoDao();
}
