package top.huangguaniu.youcan.data.local.room.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Observable;
import top.huangguaniu.youcan.data.local.room.bean.LocalInfoEntity;

/**
 *
 * @author 侯延旭
 * @date 2018/7/18
 */
@Dao
public interface LocalInfoDao {
    @Query("select * from local")
    Observable<List<LocalInfoEntity>> queryLocalInfo();
}
