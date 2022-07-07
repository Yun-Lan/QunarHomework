package com.qunar.homework.work3.sorter;

import java.util.*;

/**
 * @author: lymtics
 * @description:文本排序，java的字符排序
 */
public class CharOrderTextSorter implements TextSorter{
    private List<String> propertyList;
    @Override
    public Boolean fit(String order) {
        if(order.startsWith("$charOrder(")){
            return true;
        }
        return false;
    }

    @Override
    public List<String> sort(Map<String, String> property) {
        if(propertyList == null){
            propertyList = new ArrayList<>(property.values());
            propertyList.sort(Comparator.naturalOrder());
        }
        return propertyList;
    }
}
