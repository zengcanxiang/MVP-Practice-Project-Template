package cn.zengcanxiang.mvp_practice_project_template.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.orhanobut.logger.Logger;

import java.util.Map;

/**
 * SharedPreferences 工具类
 */
public class SharePreUtil {

    private static final String SP = "SharePreData";

    private static SharedPreferences sp;

    private SharePreUtil() {
    }

    public static void init(Application application) {
        sp = application.getSharedPreferences(SP,
                Context.MODE_PRIVATE);
    }

    public static String getStringValue(String key) {
        return getStringValue(key, "");
    }

    public static String getStringValue(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    public static int getIntValue(String key) {
        return getIntValue(key, 0);
    }

    public static int getIntValue(String key, int defValue) {
        return sp.getInt(key, defValue);
    }

    public static boolean getBooleanValue(String key, boolean defValue) {
        return sp.getBoolean(key, defValue);
    }

    public static Map<String, ?> getAllValue() {
        return sp.getAll();
    }

    public static float getFloatValue(String key) {
        return sp.getFloat(key, 0);
    }

    public static Long getLongValue(String key) {
        return sp.getLong(key, 0);
    }

    public static boolean putString(String key, String value) {
        Editor editor = sp.edit();
        editor.putString(key, value);
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("putStr失败");
        }
        return fruit;
    }

    public static boolean putInt(String key, int value) {
        Editor editor = sp.edit();
        editor.putInt(key, value);
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("putInt失败");
        }
        return fruit;
    }

    public static boolean putLong(String key, Long value) {
        Editor editor = sp.edit();
        editor.putLong(key, value);
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("putLong失败");
        }
        return fruit;
    }

    public static boolean putBoolean(String key, boolean value) {
        Editor editor = sp.edit();
        editor.putBoolean(key, value);
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("putBoolean失败");
        }
        return fruit;
    }

    public static boolean clear() {
        Editor editor = sp.edit();
        editor.clear();
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("clear失败");
        }
        return fruit;
    }

    public static boolean delete(String key) {
        Editor editor = sp.edit();
        editor.remove(key);
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("delete失败");
        }
        return fruit;
    }

    public static boolean putJSONObject(String key, Object value) {
        String str = JSON.toJSONString(value);
        Editor editor = sp.edit();
        editor.putString(key, str);
        boolean fruit = editor.commit();
        if (!fruit) {
            Logger.e("putObj失败");
        }
        return fruit;
    }

    public static <T> T getJSONObject(String key, Class<T> c) {
        String json = sp.getString(key, "");
        return JSON.parseObject(json, c);
    }
}
