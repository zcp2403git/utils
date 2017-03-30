package com.xiaoka.xksupportutils.network;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.xiaoka.xksupportutils.file.SPUtil;
import com.xiaoka.xksupportutils.file.ToastUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;

/**
 * Created by Air on 2016/5/11.
 * Changed by miluo on 2016/11/02.
 */
public class ChangeHostUtil {
    public static final String KEY_HOSTS = "HOSTS.cfg";
    //    private static final Hosts DEFAULT_HOSTS;
    public static String SETTLE_DEBUG_HOST = "https://int-stmt.ddyc.com:8443";
    public static String XK_ZONE = "xk_zone";

    public static Build mBuild;
    private static ChangeHostUtil mInstance;

    public static ChangeHostUtil init(Build build) {
        if (null == mInstance) {
            build.hosts = Hosts.create(build.host, build.httpsHost);
            mInstance = new ChangeHostUtil(build);
        }
        return mInstance;
    }

    public static ChangeHostUtil getInstance() {
        if (null == mInstance) {
            throw new RuntimeException("must invoke init() before getInstance");
        }
        return mInstance;
    }

    private ChangeHostUtil(Build build) {
        this.mBuild = build;
    }

    public static class Build {
        private String host = "";
        private String httpsHost = "";
        private Context context;
        private Hosts hosts = Hosts.create(host, httpsHost);
        private String buildType;

        public Build(Context context) {
            this.context = context;
        }

        public Build setHost(String host) {
            this.host = host;
            return this;
        }

        public Build setHttpsHost(String httpsHost) {
            this.httpsHost = httpsHost;
            return this;
        }

        public Build setBuildType(String buildType) {
            this.buildType = buildType;
            return this;
        }

        public Build setHosts() {
            this.hosts = Hosts.create(host, httpsHost);
            return this;
        }

//        public ChangeEnvUtil build(){
//            hosts = Hosts.create(host, httpsHost);
//            return new ChangeEnvUtil(this);
//        }
    }

//    static {
//        DEFAULT_HOSTS = Hosts.create(BuildConfig.HOST, BuildConfig.HTTPS_HOST);
//    }

    @SuppressWarnings("all")
    public Hosts readHosts() {
        if (mBuild.buildType.equals("release")) return mBuild.hosts;

        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = mBuild.context.openFileInput(KEY_HOSTS);
            ois = new ObjectInputStream(fis);
            return Hosts.class.cast(ois.readObject());
        } catch (FileNotFoundException e) {
//            Log.e("", e.getLocalizedMessage());
        } catch (StreamCorruptedException e) {
//            Log.e("", e.getLocalizedMessage());
        } catch (IOException e) {
//            Log.e("", e.getLocalizedMessage());
        } catch (ClassNotFoundException e) {
//            Log.e("", e.getLocalizedMessage());
        } finally {
            try {
                if (ois != null) ois.close();
                if (fis != null) fis.close();
            } catch (IOException e) {
//                Log.e("", e.getLocalizedMessage());
            }
        }

