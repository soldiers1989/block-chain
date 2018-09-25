package com.xdaocloud.futurechain.mapper;

import org.apache.ibatis.annotations.Param;
import com.xdaocloud.futurechain.model.ReadingRecord;

public interface ReadingRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReadingRecord record);

    int insertSelective(ReadingRecord record);

    ReadingRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReadingRecord record);

    int updateByPrimaryKey(ReadingRecord record);
    
    /**
     * 用户是否已阅读
     */
    long selectIsRead(@Param("userId") Long userId,@Param("circleId")  Long circleId);
    
    /**
     * 查询阅读数(一个月内)       AND
     (gmt_create <![CDATA[ >= ]]> (select DATE_ADD(curdate(),interval -day(curdate())+1 day)) AND gmt_create <![CDATA[ <= ]]> (select last_day(curdate())))
     */
    long selectReadCountByMonth(@Param("circleId") Long circleId);
    
    /**
     * 查询阅读朋友数
     */
    long selectReadFriendNum(@Param("userid") Long userId,@Param("circleId")  Long circleId);
    
    /**
     * 查询阅读总数
     */
    long selectReadCountAll(@Param("userId") Long userId);

}