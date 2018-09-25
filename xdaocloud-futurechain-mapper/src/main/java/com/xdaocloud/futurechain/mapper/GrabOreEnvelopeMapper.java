package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.ore.GrabOreEnvelopeDTO;
import com.xdaocloud.futurechain.model.GrabOreEnvelope;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface GrabOreEnvelopeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(GrabOreEnvelope record);

    int insertSelective(GrabOreEnvelope record);

    GrabOreEnvelope selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(GrabOreEnvelope record);

    int updateByPrimaryKey(GrabOreEnvelope record);

    /**
     * 根据矿包id查询已抢矿包数数量
     *
     * @param oreEnvelopeId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Integer findCountByOreEnvelopeId(@Param("oreEnvelopeId") Long oreEnvelopeId);

    /**
     * 根据矿包id 查询已抢矿包记录
     *
     * @param oreEnvelopeId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<GrabOreEnvelopeDTO> findByOreEnvelopeId(@Param("oreEnvelopeId") Long oreEnvelopeId);

    /**
     * 根据用户id和矿包id查询已抢矿包数量
     *
     * @param oreEnvelopeId
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Integer findCountByOreEnvelopeIdAndUserId(@Param("oreEnvelopeId") Long oreEnvelopeId, @Param("userId") Long userId);


    /**
     * 根据用户id和矿包id查询已抢矿包随机数
     *
     * @param oreEnvelopeId
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findRandomNumberByOreEnvelopeIdAndUserId(@Param("oreEnvelopeId") Long oreEnvelopeId, @Param("userId") Long userId);


    /**
     * 根据矿包id和抢矿包的用户id 查到抢矿包记录
     *
     * @param oreEnvelopeId
     * @param userId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    GrabOreEnvelope findByOreEnvelopeIdAndUserId(@Param("oreEnvelopeId") Long oreEnvelopeId, @Param("userId") Long userId);


    /**
     * 根据查询矿包id查询到所抢红包的总数
     *
     * @param oreEnvelopeId
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    BigDecimal findSumByOreEnvelopeId(@Param("oreEnvelopeId") Long oreEnvelopeId);

}