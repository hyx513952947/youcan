package top.huangguaniu.youcan.components.media;

import java.io.File;

/**
 * Created by 侯延旭 on 2018/7/13.
 */
public interface AudioPlayer{
    void prepare(File file);
    void play();
    void stop();
    void pause();
    void rePlay();
}
