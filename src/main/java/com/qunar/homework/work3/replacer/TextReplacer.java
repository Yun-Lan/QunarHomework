package com.qunar.homework.work3.replacer;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author: lymtics
 * @description:文本替换器接口
 */
public interface TextReplacer {
    ArrayList<String> replaceText(String[] originContent, Map<String, String> property);
}
