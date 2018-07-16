package top.huangguaniu.youcan.components.media;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by 侯延旭 on 2018/7/12.
 */
public class FileUtil {
    static String FILENAME ="/daydayup/";

    public static String getExtraPath(){
        File file =  Environment.getExternalStorageDirectory();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File fileCreated = new File(file.getPath()+FILENAME);
            if (!fileCreated.exists()){
                fileCreated.mkdirs();
            }
            File fileImage = new File(fileCreated.getPath()+"/images/");
            if (!fileImage.exists()){
                fileImage.mkdirs();
            }
            return file.getPath();
        }
        return file.getPath();
    }
    public static String getImagesPath(){
        File file =  Environment.getExternalStorageDirectory();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File fileCreated = new File(file.getPath()+FILENAME+"/images/");
            if (!fileCreated.exists()){
                fileCreated.mkdirs();
            }
            return fileCreated.getPath();
        }
        return null;
    }
    public static String getNewImagePath(String name){
        File file =  Environment.getExternalStorageDirectory();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File fileCreated = new File(file.getPath()+FILENAME+"/images",name);
            if (!fileCreated.exists()){
                try {
                    fileCreated.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return fileCreated.toString();
        }
        return null;
    }
    public static File getNewImageFile(String name){
        File file =  Environment.getExternalStorageDirectory();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File fileCreated = new File(file.getPath()+FILENAME+"/images",name);
            if (!fileCreated.exists()){
                try {
                    fileCreated.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return fileCreated;
        }
        return null;
    }

    /**
     * 手绘
     */
    public static File getNewDrawFile(String name){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File filePath = new File(Environment.getExternalStorageDirectory().getPath()+FILENAME+"/images/draws/");
            if (!filePath.exists()){
                filePath.mkdirs();
            }
            File fileCreated = new File(Environment.getExternalStorageDirectory().getPath()+FILENAME+"/images/draws",name);
            if (!fileCreated.exists()){
                try {
                    fileCreated.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return fileCreated;
        }
        return null;
    }
    /**
     * 录音
     */
    public static File getRecordFile(String name){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File filePath = new File(Environment.getExternalStorageDirectory().getPath()+FILENAME+"/records/");
            if (!filePath.exists()){
                filePath.mkdirs();
            }
            File fileCreated = new File(Environment.getExternalStorageDirectory().getPath()+FILENAME+"/records",name);
            if (!fileCreated.exists()){
                try {
                    fileCreated.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return fileCreated;
        }
        return null;
    }

    /**
     * 录音的PCM缓存列表
     */
    public static File getRecordCacheFile(String name){
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File filePath = new File(Environment.getExternalStorageDirectory().getPath()+FILENAME+"/records/cache/");
            if (!filePath.exists()){
                filePath.mkdirs();
            }
            File fileCreated = new File(Environment.getExternalStorageDirectory().getPath()+FILENAME+"/records/cache",name);
            if (!fileCreated.exists()){
                try {
                    fileCreated.createNewFile();
                } catch (IOException e) {
                    return null;
                }
            }
            return fileCreated;
        }
        return null;
    }
}
