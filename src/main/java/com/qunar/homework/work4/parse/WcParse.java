package com.qunar.homework.work4.parse;

import com.qunar.core.io.FileInput;

/**
 * @author: lymtics
 * @description:Wc命令解析器
 */
public class WcParse implements PipeParse{
    @Override
    public Boolean fit(String[] order) {
        if(!order[0].equals("wc") || !order[1].equals("-l")){
            return false;
        }
        if(order.length == 2 || order.length == 3){
            return true;
        }
        return false;
    }

    @Override
    public String[] parse(String[] order, String[] sourse) {
        if(order.length == 2){
            return new String[]{String.valueOf(sourse.length)};
        }else{
            FileInput fileInput = new FileInput();
            String fileContent = new String(fileInput.fileInput(order[2]));
            String[] result = fileContent.split("\n");
            return new String[]{String.valueOf(result.length)};
        }
    }
}
