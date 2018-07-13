package top.huangguaniu.youcan.components.media;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import okio.BufferedSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by 侯延旭 on 2018/7/13.
 */
public class FileIo {
    /**
     * 字节流写入文件
     */
    public static void byteToFile(byte[] bytes, File file){
        try {
            Sink sink = Okio.sink(file);
            BufferedSink bufferedSink = Okio.buffer(sink);
            bufferedSink.write(bytes);
            bufferedSink.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
