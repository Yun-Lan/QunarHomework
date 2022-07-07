package com.qunar.homework.work1;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.qunar.core.io.FileInput;
import com.qunar.pojo.UriNum;
import com.sun.jndi.toolkit.url.Uri;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.*;

/**
 * @author: lymtics
 * @description:第一题：分析日志文件并且统计
 */
public class Question1 {
    //匿名Comparator实现
    public static Comparator<UriNum> uriNumComparator = new Comparator<UriNum>(){

        @Override
        public int compare(UriNum o1, UriNum o2) {
            return o1.getNum() - o2.getNum();
        }

    };

    private static String filePath = "access.log";  //文件名
    private static Multiset<String> requestType = HashMultiset.create();    //请求类型计数集合
    private static Multiset<String> requestUri = HashMultiset.create();     //请求uri集合
    static Queue<UriNum> uriNumPriorityQueue = new PriorityQueue<UriNum>(10, uriNumComparator); //请求次数优先队列
    private static Multimap<String, String> uriMap = HashMultimap.create();     //请求子集集合

    public static void main(String[] args) {
        //读取文件
        FileInput fileInput = new FileInput();
        String fileContent = new String(fileInput.fileInput(filePath));
        String[] fileLines = fileContent.split("\n");
        //获取请求总数
        requestSum(fileLines);
        //获取请求次数最多的十个接口
        getTenMostRequest(fileLines);
        //获取GET和POST请求的数量
        getAndPostSum(fileLines);
        //按照URI进行分类
        groupByFirstURI(fileLines);

    }

    /**
     * 1.获取请求总量
     */
    public static void requestSum(String[] fileLines){
        System.out.println("请求总量为:" + fileLines.length);
    }

    /**
     * 2.获取请求最频繁的十个接口及其请求数量
     */
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

    /**
     * 3.获取GET和POST请求次数
     */
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

    /**
     * 4.按照AAA形式进行分类
     */
    public static void groupByFirstURI(String[] fileLines){
        for(String requestLine : fileLines){
            String[] requestSplit = requestLine.split(" ");
            String[] uriHead = requestSplit[1].split("/");
            String[] request = requestSplit[1].split("\\?");
            uriMap.put(uriHead[1], request[0]);
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
