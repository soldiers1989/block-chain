
package com.xdaocloud.futurechain.ethereum;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.ethereum.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.dto.resp.etherum.GasPriceDTO;
import com.xdaocloud.futurechain.model.EthereumWallet;
import com.xdaocloud.futurechain.service.EthereumWalletService;
import com.xdaocloud.futurechain.service.FtcTransactionService;
import com.xdaocloud.futurechain.util.EthereumUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.core.methods.response.EthCompileSolidity;
import org.web3j.protocol.core.methods.response.EthGetBalance;

import javax.validation.Valid;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * 以太坊操作类
 */

@RestController
public class EthereumController extends BaseController {

    private static Logger LOGGER = LoggerFactory.getLogger(EthereumController.class);


/**
     * 以太坊账户操作
     */

    @Autowired
    private EthereumAccount ethereumAccount;


/**
     * 以太坊交易控制
     */

    @Autowired
    private EthereumTrade ethereumTrade;


    @Autowired
    private EthereumWalletService ethereumWalletService;

    @Autowired
    private FtcTransactionService ftcTransactionService;



/**
     * 创建以太坊账钱包
     *
     * @param request
     * @return
     */

    @ApiOperation(value = "创建钱包", notes = "userid:用户id,password:钱包密码,remark:备注（可以为空）")
    @PostMapping("v1/ethereum/create/wallet")
    public ResultInfo<?> createEthereumAccount(@Valid @RequestBody CreateWalletRequest request, BindingResult bindingResult) throws Exception {
        parseBindingResult(bindingResult);
        int i = ethereumWalletService.findCountByUserid(Long.valueOf(request.getUserid()));
        if (i > 0) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "已经存在钱包");
        }
        EthereumWallet ethereumWallet = ethereumAccount.createEthereumWallet(request);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, ethereumWallet);
    }


/**
     * 查询以太坊账户余额
     *
     * @param walletAddress 钱包地址
     * @return
     */

    @ApiOperation(value = "查询余额", notes = "walletAddress：钱包地址（42位）")
    @PostMapping("v1/ethereum/get/balance")
    public ResultInfo<?> ethGetBalance(@Valid @RequestBody WalletRequest walletAddress, BindingResult bindingResult) throws Exception {
        parseBindingResult(bindingResult);
        EthGetBalance ethGetBalance = ethereumTrade.ethGetBalance(walletAddress.getWalletAddress());
        String str = EthereumUtils.conversionEther(ethGetBalance.getBalance());
        Map<String,String> map = new HashMap<>();
        map.put("value",str);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }


/**
     * 发起交易
     *
     * @param tRequest
     * @return
     * @throws IOException
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws TimeoutException
     * @throws CipherException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */

    @ApiOperation(value = "查询余额", notes = "userid:用户id, passphrase:钱包密码, from:转出账户, to:转入账户, gas:gas数量, gasPrice:gas单价, value:转账金额, data:已编译的合同代码或被调用的方法签名和编码参数的散列???（可以为空字符串）")
    @PostMapping("v1/ethereum/send/transaction")
    public ResultInfo<?> sendTransaction(@Valid @RequestBody TransactionRequest tRequest, BindingResult bindingResult) throws Exception {
        parseBindingResult(bindingResult);
        return ethereumTrade.ethTransaction(tRequest);
    }


/**
     * 查询交易记录
     *
     * @param request
     * @return
     * @throws IOException
     */

    @ApiOperation(value = "查询交易记录", notes = "address：钱包地址（42位）,page:起始页,size: 一页的数量")
    @PostMapping("v1/ethereum/get/transaction")
    public ResultInfo<?> sendTransaction(@Valid @RequestBody TransactionRecordRequest request, BindingResult bindingResult) throws Exception {
        parseBindingResult(bindingResult);
        if (StringUtils.isBlank(request.getAddress()) || request.getAddress().length() != 42 || !request.getAddress().substring(0, 2).equals("0x")) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM, "错误地址格式");
        }
        if (request.getPage() < 1 || request.getSize() < 1) {
            return new ResultInfo<>(ResultInfo.INVALID_PARAM, ResultInfo.MSG_INVALID_PARAM);
        }
        return ftcTransactionService.findByAddress(request);
    }



/**
     * gas单价和数量
     *
     * @return gasPriceDTO
     * @throws IOException
     */

    @ApiOperation(value = "系统推荐gas单价和数量", notes = "查询以太坊账户交易所需gas单价和数量")
    @GetMapping("v1/ethereum/get/gas/price")
    public ResultInfo<?> ethGasPrice() throws Exception {
        GasPriceDTO gasPriceDTO = ethereumTrade.ethGasAndGasPrice();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, gasPriceDTO);
    }



/**
     * 查询用户的线包地址
     *
     * @return
     * @throws IOException
     */

    @ApiOperation(value = "查询用户的线包地址", notes = "userid：用户id")
    @PostMapping("v1/ethereum/get/wallet/address")
    public ResultInfo<?> ethGetWalletAddress(@Valid @RequestBody UserIdRequest userIdRequest, BindingResult bindingResult) throws Exception {
        parseBindingResult(bindingResult);
        String string = ethereumWalletService.findWalletAddressByUserid(Long.valueOf(userIdRequest.getUserid()));

        Map<String, String> map = new HashMap<>();
        map.put("address", string);

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }


/**
     * 发布合约
     *
     * @param solidityRequest
     * @return
     * @throws Exception
     */

    @ApiOperation(value = "发布合约", notes = "code：合约代码,userid：用户id")
    @PostMapping("v1/ethereum/compile/solidity")
    public ResultInfo<?> ethCompileSolidity(@Valid @RequestBody SolidityRequest solidityRequest) throws Exception {
        EthCompileSolidity compileSolidity = ethereumTrade.ethCompileSolidity(solidityRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, compileSolidity);
    }

}

