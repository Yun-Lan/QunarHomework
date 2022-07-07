package com.qunar.homework.work3.sorter;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import javax.xml.soap.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lymtics
 * @description:索引排序，文本中每行第一个数字为索引
 */
public class IndexOrderTextSorter implements TextSorter {
    private BiMap<String, String> biMap;
    private List<String> propertyList;
    @Override
    public Boolean fit(String order) {
        if(order.startsWith("$indexOrder")){
            return true;
        }
        return false;
    }

    @Override
    public List<String> sort(Map<String, String> property) {
        if(biMap == null || propertyList == null){
            biMap = HashBiMap.create(property).inverse();
            propertyList = new ArrayList<>(property.values());
            propertyList.sort((o1, o2) -> Integer.parseInt(biMap.get(o1)) - Integer.parseInt(biMap.get(o2)));

        }
        return propertyList;
    }
}
