package com.xiaoka.xksupportutils.systemmanager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;

/**
 * Created by changping on 16/12/5.
 */

public class PackageManagerUtils {

    public static String getAppVersion(Context mContext) {
        PackageManager pm = mContext.getPackageManager();
        try {
            // 代表的就是清单文件的信息。
            PackageInfo packageInfo = pm.getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            // 肯定不会发生。
            // can't reach
            return "";
        }
    }


    /**
     * 判断应用是否安装
     * @param context
     * @param appPackName
     * @return
     */
    public static boolean isAppInstall(Context context,String appPackName) {
        PackageInfo packageInfo;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(appPackName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }

        return packageInfo != null;
    }

    /**
     * 根据报名检查应用是否安装到本地
     *
     * created at 2016/11/9 19:38
     */
    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * 获取Meta-Data
     *
     * @param context
     * @param key
     * @return
     */
    public static String getAppMetaData(Context context, String key) {
        if (context == null || TextUtils.isEmpty(key)) {
            return null;
        }
        String resultData = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        resultData = applicationInfo.metaData.getString(key);
                    }
                }

            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return resultData;
    }

    //判断一个Schema是否有效
    public static boolean isSchemaValid(Context context,String schema) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(schema));
        List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
        return  !activities.isEmpty();
    }


}
