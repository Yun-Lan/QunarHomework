package com.qunar.homework.work2;

import java.io.*;

/**
 * @author: lymtics
 * @description:第二题：统计有效代码的行数
 */
public class Question2 {
    public static void main(String[] args) {
        int correctCodeNum = 0;       //有效行数计数
        try {
            //读取文件
            BufferedReader bufferedReader = new BufferedReader(new FileReader("F:\\qunar\\Question 2\\StringUtils.java"));
            String code = bufferedReader.readLine();
            String trimCode = null;
            //去除字符串首尾空格
            if(code.trim() != null){
                trimCode = code.trim();
            }
            while (code != null){
                //判断多行注释开头
                if(trimCode != null && !trimCode.equals("") && trimCode.startsWith("/*")) {
                    //判断多行注释结束
                    while (trimCode == null || trimCode.equals("") || !trimCode.endsWith("*/")){
                        code = bufferedReader.readLine();
                        if (code != null && !code.equals("")) {
                            trimCode = code.trim();
                        } else {
                            trimCode = null;
                        }
                    }
                }
                //判断单行注释、空行
                if(trimCode != null && !trimCode.equals("") && !trimCode.startsWith("//") && !trimCode.startsWith("*")){
                    correctCodeNum++;
                }
                code = bufferedReader.readLine();
                if(code != null && !code.equals("")){
                    trimCode = code.trim();
                }else{
                    trimCode = null;
                    continue;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(correctCodeNum);
        //将有效行数写入到文件中
        File file = new File("F:\\ideaProject\\QunarHomework\\src\\main\\resources\\validLineCount.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(String.valueOf(correctCodeNum));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
