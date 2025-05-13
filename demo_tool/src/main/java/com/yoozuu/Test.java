package com.yoozuu;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxNum = 10000000;
        long maxHap = 518879945896L;
        // 提示用户输入年龄
        System.out.print("请输入: ");
        // 读取用户输入的整数
        int totalNum = scanner.nextInt();

        System.out.println(getHap(maxNum, totalNum, maxHap));
        // 关闭 Scanner 对象，释放资源
        scanner.close();
    }

    private static long getHap(int maxNum, int totalNum, long maxHp) {
        return maxHp * totalNum / maxNum;
    }
}
