package util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static String readFileToString(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (Exception e) {}
        return content.toString();
    }

    public static List<String> readFileToString(String filePath, String charSet) {
        File detailFile = new File(filePath);
        if (!detailFile.exists()) {
            try {
                detailFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<String> lines = new ArrayList<String>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath)), charSet))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }

        } catch (IOException e) {
            System.err.println("读取文件时发生错误: " + e.getMessage());
        }
        return lines;
  }

    public static boolean writeFile(String filePath, List<String> content, boolean newline) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String str : content) {
                writer.append(str);
                if (newline)
                    writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("写入文件时发生错误: " + e.getMessage());
            return false;
        }
        return true;
    }

    public static boolean writeFile(String filePath, List<String> content) {
        return writeFile(filePath, content, true);
    }
}
