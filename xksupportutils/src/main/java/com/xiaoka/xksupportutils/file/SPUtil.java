package com.xiaoka.xksupportutils.file;

import android.content.Context;
import android.content.SharedPreferences;


import com.xiaoka.xksupportutils.app.ApplicationUtil;

import java.util.Map;
import java.util.Set;

import rx.functions.Func1;


public class SPUtil {
    private SPUtil(){}

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";

    private static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    private static <T> void editorDoFunc(Context context, Func1<SharedPreferences.Editor, T> funcEditor) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor editor = sp.edit();
        funcEditor.call(editor);
        editor.apply();
    }

    public static <T> void put(Context context, final String key, final T value) {
        editorDoFunc(context, new Func1<SharedPreferences.Editor, Void>() {
            @Override
            public Void call(SharedPreferences.Editor editor) {
                if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (Long) value);
                } else if(value instanceof Set){
                    editor.putStringSet(key, (Set<String>) value);
                } else {
                    editor.putString(key, value.toString());
                }
                return null;
            }
        });
    }

    /**
     * 移除某个key值已经对应的值
     * @param context
     * @param key
     */
    public static void remove(Context context, final String key) {
        editorDoFunc(context, new Func1<SharedPreferences.Editor, Void>() {
            @Override
            public Void call(SharedPreferences.Editor editor) {
                editor.remove(key);
                return null;
            }
        });
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        editorDoFunc(context, new Func1<SharedPreferences.Editor, Void>() {
            @Override
            public Void call(SharedPreferences.Editor editor) {
                editor.clear();
                return null;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Context context, String key, T defaultValue) {
        SharedPreferences sp = getSp(context);

        if (defaultValue instanceof String) {
            return (T) sp.getString(key, (String) defaultValue);
        } else if (defaultValue instanceof Integer) {
            return (T) Integer.valueOf(sp.getInt(key, (Integer) defaultValue));
        } else if (defaultValue instanceof Boolean) {
            return (T) Boolean.valueOf(sp.getBoolean(key, (Boolean) defaultValue));
        } else if (defaultValue instanceof Long) {
            return (T) Long.valueOf(sp.getLong(key, (Long) defaultValue));
        } else if (defaultValue instanceof Float) {
            return (T) Float.valueOf(sp.getFloat(key, (Float) defaultValue));
        } else if(defaultValue instanceof Set){
            return (T) sp.getStringSet(key, (Set<String>) defaultValue);
        }

        return defaultValue;
    }

    //modify by changping
    public static boolean getBoolean(Context context, String prefKey ) {
        return getSp(context).getBoolean(prefKey, false);
    }

    public static float getFloat(Context context, String prefKey) {
        return getSp(context).getFloat(prefKey, 0.0f);
    }

    public static int getInt(Context context, String prefKey) {
        return getSp(context).getInt(prefKey, 0);
    }

    public static long getLong(Context context, String prefKey) {
        return getSp(context).getLong(prefKey, 0);
    }

    public static String getString(Context context, String prefKey) {
        return getSp(context).getString(prefKey, "");
    }

    public static boolean getBoolean( String prefKey ) {
        return getSp(ApplicationUtil.getContext()).getBoolean(prefKey, false);
    }

    public static float getFloat( String prefKey) {
        return getSp(ApplicationUtil.getContext()).getFloat(prefKey, 0.0f);
    }

    public static int getInt( String prefKey) {
        return getSp(ApplicationUtil.getContext()).getInt(prefKey, 0);
    }

    public static long getLong( String prefKey) {
        return getSp(ApplicationUtil.getContext()).getLong(prefKey, 0);
    }

    public static String getString( String prefKey) {
        return getSp(ApplicationUtil.getContext()).getString(prefKey, "");
    }

    public static <T> void put( final String key, final T value) {
        editorDoFunc(ApplicationUtil.getContext(), new Func1<SharedPreferences.Editor, Void>() {
            @Override
            public Void call(SharedPreferences.Editor editor) {
                if (value instanceof String) {
                    editor.putString(key, (String) value);
                } else if (value instanceof Integer) {
                    editor.putInt(key, (Integer) value);
                } else if (value instanceof Boolean) {
                    editor.putBoolean(key, (Boolean) value);
                } else if (value instanceof Float) {
                    editor.putFloat(key, (Float) value);
                } else if (value instanceof Long) {
                    editor.putLong(key, (Long) value);
                } else if(value instanceof Set){
                    editor.putStringSet(key, (Set<String>) value);
                } else {
                    editor.putString(key, value.toString());
                }
                return null;
            }
        });
    }
    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public static void remove( final String key) {
        editorDoFunc(ApplicationUtil.getContext(), new Func1<SharedPreferences.Editor, Void>() {
            @Override
            public Void call(SharedPreferences.Editor editor) {
                editor.remove(key);
                return null;
            }
        });
    }
    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        return getSp(context).getAll();
    }
}
