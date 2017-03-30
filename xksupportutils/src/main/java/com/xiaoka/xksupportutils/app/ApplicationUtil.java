package com.xiaoka.xksupportutils.app;

import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created by changping on 16/9/1.
 */
public class ApplicationUtil {
    private static Context mContext;

    /**
     *
     * 目前已知bug:在retrofit中的okhttp拦截器中使用，在4.4一下手机会出现mContext反射获取不到
     * auditor changping
     * @return mContext
     */
    public static Context getContext() {
        if (mContext == null) {
            try {
                Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
                Method method = activityThreadClass.getMethod("currentApplication");
                mContext = (Context) method.invoke(null, (Object[]) null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mContext;
    }
    /**
     * 解决上面的bug
     *
     * @param context
     * @return
     */
    public static void init(Context context) {
        if (context == null ){
            getContext();
        }else
            mContext=context;
    }
}
