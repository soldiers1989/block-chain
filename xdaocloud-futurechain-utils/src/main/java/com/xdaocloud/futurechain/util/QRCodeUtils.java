package com.xdaocloud.futurechain.util;


import cn.hutool.extra.qrcode.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;

import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * 生成二维码工具类
 */
public class QRCodeUtils {

    private static final int IMAGE_WIDTH = 100;
    private static final int IMAGE_HEIGHT = 100;
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;
    private static final int FRAME_WIDTH = 1;
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();

    public static void encode(String content, int width, int height,
                              String srcImagePath, OutputStream outputStream) {
        try {
            ImageIO.write(genBarcode(content, width, height, srcImagePath),
                    "jpg",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage genBarcode(String content, int width,
                                            int height, String srcImagePath) throws WriterException,
            IOException {
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH,
                IMAGE_HEIGHT, true);
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];
        for (int i = 0; i < scaleImage.getWidth(); i++) {
            for (int j = 0; j < scaleImage.getHeight(); j++) {
                srcPixels[i][j] = scaleImage.getRGB(i, j);
            }
        }
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 生成二维码
        BitMatrix matrix = mutiWriter.encode(content, BarcodeFormat.QR_CODE,
                width, height, hint);
        // 二维矩阵转为一维像素数组
        int halfW = matrix.getWidth() / 2;
        int halfH = matrix.getHeight() / 2;
        int[] pixels = new int[width * height];
        for (int y = 0; y < matrix.getHeight(); y++) {
            for (int x = 0; x < matrix.getWidth(); x++) {
                // 左上角颜色,根据自己需要调整颜色范围和颜色
                if (x > 0 && x < 10 && y > 0 && y < 10) {
                    Color color = new Color(231, 144, 56);
                    int colorInt = color.getRGB();
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                }
                // 读取图片
                else if (x > halfW - IMAGE_HALF_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH
                        && y < halfH + IMAGE_HALF_WIDTH) {
                    pixels[y * width + x] = srcPixels[x - halfW
                            + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];
                } else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        - IMAGE_HALF_WIDTH + FRAME_WIDTH)
                        || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH
                        && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH
                        && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH
                        + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {
                    pixels[y * width + x] = 0xfffffff;
                    // 在图片四周形成边框
                } else {
                    // 二维码颜色
                    int num1 = (int) (50 - (50.0 - 13.0) / matrix.getHeight()
                            * (y + 1));
                    int num2 = (int) (165 - (165.0 - 72.0) / matrix.getHeight()
                            * (y + 1));
                    int num3 = (int) (162 - (162.0 - 107.0)
                            / matrix.getHeight() * (y + 1));
                    Color color = new Color(num1, num2, num3);
                    int colorInt = color.getRGB();
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；
                    pixels[y * width + x] = matrix.get(x, y) ? colorInt
                            : 16777215;
                    // 0x000000:0xffffff
                }
            }
        }
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        image.getRaster().setDataElements(0, 0, width, height, pixels);
        return image;
    }

    private static BufferedImage scale(String srcImageFile, int height,
                                       int width, boolean hasFiller) throws IOException {
        double ratio = 0.0; // 缩放比例
        //File file = new File(srcImageFile);
        URL url = new URL(srcImageFile);
        InputStream inputStream = url.openConnection().getInputStream();
        BufferedImage srcImage = ImageIO.read(inputStream);
        Image destImage = srcImage.getScaledInstance(width, height,
                BufferedImage.SCALE_SMOOTH);
        // 计算比例
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {
            if (srcImage.getHeight() > srcImage.getWidth()) {
                ratio = (new Integer(height)).doubleValue()
                        / srcImage.getHeight();
            } else {
                ratio = (new Integer(width)).doubleValue()
                        / srcImage.getWidth();
            }
            AffineTransformOp op = new AffineTransformOp(
                    AffineTransform.getScaleInstance(ratio, ratio), null);
            destImage = op.filter(srcImage, null);
        }
        if (hasFiller) {
            // 补白
            BufferedImage image = new BufferedImage(width, height,
                    BufferedImage.TYPE_INT_RGB);
            Graphics2D graphic = image.createGraphics();
            graphic.setColor(Color.white);
            graphic.fillRect(0, 0, width, height);
            if (width == destImage.getWidth(null))
                graphic.drawImage(destImage, 0,
                        (height - destImage.getHeight(null)) / 2,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            else
                graphic.drawImage(destImage,
                        (width - destImage.getWidth(null)) / 2, 0,
                        destImage.getWidth(null), destImage.getHeight(null),
                        Color.white, null);
            graphic.dispose();
            destImage = image;
        }
        return (BufferedImage) destImage;
    }

    //二维码颜色
    private static final int BLACK = 0xFF000000;//0xFFFF0000，红色
    //二维码背景色
    private static final int WHITE = 0xFFFFFFFF;//0xFF0000FF，蓝色
    //注：二维码颜色色差大，扫描快，但如果"BLACK'设置为黑色外其他颜色，可能无法扫描
    //二维码图片宽度
    private static final int width = 300;
    //二维码图片高度
    private static final int height = 300;
    //二维码格式参数
    private static final EnumMap<EncodeHintType, Object> hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
    static{
        /*二维码的纠错级别(排错率),4个级别：
         L (7%)、
         M (15%)、
         Q (25%)、
         H (30%)(最高H)
         纠错信息同样存储在二维码中，纠错级别越高，纠错信息占用的空间越多，那么能存储的有用讯息就越少；共有四级；
         选择M，扫描速度快。
         */
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 二维码边界空白大小 1,2,3,4 (4为默认,最大)
        hints.put(EncodeHintType.MARGIN, 1);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.MAX_SIZE, 350);
        hints.put(EncodeHintType.MIN_SIZE, 150);
    }
    /**
     * 绘制二维码
     * @param contents 二维码内容
     * @return image 二维码图片
     * */
    public static BufferedImage encodeImg(String contents){
        BufferedImage image = null;
        try{
            BitMatrix matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, width, height, hints);
            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            int width = matrix.getWidth();
            int height = matrix.getHeight();
            for(int x = 0; x < width; x++){
                for(int y =0;y < height; y++){
                    image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
                }
            }
        }catch(Exception e){
            System.out.println("生成二维码失败"+e.getMessage());
        }
        return image;
    }

    /**
     * 二维码输出到文件
     *  @param contents 二维码内容
     * @param format 图片格式
     * @param file 输出文件
     * */
    public static void writeToFile(String contents,String format,File file){
        BufferedImage image = encodeImg(contents);
        try {
            ImageIO.write(image, format, file);
        } catch (IOException e) {
            System.out.println("二维码写入文件失败"+e.getMessage());
        }
    }
    /**
     * 二维码流式输出
     *  @param contents 二维码内容
     * @param format 图片格式
     * @param stream 输出流
     * */
    public static void writeToStream(String contents,String format,OutputStream stream){
        BufferedImage image = encodeImg(contents);
        try {
            ImageIO.write(image, format, stream);
        } catch (IOException e) {
           e.getMessage();
        }
    }

}




