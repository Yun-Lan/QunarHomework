package com.qunar.homework.work4.parse;

/**
 * @author: lymtics
 * @description:解析器接口
 */
public interface PipeParse {
    //判断解析器是否适用
    Boolean fit(String[] order);
    //解析
    String[] parse(String[] order, String[] sourse);
}
