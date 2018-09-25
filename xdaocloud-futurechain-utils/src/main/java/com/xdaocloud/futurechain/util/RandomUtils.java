package com.xdaocloud.futurechain.util;

import java.util.Random;

public class RandomUtils {

    public static String getItemID( int n )
    {
        String val = "";
        Random random = new Random();
        for ( int i = 0; i < n; i++ )
        {
            String str = random.nextInt( 2 ) % 2 == 0 ? "num" : "char";
            if ( "char".equalsIgnoreCase( str ) )
            { // 产生字母
                int nextInt = random.nextInt( 2 ) % 2 == 0 ? 65 : 97;
                // System.out.println(nextInt + "!!!!"); 1,0,1,1,1,0,0
                val += (char) ( nextInt + random.nextInt( 26 ) );
            }
            else if ( "num".equalsIgnoreCase( str ) )
            { // 产生数字
                val += String.valueOf( random.nextInt(5) );
            }
        }
        return val;
    }

    //生成随机数
    public static String getNumberId() {
        Random rand = new Random();//生成随机数
        String cardNnumer = "";
        for (int a = 0; a < 6; a++) {
            cardNnumer += rand.nextInt(10);//生成6位数字
        }
        cardNnumer = "1" + cardNnumer;
        return cardNnumer;
    }



    public static void main(String[] args) throws Exception {


        String bigMoney = "103000";

        System.out.println("====moneyStr===="+bigMoney.toString().indexOf("."));

        int dian = bigMoney.toString().indexOf(".");

        System.out.println("====moneyStr===="+bigMoney.toString().substring(0,dian));
    }
}
