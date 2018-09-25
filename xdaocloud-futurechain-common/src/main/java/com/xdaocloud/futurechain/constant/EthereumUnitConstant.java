package com.xdaocloud.futurechain.constant;

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
public class EthereumUnitConstant {

    public static final BigInteger ETHER =  new BigInteger("1000000000000000000");
}
