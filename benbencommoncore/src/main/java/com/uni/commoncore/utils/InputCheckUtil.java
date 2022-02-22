package com.uni.commoncore.utils;


import android.app.Activity;
import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类名：InputCheck 说明：输入内容的检查工具类
 */
public class InputCheckUtil {
    /**
     * java 验证端口号是否合法
     *
     * @param port 端口号
     * @return
     */
    public static boolean checkPort(String port) {
        boolean flag = false;
        Pattern pattern = Pattern
                .compile("([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])");
        Matcher m = pattern.matcher(port);
        flag = m.matches();
        return flag;
    }

    /**
     * Java 验证Ip是否合法
     */
    public static boolean checkIp(String ip) {
        boolean flag = false;
        Pattern pattern = Pattern
                .compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ip);
        flag = m.matches();
        return flag;
    }

    /**
     * 身份证验证
     *
     * @param strIdCard
     * @return
     */
    public static boolean checkIdCard(String strIdCard) {
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        boolean flag = false;
        Pattern idNumPattern = Pattern.compile("^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$");
        Matcher m = idNumPattern.matcher(strIdCard);
        flag = m.matches();
        return flag;
    }

    /**
     * Java 验证手机号是否合法 头三位大于130的11位数
     */
    public static boolean checkPhoneNum(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        boolean flag = false;
//		Pattern pattern = Pattern
//				.compile("^[1]([3][0-9]{1}|[7][0-9]{1}|59|58|88|89)[0-9]{8}$");
        Pattern pattern = Pattern
                .compile("^[1][3-9]{1}[0-9]{9}$");
        Matcher m = pattern.matcher(ip);
        flag = m.matches();
        return flag;
    }

    //判断email格式是否正确
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

    public static String hidePhone(String phone) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(phone) && phone.length() > 6) {
            for (int i = 0; i < phone.length(); i++) {
                char c = phone.charAt(i);
                if (i >= 3 && i <= 6) {
                    sb.append('*');
                } else {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    //过滤表情
    public static void filterEmoji(EditText editText, final Activity mContext) {
        InputFilter emojiFilter = new InputFilter() {
            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    Toast.makeText(mContext, "不支持输入表情", Toast.LENGTH_SHORT).show();
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{emojiFilter});
    }

    //过滤符号
    public static void filterSymbol(EditText editText, final Activity mContext) {
        InputFilter inputFilter = new InputFilter() {

            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    Toast.makeText(mContext, "只能输入汉字,英文，数字", Toast.LENGTH_SHORT).show();
                    return "";
                }

            }
        };
        editText.setFilters(new InputFilter[]{inputFilter});
    }

    /**
     * 规则1：至少包含大小写字母及数字中的一种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        boolean isLetterOrDigit = false;//定义一个boolean值，用来表示是否包含字母或数字
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLetterOrDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }

    /**
     * 规则2：至少包含大小写字母及数字中的两种
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     * 规则3：必须同时包含大小写字母及数字
     * 是否包含
     *
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
        boolean isLowerCase = false;//定义一个boolean值，用来表示是否包含字母
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true;
            } else if (Character.isLowerCase(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }

    /**
     * 判断EditText输入的数字、中文还是字母方法
     */
    public static void whatIsInput(Context context, EditText edInput) {
        String txt = edInput.getText().toString();

        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(context, "输入的是数字", Toast.LENGTH_SHORT).show();
        }
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(context, "输入的是字母", Toast.LENGTH_SHORT).show();
        }
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(txt);
        if (m.matches()) {
            Toast.makeText(context, "输入的是汉字", Toast.LENGTH_SHORT).show();
        }
    }

}
