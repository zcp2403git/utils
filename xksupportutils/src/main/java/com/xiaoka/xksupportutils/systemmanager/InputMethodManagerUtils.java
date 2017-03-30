package com.xiaoka.xksupportutils.systemmanager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by changping on 16/12/1.
 */

public class InputMethodManagerUtils {


    public static void showKeyboard(Context context, View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view , InputMethodManager.SHOW_FORCED);
    }

    public static void hideKeyboard(Activity context) {
        hideKeyboard(context,context.getCurrentFocus());
    }

    public static void hideKeyboard(Activity context,View view) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(view.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }


}
