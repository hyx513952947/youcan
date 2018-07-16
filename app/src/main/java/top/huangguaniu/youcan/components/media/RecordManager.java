package top.huangguaniu.youcan.components.media;

/**
 *
 * @author 侯延旭
 * @date 2018/7/16
 */
public interface RecordManager {
    void start();
    void stop();
    void release();
    void cancel();
    void pause();
    void onStateChange(int state);
}
