package com.sexyuncle.widgetsdemo;

import android.util.Log;

import java.util.Locale;

/**
 * Created by dev-sexyuncle on 16/4/22.
 */
public class LogUtil {
    private static final boolean DEBUG = true;//是否可以debug
    private static final String TAG = WidgetApplication.getInstance().getPackageName();


    /**
     * @description 打印调试信息
     * @param format 字符串格式化形式
     * @param objects 要打印的对象
     */
    public static void D(String format,Object...objects){
        if (DEBUG){
            Log.d(TAG,buildMessage(format,objects));
        }
    }
    /**
     * @description 打印错误信息
     * @param format 字符串格式化形式
     * @param objects 要打印的对象
     */
    public static void E(String format,Object...objects){
        if (DEBUG){
            Log.e(TAG,buildMessage(format,objects));
        }
    }

    /**
     * Formats the caller's provided message and prepends useful info like
     * calling thread ID and method name.
     */
    private static String buildMessage(String format, Object... args) {
        String msg = (args == null) ? format : String.format(Locale.US, format, args);
        StackTraceElement[] trace = new Throwable().fillInStackTrace().getStackTrace();

        String caller = "<unknown>";
        // Walk up the stack looking for the first caller outside of VolleyLog.
        // It will be at least two frames up, so start there.
        for (int i = 2; i < trace.length; i++) {
            Class<?> clazz = trace[i].getClass();
            if (!clazz.equals(LogUtil.class)) {
                String callingClass = trace[i].getClassName();
                callingClass = callingClass.substring(callingClass.lastIndexOf('.') + 1);
                callingClass = callingClass.substring(callingClass.lastIndexOf('$') + 1);

                caller = callingClass + "." + trace[i].getMethodName();
                break;
            }
        }
        return String.format(Locale.US, "[%d] %s: %s",
                Thread.currentThread().getId(), caller, msg);
    }
}