        return mBuild.hosts;
    }

    public static void saveHosts(String httpUrl, String httpsUrl) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = mBuild.context.openFileOutput(KEY_HOSTS, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(new Hosts(httpUrl, httpsUrl));
        } catch (FileNotFoundException e) {
            Log.e("", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e("", e.getLocalizedMessage());
        } finally {
            try {
                if (oos != null) oos.close();
                if (fos != null) fos.close();
            } catch (IOException e) {
                Log.e("", e.getLocalizedMessage());
            }
        }

    }

    public static void deleteHosts() {
        mBuild.context.deleteFile(KEY_HOSTS);
    }

    public static void showXKZoneDialog(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        final EditText xkZoneEditText = generateEditText(context, "Project Name");
        String xkzone = SPUtil.getString(ChangeHostUtil.XK_ZONE);
        if(!TextUtils.isEmpty(xkzone)){
            xkZoneEditText.setText(xkzone);
        }
        linearLayout.addView(xkZoneEditText);
        (new AlertDialog.Builder(context)).setTitle("请输入项目名").setView(linearLayout).setNegativeButton("取消", (DialogInterface.OnClickListener)null).setPositiveButton("确认", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String xkZone = xkZoneEditText.getText().toString().trim();
                SPUtil.put(context, XK_ZONE, xkZone);
//                ServerConfig.xkZone = xkZone;
            }
        }).show();
    }

    private static EditText generateEditText(Context context, String hint) {
        EditText editText = new EditText(context);
        editText.setHint(hint);
        editText.setFadingEdgeLength(0);
        editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return editText;
    }

    /**
     * 此处传入的Context，不可以是Application,因为要显示弹框
     * @param context
     */
    public void showChooseDialog(Context context) {
        String[] envArr = {"int", "inta", "intb", "intc", "intd", "last", "api", "xkzone"};
        new AlertDialog.Builder(context)
                .setTitle("请选择环境")
                .setItems(envArr, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if(envArr[which].equals("xkzone")){
                            showXKZoneDialog(context);
                            return;
                        }

                        String httpStr;
                        String httpsStr;

                        if(envArr[which].equals("int")) {
                            httpStr = "http://" + envArr[which] + ".xiaokakeji.com:8091";
                            httpsStr = "https://" + envArr[which] + "-api.ddyc.com:8443";
                            SETTLE_DEBUG_HOST = "https://" + envArr[which] + "-stmt.ddyc.com:8443";
                        } else if(envArr[which].startsWith("int")) {
                            httpStr = "http://" + envArr[which] + ".xiaokakeji.com:8090";
                            httpsStr = "https://" + envArr[which] + "-api.ddyc.com:8443";
                            SETTLE_DEBUG_HOST = "https://" + envArr[which] + "-stmt.ddyc.com:8443";
                        } else if("last".equals(envArr[which])) {
                            httpStr = "http://last.api.yangchediandian.com";
                            httpsStr = "https://last-api.ddyc.com";
                            SETTLE_DEBUG_HOST = "https://last-stmt.ddyc.com";
                        } else if("api".equals(envArr[which])){
                            httpStr = "http://api.yangchediandian.com";
                            httpsStr = "https://api.ddyc.com";
                            SETTLE_DEBUG_HOST = "https://stmt.ddyc.com";
                        }else if("drm-api".equals(envArr[which])){
                            httpStr = "http://drm-api.ddyc.com";
                            httpsStr = "https://drm-api.ddyc.com";
                            SETTLE_DEBUG_HOST = "https://stmt.ddyc.com";
                        } else {
                            httpStr = "http://" + envArr[which] + ".yangchediandian.com";
                            httpsStr = "https://" + envArr[which] + ".ddyc.com";
                            SETTLE_DEBUG_HOST = "https://stmt.ddyc.com";
                        }

//                        if (envArr[which].startsWith("int")) {
//                            httpStr = "http://" + envArr[which] + ".xiaokakeji.com:8090";
//                            httpsStr = "https://" + envArr[which] + "-api.ddyc.com:8443";
//                            SETTLE_DEBUG_HOST = "https://" + envArr[which] + "-stmt.ddyc.com:8443";
//                        } else if ("last".equals(envArr[which])) {
//                            httpStr = "http://last.api.yangchediandian.com";
//                            httpsStr = "https://last-api.ddyc.com";
//                            SETTLE_DEBUG_HOST = "https://last-stmt.ddyc.com";
//                        } else if ("api".equals(envArr[which])) {
////                            RELEASE_HOST=\"http://api.yangchediandian.com\"
////                            HTTPS_RELEASE_HOST=\"https://api.ddyc.com\"
//                            httpStr = "http://api.yangchediandian.com";
//                            httpsStr = "https://api.ddyc.com";
//                            SETTLE_DEBUG_HOST = "https://stmt.ddyc.com";
//                        } else {
//                            httpStr = "http://" + envArr[which] + ".yangchediandian.com";
//                            httpsStr = "https://" + envArr[which] + ".ddyc.com";
//                            SETTLE_DEBUG_HOST = "https://stmt.ddyc.com";
//                        }
                        Log.i("ChangeHostUtil", "httpStr = " + httpStr);
                        Log.i("ChangeHostUtil", "httpsStr = " + httpsStr);
                        SPUtil.put(context, "SETTLE_HOST", SETTLE_DEBUG_HOST);
                        saveHosts(httpStr, httpsStr);
                        restart(context);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }

    public static void showModifyDialog(Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        final EditText httpEditText = generateEditText(context, "http url");
        httpEditText.setText("http://");
        linearLayout.addView(httpEditText);
        final EditText httpsEditText = generateEditText(context, "https url");
        httpsEditText.setText("https://");
        linearLayout.addView(httpsEditText);

        new AlertDialog.Builder(context)
                .setTitle("请输入环境")
                .setView(linearLayout)
                .setNegativeButton("取消", null)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String httpStr = getEditTextStr(httpEditText);
                        String httpsStr = getEditTextStr(httpsEditText);
                        if (TextUtils.isEmpty(httpStr) || TextUtils.isEmpty(httpsStr)) {
                            ToastUtil.showToast(context, "http or https url can not be null");
                        } else {
                            saveHosts(httpStr, httpsStr);
                            dialog.dismiss();
                            restart(context);
                        }
                    }
                }).show();
    }

    @NonNull
    private static String getEditTextStr(EditText editText) {
        return editText.getText().toString().trim();
    }

