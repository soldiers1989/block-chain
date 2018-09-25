
package com.xdaocloud.futurechain.ethereum;

import com.alibaba.fastjson.JSON;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.ethereum.SolidityRequest;
import com.xdaocloud.futurechain.dto.req.ethereum.TransactionRequest;
import com.xdaocloud.futurechain.dto.resp.etherum.GasPriceDTO;
import com.xdaocloud.futurechain.model.FtcTransaction;
import com.xdaocloud.futurechain.service.FtcTransactionService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;


/**
 * 以太坊交易类
 */

@Service
public class EthereumTrade {

    private static Logger LOGGER = LoggerFactory.getLogger(EthereumTrade.class);

    //@Autowired
    private Web3j web3j;

    //@Autowired
    private Admin admin;

    @Autowired
    private FtcTransactionService ftcTransactionService;


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

    public ResultInfo<?> ethTransaction(TransactionRequest tRequest) throws Exception {


/* 为了防止交易的重播攻击，每笔交易必须有一个nonce随机数，针对每一个账户nonce都是从0开始，当nonce为0的交易处理完之后，才会处理nonce为1的交易，并依次加1的交易才会被处理。
       以下是nonce使用的几条规则：当nonce太小，交易会被直接拒绝； 当nonce太大，交易会一直处于队列之中。*/

        EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(tRequest.getFrom(), DefaultBlockParameter.valueOf("latest")).sendAsync().get();
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        nonce = nonce.add(BigInteger.valueOf(1));
        //查询余额
        EthGetBalance ethGetBalance = web3j.ethGetBalance(tRequest.getFrom(), DefaultBlockParameter.valueOf("latest")).send();
        //需要的以太币数量 sumWei = value+gas*gasPrice;
        BigInteger sumWei = tRequest.getValue().add(tRequest.getGas().multiply(tRequest.getGasPrice()));
        if (ethGetBalance.getBalance().compareTo(sumWei) != 1){//compareTo方法来比较，小于则返回-1，等于则返回0，大于则返回1
            LOGGER.info("===余额===" + ethGetBalance.getBalance()+"===需要金额=="+sumWei);
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE,"余额=" + ethGetBalance.getBalance()+"，一共需要金额="+sumWei);
        }
        //1.解锁账户
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(tRequest.getFrom(), tRequest.getPassphrase()).send();
        LOGGER.info("===personalUnlockAccount==" + JSON.toJSONString(personalUnlockAccount));
        if (null != personalUnlockAccount.getError()){//错误信息不为空说明交易出错
            saveErrorTransactionRecord(tRequest,personalUnlockAccount.getError().getMessage(),nonce);
            LOGGER.error(tRequest.getFrom()+"==账户解锁失败==");
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE,"账户解锁失败");
        }

        Transaction transaction = new Transaction(tRequest.getFrom(), nonce, tRequest.getGasPrice(), tRequest.getGas(), tRequest.getTo(), tRequest.getValue(), tRequest.getData());
        //2.发送交易
        EthSendTransaction ethSendTransaction = admin.personalSendTransaction(transaction, tRequest.getPassphrase()).send();
        LOGGER.info("===ethSendTransaction==" + JSON.toJSONString(ethSendTransaction));
        if (null != ethSendTransaction.getError()) {//错误信息不为空说明交易出错
            saveErrorTransactionRecord(tRequest,ethSendTransaction.getError().getMessage(),nonce);
                return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE,ethSendTransaction.getError().getMessage());
        }
        //保存交易信息
        FtcTransaction ftcTransaction = new FtcTransaction(Long.valueOf(tRequest.getUserid()), tRequest.getFrom(), tRequest.getTo(), tRequest.getValue(), tRequest.getGas(),
                tRequest.getGasPrice(), (byte) 3, nonce, ethSendTransaction.getTransactionHash(), false);
       ftcTransactionService.saveFtcTransaction(ftcTransaction);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


/**
     * 查询交易记录
     *
     * @param walletAddress 钱包地址
     * @return transactionCountResponse
     * @throws IOException
     */

    public EthGetTransactionCount getTransactionCount(String walletAddress) throws IOException {
        if (StringUtils.isBlank(walletAddress)) return null;
        EthGetTransactionCount transactionCountResponse = web3j.ethGetTransactionCount(walletAddress, DefaultBlockParameter.valueOf("latest")).send();
        return transactionCountResponse;
    }


/**
     * 查询以太坊账户余额
     *
     * @param walletAddress 钱包地址
     * @return ethGetBalance
     */

    public EthGetBalance ethGetBalance(String walletAddress) throws IOException {
        EthGetBalance ethGetBalance = web3j.ethGetBalance(walletAddress, DefaultBlockParameter.valueOf("latest")).send();
        return ethGetBalance;
    }


/**
     *  获取 gas单价和数量
     * @return BigInteger
     * @throws IOException
     */

    public GasPriceDTO ethGasAndGasPrice() throws IOException {
        EthGasPrice ethGasPrice = web3j.ethGasPrice().send();
        EthEstimateGas ethEstimateGas = web3j.ethEstimateGas(null).send();
        GasPriceDTO gasPriceDTO = new GasPriceDTO(ethEstimateGas.getAmountUsed(),ethGasPrice.getGasPrice());
        return gasPriceDTO;
    }


/**
     * 保存错误交易信息
     *
     * @param tRequest
     * @param errorMessage
     * @param nonce
     */

    public void saveErrorTransactionRecord(TransactionRequest tRequest,String errorMessage,BigInteger nonce){
        FtcTransaction ftcTransaction = new FtcTransaction(Long.valueOf(tRequest.getUserid()), tRequest.getFrom(), tRequest.getTo(), tRequest.getValue(), tRequest.getGas(),
                tRequest.getGasPrice(), (byte) 0, errorMessage, nonce, false);
         ftcTransactionService.saveFtcTransaction(ftcTransaction);
    }


/**
     * 发布合约
     *
     * @param solidityRequest
     * @return
     * @throws IOException
     */

    public EthCompileSolidity ethCompileSolidity(SolidityRequest solidityRequest) throws IOException {
        EthCompileSolidity ethCompileSolidity = web3j.ethCompileSolidity(solidityRequest.getCode()).send();
        return ethCompileSolidity;
    }


}

