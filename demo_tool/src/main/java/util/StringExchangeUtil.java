package util;

import com.yoozuu.excel_parse.constant.CharConstant;

/**
 * 字符串工具类
 */
public class StringExchangeUtil {
    /**
     * 下划线转大驼峰
     * @param input 字符串
     * @return 驼峰字符串
     */
    public static String toUpperCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        String[] parts = input.split(CharConstant.UNDER_LINE);
        for (String part : parts) {
            if (!part.isEmpty()) {
                result.append(Character.toUpperCase(part.charAt(0)))
                        .append(part.substring(1));
            }
        }
        return result.toString();
    }

    /**
     * 下划线转小驼峰
     * @param input 字符串
     * @return 驼峰字符串
     */
    public static String toLowerCamelCase(String input) {
        StringBuilder result = new StringBuilder();
        String[] parts = input.split(CharConstant.UNDER_LINE);
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) {
                if (i == 0) {
                    // 首个单词首字母小写
                    result.append(part.toLowerCase());
                } else {
                    // 后续单词首字母大写
                    result.append(Character.toUpperCase(part.charAt(0)))
                            .append(part.substring(1));
                }
            }
        }
        return result.toString();
    }
}
