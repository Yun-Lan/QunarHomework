package com.qunar.homework.work1;

import com.google.common.collect.*;
import com.qunar.core.io.FileInput;
import com.qunar.pojo.UriNum;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @author: lymtics
 * @date: 2022/7/7 17:17
 * @description:
 */

public class Question1Test {

    //匿名Comparator实现
    public static Comparator<UriNum> uriNumComparator = new Comparator<UriNum>(){

        @Override
        public int compare(UriNum o1, UriNum o2) {
            return o1.getNum() - o2.getNum();
        }

    };

    private static String filePath = "F:\\qunar\\Question 1\\access.log";
    private static Multiset<String> requestType = HashMultiset.create();
    private static Multiset<String> requestUri = HashMultiset.create();
    static Queue<UriNum> uriNumPriorityQueue = new PriorityQueue<UriNum>(10, uriNumComparator);
//    private static HashMap<String, HashSet<String>> uriMap = new HashMap<>();
    private static Multimap<String, String> uriMap = HashMultimap.create();

    public static void main(String[] args) {
            FileInput fileInput = new FileInput();
            String fileContent = new String(fileInput.fileInput(filePath));
            String[] fileLines = fileContent.split("\n");
            requestSum(fileLines);
            getTenMostRequest(fileLines);
            getAndPostSum(fileLines);
            groupByFirstURI(fileLines);

    }

    public static void requestSum(String[] fileLines){
        System.out.println("请求总量为:" + fileLines.length);
    }

    public static void getTenMostRequest(String[] fileLines){
        for(String requestLine : fileLines){
            String[] requestSplit = requestLine.split(" ");
            String[] uriSplit = requestSplit[1].split("\\?");
            requestUri.add(uriSplit[0]);
        }
        for(String uri : requestUri.elementSet()){
            UriNum uriNum = new UriNum(uri, requestUri.count(uri));
            if(uriNumPriorityQueue.size() < 10){
                uriNumPriorityQueue.add(uriNum);
            }else{
                if(requestUri.count(uri) > uriNumPriorityQueue.element().getNum()) {
                    uriNumPriorityQueue.remove();
                    uriNumPriorityQueue.add(uriNum);
                }
            }
        }
        pollDataFromQueue(uriNumPriorityQueue);
    }

    public static void getAndPostSum(String[] fileLines){
        for(String requestLine : fileLines){
            String[] requestSplit = requestLine.split(" ");
            requestType.add(requestSplit[0]);
        }
        for(String type : requestType.elementSet()){
            if(type.equals("GET") || type.equals("POST")){
                System.out.println(type + "请求一共有:" + requestType.count(type));
            }
        }
    }

    public static void groupByFirstURI(String[] fileLines){
        for(String requestLine : fileLines){
            String[] requestSplit = requestLine.split(" ");
            String[] uriHead = requestSplit[0].split("/");
            uriMap.put(uriHead[1], requestSplit[1]);
        }
        for(String head : uriMap.keySet()){
            System.out.println(head + "类的有:");
            for (String uri : uriMap.get(head)){
                System.out.println(uri);
            }
        }

    }




    //用于从队列取数据的通用方法
    private static void pollDataFromQueue(Queue<UriNum> uriNumPriorityQueue) {
        while(true){
            UriNum uriNum = uriNumPriorityQueue.poll();
            if(uriNum == null) break;
            System.out.println("前十的网络接口是:"+uriNum.getUri() + "数量是：" + uriNum.getNum());
        }
    }
}
