package com.xdaocloud.futurechain.util;

import java.util.Date;

public class ProductOrderUtils {

    /**
     * @Title: createDiscountOrderNumber
     * @Description: 创建单号16位
     * @return String
     */
    public static String createOrderNumber() {
        String orderNumber = "";
        int code = 0;
        while (code < 100) {
            code = (int) (Math.random() * 1000);
        }
        String currentTime = String.valueOf(new Date().getTime());
        orderNumber = currentTime + code;
        return orderNumber;
    }
}
