package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.service.FootballMatchService;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/14.
 */

@RestController("WebController")
@RequestMapping("/api/web/v1")
public class WebController {

    private static Logger LOG = LoggerFactory.getLogger(WebController.class);
    @Autowired
    private FootballMatchService footballMatchService;


    /**
     * 添加麦链杯比赛
     *
     * @param map 参数校验结果
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */

    @ApiOperation(value = "添加麦链杯比赛")
    @PostMapping("/addFootballMatch")
    public ResultInfo<?> addFootballMatch(@RequestBody Map<String, Object> map)
            throws Exception {
        return footballMatchService.addFootballMatch(map);
    }

    /**
     * 麦链杯比赛列表
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */

    @ApiOperation(value = "麦链杯比赛列表")
    @GetMapping("/footballMatchList")
    @RequiresPermissions("footballMatchList")
    public ResultInfo<?> footballMatchList()
            throws Exception {
        return footballMatchService.footballMatchList();
    }

    /**
     * 国家队员列表
     *
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */
    @ApiOperation(value = "国家队员列表")
    @GetMapping("/footballTeamList")
    public ResultInfo<?> footballTeamList()
            throws Exception {
        return footballMatchService.footballTeamList();
    }


    /**
     * 根据国家(球队ID)查询队员列表
     *
     * @param map 参数校验结果
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */
    @ApiOperation(value = "根据国家查询队员列表")
    @GetMapping("/teamMemberList")
    public ResultInfo<?> footballTeamMemberList(@RequestBody Map<String, Object> map)
            throws Exception {
        return footballMatchService.footballTeamMemberList(map);
    }

    /**
     * 根据球队ID和队员ID删除队员
     *
     * @param map 参数校验结果
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */
    @ApiOperation(value = "根据球队ID和队员ID删除队员")
    @GetMapping("/deleteTeamMember")
    public ResultInfo<?> deleteTeamMember(@RequestBody Map<String, Object> map)
            throws Exception {
        return footballMatchService.deleteTeamMember(map);
    }


    /**
     * 公布比赛结果，根据比赛结果，进行结算工资
     *
     * @param map footballMatchId 比赛ID  win 1：球队a赢球，2：球队b赢球，0：平局',
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */
    @ApiOperation(value = "公布比赛结果，根据比赛结果，进行结算工资")
    @PostMapping("/settlement")
    public ResultInfo<?> settlement(@RequestBody Map<String, Object> map) throws Exception {
        return footballMatchService.settlement(map);
    }

    /**
     * 修改让球分
     *
     * @param map
     * @return ResultInfo<> 返回查询结果
     * @throws Exception 异常
     * @date 2018年6月14日
     * @author LiMaoDa
     */
    @ApiOperation(value = "修改让球分")
    @PostMapping("/updateConcede")
    public ResultInfo<?> updateConcede(@RequestBody Map<String, Object> map) throws Exception {
        return footballMatchService.updateConcede(map);
    }
}
