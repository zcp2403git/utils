package com.xiaoka.xksupportutils.file;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import com.xiaoka.xksupportutils.app.ApplicationUtil;

/**
 * Created by changping on 16/9/1.
 */
public class ToastUtil {
    private static Toast mToast = null;

    public static void showToast(final String msg, boolean timeLong, Context context) {
        if (TextUtils.isEmpty(msg)) return;
        if (mToast == null) {
//            if (mToast!=null)
//                mToast.cancel();
            if (timeLong)
                mToast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
            else
                mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);

        } else {
            mToast.setText(msg);
        }
        mToast.show();

    }

    public static void showToast(Context context, String msg) {
        showToast(msg, false, context);
    }

    public static void showToast(String msg) {
        showToast(msg, false, ApplicationUtil.getContext());
    }

    public void showToastInThread(String msg) {
        Looper.prepare();
        if (mToast == null) {
//            if (mToast!=null)
//                mToast.cancel();
            mToast = Toast.makeText(ApplicationUtil.getContext(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
        Looper.loop();
    }
}
