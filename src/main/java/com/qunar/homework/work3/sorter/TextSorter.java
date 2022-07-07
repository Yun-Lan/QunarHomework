package com.qunar.homework.work3.sorter;

import java.util.List;
import java.util.Map;

/**
 * @author: lymtics
 * @description:文本排序接口
 */
public interface TextSorter {
    //判断是否排序器是否适用
    Boolean fit(String order);
    //排序
    List<String> sort(Map<String, String> property);
}
