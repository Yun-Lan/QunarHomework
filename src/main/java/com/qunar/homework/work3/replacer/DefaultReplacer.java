package com.qunar.homework.work3.replacer;

import com.qunar.homework.work3.sorter.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lymtics
 * @description:默认文本替换器
 */
public class DefaultReplacer implements TextReplacer {
    private List<TextSorter>  textSorterList = new ArrayList<>();
    {
        addSorter(new NatureOrderTextSorter());
        addSorter(new IndexOrderTextSorter());
        addSorter(new CharOrderTextSorter());
        addSorter(new CharOrderDESCTextSorter());
    }

    private void addSorter(TextSorter textSorter){
        textSorterList.add(textSorter);
    }

    @Override
    public ArrayList<String> replaceText(String[] originContent, Map<String, String> property) {
        ArrayList<String> resultList = new ArrayList<>(property.size());
        int startIndex = 0;
        int numIndex = 0;
        int endIndex = 0;
        for(String originLine : originContent){
            startIndex = originLine.indexOf('$');
            if(startIndex == -1){
                resultList.add(originLine);
                continue;
            }
            numIndex = originLine.indexOf('(');
            endIndex = originLine.indexOf(')');
            String replaceOrder = originLine.substring(startIndex, endIndex + 1);
            String replaceIndex = originLine.substring(numIndex + 1, endIndex);
            for(TextSorter sorter : textSorterList){
                if(sorter.fit(replaceOrder)){
                    String replaceWord = sorter.sort(property).get(Integer.parseInt(replaceIndex));
//                    System.out.println(replaceWord);
                    resultList.add(originLine.replace(replaceOrder, replaceWord));
                }
            }
        }
        return resultList;
    }
}
