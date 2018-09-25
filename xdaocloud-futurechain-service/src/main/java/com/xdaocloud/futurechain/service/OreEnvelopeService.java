package com.xdaocloud.futurechain.service;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.ore.CreateOreEnvelopeRequest;
import com.xdaocloud.futurechain.dto.req.ore.GrabOreEnvelopeRequest;
import com.xdaocloud.futurechain.dto.req.user.UserIdRequest;

public interface OreEnvelopeService {


    /**
     * 创建矿包
     *
     * @param request
     * @return
     * @throws Exception
     */
    ResultInfo<?> createOreEnvelope(CreateOreEnvelopeRequest request) throws Exception;


    /**
     * 抢矿包
     *
     * @param request
     * @return
     * @throws Exception
     */
    ResultInfo<?> grabOreEnvelope(GrabOreEnvelopeRequest request) throws Exception;

    /**
     * 获取矿包列表
     *
     * @param request
     * @return
     */
    ResultInfo getOreEnvelopes(UserIdRequest request);
}
