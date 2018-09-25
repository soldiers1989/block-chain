package com.xdaocloud.futurechain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.util.StringUtil;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.QiniuConfig;
import com.xdaocloud.futurechain.common.XdaoException;
import com.xdaocloud.futurechain.dto.req.contract.*;
import com.xdaocloud.futurechain.dto.req.eos.ContractRequest;
import com.xdaocloud.futurechain.dto.req.huanxin.Message;
import com.xdaocloud.futurechain.dto.req.huanxin.SendMessageRequest;
import com.xdaocloud.futurechain.dto.resp.contract.ContractDetailsResponse;
import com.xdaocloud.futurechain.dto.resp.contract.ContractTypeResponse;
import com.xdaocloud.futurechain.dto.resp.contract.HotTypeResponse;
import com.xdaocloud.futurechain.huanxin.HuanxinMessage;
import com.xdaocloud.futurechain.mapper.*;
import com.xdaocloud.futurechain.model.*;
import com.xdaocloud.futurechain.service.ContractService;
import com.xdaocloud.futurechain.service.EosService;
import com.xdaocloud.futurechain.service.RestTemplateService;
import com.xdaocloud.futurechain.util.DateUtils;
import com.xdaocloud.futurechain.util.QiNiuUtils;
import com.xdaocloud.futurechain.util.tree.TreeDataBuilder;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 合约
 */
@Service
public class ContractServiceImpl implements ContractService {

    private static Logger LOG = LoggerFactory.getLogger(ContractServiceImpl.class);

    @Autowired
    private ContractMapper contractMapper;

    /**
     * 七牛云储存配置
     */
    @Autowired
    private QiniuConfig qiniuConfig;

    @Autowired
    private OreTransactionMapper oreTransactionMapper;

    @Autowired
    private ContractTypeMapper contractTypeMapper;

    @Autowired
    private ContractMessageMapper contractMessageMapper;

    @Autowired
    private UserContractMapper userContractMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HuanxinMessage huanxinMessage;

    @Autowired
    private RestTemplateService restTemplateService;

    @Autowired
    private EosWalletMapper eosWalletMapper;

    @Autowired
    private EosService eosService;


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
    @Override
    @Transactional
    public Contract createContract(MultipartFile multipartFile, Long userId, Long signId, String conName, Integer role,
                                   Long contractTypeId, String conAddress, String longitude, String latitude,
                                   BigDecimal money, BigDecimal penalty, Integer borrowWay, String backTime, String accrual, String remarks, Long contractId) throws IOException {
        String path = "";
        if (multipartFile != null) {
            path = QiNiuUtils.pushFile(multipartFile, qiniuConfig);
            if (path == null) {
                return null;
            }
        }
        Contract contract = new Contract();
        contract.setUserId(userId);
        contract.setSignId(signId);
        contract.setConName(conName);
        contract.setRole(role);
        contract.setContractTypeId(contractTypeId);
        contract.setConAddress(conAddress);
        contract.setLongitude(longitude);
        contract.setLatitude(latitude);
        contract.setMoney(money);
        contract.setPenalty(penalty);
        contract.setBorrowWay(borrowWay);
        contract.setBackTime(backTime);
        contract.setAccrual(accrual);
        contract.setRemark(remarks);
        if (StringUtils.isNotBlank(path)) {
            contract.setPortrait(path);
        }
        contract.setIsDeleted(true);
        //第一次创建，保存
        if (contractId == 0) {
            contractMapper.insertSelective(contract);
        } else {//不是第一次创建，更新
            contract.setId(contractId);
            contractMapper.updateByPrimaryKeySelective(contract);
        }
        return contract;
    }

    @Override
    public Boolean addRichText(String richText, Long contractId) {
        Contract contract = new Contract();
        contract.setId(contractId);
        contract.setRichText(richText);
        contractMapper.updateByPrimaryKeySelective(contract);
        return true;
    }

    @Override
    public List<HotTypeResponse> getHotTypeList() {

        List<HotTypeResponse> list = contractTypeMapper.getHotTypeList();
        return list;
    }

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
    @Transactional
    @Override
    public ResultInfo<?> updateContract(Long contractId, MultipartFile portrait, Long userid, String conName, String cruxWord, String classify, String pushTime, String validTime, Boolean conOpen) throws Exception {

        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        if (portrait != null) {
            String path = QiNiuUtils.pushFile(portrait, qiniuConfig);
            if (path == null) return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE, "图片上传失败");
            contract.setPortrait(path);
        }
        if (StringUtils.isNotBlank(conName)) {
            contract.setConName(conName);
        }

        contractMapper.updateByPrimaryKeySelective(contract);

