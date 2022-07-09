package com.qunar.core.io;

import java.io.*;
import java.net.URL;

/**
 * @author: lymtics
 * @date: 2022/6/20 21:39
 * @description:
 */
public class FileInput {
    public byte[] fileInput(String fileName){

//        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        URL url = FileInput.class.getClassLoader().getResource(fileName);
        File file = new File(url.getFile());
        byte[] fileContent = new byte[(int)file.length()];
        InputStream in = FileInput.class.getClassLoader().getResourceAsStream(fileName);
        try {

            in.read(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(fileContent.length);
//        for(int i = 0; i < fileContent.length; i++){
//            System.out.println(fileContent[i]);
//        }
//        String res = new String(fileContent);
//        System.out.println(res);
        return fileContent;
    }
}
