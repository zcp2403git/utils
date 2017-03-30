package com.xiaoka.xksupportutils.ui;

import android.graphics.Bitmap;
import android.view.View;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by changping on 16/10/25.
 * 截屏用的
 */

public class PrintScreenUtils {
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    public static void saveScreen4Pic(View v, String path) {
        String fname = path + sdf.format(new Date()) + ".png";
        View view = v.getRootView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(fname);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
    }

    public static void saveView4Pic(View view, String path) {

        String fname = path + sdf.format(new Date()) + ".png";
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        if (bitmap != null) {
            try {
                FileOutputStream out = new FileOutputStream(fname);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
