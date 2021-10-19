package com.example.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
public class UploadUtil {

    /**
     * 上传文件
     * @param file
     * @param path
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String upload(MultipartFile file, String path, String fileName) throws Exception {
        // 生成新的文件名
        String realPath = path + UUID.randomUUID().toString().replace("-", "")+fileName.substring(fileName.lastIndexOf("."));
        File dest = new File(realPath);
        try {
            // 保存文件
            file.transferTo(dest);
        } catch (Exception e) {
            log.error("文件保存失败", e);
            return null;
        }
        return realPath;
    }

    public static String upload(InputStream in, String path) throws Exception {
        String realPath = path + UUID.randomUUID().toString().replace("-", "");
        byte[] bs = new byte[1024];
        int len;
        boolean fisrtRead = true;
        OutputStream os = null;
        try {
            // 开始读取
            while ((len = in.read(bs)) != -1) {
                if (fisrtRead) {
                    //第一次读取，根据流里前三个字节，获取图片后缀
                    fisrtRead = false;
                    byte[] fileTypeBytes = Arrays.copyOf(bs, 3);
                    String fileType = FileTypeUtil.getFileType(fileTypeBytes);
                    //TODO 非图片不保存
                    realPath += fileType;
                    os = new FileOutputStream(realPath);
                }
                os.write(bs, 0, len);
            }
        } catch(Exception e) {
            log.error("文件保存失败", e);
            return null;
        } finally {
            if (os != null) {
                os.close();
            }
        }
        return realPath;
    }
}
