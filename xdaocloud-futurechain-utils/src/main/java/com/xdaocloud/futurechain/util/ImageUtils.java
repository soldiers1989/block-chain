package com.xdaocloud.futurechain.util;

import com.xdaocloud.base.info.ResultInfo;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public class ImageUtils {

    /**
     * 检查图片文件是否符合规格
     * @param multipartFile
     * @return
     */
    public static ResultInfo<?> checkImage(@RequestParam(name = "portrait", required = false) MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        int index = fileName.lastIndexOf(".");
        if (index < 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM, "文件格式有误");
        }
        char[] ch = fileName.toCharArray();
        // 根据 copyValueOf(char[] data, int offset, int count) 取得最后一个字符串
        String extension = String.copyValueOf(ch, index + 1, ch.length - index - 1);

        if (!"jpeg".equals(extension)
                && !"jpg".equals(extension) && !"png".equals(extension)
                && !"gif".equals(extension)) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM, "文件格式有误");
        }
        if (multipartFile.getSize() > 1 * 1024 * 1024) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_INVALID_PARAM, "图片太大");
        }
        return null;
    }
}
