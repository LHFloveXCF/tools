package util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class PropertiesUtil<T> {
    public T loadConfig(Class<T> clazz, String fileName) {
        Properties properties = new Properties();
        T config = null;

        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            if (inputStream != null) {
                // 使用 InputStreamReader 指定 UTF-8 编码
                try (InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
                    properties.load(reader);
                    config = clazz.getDeclaredConstructor().newInstance();

                    for (Field field : clazz.getDeclaredFields()) {
                        String propertyName = convertToPropertyName(field.getName());
                        String value = properties.getProperty(propertyName);
                        if (value != null) {
                            value = value.replaceAll("^\"|\"$", "");
                            field.setAccessible(true);
                            if (field.getType() == int.class) {
                                field.setInt(config, Integer.parseInt(value));
                            } else {
                                field.set(config, value);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("加载配置时出错: " + e.getMessage());
            e.printStackTrace();
        }
        return config;
    }

    private String convertToPropertyName(String fieldName) {
        StringBuilder sb = new StringBuilder();
        for (char c : fieldName.toCharArray()) {
            if (Character.isUpperCase(c)) {
                sb.append('_').append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
