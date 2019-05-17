package com.shapestudio.common.util;

import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;

public class SecurityUtil {

    public static String decretValue(String value) {
        return clipString(new String(Base64Utils.decode(value.getBytes()), Charset.forName("utf-8")));
    }

    private static String clipString(String value) {
        return value.substring(0, value.length() - 11);
    }

    public static void main(String[] args) {
        System.out.println(new String(Base64Utils.encode("123456ShapeStudio".getBytes(Charset.forName("utf-8"))), Charset.forName("utf-8")));
    }
}
