package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.contract.*;
import com.xdaocloud.futurechain.dto.resp.contract.ContractDetailsResponse;
import com.xdaocloud.futurechain.dto.resp.contract.ContractTypeResponse;
import com.xdaocloud.futurechain.dto.resp.contract.HotTypeResponse;
import com.xdaocloud.futurechain.model.Contract;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 合约
 */
public interface ContractService {


    /**
     * 修改合约
     *
     * @param contractId 合约id
     * @param portrait   图片文件
     * @param userid     用户id
     * @param conName    合约名称
     * @param cruxWord   关键词
     * @param classify   分类
     * @param pushTime   推送时间
     * @param validTime  有效时间(1：一天，2：一周，3：一个月，4：一年)
     * @return
     * @throws Exception
     */
    ResultInfo<?> updateContract(Long contractId, MultipartFile portrait, Long userid, String conName, String cruxWord, String classify, String pushTime, String validTime, Boolean conOpen) throws Exception;


    /**
     * 删除合约
     *
     * @param contractId 合约id
     * @return
     */
    ResultInfo<?> deleteContract(Long contractId) throws Exception;


    /**
     * 查询合约
     *
     * @param userid 用户id
     * @return
     * @throws Exception
     */
    ResultInfo<?> findContract(Long userid) throws Exception;

    /**
     * DAPP首页根据合约名称及用户ID查询合约列表
     *
     * @param dappContractRequest
     * @return
     */
    List<Map<String, Object>> findContractByUserIdOrContractName(DappContractRequest dappContractRequest);

    /**
     * 获取合约类型
     *
     * @param
     * @return
     * @author LuoFuMIn
     * @date 2018/7/18
     */
    List<ContractTypeResponse> getTypeList();

    /**
     * 获取热门合约类型
     *
     * @return ResultInfo
     * @author LuoFuMin
     * @date 2018/7/23
     */
    List<HotTypeResponse> getHotTypeList();

    /**
     * DAPP首页我的发布列表根据合约名称及用户ID查询合约列表
     *
     * @param dappContractRequest
     * @return
     */
    List<Map<String, Object>> findMyReleaseListByUserIdOrContractName(DappContractRequest dappContractRequest);

    /**
     * DAPP首页我的签约列表根据合约名称及用户ID查询合约列表
     *
     * @param dappContractRequest
     * @return
     */
    List<Map<String, Object>> findMyContractListListByUserIdOrContractName(DappContractRequest dappContractRequest);


    /**
     * 创建合约
     *
     * @param multipartFile  图片文件
     * @param userId         用户id
     * @param signId         签约用户id
     * @param conName        合约名称
     * @param contractTypeId 合约类型
     * @param conAddress     合约场地
     * @param longitude      经度
     * @param latitude       纬度
     * @param money          接口金额
     * @param borrowWay      借款方式
     * @param backTime       还款时间
     * @param accrual        利息
     * @param penalty        违约金
     * @param role           合约类型身份：1-放贷，2-借贷
     * @param remarks        备注
     * @param contractId     发布 defaultValue = 0 表示第一次创建
     * @return
     * @throws Exception
     * @date 2018年7月20日
     * @author LuoFuMin
     */

    Contract createContract(MultipartFile multipartFile, Long userId, Long signId, String conName, Integer role, Long contractTypeId, String conAddress, String longitude, String latitude, BigDecimal money, BigDecimal penalty, Integer borrowWay, String backTime, String accrual, String remarks, Long contractId) throws IOException;


    /**
     * 添加富文本
     *
     * @param richText: 富文本, contractId;合约id
     * @return Boolean
     * @author LuoFuMin
     * @date 2018/7/24
     */
    Boolean addRichText(String richText, Long contractId);


    /**
     * 合约管理 -- 合约信息
     *
     * @param contractManageRequest
     * @return
     */
    Map<String, Object> findContractManagement(ContractManageRequest contractManageRequest);


    /**
     * 获取合约详情
     *
     * @param contractId 合约id
     * @return Contract
     * @author LuoFuMin
     * @date 2018/7/24
     */
    ContractDetailsResponse getDetails(Long contractId);


    /**
     * 合约管理 --- 发起延期还款
     *
     * @param contractPostponedRequest
     * @return
     */
    int contractPostponed(ContractPostponedRequest contractPostponedRequest);

    /**
     * 合约管理 --- 回应发起延期还款---同意或者不同意
     *
     * @param contractResRequest
     * @return
     */
    int setContractPostponed(ContractResRequest contractResRequest);

    /**
     * 签约合约
     *
     * @param participateContractRequest
     * @return
     */
    int participateContract(participateContractRequest participateContractRequest);

    /**
     * 还款/收款
     *
     * @param contractRepaymentRequest
     * @return
     */
    int contractRepayment(contractRepaymentRequest contractRepaymentRequest);

    /**
     * 确认发布
     *
     * @param contractId 合约id
     * @return Boolean
     * @author LuoFuMin
     * @date 2018/7/26
     */
    Boolean confirm(Long contractId) throws Exception;

    /**
     * 合约管理 -- 消息记录
     *
     * @param contractManageNewsRequest
     * @return
     */
    List<Map<String, Object>> findContractNews(ContractManageNewsRequest contractManageNewsRequest);

    /**
     * 合约预览
     *
     * @param contractId 合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    ContractDetailsResponse preview(Long contractId);

    /**
     * 删除富文本
     *
     * @param contractId richText: 富文本, contractId;合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    Boolean delRichText(Long contractId);
}
