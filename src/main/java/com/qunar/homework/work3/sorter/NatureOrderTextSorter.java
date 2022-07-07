package com.qunar.homework.work3.sorter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lymtics
 * @description:自然排序，即文本中排列顺序
 */
public class NatureOrderTextSorter implements TextSorter{
    private ArrayList<String> propertyList;
    @Override
    public Boolean fit(String order) {
        if(order.startsWith("$natureOrder")){
            return true;
        }
        return false;
    }

    @Override
    public List<String> sort(Map<String, String> property) {
        if(propertyList == null){
            propertyList = new ArrayList<>(property.values());
        }
        return propertyList;
    }
}
