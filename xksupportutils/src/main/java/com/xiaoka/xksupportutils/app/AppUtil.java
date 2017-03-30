package com.xiaoka.xksupportutils.app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by changping on 16/12/5.
 */
public class AppUtil {


    /**
     * 跳到应用市场，下载应用
     *
     * @param context
     * @param packName
     */
    public static void jumpToMarket(Context context, String packName) {
        String str = "market://details?id=" + packName;
        Intent localIntent = new Intent("android.intent.action.VIEW");
        localIntent.setData(Uri.parse(str));
        try {
            context.startActivity(localIntent);
        } catch (Exception e) {
            //  PromptUtil.showNormalToast("没有检测到应用市场，请先安装");
        }
    }


    public static String getMacAddress(Context context) {
        WifiInfo wifiInfo = ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
        if (wifiInfo != null) {
            return wifiInfo.getMacAddress();
        } else {
            return "";
        }
    }

    /**
     * 得到设备名字
     */
    public static String getDeviceName() {
        String model = android.os.Build.MODEL;
        if (TextUtils.isEmpty(model)) {
            return "";
        } else {
            return model;
        }
    }

    /**
     * 得到品牌名字
     */
    public static String getBrandName() {
        String brand = android.os.Build.BRAND;
        if (TextUtils.isEmpty(brand)) {
            return "";
        } else {
            return brand.replace(" ", "_");
        }
    }

    public static String getIMEI(Context context) {
        try {
            String deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (TextUtils.isEmpty(deviceId)) {
                return "";
            } else {
                return deviceId.replace(" ", "");
            }
        } catch (Exception e) {
            return "";
        }
    }

    public static String getDeviceId() {
//		TelephonyManager tm = (TelephonyManager) XKApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
//		String DEVICE_ID = tm.getDeviceId();
//		return DEVICE_ID;
        String str = getIMEI() + getDeviceName() + getBrandName() /*
                                                                 * +
																 * getMacAddress
																 * ()
																 */;
        return MD5Helper(str);
    }

    public static String MD5Helper(String str) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
            byte[] byteArray = messageDigest.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteArray.length; i++) {
                if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                    sb.append("0").append(
                            Integer.toHexString(0xFF & byteArray[i]));
                } else {
                    sb.append(Integer.toHexString(0xFF & byteArray[i]));
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("no device Id");
    }

    public static String getIMEI() {
        try {
            String deviceId = ((TelephonyManager) ApplicationUtil.getContext()
                    .getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (null == deviceId || deviceId.length() <= 0) {
                return "";
            } else {
                return deviceId.replace(" ", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
