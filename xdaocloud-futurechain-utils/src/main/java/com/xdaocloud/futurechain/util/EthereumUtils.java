package com.xdaocloud.futurechain.util;


import java.math.BigInteger;

/**
 * Ether币的基本单位
 * Ether币最小的单位是Wei，也是命令行默认的单位, 然后每1000个进一个单位，依次是
 * kwei (1000 Wei)
 * mwei (1000 KWei)
 * gwei (1000 mwei)
 * szabo (1000 gwei)
 * finney (1000 szabo)
 * ether (1000 finney)
 * 1(ether) = 1 000 000 000 000 000 000(Wei)
 */
public class EthereumUtils {


    /**
     * 转换单位
     *
     * @param ftc 以太币
     * @return
     */
    public static String conversion(BigInteger ftc) {
        StringBuffer money = new StringBuffer();
        //compareTo方法来比较，小于则返回-1，等于则返回0，大于则返回1
        if (ftc.compareTo(BigInteger.valueOf(0)) > 0) {

            BigInteger ether_bigInteger = ftc.divide(new BigInteger("1000000000000000000"));
            int ether = ether_bigInteger.compareTo(BigInteger.valueOf(0));
            if (ether > 0) {
                money.append(ether_bigInteger).append("(ether)");
                ftc =  ftc.remainder(new BigInteger("1000000000000000000"));
            }

            BigInteger finney_bigInteger = ftc.divide(new BigInteger("1000000000000000"));
            int finney = finney_bigInteger.compareTo(BigInteger.valueOf(0));
            if (finney > 0) {
                money.append(finney_bigInteger).append("(finney)");
                ftc =  ftc.remainder(new BigInteger("1000000000000000"));
            }

            BigInteger szabo_bigInteger = ftc.divide(new BigInteger("1000000000000"));
            int szabo = szabo_bigInteger.compareTo(BigInteger.valueOf(0));
            if (szabo > 0) {
                money.append(szabo_bigInteger).append("(szabo)");
                ftc =  ftc.remainder(new BigInteger("1000000000000"));
            }

            BigInteger gwei_bigInteger = ftc.divide(new BigInteger("1000000000"));
            int gwei = gwei_bigInteger.compareTo(BigInteger.valueOf(0));
            if (gwei > 0) {
                money.append(gwei_bigInteger).append("(gwei)");
                ftc =  ftc.remainder(new BigInteger("1000000000"));
            }

            BigInteger mwei_bigInteger = ftc.divide(new BigInteger("1000000"));
            int mwei = mwei_bigInteger.compareTo(BigInteger.valueOf(0));
            if (mwei > 0) {
                money.append(mwei_bigInteger).append("(mwei)");
                ftc =  ftc.remainder(new BigInteger("1000000"));
            }

            BigInteger kwei_bigInteger = ftc.divide(new BigInteger("1000"));
            int kwei = kwei_bigInteger.compareTo(BigInteger.valueOf(0));
            if (kwei > 0) {
                money.append(kwei_bigInteger).append("(kwei)");
                ftc =  ftc.remainder(new BigInteger("1000"));
            }
            if (ftc.compareTo(BigInteger.valueOf(0)) > 0) {
                money.append(ftc).append("(wei)");
            }
            return money.toString();
        }
        return null;
    }

    public static String conversionEther(BigInteger ftc){
        StringBuffer money = new StringBuffer();
        //compareTo方法来比较，小于则返回-1，等于则返回0，大于则返回1
        if (ftc.compareTo(BigInteger.valueOf(0)) > 0) {

            BigInteger ether_bigInteger = ftc.divide(new BigInteger("1000000000000000000"));
            int ether = ether_bigInteger.compareTo(BigInteger.valueOf(0));
            if (ether > 0) {
                money.append(ether_bigInteger).append(".");
                ftc = ftc.remainder(new BigInteger("1000000000000000000"));
                money.append(ether_bigInteger).append(ftc);
                return money.toString();
            }
        }
        return "0";
    }


    public static void main(String[] args) {
        System.out.println("===" + conversion(new BigInteger("1102550054646312574643")));
    }
}
