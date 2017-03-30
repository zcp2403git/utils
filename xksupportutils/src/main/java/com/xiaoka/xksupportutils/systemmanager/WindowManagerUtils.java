package com.xiaoka.xksupportutils.systemmanager;

import android.app.Activity;
import android.view.WindowManager;

/**
 * Created by changping on 16/11/28.
 */

public class WindowManagerUtils {

    /**
     * 设置屏幕的背景透明度
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity context, float bgAlpha)
    {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }


}
