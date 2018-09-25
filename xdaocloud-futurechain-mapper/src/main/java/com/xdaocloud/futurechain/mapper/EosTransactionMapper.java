package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.EosTransaction;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface EosTransactionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(EosTransaction record);

    int insertSelective(EosTransaction record);

    EosTransaction selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(EosTransaction record);

    int updateByPrimaryKey(EosTransaction record);

    List<EosTransaction> findListByParam(EosTransaction record);

    List<EosTransaction> findListByWalletName(String walletName);

    List<EosTransaction> findListByFromWalletName(String walletName);

    /**
     * 根据人账钱包名查询交易记录
     *
     * @param walletName
     * @return List<EosTransaction>
     * @date 2018年6月20日
     * @author LuoFuMin
     */
    List<EosTransaction> findListByToWalletName(String walletName);

    /**
     * 查询总入账
     *
     * @param walletName
     * @return 入账总金额
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    BigDecimal findSumByToWalletAndTranState(@Param("walletName") String walletName,@Param("tranState") int tranState);


    /**
     * 查询总出账
     *
     * @param walletName
     * @return 出账总金额
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    BigDecimal findSumByFromWalletAndTranState(@Param("walletName") String walletName,@Param("tranState") int tranState);

    /**
     * 查询总入账
     *
     * @param walletName
     * @return 入账总金额
     * @date 2018年6月20日
     * @author LuoFuMin
     */
    BigDecimal findSumByToWallet(String walletName);


    /**
     * 查询总出账
     *
     * @param walletName
     * @return 出账总金额
     * @date 2018年6月20日
     * @author LuoFuMin
     */
    BigDecimal findSumByFromWallet(String walletName);



    /**
     * 查询所有eos交易记录
     * @return   List<EosTransaction>
     */
    List<EosTransaction> findAllList();
}