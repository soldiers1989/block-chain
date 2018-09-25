package com.xdaocloud.futurechain.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符工具
 */
public class CharacterUtils {

    /**
     * 判断是否含有特殊字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);

        return m.find();
    }

    /**
     * 该方法主要使用正则表达式来判断字符串中是否包含字母
     * @author fenggaopan 2015年7月21日 上午9:49:40
     * @param cardNum 待检验的原始卡号
     * @return 返回是否包含
     */
    public static boolean judgeContainsStr(String cardNum) {
        String regex=".*[A-Z]+.*";
        Matcher m=Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }



    /**
     * 判断是否含有"06789"字符
     *
     * @param str
     * @return true为包含，false为不包含
     */
    public static boolean isNumberChar(String str) {
        String  [] strs = {"0","6","7","8","9"};
        for (String s :strs){
            if ( str.indexOf(s)!=-1) return true;

        }
        return false;
    }


    /**
     * 判断是否包含zw中文
     *
     * @param strName
     * @return
     */


    public static boolean checkChinese(String strName) {

        char[] ch = strName.toCharArray();
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (is_chinese(c)) {
                return true;
            }
        }
        return false;
    }

    private static boolean is_chinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }
}
