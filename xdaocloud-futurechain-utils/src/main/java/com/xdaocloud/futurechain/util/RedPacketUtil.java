package com.xdaocloud.futurechain.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RedPacketUtil {

    /**
     * 技术红包随机数
     *
     * @param mmm      红包总数
     * @param number   分红包个数
     * @param accurate 小数点精确位数
     * @return
     */
    public static List<BigDecimal> math(BigDecimal mmm, int number, int accurate) {
        if (mmm.doubleValue() < number * 0.01) {
            return null;
        }
        Random random = new Random();
        // 金钱，按分计算 10块等于 1000分
        int money = mmm.multiply(BigDecimal.valueOf(accurate)).intValue();
        // 随机数总额
        double count = 0;
        // 每人获得随机点数
        double[] arrRandom = new double[number];
        // 每人获得钱数
        List<BigDecimal> arrMoney = new ArrayList<BigDecimal>(number);
        // 循环人数 随机点
        for (int i = 0; i < arrRandom.length; i++) {
            int r = random.nextInt((number) * 99) + 1;
            count += r;
            arrRandom[i] = r;
        }
        // 计算每人拆红包获得金额
        int c = 0;
        for (int i = 0; i < arrRandom.length; i++) {
            // 每人获得随机数相加 计算每人占百分比
            Double x = new Double(arrRandom[i] / count);
            // 每人通过百分比获得金额
            int m = (int) Math.floor(x * money);
            // 如果获得 0 金额，则设置最小值 1分钱
            if (m == 0) {
                m = 1;
            }
            // 计算获得总额
            c += m;
            // 如果不是最后一个人则正常计算
            if (i < arrRandom.length - 1) {
                arrMoney.add(new BigDecimal(m).divide(new BigDecimal(accurate)));
            } else {
                // 如果是最后一个人，则把剩余的钱数给最后一个人
                arrMoney.add(new BigDecimal(money - c + m).divide(new BigDecimal(accurate)));
            }
        }
        // 随机打乱每人获得金额
        Collections.shuffle(arrMoney);
        return arrMoney;
    }


    public static void main(String[] args) {



       List<BigDecimal> list = math(new BigDecimal("1.3"), 3, 10000);

        BigDecimal sum = new BigDecimal("0");

        for (BigDecimal b : list) {
            sum = sum.add(b);
        }
        System.out.println("====" + sum);

        String str = JSON.toJSONString(list);
        System.out.println("==str==" + str);

    }
}
/*
    List<Student> students = new ArrayList();
    String str = JSON.toJSONString(students); // List转json

    Json 转List 方法一

        String json = ""; //获取的Json数据
        List<Student> students = JSON.parseObject(json,new TypeReference<List<Student>>(){}); // Json 转List

        Json 转List方法二

        List<Student> students = JSON.parseArray(json,Student.class);*/
