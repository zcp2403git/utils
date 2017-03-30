package com.xiaoka.xksupportutils.exception;

import android.util.Log;

/**
 * Created by changping on 16/12/1.
 */

public class ExceptionLog {

    public static void recordException(Exception e){

        Log.e("recordException", "recordException:  ",e );
    }
}
