package com.xdaocloud.futurechain.eos;


import com.xdaocloud.futurechain.constant.EOSConstant;
import com.xdaocloud.futurechain.dto.EosResponse;
import com.xdaocloud.futurechain.dto.resp.eos.KeyDTO;
import com.xdaocloud.futurechain.mapper.EosTransactionMapper;
import com.xdaocloud.futurechain.model.EosTransaction;
import io.eblock.eos4j.Ecc;
import io.eblock.eos4j.Rpc;
import io.eblock.eos4j.api.exception.ApiException;
import io.eblock.eos4j.api.vo.account.Balance;
import io.eblock.eos4j.api.vo.transaction.Transaction;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;


@Service
public class EosAccount {

    private static final Logger LOG = LoggerFactory.getLogger(EosAccount.class);

    @Autowired
    private Rpc rpc;

    @Autowired
    private EosTransactionMapper eosTransactionMapper;

    public static final String TRANSACTION_FAILURE = "交易失败";

    public static final String TRANSACTION_SUCCESS = "交易成功";

    @Autowired
    private EOSConstant eosConstant;


    /**
     * 将double格式化为指定小数位的String，不足小数位用0补全
     *
     * @param v 需要格式化的数字
     * @return
     */
    public static String roundByScale(double v) {
        int scale = 4;
        String formatStr = "0.";
        for (int i = 0; i < scale; i++) {
            formatStr = formatStr + "0";
        }
        return new DecimalFormat(formatStr).format(v);
    }


    /**
     * 创建账户
     *
     * @param account 账目名称
     * @return
     */
    public EosResponse newAccount(String account) throws Exception {

        String owner_private_key = Ecc.seedPrivate(UUID.randomUUID().toString());
        LOG.info("owner_private_key :" + owner_private_key);
        String owner_public_key = Ecc.privateToPublic(owner_private_key);
        LOG.info("owner_public_key:" + owner_public_key);

        String active_private_key = Ecc.seedPrivate(UUID.randomUUID().toString());
        LOG.info("active_private_key :" + owner_private_key + "\n");

        String active_public_key = Ecc.privateToPublic(active_private_key);
        LOG.info("active_public_key :" + owner_public_key + " \n ");

        LOG.info("============= 创建账户并且抵押 ===============");

        try {
            Transaction t2 = rpc.createAccount(eosConstant.eosio_private_key, eosConstant.eosio, account, owner_public_key, active_public_key, 8192L, "100.00 MPC", "100.00 MPC", 0l);
            LOG.info("创建成功 = " + t2.getTransactionId() + " \n ");
        } catch (ApiException ex) {
            LOG.error("=====code:" + ex.getError().getError().getCode() + "=====Name: " + ex.getError().getError().getName() + "=====What: " + ex.getError().getError().getWhat());
            ex.printStackTrace();
            return new EosResponse(501, ex.getError().getError().getName(), ex.getError().getError().getWhat());
        }

      /*  try {
            Transaction t1 = rpc.transfer(EOSConstant.maiplatform_private_key, EOSConstant.maitoken, EOSConstant.maiplatform, account, "1.0000 MAI", "");
            LOG.info("转账成功 = " + t1.getTransactionId() + " \n ");
        } catch (ApiException ex) {
            LOG.error("=====code:" + ex.getError().getError().getCode() + "=====Name: " + ex.getError().getError().getName() + "=====What: " + ex.getError().getError().getWhat());
            ex.printStackTrace();
            return new EosResponse(501, ex.getError().getError().getName(), ex.getError().getError().getWhat());
        }
        try {
            Transaction t1 = rpc.transfer(active_private_key, EOSConstant.maitoken, account, EOSConstant.maiplatform, "1.0000 MAI", "");
            System.out.println("转账成功 = " + t1.getTransactionId() + " \n ");
        } catch (ApiException ex) {
            LOG.error("=====code:" + ex.getError().getError().getCode() + "=====Name: " + ex.getError().getError().getName() + "=====What: " + ex.getError().getError().getWhat());
            ex.printStackTrace();
            return new EosResponse(501, ex.getError().getError().getName(), ex.getError().getError().getWhat());
        }*/

        KeyDTO keyDTO = new KeyDTO();
        keyDTO.setActivePrivateKey(active_private_key);
        keyDTO.setActivePublicKey(active_public_key);
        keyDTO.setOwnerPrivateKey(owner_private_key);
        keyDTO.setOwnerPublicKey(owner_public_key);

        return new EosResponse(1, keyDTO);
    }

