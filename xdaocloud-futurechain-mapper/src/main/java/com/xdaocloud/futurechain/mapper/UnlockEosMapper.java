package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.eos.UnlockEosInfo;
import com.xdaocloud.futurechain.model.UnlockEos;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface UnlockEosMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UnlockEos record);

    int insertSelective(UnlockEos record);

    UnlockEos selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UnlockEos record);

    int updateByPrimaryKey(UnlockEos record);

    /**
     * 查询解锁总数
     *
     * @param userId 用户id
     * @return 解锁总数
     * @date 2018年6月27日
     * @author LuoFuMin
     */
    BigDecimal findSumAmountByUserId(@Param("userId") Long userId);


    /**
     * 查询列表
     *
     * @return List
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<UnlockEosInfo> findList();

    /**
     * 查询列表
     *
     * @param phone 手机号码
     * @return List
     * @date 2018年7月2日
     * @author LuoFuMin
     */
    List<UnlockEosInfo> findListByPhone(String phone);
}