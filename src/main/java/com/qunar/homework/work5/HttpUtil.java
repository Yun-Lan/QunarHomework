package com.qunar.homework.work5;

import com.qunar.pojo.ResultCount;
import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: lymtics
 * @description:Http爬取工具类
 */
public class HttpUtil {

    //发送http连接请求获取网页数据
    public String doGet(String getUrl) throws IOException {
        URL url = new URL(getUrl);
        final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(3000);
        connection.setReadTimeout(200000);
        connection.connect();
        if (connection.getResponseCode() == 200) {
            InputStream inputStream = null;
            try {
                inputStream = connection.getInputStream();
                return Jsoup.parse(new String(toByteArray(inputStream), StandardCharsets.UTF_8)).text();
            } finally {
                Optional.ofNullable(inputStream).ifPresent(it -> {
                    try {
                        it.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        return null;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    //统计字符并且写回
    public static ResultCount writeBack(String url) {
        try {
            String httpContent = new HttpUtil().doGet(url);
            int allCharCount = httpContent.length();    //总字符数
            int chineseCount = 0;   //中文字符数
            int letterCount = 0;    //英文字符数
            int charCount = 0;      //标点符号字符数
            Pattern wordPattern = Pattern.compile("[a-zA-Z]+$");    //字母匹配
            Pattern numPattern = Pattern.compile("[0-9]*");         //数字匹配
            Pattern ChinesePattern = Pattern.compile("[\\u4e00-\\u9fa5]{0,}$");     //中文匹配
            Matcher wordMatcher = null;
            Matcher numMatcher = null;
            Matcher ChineseMatcher = null;
            for (int i = 0; i < httpContent.toCharArray().length; i++) {
                String str = String.valueOf(httpContent.charAt(i));
                wordMatcher = wordPattern.matcher(str);
                numMatcher = numPattern.matcher(str);
                ChineseMatcher = ChinesePattern.matcher(str);
                if (wordMatcher.matches()) {
                    letterCount++;
                }else if (ChineseMatcher.matches()){
                    chineseCount++;
                }else if (str.equals(" ")){
                    continue;
                }else if (!numMatcher.matches()){
                    charCount++;
                }
            }
            return new ResultCount(allCharCount, chineseCount, letterCount, charCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
