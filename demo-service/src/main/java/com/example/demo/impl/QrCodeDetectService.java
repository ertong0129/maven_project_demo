package com.example.demo.impl;


import cn.hutool.core.util.StrUtil;
import com.example.demo.result.Result;
import com.example.demo.util.QrCodeDetectUtil;
import com.example.demo.util.UploadUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;



/*
 *
 * @author paida 派哒 zeyu.pzy@alibaba-inc.com
 * @date 2020/10/27
 */

@Service
@Slf4j
public class QrCodeDetectService {

    @Value("${qrcode-dir}")
    private String qrCodeDir;

    public String getQrCode(String filePath) {
        return QrCodeDetectUtil.getQrCode(filePath).stream().findFirst().orElse(null);
    }

    /**
     * 上传图片并获取qrcode
     * @param file
     * @return
     * @throws Exception
     */
    public Result<String> uploadImgGetQrCode(MultipartFile file) throws Exception {
        //上传
        String localImgPath = UploadUtil.upload(file, qrCodeDir, file.getOriginalFilename());
        if (StrUtil.isBlank(localImgPath)) {
            return Result.buildFail("保存图片失败");
        }
        return Result.buildSuccess(QrCodeDetectUtil.getQrCode(localImgPath).stream().findFirst().orElse(null));

    }

    /**
     * 根据url获取qrcode
     * @param imgUrl
     * @return
     * @throws Exception
     */
    public Result<String> httpImgGetQrCode(String imgUrl) throws Exception {
        URL url = new URL(imgUrl);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        InputStream is = null;
        String localImgPath = null;
        try {
            is = con.getInputStream();
            localImgPath = UploadUtil.upload(is, qrCodeDir);
        } catch (FileNotFoundException fe) {
            log.error("url文件不存在，url:{}", imgUrl);
            return Result.buildFail("url文件不存在");
        } catch (Exception e) {
            log.error("保存图片失败", e);
        } finally {
            if (is != null) {
                is.close();
            }
        }
        if (StrUtil.isBlank(localImgPath)) {
            return Result.buildFail("保存图片失败");
        }
        return Result.buildSuccess(QrCodeDetectUtil.getQrCode(localImgPath).stream().findFirst().orElse(null));
    }
}
