package com.lf.timepicker.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.IOException;

/**
 * Created by LiFei on 2017/5/23.
 */

public class ZoomUtil {
    // 缩放图片
    public static Bitmap zoomImg(String img, int newWidth, int newHeight) {
    // 图片源
        Bitmap bm = BitmapFactory.decodeFile(img);
        if (null != bm) {
            return zoomImg(bm, newWidth, newHeight);
        }
        return null;
    }

    public static Bitmap zoomImg(Context context, String img, int newWidth, int newHeight) {
// 图片源
        try {
            Bitmap bm = BitmapFactory.decodeStream(context.getAssets()
                    .open(img));
            if (null != bm) {
                return zoomImg(bm, newWidth, newHeight);
            }
        } catch (IOException e) {
         // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    // 缩放图片
    private static Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }
}