        addOreTransactionRecord(userid, (long) -5, "修改合约");
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
    }

    /**
     * 删除合约
     *
     * @param contractId 合约id
     * @return
     */
    @Transactional
    @Override
    public ResultInfo<?> deleteContract(Long contractId) {
        int i = contractMapper.deleteByPrimaryKey(contractId);
        if (i > 0) {
            return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
        } else {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
    }

    /**
     * 查询合约
     *
     * @param userid 用户id
     * @return
     * @throws Exception
     */
    @Override
    public ResultInfo<?> findContract(Long userid) throws Exception {
        List<Contract> contractList = contractMapper.findContract(userid);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, contractList);
    }

    /**
     * 获取合约类型
     *
     * @param
     * @return
     * @author LuoFuMIn
     * @date 2018/7/18
     */
    @Override
    public List<ContractTypeResponse> getTypeList() {

        List<ContractType> list = contractTypeMapper.getAll();

        // 转成树形结构
        TreeDataBuilder<ContractTypeResponse> treeDataBuilder = new TreeDataBuilder<>();
        List<ContractTypeResponse> trees = new ArrayList<>();
        for (ContractType data : list) {
            trees.add(new ContractTypeResponse(data.getId(), data.getName(), data.getParentId(), new ArrayList<>()));
        }
        List<ContractTypeResponse> build = treeDataBuilder.build(trees);

        return build;
    }


    /**
     * DAPP首页根据合约名称及用户ID查询合约列表
     *
     * @param dappContractRequest
     * @return
     */
    @Override
    public List<Map<String, Object>> findContractByUserIdOrContractName(DappContractRequest dappContractRequest) {
        List<Map<String, Object>> list = new ArrayList<>();
        int pageNum = 1;
        pageNum = dappContractRequest.getPage();
        int pageSize = 0;
        pageSize = dappContractRequest.getSize();
        int count = userContractMapper.findContractByUserIdOrContractNameCount(dappContractRequest); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }
            dappContractRequest.setPage(pageNums);
            //查询用户
            list = userContractMapper.findContractByUserIdOrContractName(dappContractRequest);
        }

        return list;

    }


    /**
     * DAPP首页我的发布列表根据合约名称及用户ID查询合约列表
     *
     * @param dappContractRequest
     * @return
     */
    @Override
    public List<Map<String, Object>> findMyReleaseListByUserIdOrContractName(DappContractRequest dappContractRequest) {
        List<Map<String, Object>> listData = new ArrayList<>();
        int pageNum = 1;
        pageNum = dappContractRequest.getPage();
        int pageSize = 0;
        pageSize = dappContractRequest.getSize();
        int count = contractMapper.findMyReleaseListByUserIdOrContractNameCount(dappContractRequest); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }
            dappContractRequest.setPage(pageNums);
            //用户发布
            List<Contract> list = contractMapper.findMyReleaseListByUserIdOrContractName(dappContractRequest);
            if (list != null && list.size() > 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = new HashedMap();
                    map.put("contractId", list.get(i).getId());// id
                    map.put("contractName", list.get(i).getConName());//合约名称
                    map.put("money", list.get(i).getMoney());// 还款金额
                    map.put("backTime", list.get(i).getBackTime());//还款时间
                    //判断状态 未开始 进行中、已结束、已取消
                    if (list.get(i).getState() != null && list.get(i).getState() == 0) {
                        map.put("contractState", 0);//状态  0 已取消
                        map.put("state", 2);//  2 空
                    } else if (format.format(new Date()).compareTo(list.get(i).getBackTime()) >= 0 || list.get(i).getCollectState()) {
                        map.put("contractState", 2);//状态  2 已结束
                        if (list.get(i).getCollectState()) {
                            map.put("state", 1);//  1 已还
                        } else {
                            map.put("state", 0);//  0 未还
                        }
                    } else if (!list.get(i).getSignState()) {
                        map.put("contractState", 1);//状态  1 未开始
                        map.put("state", 2);//  2 空
                    } else {
                        map.put("contractState", 3);//状态  3 进行中
                        if (list.get(i).getCollectState()) {
                            map.put("state", 1);//  1 已还
                        } else {
                            map.put("state", 0);//  0 未还
                        }
                    }
                    map.put("portrait", list.get(i).getPortrait());//封面

                    //借入借出根据用户判断
                    //1、判断合约是放贷 还是借贷
                    //合约是放贷 再判断 访问用户是否是发布合约的作者 是则 借出  不是则  借入
                    if (list.get(i).getRole() == 1) { //Role:1-放贷，2-借贷
                        if (Long.parseLong(dappContractRequest.getUserId()) == list.get(i).getUserId()) {
                            map.put("role", 2);//2-借出
                        } else {
                            map.put("role", 1);//1-借入
                        }
                    } else {
                        if (Long.parseLong(dappContractRequest.getUserId()) == list.get(i).getUserId()) {
                            map.put("role", 1);//1-借入
                        } else {
                            map.put("role", 2);//2-借出
                        }
                    }


                    Map<String, Object> objectMap = contractTypeMapper.selectParentIdById(list.get(i).getContractTypeId());
                    if (objectMap != null && objectMap.size() > 0) {
                        map.put("industryParentName", objectMap.get("parentName"));//一级类型名称   parentName
                        map.put("industryName", objectMap.get("name"));//二级类型名称  name
                    } else {
                        map.put("industryParentName", "");//一级类型名称   parentName
                        map.put("industryName", "");//二级类型名称  name
                    }
                    map.put("type", 1);//借款类合约  后期再加？
                    listData.add(map);
                }
            }
        }
        return listData;
    }


    /**
     * DAPP首页我的签约列表根据合约名称及用户ID查询合约列表
     *
     * @param dappContractRequest
     * @return
     */
    @Override
    public List<Map<String, Object>> findMyContractListListByUserIdOrContractName(DappContractRequest dappContractRequest) {
        List<Map<String, Object>> listData = new ArrayList<>();
        int pageNum = 1;
        pageNum = dappContractRequest.getPage();
        int pageSize = 0;
        pageSize = dappContractRequest.getSize();
        int count = contractMapper.findMyContractListListByUserIdOrContractNameCount(dappContractRequest); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }
            dappContractRequest.setPage(pageNums);
            //用户发布
            List<Contract> list = contractMapper.findMyContractListListByUserIdOrContractName(dappContractRequest);
            if (list != null && list.size() > 0) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (int i = 0; i < list.size(); i++) {
                    Map<String, Object> map = new HashedMap();
                    map.put("contractId", list.get(i).getId());// id
                    map.put("portrait", list.get(i).getPortrait());//封面
                    map.put("contractName", list.get(i).getConName());//合约名称
                    map.put("money", list.get(i).getMoney());// 还款金额
                    map.put("backTime", list.get(i).getBackTime());//还款时间
                    //判断状态 未开始 进行中、已结束、已取消
                    if (list.get(i).getState() != null && list.get(i).getState() == 0) {
                        map.put("contractState", 0);//状态  0 已取消
                        map.put("state", 2);//  2 空
                    } else if (format.format(new Date()).compareTo(list.get(i).getBackTime()) >= 0 || list.get(i).getCollectState()) {
                        map.put("contractState", 2);//状态  2 已结束
                        if (list.get(i).getCollectState()) {
                            map.put("state", 1);//  1 已还
                        } else {
                            map.put("state", 0);//  0 未还
                        }
                    } else if (!list.get(i).getSignState()) {
                        map.put("contractState", 1);//状态  1 未开始
                        map.put("state", 2);//  2 空
                    } else {
                        map.put("contractState", 3);//状态  3 进行中
                        if (list.get(i).getCollectState()) {
                            map.put("state", 1);//  1 已还
                        } else {
                            map.put("state", 0);//  0 未还
                        }
                    }

                    //借入借出根据用户判断
                    //1、判断合约是放贷 还是借贷
                    //合约是放贷 再判断 访问用户是否是发布合约的作者 是则 借出  不是则  借入
                    if (list.get(i).getRole() == 1) { //Role:1-放贷，2-借贷
                        if (Long.parseLong(dappContractRequest.getUserId()) == list.get(i).getUserId()) {
                            map.put("role", 2);//2-借出
                        } else {
                            map.put("role", 1);//1-借入
                        }
                    } else {
                        if (Long.parseLong(dappContractRequest.getUserId()) == list.get(i).getUserId()) {
                            map.put("role", 1);//1-借入
                        } else {
                            map.put("role", 2);//2-借出
                        }
                    }


                    Map<String, Object> objectMap = contractTypeMapper.selectParentIdById(list.get(i).getContractTypeId());
                    if (objectMap != null && objectMap.size() > 0) {
                        map.put("industryParentName", objectMap.get("parentName"));//一级类型名称   parentName
                        map.put("industryName", objectMap.get("name"));//二级类型名称  name
                    } else {
                        map.put("industryParentName", "");//一级类型名称   parentName
                        map.put("industryName", "");//二级类型名称  name
                    }
                    map.put("type", 1);//借款类合约  后期再加？
                    listData.add(map);
                }
            }
        }
        return listData;
    }

    /**
     * 合约管理 -- 合约信息
     *
     * @param contractManageRequest
     * @return
     */
    @Override
    public Map<String, Object> findContractManagement(ContractManageRequest contractManageRequest) {
        Map<String, Object> contractMap = new HashedMap();
        int type = 0;
        if (contractManageRequest.getType() == 1) {
            type = 1;
        }
        Contract list = contractMapper.findMyReleaseByUserIdOrContractId(contractManageRequest.getContractId(), Long.parseLong(contractManageRequest.getUserId()), type);
        if (list != null) {
            contractMap = getContract(type, list);
            //聊天室 数据
            //用户ID、头像、环信ID、昵称
            long userId = 0;
            if (list.getUserId() == Long.parseLong(contractManageRequest.getUserId())) {
                userId = list.getSignId();
            } else {
                userId = list.getUserId();
            }

            User user = userMapper.selectByPrimaryKey(userId);
            if (user != null) {
                contractMap.put("userId", user.getId());
                contractMap.put("userNickname", user.getNickname());
                contractMap.put("userAvatar", user.getAvatar());
            } else {
                contractMap.put("userId", null);
                contractMap.put("userNickname", null);
                contractMap.put("userAvatar", null);
                contractMap.put("userEasemobAccount", null);
            }
            //更新首页合约排序
            updateUserContract(Long.parseLong(contractManageRequest.getUserId()), contractManageRequest.getContractId(), 2);
        }
        return contractMap;
    }

    /**
     * 合约管理 -- 消息记录
     *
     * @param contractManageNewsRequest
     * @return
     */
    @Override
    public List<Map<String, Object>> findContractNews(ContractManageNewsRequest contractManageNewsRequest) {
        List<Map<String, Object>> contractMessagesList = new ArrayList<>();
        int type = 0;
        if (contractManageNewsRequest.getType() == 1) {
            type = 1;
        }
        Contract list = contractMapper.findMyReleaseByUserIdOrContractId(contractManageNewsRequest.getContractId(), Long.parseLong(contractManageNewsRequest.getUserId()), type);
        if (list != null) {
            //消息  t_contract_message
            contractMessagesList = getContractMessageList(contractManageNewsRequest, list);
        }
        return contractMessagesList;
    }


    /**
     * 合约预览
     *
     * @param contractId 合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @Override
    public ContractDetailsResponse preview(Long contractId) {
        return contractMapper.findContractDetails(contractId, true);
    }

    /**
     * 删除富文本
     *
     * @param contractId richText: 富文本, contractId;合约id
     * @return ResultInfo<?>
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @Override
    @Transactional
    public Boolean delRichText(Long contractId) {

        contractMapper.delRichText(contractId);

        return true;
    }


    /**
     * 我的签约管理 （无效）
     *
     * @param contractManageRequest
     * @return
     */
  /*  public Map<String, Object> findMyContractByUserIdOrContractId(ContractManageRequest contractManageRequest) {
        Map<String, Object> contractMap = new HashedMap();
        Contract  list = contractMapper.findMyReleaseByUserIdOrContractId(contractManageRequest.getContractId(),Long.parseLong(contractManageRequest.getUserId()),0);
        if(list != null ){
            //合约  Contract
            Map<String, Object> contractData = getContract(0, list);
            contractMap.put("contractData", contractData);

            //消息  t_contract_message
            List<Map<String, Object>> contractMessagesList = getContractMessageList(contractManageRequest.getContractId(), contractManageRequest.getUserId(), list);
            contractMap.put("contractMessageData", contractMessagesList);

            //更新首页合约排序
            updateUserContract(Long.parseLong(contractManageRequest.getUserId()),contractManageRequest.getContractId(),2);
        }
        return contractMap;
    }*/


    /**
     * 获取合约详情
     *
     * @param contractId 合约id
     * @return Contract
     * @author LuoFuMin
     * @date 2018/7/24
     */
    @Override
    public ContractDetailsResponse getDetails(Long contractId) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContractDetailsResponse contractDetailsResponse = contractMapper.findContractDetails(contractId, false);

        if (contractDetailsResponse.getState() != null && contractDetailsResponse.getState() == 0) {
            //状态  0 已取消
            contractDetailsResponse.setContractState(0);
        }
        if (contractDetailsResponse.getState() != null && contractDetailsResponse.getState() == 1) {
            //状态  4 不通过
            contractDetailsResponse.setContractState(4);
        }
        if (format.format(new Date()).compareTo(contractDetailsResponse.getBackTime()) >= 0 || contractDetailsResponse.getCollectState()) {
            //状态  2 已结束
            contractDetailsResponse.setContractState(2);
        }
        if (!contractDetailsResponse.getSignState()) {
            //状态  1 未开始
            contractDetailsResponse.setContractState(1);
        } else {
            //状态  3 进行中
            contractDetailsResponse.setContractState(3);
        }
        return contractDetailsResponse;
    }

    /**
     * 合约管理---合约信息
     *
     * @param type 1：我的发布管理   0：我的签约管理
     * @param list
     * @return
     */
    private Map<String, Object> getContract(int type, Contract list) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashedMap();
        map.put("contractId", list.getId());// id
        map.put("isSign", list.getSignState());// 是否签约
        map.put("contractName", list.getConName());//合约名称
        map.put("money", list.getMoney());// 还款金额
        map.put("backTime", list.getBackTime());//还款时间
        //判断状态 未开始 进行中、已结束、已取消
        if (list.getState() != null && list.getState() == 0) {
            map.put("contractState", 0);//状态  0 已取消
            map.put("state", 2);//  2 空
        } else if (list.getState() != null && list.getState() == 1) {
            map.put("contractState", 4);//状态  4 不通过
            map.put("state", 2);//  0 未还
        } else if (format.format(new Date()).compareTo(list.getBackTime()) >= 0 || list.getCollectState()) {
            map.put("contractState", 2);//状态  2 已结束
            if (list.getCollectState()) {
                map.put("state", 1);//  1 已还
            } else {
                map.put("state", 0);//  0 未还
            }
        } else if (!list.getSignState()) {
            map.put("contractState", 1);//状态  1 未开始
            map.put("state", 2);//  2 空
        } else {
            map.put("contractState", 3);//状态  3 进行中
            if (list.getCollectState()) {
                map.put("state", 1);//  1 已还
            } else {
                map.put("state", 0);//  0 未还
            }
        }
        map.put("portrait", list.getPortrait());//封面

        //借入借出根据用户判断
        //1、判断合约是放贷 还是借贷
        //合约是放贷 再判断 访问用户是否是发布合约的作者 是则 借出  不是则  借入
        if (list.getRole() == 1) { //Role:1-放贷，2-借贷
            if (type == 1) {
                map.put("role", 2);//2-借出
            } else {
                map.put("role", 1);//1-借入
            }
        } else {
            if (type == 1) {
                map.put("role", 1);//1-借入
            } else {
                map.put("role", 2);//2-借出
            }
        }

        map.put("isReceivables", list.getCollectState());//是否收款  collect_state   是否还款 still_state

        Map<String, Object> objectMap = contractTypeMapper.selectParentIdById(list.getContractTypeId());
        if (objectMap != null && objectMap.size() > 0) {
            map.put("industryParentName", objectMap.get("parentName"));//一级类型名称   parentName
            map.put("industryName", objectMap.get("name"));//二级类型名称  name
        } else {
            map.put("industryParentName", "");//一级类型名称   parentName
            map.put("industryName", "");//二级类型名称  name
        }
        return map;
    }

    /**
     * 合约管理----合约消息过程
     *
     * @param contractManageNewsRequest
     * @param list                      合约
     * @return
     */
    private List<Map<String, Object>> getContractMessageList(ContractManageNewsRequest contractManageNewsRequest, Contract list) {
        List<Map<String, Object>> contractMessagesList = new ArrayList<>();

        //分页
        int pageNum = 1;
        pageNum = contractManageNewsRequest.getPage();
        int pageSize = 0;
        pageSize = contractManageNewsRequest.getSize();
        int count = contractMessageMapper.selectByContractIdCount(contractManageNewsRequest.getContractId()); //总记录数
        int Pages, pageNums = 0;
        if (count % pageSize == 0) {
            Pages = count / pageSize;
        } else {
            Pages = count / pageSize + 1;
        }
        //如果传入的当前页比最大页大就返回空
        if (pageNum <= Pages) {
            if (pageNum > 1) {
                pageNums = (pageNum - 1) * pageSize;
            } else {
                pageNums = 0;
            }

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            List<Map<String, Object>> contractMessages = contractMessageMapper.selectByContractId(contractManageNewsRequest.getContractId(), pageNums, pageSize);
            if (contractMessages != null && contractMessages.size() > 0) {
                User user = null;
                if (list.getUserId().toString().equals(contractManageNewsRequest.getUserId())) {
                    user = userMapper.selectByPrimaryKey(list.getSignId());
                } else {
                    user = userMapper.selectByPrimaryKey(list.getUserId());
                }

                for (int i = 0; i < contractMessages.size(); i++) {
                    Map<String, Object> messagesMap = new HashedMap();
                    //1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
                    if ((long) contractMessages.get(i).get("causeType") == 1) {
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我发起借款合约申请");
                        } else {
                            messagesMap.put("content", user.getNickname() + "发起借款合约申请");
                        }
                        messagesMap.put("type", 1);
                    } else if ((long) contractMessages.get(i).get("causeType") == 2) {
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我发起延期还款申请");
                        } else {
                            if (contractMessages.get(i).get("state") != null) {
                                messagesMap.put("state", 1);
                            } else {
                                messagesMap.put("state", 0);
                            }
                            messagesMap.put("content", user.getNickname() + "发起延期还款申请");
                            messagesMap.put("cause", contractMessages.get(i).get("cause").toString());
                            messagesMap.put("deferTime", format.format(contractMessages.get(i).get("deferTime")));
                        }
                        messagesMap.put("type", 2);
                    } else if ((long) contractMessages.get(i).get("causeType") == 3) {
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我发起取消合约申请");
                        } else {
                            messagesMap.put("content", user.getNickname() + "发起取消合约申请");
                            if (contractMessages.get(i).get("state") != null) {
                                messagesMap.put("state", 1);
                            } else {
                                messagesMap.put("state", 0);
                            }
                            messagesMap.put("cause", contractMessages.get(i).get("cause").toString());
                        }
                        messagesMap.put("type", 3);
                    } else if ((long) contractMessages.get(i).get("causeType") == 4) {
                        String v = "";
                        if ((int) contractMessages.get(i).get("state") == 0) {
                            v = "拒绝";
                        } else {
                            v = "同意";
                        }
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我" + v + "了" + user.getNickname() + "发起的延期还款申请");
                        } else {
                            messagesMap.put("content", user.getNickname() + v + "了" + "你发起的延期还款申请");
                           /* messagesMap.put("cause", contractMessages.get(i).get("cause").toString());
                            messagesMap.put("deferTime", format.format(contractMessages.get(i).get("deferTime")));*/
                        }
                        messagesMap.put("type", 4);
                    } else if ((long) contractMessages.get(i).get("causeType") == 5) {
                        String v = "";
                        if ((int) contractMessages.get(i).get("state") == 0) {
                            v = "拒绝";
                        } else {
                            v = "同意";
                        }
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我" + v + "了" + user.getNickname() + "发起的取消合同申请");
                        } else {
                            messagesMap.put("content", user.getNickname() + v + "了" + "你发起的取消合同申请");
                        }
                        messagesMap.put("type", 5);
                    } else if ((long) contractMessages.get(i).get("causeType") == 6) {
                        String v = "";
                        if ((int) contractMessages.get(i).get("state") == 0) {
                            v = "拒绝";
                        } else {
                            v = "同意";
                        }
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我" + v + "了" + user.getNickname() + "发起的借款合约申请");
                        } else {
                            messagesMap.put("content", user.getNickname() + v + "了" + "你发起的借款合约申请");
                        }
                        messagesMap.put("type", 6);
                    } else if ((long) contractMessages.get(i).get("causeType") == 7) {
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我已确认还款");
                        } else {
                            messagesMap.put("content", user.getNickname() + "已确认还款");
                        }
                        messagesMap.put("type", 7);
                    } else if ((long) contractMessages.get(i).get("causeType") == 8) {
                        if (contractManageNewsRequest.getUserId().equals(contractMessages.get(i).get("userId").toString())) {
                            messagesMap.put("content", "我已确认收款");
                        } else {
                            messagesMap.put("content", user.getNickname() + "已确认收款");
                        }
                        messagesMap.put("type", 8);
                    }
                    if (messagesMap.get("cause") == null) {
                        messagesMap.put("cause", "");
                    }
                    if (messagesMap.get("deferTime") == null) {
                        messagesMap.put("deferTime", "");
                    }
                    if (messagesMap.get("state") == null) {
                        messagesMap.put("state", "");
                    }
                    messagesMap.put("gmtCreate", format.format(contractMessages.get(i).get("gmtCreate")));
                    messagesMap.put("avatar", contractMessages.get(i).get("avatar"));
                    messagesMap.put("contractMessageId", contractMessages.get(i).get("contractMessageId"));
                    contractMessagesList.add(messagesMap);
                }
            }
        }

        return contractMessagesList;
    }


    /**
     * 合约管理 --- 发起延期还款获取发起取消合约
     *
     * @param contractPostponedRequest
     * @return
     */
    @Override
    @Transactional
    public int contractPostponed(ContractPostponedRequest contractPostponedRequest) {

        Contract contract = contractMapper.selectByPrimaryKey(contractPostponedRequest.getContractId());
        try {
            if (contract == null || contract.getIsDeleted()) {
                return -1;//合约不存在
            }
            if (StringUtil.isEmpty(contractPostponedRequest.getCause())) {
                return -3;//原因不能为空
            }
            ContractMessage contractMessage = new ContractMessage();
            contractMessage.setCause(contractPostponedRequest.getCause());//原因

            //1  发起延期还款、2 发起取消合约
            if (contractPostponedRequest.getType() == 1) {
                if (StringUtil.isEmpty(contractPostponedRequest.getDeferTime())) {
                    return -3;//延期还款时间不能为空
                }
                contractMessage.setCauseType(2);//1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                contractMessage.setDeferTime(format.parse(contractPostponedRequest.getDeferTime()));//延期时间
            } else if (contractPostponedRequest.getType() == 2) {
                contractMessage.setCauseType(3);//1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
            } else {
                return -2;//类型不对
            }
            contractMessage.setContractId(contractPostponedRequest.getContractId()); //合约ID
            contractMessage.setUserId(Long.parseLong(contractPostponedRequest.getUserId()));//消息发起方
            contractMessage.setIsDeleted(false);
            contractMessage.setGmtCreate(new Date());
            contractMessage.setGmtModified(new Date());
            contractMessageMapper.insertSelective(contractMessage);

            //更新首页合约排序
            updateUserContract(Long.parseLong(contractPostponedRequest.getUserId()), contractPostponedRequest.getContractId(), 2);

            //环信透传
            HuanXinTouChuan(Long.parseLong(contractPostponedRequest.getUserId()), contract);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    /**
     * 合约管理 --- 回应发起延期还款或者取消合同---同意或者不同意
     *
     * @param contractResRequest
     * @return
     */
    @Override
    @Transactional
    public int setContractPostponed(ContractResRequest contractResRequest) {
        ContractMessage contractMessage = contractMessageMapper.selectByPrimaryKey(contractResRequest.getContractMessageId());
        try {
            if (contractMessage == null || contractMessage.getIsDeleted()) {
                return -1;//不存在
            }
            if (contractMessage.getState() != null) {
                return -3;//不存在
            }
            contractMessage.setIsDeleted(false);
            contractMessage.setGmtModified(new Date());
            if (contractResRequest.getState()) {
                //1-同意
                contractMessage.setState(1);
            } else {
                //0-拒绝
                contractMessage.setState(0);
            }
            contractMessageMapper.updateByPrimaryKeySelective(contractMessage);

            //同意或者不同意 写消息表
            ContractMessage contractMessages = new ContractMessage();

            //1  回应延期还款、2 回应取消合约
            if (contractResRequest.getType() == 1) {
                //1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
                contractMessages.setCauseType(4);
            } else if (contractResRequest.getType() == 2) {
                //1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
                contractMessages.setCauseType(5);
            } else {
                return -2;//类型不对
            }
            if (contractResRequest.getState()) {
                Contract contract = contractMapper.selectByPrimaryKey(contractMessage.getContractId());
                EosWallet eosWallet = null;
                String privatekey = "";

                if (contract.getRole() == 1) {
                    eosWallet = eosWalletMapper.findOneByUserId(contract.getSignId());
                    privatekey = eosService.decryptBActivePrivateKey(eosWallet);
                } else {
                    eosWallet = eosWalletMapper.findOneByUserId(contract.getUserId());
                    privatekey = eosService.decryptBActivePrivateKey(eosWallet);
                }
                //发起延期还款
                String url = "http://192.168.1.199:8989/v1/borrow_money/delayrefund";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", contract.getContractHashId());
                jsonObject.put("time", DateUtils.dateToTimeStamp(DateUtils.formatDateToString(contractMessage.getDeferTime(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
                jsonObject.put("privatekey", privatekey);
                String str = restTemplateService.doPost(url, jsonObject);
                LOG.info("》》》 dapp 返回结果" + str);

                JSONObject json = JSONObject.parseObject(str);
                if ("true".equals(json.get("runresult"))) {
                    //1-同意
                    contractMessage.setState(1);
                } else {
                    return -4;
                }
            } else {
                contractMessages.setState(0);//0-拒绝
            }
            contractMessages.setContractId(contractMessage.getContractId()); //合约ID
            contractMessages.setUserId(Long.parseLong(contractResRequest.getUserId()));//消息发起方
            contractMessages.setIsDeleted(false);
            contractMessages.setGmtCreate(new Date());
            contractMessages.setGmtModified(new Date());
            contractMessageMapper.insertSelective(contractMessages);

            Contract contractS = new Contract();
            contractS.setId(contractMessage.getContractId());
            contractS.setGmtModified(new Date());
            //当回应取消合约并且同意时,修改合约状态
            if (contractResRequest.getType() == 1 && contractResRequest.getState()) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                contractS.setBackTime(format.format(contractMessage.getDeferTime()));
            }
            //当回应取消合约并且同意时,修改合约状态
            if (contractResRequest.getType() == 2 && contractResRequest.getState()) {
                contractS.setState(0); // 0 取消合约
            }
            contractMapper.updateByPrimaryKeySelective(contractS);
            //更新首页合约排序
            updateUserContract(Long.parseLong(contractResRequest.getUserId()), contractMessage.getContractId(), 2);

            //环信透传
            Contract contract = contractMapper.selectByPrimaryKey(contractMessage.getContractId());
            if (contract != null) {
                HuanXinTouChuan(Long.parseLong(contractResRequest.getUserId()), contract);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    /**
     * 更新时间或者添加用户合约表（合约首页用户合约表）
     *
     * @param userId     用户ID
     * @param contractId 合约ID
     * @param type       1 自己发布的  2 参与或者签约 （更新时间时这个值可以随便传）
     * @return
     */
    private boolean updateUserContract(long userId, long contractId, int type) {
        //TODO 返回 list 数组
        UserContract userContract = userContractMapper.selectByUserIdAndContractId(userId, contractId);
        try {
            if (userContract == null) {
                UserContract userContracts = new UserContract();
                userContracts.setContractId(contractId);
                userContracts.setUserId(userId);
                if (type == 1) {
                    userContracts.setType(true);
                } else if (type == 2) {
                    userContracts.setType(false);
                }
                userContractMapper.insertSelective(userContracts);
            } else {
                userContract.setGmtModified(new Date());
                userContractMapper.updateByPrimaryKeySelective(userContract);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /**
     * 签约合约
     *
     * @param participateContractRequest
     * @return
     */
    @Override
    @Transactional
    public int participateContract(participateContractRequest participateContractRequest) {
        try {
            if (!participateContractRequest.getState()) {
                //拒绝签约
                Contract contract = new Contract();
                contract.setId(participateContractRequest.getContractId());
                contract.setGmtModified(new Date());
                contract.setState(1);//状态 0：合约取消 1:合约不通过（拒绝签约）
                contractMapper.updateByPrimaryKeySelective(contract);
            } else {
                //同意签约
                Contract contract = new Contract();
                contract.setId(participateContractRequest.getContractId());
                contract.setGmtModified(new Date());
                contract.setSignState(true);
                contractMapper.updateByPrimaryKeySelective(contract);
                //更新首页合约排序
                updateUserContract(Long.parseLong(participateContractRequest.getUserId()), participateContractRequest.getContractId(), 2);
            }

            //同意或者不同意 写消息表
            ContractMessage contractMessages = new ContractMessage();
            // 同意或者不同意签约
            contractMessages.setCauseType(6);//1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款
            if (participateContractRequest.getState()) {
                contractMessages.setState(1);//1-同意
            } else {
                contractMessages.setState(0);//0-拒绝
            }
            contractMessages.setContractId(participateContractRequest.getContractId()); //合约ID
            contractMessages.setUserId(Long.parseLong(participateContractRequest.getUserId()));//消息发起方
            contractMessages.setIsDeleted(false);
            contractMessages.setGmtCreate(new Date());
            contractMessages.setGmtModified(new Date());
            contractMessageMapper.insertSelective(contractMessages);

            //环信透传
            Contract contract = contractMapper.selectByPrimaryKey(participateContractRequest.getContractId());
            if (contract != null) {
                HuanXinTouChuan(Long.parseLong(participateContractRequest.getUserId()), contract);
            }
            // dapp 创建合约
            String contractHashId = dappCreateContract(contract);

            if (StringUtils.isBlank(contractHashId)) {
                throw new XdaoException(300, "签约失败");
            }
            //保存合约状态
            contract.setContractHashId(contractHashId);
            contract.setSignState(true);
            contractMapper.updateByPrimaryKeySelective(contract);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }


    /**
     * 还款/收款
     *
     * @param contractRepaymentRequest
     * @return
     */
    @Override
    @Transactional
    public int contractRepayment(contractRepaymentRequest contractRepaymentRequest) {
        try {
            Contract contracts = contractMapper.selectByPrimaryKey(contractRepaymentRequest.getContractId());
            contracts.setId(contractRepaymentRequest.getContractId());
            //1 还款 2 收款
            if (contractRepaymentRequest.getType() == 1) {
                String privatekey = "";
                EosWallet eosWallet = null;
                //判断合约是放贷还是借款，role=1 放贷，role=2 借款
                if (contracts.getRole() == 2 && contracts.getUserId().toString().equals(contractRepaymentRequest.getUserId())) {
                    eosWallet = eosWalletMapper.findOneByUserId(contracts.getUserId());
                    privatekey = eosService.decryptBActivePrivateKey(eosWallet);
                }
                if (contracts.getRole() == 1 && contracts.getSignId().toString().equals(contractRepaymentRequest.getUserId())) {
                    eosWallet = eosWalletMapper.findOneByUserId(contracts.getSignId());
                    privatekey = eosService.decryptBActivePrivateKey(eosWallet);
                }
                String url = "http://192.168.1.199:8989/v1/borrow_money/refund";
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uid", contracts.getContractHashId());
                jsonObject.put("privatekey", privatekey);
                String str = restTemplateService.doPost(url, jsonObject);
                LOG.info("》》》 dapp 返回结果" + str);

                JSONObject json = JSONObject.parseObject(str);
                if ("true".equals(json.get("runresult"))) {
                    //still_state 还款状态
                    contracts.setStillState(true);
                } else {
                    return -1;
                }
            } else if (contractRepaymentRequest.getType() == 2) {
                //collect_state  收款状态
                contracts.setCollectState(true);
            } else {
                return -1;
            }
            contracts.setGmtModified(new Date());
            contracts.setSignState(true);
            contractMapper.updateByPrimaryKeySelective(contracts);

            //更新首页合约排序
            updateUserContract(Long.parseLong(contractRepaymentRequest.getUserId()), contractRepaymentRequest.getContractId(), 2);

            //同意或者不同意 写消息表
            ContractMessage contractMessages = new ContractMessage();
            //1 还款 2 收款
            if (contractRepaymentRequest.getType() == 1) {
                contractMessages.setCauseType(7);//1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款  7 还款  8 收款
            } else if (contractRepaymentRequest.getType() == 2) {
                contractMessages.setCauseType(8);//1 合同申请、2 延期还款、3 取消合约 4、同意或者不同意延期还款 5同意或者不同意取消合同 6同意或者不同意签约  7 还款  8 收款  7 还款  8 收款
            }

            contractMessages.setContractId(contractRepaymentRequest.getContractId()); //合约ID
            contractMessages.setUserId(Long.parseLong(contractRepaymentRequest.getUserId()));//消息发起方
            contractMessages.setIsDeleted(false);
            contractMessages.setGmtCreate(new Date());
            contractMessages.setGmtModified(new Date());
            contractMessageMapper.insertSelective(contractMessages);

            //更新首页合约排序
            updateUserContract(Long.parseLong(contractRepaymentRequest.getUserId()), contractRepaymentRequest.getContractId(), 2);

            //环信透传
            Contract contract = contractMapper.selectByPrimaryKey(contractRepaymentRequest.getContractId());
            if (contract != null) {
                HuanXinTouChuan(Long.parseLong(contractRepaymentRequest.getUserId()), contract);
            }


        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    /**
     * 确认发布
     *
     * @param contractId 合约id
     * @return Boolean
     * @author LuoFuMin
     * @date 2018/7/26
     */
    @Override
    @Transactional
    public Boolean confirm(Long contractId) throws Exception {
        Contract contract = contractMapper.selectByPrimaryKey(contractId);
        //判断合约是否发布，不能重复发布
        if (!contract.getIsDeleted()) {
            return false;
        }
        contract.setIsDeleted(false);
        //更新合约状态
        contractMapper.updateByPrimaryKeySelective(contract);

        Contract contract1 = contractMapper.selectByPrimaryKey(contractId);
        UserContract userContract = new UserContract();
        userContract.setUserId(contract1.getUserId());
        userContract.setContractId(contract1.getId());
        userContract.setType(true);
        userContractMapper.insertSelective(userContract);

        UserContract userContract1 = new UserContract();
        userContract1.setUserId(contract1.getSignId());
        userContract1.setContractId(contract1.getId());
        userContract1.setType(false);
        userContractMapper.insertSelective(userContract1);

        /**
         * 发送环信通知
         */
        HuanXinTouChuan(contract1.getUserId(), contract1);

        return true;
    }

    /**
     * dapp 创建合约
     *
     * @param contract 合约信息
     * @return
     */
    private String dappCreateContract(Contract contract) throws Exception {

        EosWallet walletUser = eosWalletMapper.findOneByUserId(contract.getUserId());
        String keyUser = eosService.decryptBActivePrivateKey(walletUser);

        EosWallet walletSign = eosWalletMapper.findOneByUserId(contract.getSignId());
        String keySign = eosService.decryptBActivePrivateKey(walletSign);
        ContractType contractType = contractTypeMapper.selectByPrimaryKey(contract.getContractTypeId());

        ContractRequest contractRequest = new ContractRequest();
        contractRequest.setContractHashId("");
        contractRequest.setId(contract.getId().toString());
        contractRequest.setUserId(contract.getUserId().toString());
        contractRequest.setRole(contract.getRole().toString());
        contractRequest.setSignId(contract.getSignId().toString());
        contractRequest.setUserEosAccount(walletUser.getWalletName());
        contractRequest.setUserKey(keyUser);
        contractRequest.setSignEosAccount(walletSign.getWalletName());
        contractRequest.setSignKey(keySign);
        contractRequest.setSignTime(DateUtils.timeStamp());
        contractRequest.setConName(contract.getConName());
        contractRequest.setContractTypeId(contractType.getName());
        contractRequest.setConAddress(contract.getConAddress());
        contractRequest.setPortrait(contract.getPortrait());
        contractRequest.setLongitude(contract.getLongitude());
        contractRequest.setLatitude(contract.getLatitude());
        contractRequest.setMoney(contract.getMoney() + " MAI");
        contractRequest.setBorrowWay(contract.getBorrowWay().toString());
        contractRequest.setAccrual(contract.getAccrual());
        contractRequest.setBackTime(DateUtils.dateToTimeStamp(contract.getBackTime(), "yyyy-MM-dd HH:mm:ss"));
        contractRequest.setRemark(contract.getRemark());
        contractRequest.setRichText(contract.getRichText());
        contractRequest.setPenalty(contract.getPenalty() + " MAI");
        String gmtCreate = DateUtils.formatDateToString(contract.getGmtCreate(), "yyyy-MM-dd HH:mm:ss");
        contractRequest.setGmtCreate(DateUtils.dateToTimeStamp(gmtCreate, "yyyy-MM-dd HH:mm:ss"));

        String url = "http://192.168.1.199:8989/v1/borrow_money/create";
        String info = JSON.toJSONString(contractRequest);
        LOG.info("》》》转换后 info ==" + info);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("info", info);
        jsonObject.put("bcipher", "1");
        LOG.info("》》》 合约请求参数 jsonObject ==" + jsonObject);
        String str = restTemplateService.doPost(url, jsonObject);
        LOG.info("》》》 dapp 返回结果" + str);

        JSONObject josn = JSONObject.parseObject(str);

        if ("false".equals(josn.get("result").toString())) {
            return null;
        }
        String contractHashId = josn.get("id").toString();
        return contractHashId;
    }

    /**
     * 转换时间
     *
     * @param validTime
     * @return
     */
    private int transformation(String validTime) {
        if ("1".equals(validTime)) {
            return 1;
        }
        if ("2".equals(validTime)) {
            return 7;
        }
        if ("3".equals(validTime)) {
            return 30;
        }
        if ("4".equals(validTime)) {
            return 365;
        }
        return 0;
    }


    /**
     * 添加 矿石记录
     *
     * @param userid 用户id
     * @param amount 数量
     * @param way    方式
     */
    private void addOreTransactionRecord(Long userid, Long amount, String way) {
        OreTransaction oreTransaction = new OreTransaction(userid, amount, way);
        int i = oreTransactionMapper.insertSelective(oreTransaction);
    }


    /**
     * 合约发透传
     *
     * @param userId   当前用户ID
     * @param contract 合约
     */
    private void HuanXinTouChuan(long userId, Contract contract) {
        User user = userMapper.selectByPrimaryKey(userId);
        User signUser = null;
        if (contract.getUserId() == userId) {
            signUser = userMapper.selectByPrimaryKey(contract.getSignId());
        } else {
            signUser = userMapper.selectByPrimaryKey(contract.getUserId());
        }
        if (user != null && signUser != null) {
            //发环信透传消息
            SendMessageRequest sendMessageRequest = new SendMessageRequest();
            Message message = new Message();
            message.setType("cmd");// 消息类型
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("type", 3);//合约消息
            jsonObject.put("contractId", contract.getId());
            jsonObject.put("contractName", contract.getConName());
            jsonObject.put("portrait", contract.getPortrait());
            if (contract.getUserId() == userId) {
                jsonObject.put("isOwn", true);
            } else {
                jsonObject.put("isOwn", false);
            }
            message.setAction(jsonObject.toJSONString());
            sendMessageRequest.setMsg(message);
            sendMessageRequest.setFrom("admin");
            List<String> list = new ArrayList<>();
            sendMessageRequest.setTarget(list);
            sendMessageRequest.setTarget_type("users");
            Object object = huanxinMessage.sendTextMessage(sendMessageRequest);
            LOG.info("==环信透传消息返回==" + JSONObject.toJSON(object).toString());
        }
    }

}
