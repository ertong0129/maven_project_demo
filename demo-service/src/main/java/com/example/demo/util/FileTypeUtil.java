package com.example.demo.util;

import com.example.demo.constant.FileTypeDict;

import java.io.FileInputStream;
import java.io.InputStream;

public class FileTypeUtil {

    public static String getFileType(byte[] src) {
        return FileTypeDict.checkType(bytesToHexString(src));
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

}