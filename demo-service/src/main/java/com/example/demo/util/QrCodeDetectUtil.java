package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.objdetect.QRCodeDetector;
import org.opencv.wechat_qrcode.WeChatQRCode;

import java.util.ArrayList;
import java.util.List;

/**
 * 二维码检测工具类
 */
@Slf4j
public class QrCodeDetectUtil {

    //static QRCodeDetector qrCodeDetector;
    private static WeChatQRCode weChatQRCode;

    static {
        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            weChatQRCode = new WeChatQRCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片二维码
     * @param fileName
     * @return
     */
    public static List<String> getQrCode(String fileName) {
        Mat image = null;
        try {
            image = Imgcodecs.imread(fileName);
            //底层c++代码有问题，如果不加锁，并发情况下c++代码报错，会导致系统退出
            synchronized (QrCodeDetectUtil.class) {
                return weChatQRCode.detectAndDecode(image);
            }
        } catch (Exception e) {
            log.error("获取图中二维码失败,fileName:{}", fileName, e);
            return new ArrayList<>();
        } finally {
            if (image != null) {
                image.release();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(getQrCode("/Users/jingling/Desktop/图片/3.png").toString());
    }
}
