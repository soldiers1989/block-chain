package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.dto.resp.ore.OreEnvelopeListDTO;
import com.xdaocloud.futurechain.model.OreEnvelope;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OreEnvelopeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OreEnvelope record);

    int insertSelective(OreEnvelope record);

    OreEnvelope selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OreEnvelope record);

    int updateByPrimaryKey(OreEnvelope record);

    /**
     * 根据id 查询 矿包个数
     *
     * @param id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Integer findCountById(@Param("id") Long id);


    /**
     * 根据用户id
     *
     * @param userid
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    List<OreEnvelopeListDTO> findByUserId(@Param("userid") Long userid);

    /**
     * 更新矿包状态为 false
     *
     * @param id 矿包id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    int updateOreStateFalseByPrimaryKey(Long id);


}