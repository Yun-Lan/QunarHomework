package com.qunar.homework.work3.sorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author: lymtics
 * @description:文本倒序，java的字符倒序
 */
public class CharOrderDESCTextSorter implements TextSorter{
    private List<String> propertyList;
    @Override
    public Boolean fit(String order) {
        if(order.startsWith("$charOrderDESC")){
            return true;
        }
        return false;
    }

    @Override
    public List<String> sort(Map<String, String> property) {
        if(propertyList == null){
            propertyList = new ArrayList<>(property.values());
            propertyList.sort(Comparator.reverseOrder());
        }
        return propertyList;
    }
}
