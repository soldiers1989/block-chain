package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.contract.*;
import com.xdaocloud.futurechain.dto.resp.contract.ContractDetailsResponse;
import com.xdaocloud.futurechain.dto.resp.contract.ContractTypeResponse;
import com.xdaocloud.futurechain.dto.resp.contract.HotTypeResponse;
import com.xdaocloud.futurechain.model.Contract;
import com.xdaocloud.futurechain.service.ContractService;
import com.xdaocloud.futurechain.util.ImageUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 合约
 *
 * @author LuoFuMin
 */
@RestController
@Api(value = "ContractController", description = "合约")
@RequestMapping("/api/app/")
public class ContractController {

    @Autowired
    private ContractService contractService;

    /**
     * 创建合约
     *
     * @param multipartFile  图片文件
     * @param userId         用户id
     * @param signId         签约用户id
     * @param conName        合约名称
     * @param contractTypeId 合约类型id
     * @param conAddress     合约场地
     * @param longitude      经度
     * @param latitude       纬度
     * @param money          借款金额
     * @param borrowWay      借款方式
     * @param backTime       还款时间
     * @param accrual        利息
     * @param penalty        违约金
     * @param role           合约类型身份：1-放贷，2-借贷
     * @param remarks        备注
     * @param contractId     发布 defaultValue = 0 表示第一次创建
     * @return
     * @throws Exception
     * @date 2018年6月2日
     * @author LuoFuMin
     */
    @ApiOperation(value = "创建合约", notes = "创建合约")
    @PostMapping("v2/contract/create")
    public ResultInfo<?> createContract(@RequestParam(name = "portrait", required = false) MultipartFile multipartFile,
                                        @RequestParam(name = "userId") Long userId, @RequestParam(name = "signId") Long signId, @RequestParam(name = "role") Integer role,
                                        @RequestParam(name = "conName") String conName, @RequestParam(name = "contractTypeId") Long contractTypeId,
                                        @RequestParam(name = "conAddress") String conAddress, @RequestParam(name = "longitude") String longitude,
                                        @RequestParam(name = "latitude") String latitude, @RequestParam(name = "money") BigDecimal money,
                                        @RequestParam(name = "penalty") BigDecimal penalty, @RequestParam(name = "borrowWay") Integer borrowWay,
                                        @RequestParam(name = "backTime") String backTime, @RequestParam(name = "accrual") String accrual,
                                        @RequestParam(name = "remarks", required = false) String remarks, @RequestParam(name = "contractId", required = false, defaultValue = "0") Long contractId) throws Exception {
        if (multipartFile != null) {
            ResultInfo<?> x = ImageUtils.checkImage(multipartFile);
            if (x != null) return x;
        }
        Contract contract = contractService.createContract(multipartFile, userId, signId, conName, role, contractTypeId, conAddress, longitude, latitude, money, penalty, borrowWay, backTime, accrual, remarks, contractId);
        if (null == contract) return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);

        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, contract);
    }


    /**
     * 添加富文本
     *
     * @param addRichTextRequest richText: 富文本, contractId;合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @ApiOperation(value = "添加富文本", notes = "添加富文本")
    @PostMapping("v2/contract/addRichText")
    public ResultInfo<?> addRichText(@Valid @RequestBody AddRichTextRequest addRichTextRequest) throws Exception {

        Boolean aBoolean = contractService.addRichText(addRichTextRequest.getRichText(), addRichTextRequest.getContractId());
        if (!aBoolean) return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 删除富文本
     *
     * @param contractIdRequest contractId;合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @ApiOperation(value = "删除富文本", notes = "删除富文本")
    @PostMapping("v2/contract/delRichText")
    public ResultInfo<?> delRichText(@Valid @RequestBody ContractIdRequest contractIdRequest) throws Exception {
        Boolean aBoolean = contractService.delRichText(contractIdRequest.getContractId());
        if (!aBoolean) return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 确认发布
     *
     * @param
     * @return
     * @author LuoFuMin
     * @date 2018/7/28
     */

    @ApiOperation(value = "确认发布", notes = "确认发布")
    @PostMapping("v2/contract/confirm")
    public ResultInfo<?> confirm(@Valid @RequestBody ContractIdRequest contractIdRequest) throws Exception {
        Boolean aBoolean = contractService.confirm(contractIdRequest.getContractId());
        if (!aBoolean) return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }


    /**
     * 合约预览
     *
     * @param contractIdRequest 合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @ApiOperation(value = "合约预览", notes = "合约预览")
    @PostMapping("v2/contract/preview")
    public ResultInfo<?> preview(@Valid @RequestBody ContractIdRequest contractIdRequest) throws Exception {
        ContractDetailsResponse contract = contractService.preview(contractIdRequest.getContractId());
        if (contract == null) return new ResultInfo<>(ResultInfo.DATA_NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, contract);
    }


    /**
     * 获取合约详情
     *
     * @param contractIdRequest 合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @ApiOperation(value = "获取合约详情", notes = "获取合约详情")
    @PostMapping("v2/contract/getDetails")
    public ResultInfo<?> getDetails(@Valid @RequestBody ContractIdRequest contractIdRequest) throws Exception {

        ContractDetailsResponse contract = contractService.getDetails(contractIdRequest.getContractId());
        if (contract == null) return new ResultInfo<>(ResultInfo.DATA_NOT_FOUND, ResultInfo.MSG_DATA_NOT_FOUND);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, contract);
    }


    /**
     * 获取合约类型
     *
     * @param
     * @return
     * @author LuoFuMIn
     * @date 2018/7/18
     */

    @ApiOperation(value = "获取合约类型", notes = "获取合约类型")
    @GetMapping("v2/contract/get/typeList")
    public ResultInfo<?> getTypeList() throws Exception {
        List<ContractTypeResponse> list = contractService.getTypeList();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }


    /**
     * 获取热门合约类型
     *
     * @return ResultInfo
     * @author LuoFuMin
     * @date 2018/7/23
     */
    @ApiOperation(value = "获取热门合约类型", notes = "获取热门合约类型")
    @GetMapping("v2/contract/get/hotTypeList")
    public ResultInfo<?> getHotTypeList() throws Exception {
        List<HotTypeResponse> list = contractService.getHotTypeList();
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }


    /**
     * DAPP首页合约列表
     *
     * @param dappContractRequest
     * @return
     */
    @ApiOperation(value = "DAPP首页合约列表", notes = "userId:用户id searchContract:搜索内容  page 页数(默认是1) size 每页显示数量")
    @PostMapping("v2/contract/get/list")
    public ResultInfo<?> findContract(@Valid @RequestBody DappContractRequest dappContractRequest) throws Exception {
        List<Map<String, Object>> list = contractService.findContractByUserIdOrContractName(dappContractRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * DAPP首页我的发布列表
     *
     * @param dappContractRequest
     * @return
     */
    @ApiOperation(value = "DAPP首页我的发布列表", notes = "")
    @PostMapping("v2/contract/get/releaseList")
    public ResultInfo<?> findMyReleaseList(@Valid @RequestBody DappContractRequest dappContractRequest) throws Exception {
        List<Map<String, Object>> list = contractService.findMyReleaseListByUserIdOrContractName(dappContractRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * DAPP首页我的签约列表
     *
     * @param dappContractRequest
     * @return
     */
    @ApiOperation(value = "DAPP首页我的签约列表", notes = "")
    @PostMapping("v2/contract/get/contractList")
    public ResultInfo<?> findMyContractListList(@Valid @RequestBody DappContractRequest dappContractRequest) throws Exception {
        List<Map<String, Object>> list = contractService.findMyContractListListByUserIdOrContractName(dappContractRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);

    }

    /**
     * 合约管理 -- 合约信息
     *
     * @param contractManageRequest
     * @return
     */
    @ApiOperation(value = "合约管理 -- 合约信息", notes = "")
    @PostMapping("v2/contract/get/contractManagement")
    public ResultInfo<?> findContractManagement(@Valid @RequestBody ContractManageRequest contractManageRequest) throws Exception {
        Map<String, Object> map = contractService.findContractManagement(contractManageRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, map);
    }

    /**
     * 合约管理 -- 消息记录
     *
     * @param contractManageNewsRequest
     * @return
     */
    @ApiOperation(value = "合约管理 -- 消息记录", notes = "")
    @PostMapping("v2/contract/get/contractNews")
    public ResultInfo<?> findContractNews(@Valid @RequestBody ContractManageNewsRequest contractManageNewsRequest) throws Exception {
        List<Map<String, Object>> list = contractService.findContractNews(contractManageNewsRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }

    /**
     * 合约管理 --- 发起延期还款
     *
     * @param contractPostponedRequest
     * @return
     */
    @ApiOperation(value = "合约管理→发起延期还款", notes = "")
    @PostMapping("v2/contract/get/contractPostponed")
    public ResultInfo<?> contractPostponed(@Valid @RequestBody ContractPostponedRequest contractPostponedRequest) throws Exception {
        int numb = contractService.contractPostponed(contractPostponedRequest);
        if (numb == 1) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else if (numb == -1) {
            return new ResultInfo<>(ResultInfo.ERROR, "合约不存在");
        } else if (numb == -2) {
            return new ResultInfo<>(ResultInfo.ERROR, "类型不对");
        } else if (numb == -3) {
            return new ResultInfo<>(ResultInfo.ERROR, "延期还款时间或原因不能为空");
        }
        return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_ERROR);
    }

    /**
     * 合约管理→ 回应发起延期还款→ 同意或者不同意
     *
     * @param contractResRequest
     * @return
     */
    @ApiOperation(value = "合约管理→ 回应发起延期还款→ 同意或者不同意", notes = "")
    @PostMapping("v2/contract/get/setContractPostponed")
    public ResultInfo<?> setContractPostponed(@Valid @RequestBody ContractResRequest contractResRequest) throws Exception {
        int numb = contractService.setContractPostponed(contractResRequest);
        if (numb == 1) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else if (numb == -1) {
            return new ResultInfo<>(ResultInfo.ERROR, "合约不存在");
        } else if (numb == -2) {
            return new ResultInfo<>(ResultInfo.ERROR, "类型不对");
        } else if (numb == -3) {
            return new ResultInfo<>(ResultInfo.ERROR, "该消息你已处理过");
        } else if (numb == -4) {
            return new ResultInfo<>(ResultInfo.ERROR, "暂时无法处理");
        }
        return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_ERROR);
    }

    /**
     * 签约合约
     *
     * @param participateContractRequest
     * @return
     */
    @ApiOperation(value = "签约合约", notes = "")
    @PostMapping("v2/contract/get/participateContract")
    public ResultInfo<?> participateContract(@Valid @RequestBody participateContractRequest participateContractRequest) throws Exception {
        int numb = contractService.participateContract(participateContractRequest);
        if (numb == 1) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        }
        return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_ERROR);
    }

    /**
     * 还款/收款
     *
     * @param contractRepaymentRequest
     * @return
     */
    @ApiOperation(value = "还款/收款", notes = "")
    @PostMapping("v2/contract/get/contractRepayment")
    public ResultInfo<?> contractRepayment(@Valid @RequestBody contractRepaymentRequest contractRepaymentRequest) throws Exception {
        int numb = contractService.contractRepayment(contractRepaymentRequest);
        if (numb == 1) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else if (numb == -1) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_INVALID_PARAM);
        }
        return new ResultInfo<>(ResultInfo.ERROR, ResultInfo.MSG_ERROR);
    }

}
