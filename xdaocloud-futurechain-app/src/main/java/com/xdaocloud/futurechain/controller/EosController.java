package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.eos.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.mapper.UserMapper;
import com.xdaocloud.futurechain.model.User;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.util.CharacterUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static cn.hutool.core.lang.Validator.isChinese;
import static com.xdaocloud.futurechain.util.CharacterUtils.*;

/**
 * EOS 控制类
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "EosController", description = "EOS 控制类")
@RequestMapping("/api/app/")
public class EosController {

    @Autowired
    private EosService eosService;

    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    /**
     * 创建eos钱包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "创建eos钱包", notes = "创建eos钱包")
    @PostMapping("v2/eos/wallet/create")
    public ResultInfo<?> create(@Valid @RequestBody EosCreateWalletRequest request) throws Exception {
        String walletName = request.getWalletName();
        if (isSpecialChar(walletName)) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "不能包含特殊字符");
        if (isNumberChar(walletName)) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "不能包含'06789'中任意字符");
        if (CharacterUtils.checkChinese(walletName)) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户不能包含中文字符");
        if (request.getWalletName().length() > 12) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户名不能超过12个字符");
        if (judgeContainsStr(request.getWalletName())) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户名不能包含大写字母");
        if (request.getWalletName().equals(request.getPassPhrase()))
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户名号密码不能相同");
        return eosService.create(request);
    }

    /**
     * 查询余额
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询余额", notes = "查询余额")
    @PostMapping("v2/eos/wallet/get_balance")
    public ResultInfo<?> getAsset(@Valid @RequestBody UserIdRequest request) throws Exception {
        return eosService.getBalance(Long.valueOf(request.getUserid()));
    }


    /**
     * 导入eos钱包
     *
     * @参数 request
     * @返回值 ResultInfo
     * @创建人 LuoFuMIn
     * @创建时间 2018/7/18
     */
    @ApiOperation(value = "导入eos钱包", notes = "导入eos钱包")
    @PostMapping("v2/eos/wallet/importAccount")
    public ResultInfo<?> importAccount(@Valid @RequestBody ImportAccountRequest request) throws Exception {
        Boolean bool = eosService.importAccount(request);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 获取私钥
     *
     * @param request
     * @return ResultInfo
     * @author LuoFuMIn
     * @date 2018/7/18
     */
    @ApiOperation(value = "获取私钥", notes = "获取私钥")
    @PostMapping("v2/eos/wallet/getPrivateKey")
    public ResultInfo<?> getPrivateKey(@Valid @RequestBody GetPrivateKeyRequest request) throws Exception {
        User user = userMapper.selectByPrimaryKey(Long.valueOf(request.getUserid()));
        Boolean boolTransactionPassword = passwordEncoder.matches(request.getTransactionPassword(), user.getTransactionPassword());
        if (!boolTransactionPassword) {
            return new ResultInfo<>(ResultInfo.FAILURE, "交易密码错误！");
        }
        String str = eosService.getPrivateKey(request);
        Map<String, String> map = new HashMap<>();
        map.put("key", str);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }


    /**
     * 转账交易
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "转账交易", notes = "转账交易")
    @PostMapping("v2/eos/wallet/transaction")
    public ResultInfo<?> transaction(@Valid @RequestBody EosTransactionRequest request) throws Exception {

        BigDecimal num = new BigDecimal(request.getNum());
        if (num.compareTo(BigDecimal.valueOf(0.0001)) < 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "交易数量不正确");
        }
        return new ResultInfo<>(ResultInfo.FAILURE, "锁钻余额不足");
        //return eosService.transaction(request);
    }

    /**
     * 锁仓转账交易
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    @ApiOperation(value = "锁仓转账交易", notes = "锁仓转账交易")
    @PostMapping("v2/eos/wallet/lock_transaction")
    public ResultInfo<?> lockTransaction(@Valid @RequestBody EosTransactionRequest request) throws Exception {
        BigDecimal num = new BigDecimal(request.getNum());
        if (num.compareTo(BigDecimal.valueOf(0.0001)) < 0) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "交易数量不正确");
        }
        return eosService.lockTransaction(request);
    }

    /**
     * 查询是否创建钱包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询是否创建钱包", notes = "查询是否创建钱包")
    @PostMapping("v2/eos/wallet/is_exist")
    public ResultInfo<?> findWallet(@Valid @RequestBody UserIdRequest request) throws Exception {
        return eosService.findWallet(Long.valueOf(request.getUserid()));
    }

    /**
     * 麦钻收支明细
     *
     * @param pageRequest
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "麦钻收支明细", notes = "page:起始页, size: 一页的数量, userid:用户id")
    @PostMapping("v2/eos/mai/transaction")
    public ResultInfo<?> findMAI_Transaction(@Valid @RequestBody Page_Request pageRequest) throws Exception {
        return eosService.findMAI_Transaction(pageRequest);
    }


    /**
     * 查询账户
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询账户", notes = "查询账户")
    @PostMapping("v2/eos/wallet/find_eos_account")
    public ResultInfo<?> findEOS_Account(@Valid @RequestBody EOS_Account request) throws Exception {
        return eosService.findEOS_Account(request);
    }

    /**
     * 设置免密金额
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "设置免密码金额", notes = "设置免密码金额")
    @PostMapping("v2/eos/wallet/setQuota")
    public ResultInfo<?> setQuota(@Valid @RequestBody SetQuotaRequest request) throws Exception {

        return eosService.setQuota(request);
    }

    /**
     * 查询免密金额
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "查询免密码金额", notes = "查询免密码金额")
    @PostMapping("v2/eos/wallet/findQuota")
    public ResultInfo<?> findQuota(@Valid @RequestBody UserIdRequest request) throws Exception {

        return eosService.findQuota(request);
    }

    /**
     * 激活免密码
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "激活免密码", notes = "激活免密")
    @PostMapping("v2/eos/wallet/activateQuota")
    public ResultInfo<?> activateQuota(@Valid @RequestBody UserIdRequest request) throws Exception {
        return eosService.activateQuota(request);
    }


    /**
     * 迁移eos账户
     *
     * @param password
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "！！！迁移账户", notes = "！！！迁移账户")
    @PostMapping("v2/eos/wallet/moveWallet")
    public ResultInfo<?> moveWallet(String password) throws Exception {
        if ("xiaodao27821306".equals(password)) {
            return eosService.moveWallet();
        }
        return null;
    }

    /**
     * 迁移eos账户
     *
     * @param password
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "！！！迁移账户错误，在尝试", notes = "！！！迁移账户错误，在尝试")
    @PostMapping("v2/eos/wallet/moveErrorWallet")
    public ResultInfo<?> moveErrorWallet(String password) throws Exception {
        if ("xiaodao27821306".equals(password)) {
            return eosService.moveErrorWallet();
        }
        return null;
    }

    /**
     * 迁移eos交易
     *
     * @param password
     * @return
     * @throws Exception
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    @ApiOperation(value = "！！！迁移eos币", notes = "！！！迁移eos币")
    @PostMapping("v2/eos/wallet/moveTransaction")
    public ResultInfo<?> moveTransaction(String password) throws Exception {
        if ("xiaodao27821306".equals(password)) {
            return eosService.moveTransaction();
        }
        return null;
    }

    /**
     * 补贴差价
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    @ApiOperation(value = "！！！补贴差价", notes = "！！！补贴差价")
    @PostMapping("v2/eos/wallet/compensate")
    public ResultInfo<?> compensate(String password) throws Exception {
        if ("xiaodao27821306".equals(password)) {
            return eosService.compensate();
        }
        return null;
    }

    /****************************************************************************************************************************************************/


    @ApiOperation(value = "创建eos钱包", notes = "创建eos钱包")
    @PostMapping("v1/eos/wallet/create")
    public ResultInfo<?> createV1(@Valid @RequestBody EosCreateWalletRequest request) throws Exception {

        String walletName = request.getWalletName();
        if (isSpecialChar(walletName)) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "不能包含特殊字符");
        if (isNumberChar(walletName)) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "不能包含06789字符");
        if (isChinese(walletName)) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户不能包含中文字符");
        if (request.getWalletName().length() > 12) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户名不能超过12个字符");
        if (judgeContainsStr(request.getWalletName())) return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户名不能包含大写字母");
        if (request.getWalletName().equals(request.getPassPhrase()))
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, "账户名号密码不能相同");
        return eosService.create(request);
    }

    @ApiOperation(value = "查询余额", notes = "查询余额")
    @PostMapping("v1/eos/wallet/get_balance")
    public ResultInfo<?> getAssetV1(@Valid @RequestBody UserIdRequest request) throws Exception {

        return eosService.getBalance(Long.valueOf(request.getUserid()));
    }

    @ApiOperation(value = "转账交易", notes = "转账交易")
    @PostMapping("v1/eos/wallet/transaction")
    public ResultInfo<?> transactionV1(@Valid @RequestBody EosTransactionRequest request) throws Exception {

        return eosService.transaction(request);
    }

    @ApiOperation(value = "查询是否创建钱包", notes = "查询是否创建钱包")
    @PostMapping("v1/eos/wallet/is_exist")
    public ResultInfo<?> findWalletV1(@Valid @RequestBody UserIdRequest request) throws Exception {
        return eosService.findWallet(Long.valueOf(request.getUserid()));
    }

    /**
     * 麦钻收支明细
     *
     * @param pageRequest
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "麦钻收支明细", notes = "page:起始页, size: 一页的数量, userid:用户id")
    @PostMapping("v1/eos/mai/transaction")
    public ResultInfo<?> findMAI_TransactionV1(@Valid @RequestBody Page_Request pageRequest) throws Exception {
        return eosService.findMAI_Transaction(pageRequest);
    }

    @ApiOperation(value = "查询账户", notes = "查询账户")
    @PostMapping("v1/eos/wallet/find_eos_account")
    public ResultInfo<?> findEOS_AccountV1(@Valid @RequestBody EOS_Account request) throws Exception {
        return eosService.findEOS_Account(request);
    }

}