//    private static EditText generateEditText(Context context, String hint) {
//        EditText editText = new EditText(context);
//        editText.setHint(hint);
//        editText.setFadingEdgeLength(0);
//        editText.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//        return editText;
//    }

//    public static String getEnv() {
//        String host = ServerConfig.getServerHOST();
//
//        if (TextUtils.isEmpty(host)) {
//            return "未设置环境，请重新设置";
//        }
//
//        host = host.substring(0, host.indexOf('.'));
//        host = host.substring(host.lastIndexOf('/') + 1, host.length());
//        return host;
//    }

    private static void restart(Context context) {
        triggerRebirth(context);
    }

    private static final String KEY_RESTART_INTENT = "phoenix_restart_intent";

    /**
     * Call to restart the application process using the {@linkplain Intent#CATEGORY_DEFAULT default}
     * activity as an intent.
     * <p>
     * Behavior of the current process after invoking this method is undefined.
     */
    private static void triggerRebirth(Context context) {
        triggerRebirth(context, getRestartIntent(context));
    }

    /**
     * Call to restart the application process using the specified intent.
     * <p>
     * Behavior of the current process after invoking this method is undefined.
     */
    private static void triggerRebirth(Context context, Intent nextIntent) {

        String packageName = context.getPackageName();
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // In case we are called with non-Activity context.
        intent.putExtra(KEY_RESTART_INTENT, nextIntent);
        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
        Runtime.getRuntime().exit(0); // Kill kill kill!
    }

    private static Intent getRestartIntent(Context context) {
        Intent defaultIntent = new Intent(Intent.ACTION_MAIN, null);
        defaultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        defaultIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        String packageName = context.getPackageName();
        PackageManager packageManager = context.getPackageManager();
        for (ResolveInfo resolveInfo : packageManager.queryIntentActivities(defaultIntent, 0)) {
            ActivityInfo activityInfo = resolveInfo.activityInfo;
            if (activityInfo.packageName.equals(packageName)) {
                defaultIntent.setComponent(new ComponentName(packageName, activityInfo.name));
                return defaultIntent;
            }
        }

        throw new IllegalStateException("Unable to determine default activity for "
                + packageName
                + ". Does an activity specify the DEFAULT category in its intent filter?");
    }

    public static class Hosts implements Serializable {
        private static final long serialVersionUID = 45204023812093L;
        public final String httpUrl;
        public final String httpsUrl;

        public Hosts(String httpUrl, String httpsUrl) {
            this.httpUrl = httpUrl;
            this.httpsUrl = httpsUrl;
        }

        public static Hosts create(String httpUrl, String httpsUrl) {
            return new Hosts(httpUrl, httpsUrl);
        }

    }

}
