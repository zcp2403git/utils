package com.xiaoka.xksupportutils.device;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.xiaoka.xksupportutils.file.ToastUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机组件调用工具类
 *
 * @author jingle1267@163.com
 */
public final class PhoneUtil {

    /**
     * Don't let anyone instantiate this class.
     */
    private PhoneUtil() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * 调用系统发短信界面
     *
     * @param activity    Activity
     * @param phoneNumber 手机号码
     * @param smsContent  短信内容
     */
    public static void sendMessage(Context activity, String phoneNumber,
                                   String smsContent) {
        if (phoneNumber == null || phoneNumber.length() < 4) {
            return;
        }
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", smsContent);
        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(it);

    }

    /**
     * 调用系统打电话界面
     *
     * @param context     上下文
     * @param phoneNumber 手机号码
     */
    public static void callPhones(Context context, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 1) {
            return;
        }
        Uri uri = Uri.parse("tel:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_CALL, uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void callPhonesWithDialog(Context context, String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < 1) {
            ToastUtil.showToast("号码不存在");
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("确定拨打 "+phoneNumber+" ?");
        builder.setCancelable(true);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Uri uri = Uri.parse("tel:" + phoneNumber);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", null);
        builder.show();

    }

    /**
     * 判断字符串是否是手机号码格式
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((1[0-9]))\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }
}
