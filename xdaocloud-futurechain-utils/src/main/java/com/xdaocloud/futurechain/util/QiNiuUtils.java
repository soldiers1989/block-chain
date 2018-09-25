package com.xdaocloud.futurechain.util;

import com.xdaocloud.framework.qiniu.FileDTO;
import com.xdaocloud.framework.qiniu.FileUtils;
import com.xdaocloud.futurechain.common.QiniuConfig;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class QiNiuUtils {

    /**
     * 上传文件
     * @param multipartFile
     * @param qiniuConfig
     * @return 文件路径
     * @throws IOException
     */
    public static String pushFile( MultipartFile multipartFile, QiniuConfig qiniuConfig) throws IOException {
        FileDTO fileInfo = null;
        String originalName = null;
        if (multipartFile != null && StringUtils.isNotBlank(multipartFile.getOriginalFilename())) {
            originalName = multipartFile.getOriginalFilename();
            String extension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
            String path = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            String key = path + "/" + UUID.randomUUID().toString() + "." + extension;
            fileInfo = FileUtils.upload(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey(), qiniuConfig.getBucket(), key, multipartFile.getBytes());
        }
        if (fileInfo != null) {
            fileInfo.setOriginalName(originalName);
            fileInfo.setDomain(qiniuConfig.getDomain());
            //保存图片地址
            return qiniuConfig.getDomain() + fileInfo.getUrl();
        }
        return null;
    }
}
