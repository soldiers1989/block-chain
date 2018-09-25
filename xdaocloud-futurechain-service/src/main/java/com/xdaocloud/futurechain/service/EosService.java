package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.PageResponse;
import com.xdaocloud.futurechain.dto.req.demo.PageBase_Request;
import com.xdaocloud.futurechain.dto.req.demo.Page_Request;
import com.xdaocloud.futurechain.dto.req.eos.*;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;
import com.xdaocloud.futurechain.model.EosWallet;
import com.xdaocloud.futurechain.model.User;

import java.math.BigDecimal;

public interface EosService {

    /**
     * 创建钱包
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> create(EosCreateWalletRequest request) throws Exception;

    /**
     * 查询余额
     *
     * @param userId
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> getBalance(Long userId) throws Exception;


    /**
     * 查询锁仓余额
     *
     * @param userId
     * @return
     * @date 2018年6月23日
     * @author LuoFuMin
     */
    BigDecimal findlockEosBalance(Long userId);

    /**
     * 交易
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> transaction(EosTransactionRequest request) throws Exception;

    /**
     * 查询钱包
     *
     * @param userId
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> findWallet(Long userId) throws Exception;


    /**
     * 麦钻收支明细
     *
     * @param page_request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> findMAI_Transaction(Page_Request page_request) throws Exception;

    /**
     * 查询eos账户
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> findEOS_Account(EOS_Account request) throws Exception;


    /**
     * 设置免密金额
     *
     * @param setQuotaRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> setQuota(SetQuotaRequest setQuotaRequest);

    /**
     * 查询免密金额
     *
     * @param userIdRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> findQuota(UserIdRequest userIdRequest);

    /**
     * 激活免密金额
     *
     * @param userIdRequest
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> activateQuota(UserIdRequest userIdRequest);

    /**
     * 迁移钱包
     *
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> moveWallet() throws Exception;


    /**
     * 迁移交易
     *
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> moveTransaction() throws Exception;

    /**
     * 解密私钥
     *
     * @param eosWallet eos钱包
     * @return activePrivateKey eos账户私钥
     * @throws Exception
     */
    String decryptBActivePrivateKey(EosWallet eosWallet) throws Exception;

    /**
     * 检查交易密码
     *
     * @param sum                 数量
     * @param transactionPassword 交易密码
     * @param user                用户信息
     * @return
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    ResultInfo<?> checkTransactionPassword(BigDecimal sum, String transactionPassword, User user);


    /**
     * 麦圈详情扣费-----判断是否需要交易密码，判断交易密码是否正确
     *
     * @param sum                 交易数量
     * @param transactionPassword 用户输入交易密码
     * @param user                用户类
     * @return
     * @date 2018年6月7日
     * @author lmd
     */
    int maiQuanCheckTransactionPassword(BigDecimal sum, String transactionPassword, User user);

    /**
     * 补贴差价
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    ResultInfo<?> compensate() throws Exception;


    /**
     * 保存交易
     *
     * @param userId      用户id
     * @param fromAccount 转出账户
     * @param toAccount   转入账户
     * @param num         数量
     * @param desc        备注
     * @param tran_state  状态
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    void saveTransaction(Long userId, String fromAccount, String toAccount, String num, String desc, int tran_state);

    /**
     * 迁移错误的钱包账户
     *
     * @return
     * @date 2018年6月22日
     * @author LuoFuMin
     */
    ResultInfo<?> moveErrorWallet() throws Exception;

    /**
     * 锁仓转账交易
     *
     * @param request
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    ResultInfo<?> lockTransaction(EosTransactionRequest request) throws Exception;


    /**
     * 系统转账
     *
     * @return Boolean 返回是否成功
     * @throws Exception 异常
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    Boolean sysToUserTransaction(SysToUserTransactionRequest request, User user);

    /**
     * 解锁
     *
     * @return Boolean 返回是否成功
     * @throws Exception 异常
     * @date 2018年6月29日
     * @author LuoFuMin
     */
    Boolean unlockEos(UnlockEosRequest request, User user);


    /**
     * 解锁记录
     *
     * @param pageBaseRequest
     * @return
     * @throws Exception
     * @date 2018年6月28日
     * @author LuoFuMin
     */
    PageResponse getUnlockEos(PageBase_Request pageBaseRequest) throws Exception;

    /**
     * 导入eos钱包
     *
     * @描述
     * @参数
     * @返回值 Boolean
     * @创建人 LuoFuMIn
     * @创建时间 2018/7/18
     */
    Boolean importAccount(ImportAccountRequest request) throws Exception;

    /**
     * 获取密匙
     *
     * @param request
     * @return String
     * @author LuoFuMIn
     * @date 2018/7/18
     */

    String getPrivateKey(GetPrivateKeyRequest request) throws Exception;
}
