package com.xiaoka.xksupportutils.ui;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

/**
 * Created by changping on 2016/8/20 0020.
 */
public class StringUtils {

    public static boolean isEmpty(CharSequence cs) {
        return (cs == null) || (cs.length() == 0);
    }

    public static boolean isEmpty(String  cs) {
        return (cs == null) || (cs.trim().equals(""));
    }

    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    public static boolean isNotEmpty(String cs) {
        return !isEmpty(cs);
    }

    public static void filtNull(TextView textView,String s) {
        if (s != null) {
            textView.setText(s);
        } else {
            textView.setText(filtNull(s));
        }
    }


    public static void ifNullNotShow(TextView view, String s) {
        if (TextUtils.isEmpty(s)){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
            view.setText(s);
        }
    }

    public static void ifNullNotSet(TextView view, String s) {
        String tmp= filtNull(s);
        if ("".equals(tmp)){
        }else {
            view.setText(tmp);
        }
    }


    //判断过滤单个string为null
    public static String filtNull(String s) {
        if (s!=null) {
           return s;
        } else {
            return "";
        }
    }


    //判断单个对象不为null
    public static boolean filtObjectNull(Object object) {
        if (object != null) {
           return false;
        } else {
           return true;
        }
    }




}
