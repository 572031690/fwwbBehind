package com.xhy.pub;
/**
 * @Author xqs
 * @Date 2020/4/7 16:33
 * @Description 功能类
 */
public class StrUtil {
    /**
     * @Description 判断输入字符串是否为空
     * @Param 要判断是否为空的字符串
     * @Return boolean
     */
    public static boolean isBank(String str) {
        if (str == null || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