    /**
     * p2p 转账
     *
     * @param userId             用户id
     * @param fromAccount        转出账户
     * @param toAccount          转入账户
     * @param num                数量
     * @param desc               备注信息
     * @param initaPrivateActive 私钥
     * @return
     */
    public EosResponse p2pTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, String initaPrivateActive) throws Exception {
        LOG.info("》》》p2p发起交易");
        return transaction(userId, toAccount, num, desc, fromAccount, initaPrivateActive);
    }

    /**
     * 迁移数据
     *
     * @param userId             用户id
     * @param fromAccount        转出账户
     * @param toAccount          转入账户
     * @param num                数量
     * @param desc               备注信息
     * @param initaPrivateActive 私钥
     * @return
     */
    public EosResponse moveTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, String initaPrivateActive) throws Exception {
        LOG.info("》》》迁移数据 》》发起交易 ：：");
        //保存交易记录
        num = roundByScale(Double.valueOf(num));
        try {
            Transaction t1 = rpc.transfer(initaPrivateActive, eosConstant.maitoken, fromAccount, toAccount, num + " MAI", desc);
            LOG.info("转账成功 = " + t1.getTransactionId() + " \n ");
            //保存交易记录
            saveSuccessTransaction((long) 0, fromAccount, toAccount, num, desc, t1.getTransactionId(), 6);
        } catch (ApiException ex) {
            LOG.error("=====code:" + ex.getError().getError().getCode() + "=====Name: " + ex.getError().getError().getName() + "=====What: " + ex.getError().getError().getWhat());
            return new EosResponse(505, ex.getError().getError().getName(), ex.getError().getError().getWhat());
        }
        //保存交易记录
        return new EosResponse(1);

    }

    /**
     * 系统转账
     *
     * @param toAccount 转入账户
     * @param num       数量
     * @param desc      备注信息
     * @return
     */
    public EosResponse sysTransaction(String toAccount, String num, String desc) throws Exception {
        String fromAccount = eosConstant.maiplatform;
        String rootKey = eosConstant.maiplatform_private_key;
        return transaction((long) 0, toAccount, num, desc, fromAccount, rootKey);
    }


    /**
     * 运营出账（赞赏奖励）
     *
     * @param toAccount 转入账户
     * @param num       数量
     * @param desc      备注信息
     * @return
     */
    public EosResponse fromOperateTransaction(String toAccount, String num, String desc) throws Exception {
        String fromAccount = eosConstant.maioperate;
        String rootKey = eosConstant.maioperate_private_key;
        return transaction((long) 0, toAccount, num, desc, fromAccount, rootKey);
    }


    /**
     * 运营入账
     *
     * @param userId      用户id
     * @param num         数量
     * @param desc        备注信息
     * @param fromAccount 转出账户
     * @param rootKey     私钥
     * @return
     */
    public EosResponse toOperateTransaction(Long userId, String fromAccount, String num, String desc, String rootKey) throws Exception {
        String toAccount = eosConstant.maioperate;
        return transaction(userId, toAccount, num, desc, fromAccount, rootKey);
    }


    /**
     * 临时账户出账
     *
     * @param toAccount 转入账户
     * @param num       数量
     * @param desc      备注信息
     * @return
     */
    public EosResponse fromTempTransaction(String toAccount, String num, String desc) throws Exception {
        String fromAccount = eosConstant.maitemp;
        String rootKey = eosConstant.maitemp_private_key;
        return transaction((long) 0, toAccount, num, desc, fromAccount, rootKey);
    }

    /**
     * 临时账户入账
     *
     * @param userId      用户id
     * @param num         数量
     * @param desc        备注信息
     * @param fromAccount 转出账户
     * @param rootKey     私钥
     * @return
     */
    public EosResponse toTempTransaction(Long userId, String fromAccount, String num, String desc, String rootKey) throws Exception {
        String toAccount = eosConstant.maitemp;
        return transaction(userId, toAccount, num, desc, fromAccount, rootKey);
    }

    /**
     * 转账交易
     *
     * @param userId      用户id
     * @param toAccount   转入账户
     * @param num         数量
     * @param desc        备注信息
     * @param fromAccount 转出账户
     * @param rootKey     私钥
     * @return
     */
    private EosResponse transaction(Long userId, String toAccount, String num, String desc, String fromAccount, String rootKey) throws Exception {
        LOG.info("》》》发起交易请求 ==userId==" + userId + "== fromAccount==" + fromAccount + "==toAccount==" + toAccount + "==num==" + num + "==rootKey==" + rootKey + "==desc==" + desc);
        num = roundByScale(Double.valueOf(num));
        Transaction transaction = null;
        try {
            transaction = rpc.transfer(rootKey, eosConstant.maitoken, fromAccount, toAccount, num + " MAI", desc);
            LOG.info("转账成功 = " + transaction.getTransactionId() + " \n ");
            //保存交易记录
            saveSuccessTransaction((long) userId, fromAccount, toAccount, num, desc, transaction.getTransactionId(), 1);
        } catch (ApiException ex) {
            LOG.error("=====code:" + ex.getError().getError().getCode() + "=====Name: " + ex.getError().getError().getName() + "=====What: " + ex.getError().getError().getWhat());
            saveErrorTransaction((long) userId, fromAccount, toAccount, num, desc, ex.getError().getError().getWhat(), 0);
            return new EosResponse(505, ex.getError().getError().getName(), ex.getError().getError().getWhat());
        }
        //保存交易记录
        return new EosResponse(1);
    }


    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = "-" + String.valueOf(time);
        return t;
    }


    /**
     * 保存交易记录
     *
     * @param userId         用户id
     * @param fromAccount    转出账户
     * @param toAccount      转入账户
     * @param num            数量
     * @param desc           备注
     * @param transaction_id 交易id
     * @param tran_state     交易状态
     */
    private void saveSuccessTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, String transaction_id, int tran_state) {
        saveTransaction(userId, fromAccount, toAccount, num, desc, transaction_id, (byte) tran_state, TRANSACTION_SUCCESS);
    }

    private void saveTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, String transaction_id, byte tran_state, String transactionSuccess) {
        EosTransaction eosTransaction = new EosTransaction();
        eosTransaction.setUserId(userId);
        eosTransaction.setAmount(new BigDecimal(num));
        eosTransaction.setFromWallet(fromAccount);
        eosTransaction.setToWallet(toAccount);
        if (StringUtils.isNoneEmpty(desc)) {
            eosTransaction.setRemarks(desc);
        }
        eosTransaction.setTranState(tran_state);
        eosTransaction.setTranHash(transaction_id);
        eosTransaction.setTranMsg(transactionSuccess);
        eosTransactionMapper.insertSelective(eosTransaction);
    }

    /**
     * 保存交易记录
     *
     * @param userId         用户id
     * @param fromAccount    转出账户
     * @param toAccount      转入账户
     * @param num            数量
     * @param desc           备注
     * @param transaction_id 交易id
     * @param tran_state     交易状态
     */
    private void saveErrorTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, String transaction_id, int tran_state) {

        saveTransaction(userId, fromAccount, toAccount, num, desc, transaction_id, (byte) tran_state, TRANSACTION_FAILURE);
    }


    /**
     * 查询余额
     *
     * @param walletName 钱包名
     * @return
     */
    public String getBalance(String walletName) {
        LOG.info("》》》查询余额请求");
        String balance = "";
        try {
            List<Balance> balanceList = rpc.getCurrencyBalance(eosConstant.maitoken, walletName, "MAI");
            balance = balanceList.get(0).getAmount().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return balance;
    }

}