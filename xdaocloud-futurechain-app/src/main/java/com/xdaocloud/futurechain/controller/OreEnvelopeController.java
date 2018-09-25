package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.ore.CreateOreEnvelopeRequest;
import com.xdaocloud.futurechain.dto.req.ore.GrabOreEnvelopeRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.service.OreEnvelopeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * 矿包控制类
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "OreEnvelopeController", description="矿包控制类")
@RequestMapping("/api/app/")
public class OreEnvelopeController {

    /**
     * 矿包控制
     */
    @Autowired
    private OreEnvelopeService oreEnvelopeService;

    /**
     * 创建矿包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "创建矿包", notes = "userid:用户id,count:矿包数量,amount:矿包金额,ore_title:矿包标题")
    @PostMapping("v2/ore/create/envelope")
    public ResultInfo<?> createOreEnvelope(@Valid @RequestBody CreateOreEnvelopeRequest request) throws Exception {

        BigDecimal amount = new BigDecimal(request.getAmount());
        BigDecimal count = new BigDecimal(request.getCount());
        BigDecimal num = amount.divide(count,4, BigDecimal.ROUND_DOWN);
        if (num.compareTo(BigDecimal.valueOf(0.00001)) <= 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "提现数量太低");
        }
        return oreEnvelopeService.createOreEnvelope(request);
    }

    /**
     * 抢矿包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "抢矿包", notes = "userid:用户id,oreEnvelopeId:矿包id")
    @PostMapping("v2/ore/gra/envelope")
    public ResultInfo grabOreEnvelope(@Valid @RequestBody GrabOreEnvelopeRequest request) throws Exception {
        return oreEnvelopeService.grabOreEnvelope(request);
    }

    /**
     * 获取矿包列表
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取矿包列表", notes = "userid:用户id,oreEnvelopeId:矿包id")
    @PostMapping("v2/ore/get/envelope")
    public ResultInfo getOreEnvelopes(@Valid @RequestBody UserIdRequest request) throws Exception {
        return oreEnvelopeService.getOreEnvelopes(request);
    }

    /************************************************************************************************************************************************************/


    /**
     * 创建矿包
     *
     * @param request
     * @param
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "创建矿包", notes = "userid:用户id,count:矿包数量,amount:矿包金额,ore_title:矿包标题")
    @PostMapping("v1/ore/create/envelope")
    public ResultInfo<?> createOreEnvelopeV1(@Valid @RequestBody CreateOreEnvelopeRequest request) throws Exception {

        int hStr = request.getAmount().length();
        int dian = request.getAmount().indexOf(".");
        if (dian != -1) {
            if (hStr - dian > 5) {
                return new ResultInfo<>(ResultInfo.FAILURE, "小数点超长");
            }
        }
        return oreEnvelopeService.createOreEnvelope(request);
    }

    /**
     * 抢矿包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "抢矿包", notes = "userid:用户id,oreEnvelopeId:矿包id")
    @PostMapping("v1/ore/gra/envelope")
    public ResultInfo grabOreEnvelopeV1(@Valid @RequestBody GrabOreEnvelopeRequest request) throws Exception {
        return oreEnvelopeService.grabOreEnvelope(request);
    }

    /**
     * 获取矿包列表
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "获取矿包列表", notes = "userid:用户id,oreEnvelopeId:矿包id")
    @PostMapping("v1/ore/get/envelope")
    public ResultInfo getOreEnvelopesV1(@Valid @RequestBody UserIdRequest request) throws Exception {
        return oreEnvelopeService.getOreEnvelopes(request);
    }
}
