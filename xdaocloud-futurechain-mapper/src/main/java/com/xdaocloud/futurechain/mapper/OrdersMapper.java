package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.orders.BuyRecordDTO;
import com.xdaocloud.futurechain.model.Orders;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Orders record);

    int insertSelective(Orders record);

    Orders selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Orders record);

    int updateByPrimaryKey(Orders record);

    /**
     * 根据用户id 查询订单
     *
     * @param userid
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<Orders> findByUserId(@Param("userid") Long userid);


    /**
     * 根据内部订单号查询
     *
     * @param orderNo 内部订单号
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Orders findByOrderNo(@Param("orderNo") String orderNo);


    /**
     * 根据用户id查询充值总额
     *
     * @param userId 用户id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findSumFeeByUserId(@Param("userId") Long userId);


    List<BuyRecordDTO> findAllSumBuyRecord();


    /**
     * 根据用户id查询充值rmb总额
     *
     * @param userIds 用户id数组
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findSumFeeByUserIds(@Param("userIds") List<Long> userIds);


    /**
     * 根据用户id查询充值eos总额
     *
     * @param userIds 用户id数组
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findSumEosByUserIds(@Param("userIds") List<Long> userIds);


    /**
     * 根据用户id查询充值rmb总额
     *
     * @param userIds 用户id数组
     * @return
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    BigDecimal findSumRmbByUserIds(@Param("userIds") List<Long> userIds);

    /**
     * 根据用户ID查询充值总值
     *
     * @param userid 用户id
     * @return
     */
    BigDecimal selectSumFee(@Param("userid") Long userid);

    /**
     * 查询订单数量
     *
     * @param userid    用户id
     * @return
     * @date 2018年6月27日
     * @author LuoFuMin
     */
    BigDecimal findSumAmountByUserId(@Param("userid") Long userid);


    /**
     * 获取订单
     * <p>
     * Object 返回查询结果
     *
     * @throws Exception 异常
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    List<BuyRecordDTO> findSumFeeAndEos(@Param("page") Integer page, @Param("size") Integer size);


    int findCount();

    /**
     * 查询订单
     *
     * @param state
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<BuyRecordDTO> findListByState(int state);


    /**
     * 查询订单
     *
     * @param state 状态
     * @param phone 手机号码
     * @return
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<BuyRecordDTO> findListByStateAndPhone(@Param("state") int state,@Param("phone") String phone);

}