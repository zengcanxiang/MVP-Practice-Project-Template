package cn.zengcanxiang.mvp_practice_project_template.util;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import es.dmoral.toasty.Toasty;

public class ToastUtils {
    private static Toast t;

    public static void show(Context context, int resId) {
        show(context, context.getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        show(context, context.getResources().getText(resId), duration);
    }

    public static void show(Context context, String text) {
        show(context, text, Toast.LENGTH_SHORT);
    }

    public static void show(final Context context, final CharSequence text, final int duration) {
        if (t != null) {
            t.cancel();
        }
        Activity activity = AppManager.getAppManager().currentActivity();
        if (activity == null) {
            try {
                t = Toasty.normal(context, text, duration);
                t.show();
            } catch (Exception e) {
                Logger.e("Activity is not null,show toast try error");
            }
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //防止内存泄露，静态Toast使用application
                t = Toasty.normal(context.getApplicationContext(), text, duration);
                t.show();
            }
        });
    }

    public static void show(Context context, int resId, Object... args) {
        show(context,
                String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        show(context, String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration,
                            Object... args) {
        show(context,
                String.format(context.getResources().getString(resId), args),
                duration);
    }

    public static void show(Context context, String format, int duration,
                            Object... args) {
        show(context, String.format(format, args), duration);
    }

}
