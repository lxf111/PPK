package com.uni.ppk.utils;

import com.uni.commoncore.utils.StringUtils;

import java.math.BigDecimal;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/11/21
 * Time: 17:48
 */
public class ArithUtils {
    //默认除法的运算精度
    private static final int DEF_DIV_SCALE = 10;

    // 这个类不能实例化
    private ArithUtils() {

    }

    /**
     * 显示过万的数据
     *
     * @param num
     * @return
     */
    public static String showNumber(String num) {
        String number = "";
        try {
            long num1 = Long.parseLong(num);
            if (num1 < 10000) {
                number = num;
            } else if (num1 > 10000) {
                number = round(Double.parseDouble(mul(num1, 0.0001)), 2) + "w";
            }
        } catch (Exception e) {
            e.printStackTrace();
            number = "";
        }
        return number;
    }

    /**
     * 显示过万的数据
     *
     * @param num
     * @return
     */
    public static String showNumber(long num) {
        String number = "";
        if (num < 10000) {
            number = "" + num;
        } else if (num > 10000) {
            number = round(Double.parseDouble(mul(num, 0.0001)), 2) + "w";
        }
        return number;
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return String.valueOf(b1.add(b2));
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static String add(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            v1 = "0";
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.add(b2));
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return String.valueOf(b1.subtract(b2));
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static String sub(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            v1 = "0";
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return String.valueOf(b1.subtract(b2));
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        BigDecimal bg = new BigDecimal("" + b1.multiply(b2));
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return String.valueOf(f1);
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2) {
        if (StringUtils.isEmpty(v1)) {
            v1 = "0";
        }
        if (StringUtils.isEmpty(v2)) {
            v2 = "0";
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        BigDecimal bg = new BigDecimal("" + b1.multiply(b2));
        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return String.valueOf(f1);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static String div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return String.valueOf(b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);

        BigDecimal result = null;
        try {
            result = b1.divide(b2);
        } catch (Exception e) {
            result = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
        }
        return String.valueOf(result);
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return String.valueOf(b.divide(one, scale, BigDecimal.ROUND_HALF_UP));
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static String round(String v, int String, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");

        b.divide(one, scale, BigDecimal.ROUND_HALF_UP);

        return null;
    }

    /**
     * 保留两位有效数字
     *
     * @param f
     * @return
     */
    public static String saveSecond(String f) {
        if (StringUtils.isEmpty(f)) {
            f = "0";
        }
//        BigDecimal bg = new BigDecimal(f);
//        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return String.valueOf(f1);
        try {
            BigDecimal b = new BigDecimal(f);
            BigDecimal one = new BigDecimal("1");
            return String.valueOf(b.divide(one, 2, BigDecimal.ROUND_HALF_UP));
        } catch (Exception e) {
            e.printStackTrace();
            return "" + f;
        }
    }

    public static String saveSecond(double f) {
//        BigDecimal bg = new BigDecimal(f);
//        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return String.valueOf(f1);
        try {
            BigDecimal b = new BigDecimal(f);
            BigDecimal one = new BigDecimal("1");
            return String.valueOf(b.divide(one, 2, BigDecimal.ROUND_HALF_UP));
        } catch (Exception e) {
            e.printStackTrace();
            return "" + f;
        }
    }

    public static String saveDistance(double f) {
//        BigDecimal bg = new BigDecimal(f);
//        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return String.valueOf(f1);
        if (f > 1000) {
            f = f / 1000;
            BigDecimal b = new BigDecimal(f);
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, 2, BigDecimal.ROUND_HALF_UP) + "km";
        }
        BigDecimal b = new BigDecimal(f);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, 2, BigDecimal.ROUND_HALF_UP) + "m";
    }

    public static String saveDistance(String f) {
        if (StringUtils.isEmpty(f)) {
            f = "0";
        }
//        BigDecimal bg = new BigDecimal(f);
//        double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
//        return String.valueOf(f1);
        try {
            if (Double.parseDouble(f) > 1000) {
                double f1 = Double.parseDouble(f) / 1000;
                BigDecimal b = new BigDecimal(f1);
                BigDecimal one = new BigDecimal("1");
                return b.divide(one, 2, BigDecimal.ROUND_HALF_UP) + "km";
            }
            BigDecimal b = new BigDecimal(f);
            BigDecimal one = new BigDecimal("1");
            return b.divide(one, 2, BigDecimal.ROUND_HALF_UP) + "m";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "100m";
    }

    public static String saveInt(String f) {
        if (StringUtils.isEmpty(f)) {
            f = "0";
        }
        return "" + Math.round(Float.parseFloat(f));
    }

    public static String saveInt(double f) {
        return "" + Math.round(f);
    }

}
