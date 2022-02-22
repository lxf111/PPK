package com.uni.commoncore.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: log日志的
 *
 * @author zjn
 * Email：168455992@qq.com
 * @date 2019/1/15
 */
public class LogUtils {

    private static boolean isShowLog = true;

    /**
     * 截断输出日志
     *
     * @param msg
     */
    public static void e(String tag, String msg) {
        if (isShowLog) {
            msg = replaceBlank(msg);
            if (tag == null || tag.length() == 0
                    || msg == null || msg.length() == 0)
                return;
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                Log.e(tag, msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    Log.e(tag, logContent);
                }
                Log.e(tag, msg);// 打印剩余日志
            }
        }
    }

    public static String replaceBlank(String src) {
        String dest = "";
        if (src != null) {
            Pattern pattern = Pattern.compile("\t|\r|\n|\\s*");
            Matcher matcher = pattern.matcher(src);
            dest = matcher.replaceAll("");
        }
        return dest;
    }
}
