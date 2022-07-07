package com.qunar.homework.work4;

import com.qunar.homework.work4.shell.DefaultShell;
import com.qunar.homework.work4.shell.ShellParse;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author: lymtics
 * @description:第四题：Java模拟Linux命令处理和管道
 */
public class Question4 {
    public static void main(String[] args) {
        Scanner orderScanner = new Scanner(System.in);
        String orders = orderScanner.nextLine().trim();
        String[] result = new DefaultShell().parse(orders);
        System.out.println("结果：");
        for(String str : result){
            System.out.println(str);
        }
    }


}
