package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.exchange.AllForwardRecordResponse;
import com.xdaocloud.futurechain.model.Exchange;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExchangeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Exchange record);

    int insertSelective(Exchange record);

    Exchange selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Exchange record);

    int updateByPrimaryKey(Exchange record);


    /**
     * 查询已经提现的总额
     *
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findEosPutForwardSumByUserId(@Param("userId") Long userId);


    /**
     * 查询已经提现的eos总额
     *
     * @param userId
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    BigDecimal findEosPutForwardSumByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);


    /**
     * 查询已经提现的rmb总额
     *
     * @param userId
     * @return
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    BigDecimal findRmbPutForwardSumByUserIdAndType(@Param("userId") Long userId, @Param("type") Integer type);


    /**
     * 查询提现记录
     *
     * @param userId
     * @param page
     * @param size
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<Exchange> findListByUserId(@Param("userId") Long userId, @Param("page") int page, @Param("size") int size);


    /**
     * 查询提现记录
     *
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<AllForwardRecordResponse> findAllList();


    /**
     * 查询提现记录
     *
     * @param phone
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<AllForwardRecordResponse> findAllListByPhone(@Param("phone") String phone);


    /**
     * 查询提现记录
     *
     * @param userId
     * @param remark
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findSumByUserIdAndRemark(@Param("userId") Long userId, @Param("remark") String remark);
}