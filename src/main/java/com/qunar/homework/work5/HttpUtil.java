package com.qunar.homework.work5;

import org.jsoup.Jsoup;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author: lymtics
 * @description:Http爬取工具类
 */
public class HttpUtil {
        public static final String GET_METHOD = "GET";
        public static final int DEFAULT_CONNECTION_TIMEOUT = 3000;
        public static final int DEFAULT_READ_TIMEOUT = 200000;
        public static final int CODE_SUCCESS = 200;

        public String doGet(String getUrl) throws IOException {
            URL url = new URL(getUrl);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(GET_METHOD);
            connection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
            connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
            connection.connect();
            if (connection.getResponseCode() == CODE_SUCCESS) {
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

        public static void main(String[] args) throws IOException {
            final String result = new HttpUtil().doGet("https://www.baidu.com");
            System.out.println(result);
        }

}
