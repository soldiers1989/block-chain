package com.xdaocloud.futurechain.mapper;

import com.xdaocloud.futurechain.model.SignIn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignInMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SignIn record);

    int insertSelective(SignIn record);

    SignIn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SignIn record);

    int updateByPrimaryKey(SignIn record);

    /**
     * 根据用户id 查询今天是否签到
     * @param userid
     * @return true、false
     * @date 2018年6月2日
     * @author LuoFuMin
     *
     */
    Boolean todaySignIn(@Param("userid") Long userid);

    /**
     * 一周时间内签到次数
     * @return Integer
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    Integer weekSignIn();


    /**
     * 查询签到次数
     * @param userId 用户id
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    int findCountByUserId(@Param("userId") Long userId);

    List<SignIn> findListByUserId(@Param("userId") Long userId);

    int deleteByUserId(@Param("userId") Long userId);

    SignIn findLastByUserId(@Param("userId") Long userId);


}