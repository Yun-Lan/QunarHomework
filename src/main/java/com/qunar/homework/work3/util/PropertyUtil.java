package com.qunar.homework.work3.util;

import com.google.common.collect.Maps;
import com.qunar.core.io.FileInput;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedHashMap;

/**
 * @author: lymtics
 * @description:加载文件
 */
public class PropertyUtil {
    public static LinkedHashMap<String, String> loadProperty(String path){
        LinkedHashMap<String, String> properties = Maps.newLinkedHashMapWithExpectedSize(64);
        FileInput fileInput = new FileInput();
        String filecontent = new String(fileInput.fileInput(path));
        final String[] propertyLines = filecontent.split("\n");
        Arrays.stream(propertyLines).forEach(property -> {
            final String[] propertyKeyValues = Arrays.stream(property.split("\t")).map(String::trim).toArray(String[]::new);
            properties.put(propertyKeyValues[0], propertyKeyValues[1]);
        });
        return properties;
    }
}
