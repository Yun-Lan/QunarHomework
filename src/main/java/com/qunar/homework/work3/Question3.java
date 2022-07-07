package com.qunar.homework.work3;

import com.qunar.core.io.FileInput;
import com.qunar.homework.work3.replacer.DefaultReplacer;
import com.qunar.homework.work3.util.PropertyUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author: lymtics
 * @description:第三题：文本解密，替换文档内容
 */
public class Question3 {
    public static void main(String[] args) {
        // 加载prop文件
        final Map<String, String> properties = PropertyUtil.loadProperty("sdxl_prop.txt");
        String fileContent = new String(new FileInput().fileInput("sdxl_template.txt"));
        String[] originContent = fileContent.split("\n");
        ArrayList<String> resultContent = new DefaultReplacer().replaceText(originContent, properties);
        //将正确的文档写入新文件中
        File file = new File("F:\\ideaProject\\QunarHomework\\src\\main\\resources\\sdxl.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            for(String str : resultContent){
                fileWriter.write(str);
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
