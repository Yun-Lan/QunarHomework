package com.qunar.homework.work4.parse;

import com.qunar.core.io.FileInput;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: lymtics
 * @description:Grep命令解析器
 */
public class GrepParse implements PipeParse{
    @Override
    public Boolean fit(String[] order) {
        if(!order[0].equals("grep")){
            return false;
        }
        if(order.length == 2 || order.length == 3){
            return true;
        }
        return false;
    }

    @Override
    public String[] parse(String[] order, String[] sourse) {
        String[] result = null;
        if(order.length == 2){
//            Object[] filterResult = Arrays.stream(sourse).filter(s -> s.contains(order[1])).toArray();
//            System.out.println(filterResult);
//            result = filterResult.split("\n");
            return Arrays.stream(sourse).filter(str -> str.contains(order[1])).toArray(String[]::new);
        }else{
            FileInput fileInput = new FileInput();
            String filterResult = new String(fileInput.fileInput(order[2]));
            String[] res = filterResult.split("\n");
            return Arrays.stream(res).filter(str -> str.contains(order[1])).toArray(String[]::new);
        }

    }
}
