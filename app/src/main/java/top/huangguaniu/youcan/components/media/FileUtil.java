package top.huangguaniu.youcan.components.media;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

import top.huangguaniu.youcan.ui.main.views.Logger;

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
}
