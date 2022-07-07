package com.qunar.homework.work4.shell;

import com.qunar.homework.work4.parse.CatParse;
import com.qunar.homework.work4.parse.GrepParse;
import com.qunar.homework.work4.parse.PipeParse;
import com.qunar.homework.work4.parse.WcParse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lymtics
 * @description:默认shell解析器
 */
public class DefaultShell implements ShellParse {
    private List<PipeParse> parseList = new ArrayList<>();
    {
        addParse(new CatParse());
        addParse(new GrepParse());
        addParse(new WcParse());
    }

    public void addParse(PipeParse pipeParse){
        parseList.add(pipeParse);
    }
    @Override
    public String[] parse(String orders) {
        String[] result = new String[0];
        String[] order = orders.split("\\|");
        System.out.println("order[0] " + order[0]);
        for(String command : order){
            System.out.println("执行" + command);
            String[] commandWord = command.trim().split(" ");
            for(PipeParse pipeParse : parseList){
                if(pipeParse.fit(commandWord)){
                    System.out.println("匹配成功");
                    result = pipeParse.parse(commandWord, result);
                    break;
                }
            }
        }
        return result;
    }
}
