package com.xdaocloud.futurechain.controller;


import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.exchange.PutMoneyRequest;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.service.ExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;

@RestController
@RequestMapping("/api/web")
@Api(value = "WebExchangeController", description = "代理管理")
public class WebExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "查看代理候选人")
    @PostMapping("v2/exchange/get/candidates")
    @RequiresPermissions("maichain_exchange_get_candidates")
    public ResultInfo<?> getCandidates(@Valid @RequestBody PageBase_Request pageBaseRequest)
            throws Exception {
        if (pageBaseRequest.getPage() >= 1) {
            pageBaseRequest.setPage(pageBaseRequest.getPage() - 1);
        }
        PageResponse response = exchangeService.getCandidates(pageBaseRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, response);
    }

    @ApiOperation(value = "代理审核")
    @PostMapping("v2/exchange/get/agents")
    @RequiresPermissions("maichain_exchange_get_agents")
    public ResultInfo<?> getAgent(@Valid @RequestBody PageBase_Request pageBaseRequest)
            throws Exception {
        if (pageBaseRequest.getPage() >= 1) {
            pageBaseRequest.setPage(pageBaseRequest.getPage() - 1);
        }
        Integer totalCount =  userMapper.findCountByAgent(new int[]{0,1,2});
        PageResponse response = exchangeService.getAgent(pageBaseRequest);
        response.setTotalPages(totalCount / pageBaseRequest.getSize());
        response.setTotalCount(totalCount);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, response);
    }

    /**
     * 兑换记录
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "兑换记录")
    @PostMapping("v2/exchange/forward/record")
    @RequiresPermissions("maichain_exchange_forward_record")
    public ResultInfo<?> forwardRecord(@Valid @RequestBody PageBase_Request request)
            throws Exception {
        if (request.getPage() >= 1) {
            request.setPage(request.getPage() - 1);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, exchangeService.AllForwardRecord(request));
    }

    /**
     * 代理商提现
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年7月4日
     * @author LuoFuMin
     */
    @ApiOperation(value = "代理商提现")
    @PostMapping("v2/exchange/put/forward")
    @RequiresPermissions("maichain_exchange_put_forward")
    public ResultInfo<?> putForward(@Valid @RequestBody PutMoneyRequest request)
            throws Exception {
        if (request.getA_menoy() == null) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM);
        }
        BigDecimal num = request.getA_menoy();
        if (num.compareTo(BigDecimal.valueOf(0.01)) <= 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "提现数量太低");
        }
        Boolean bool = exchangeService. putMoney(request);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

}
