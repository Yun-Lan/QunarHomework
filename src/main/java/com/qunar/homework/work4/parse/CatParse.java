package com.qunar.homework.work4.parse;

import com.qunar.core.io.FileInput;

import java.io.*;

/**
 * @author: lymtics
 * @description:Cat命令解析器
 */
public class CatParse implements PipeParse{
    @Override
    public Boolean fit(String[] order) {
        if(!order[0].equals("cat") || order.length != 2){
            return false;
        }
        return true;
    }

    @Override
    public String[] parse(String[] order, String[] sourse) {
        FileInput fileInput = new FileInput();
        String fileContent = new String(fileInput.fileInput(order[1]));
        return fileContent.split("\n");
    }
}
