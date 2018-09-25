package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.eos.SysToUserTransactionRequest;
import com.xdaocloud.futurechain.dto.req.eos.UnlockEosRequest;
import com.xdaocloud.futurechain.mapper.EosWalletMapper;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.model.EosWallet;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.service.ExchangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;


@RestController
@RequestMapping("/api/web")
@Api(value = "WebEosController", description = "转账管理")
public class WebEosController {


    @Autowired
    private EosService eosService;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ExchangeService exchangeService;

    /**
     * 系统转账
     *
     * @return Boolean 返回是否成功
     * @throws Exception 异常
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    @ApiOperation(value = "系统转账")
    @PostMapping("v2/eos/sysToUserTransaction")
    @RequiresPermissions("maichain_eos_sys_to_user_transaction")
    public ResultInfo<?> sysToUserTransaction(@Valid @RequestBody SysToUserTransactionRequest request)
            throws Exception {
        User user = userMapper.findByMobileMumber(request.getMobileNumber());
        if (user == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "手机号码不存在");
        }
        EosWallet eosWallet = eosWalletMapper.findOneByUserId(user.getId());
        if (!request.getEosWallet().equals(eosWallet.getWalletName())) {
            return new ResultInfo<>(ResultInfo.FAILURE, "手机号码和钱包名称不匹配");
        }
        Boolean bool = eosService.sysToUserTransaction(request, user);
        if (bool) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, "出错，请联系管理员！");
        }

    }


    /**
     * 解锁
     *
     * @return Boolean 返回是否成功
     * @throws Exception 异常
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    @ApiOperation(value = "解锁")
    @PostMapping("v2/eos/unlock_eos")
    @RequiresPermissions("maichain_eos_unlock")
    public ResultInfo<?> unlock(@Valid @RequestBody UnlockEosRequest request)
            throws Exception {
        User user = userMapper.findByMobileMumber(request.getMobileNumber());
        if (user == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "手机号码不存在");
        }
        EosWallet eosWallet = eosWalletMapper.findOneByUserId(user.getId());
        if (eosWallet == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "钱包不存在");
        }
        if (!request.getEosWallet().equals(eosWallet.getWalletName())) {
            return new ResultInfo<>(ResultInfo.FAILURE, "手机号码和钱包名称不匹配");
        }
        if (request.getAmount().compareTo(BigDecimal.valueOf(0.00001)) <= 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "交易数量不正确");
        }
        BigDecimal balance = exchangeService.findlockEosBalance(user.getId());
        if (balance == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, "用户锁仓余额不足");
        }
        if (balance.compareTo(request.getAmount()) < 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, "用户锁仓余额不足");
        }

        Boolean bool = eosService.unlockEos(request, user);
        if (bool) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
    }

    /**
     * 解锁记录
     *
     * @param pageBaseRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @ApiOperation(value = "解锁记录")
    @PostMapping("v2/eos/get/unlockEos")
    @RequiresPermissions("maichain_eos_get_unlocks")
    public ResultInfo<?> getUnlockEos(@Valid @RequestBody PageBase_Request pageBaseRequest)
            throws Exception {
        if (pageBaseRequest.getPage() >= 1) {
            pageBaseRequest.setPage(pageBaseRequest.getPage() - 1);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, eosService.getUnlockEos(pageBaseRequest));
    }
}
