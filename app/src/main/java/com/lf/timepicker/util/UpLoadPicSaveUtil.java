package com.lf.timepicker.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Lifei on 2016/12/27.
 */

public class UpLoadPicSaveUtil {
    private static String backetName = "images";

    public static String saveFile(Context context, Bitmap bitmap) {
        String filePath = null;
//        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
//        if (hasSDCard) { // SD卡根目录
//            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + backetName + File.separator + System.currentTimeMillis() + ".jpg";
        filePath = context.getFilesDir().getAbsolutePath().toString() + File.separator + backetName + File.separator + System.currentTimeMillis() + ".jpg";
//        } else  // 系统下载缓存根目录
//            filePath = Environment.getDownloadCacheDirectory().toString()+ File.separator + backetName  + File.separator + System.currentTimeMillis() + ".jpg";

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i("imagePath", "图片已保存在" + filePath);
        return filePath;
    }
}

