package com.xdaocloud.futurechain.service.impl;

import com.xdaocloud.futurechain.constant.Constant;
import com.xdaocloud.futurechain.dto.req.address.CreateAddressRequest;
import com.xdaocloud.futurechain.dto.req.address.DeleteAddressRequest;
import com.xdaocloud.futurechain.dto.req.address.UpdateAddressRequest;
import com.xdaocloud.futurechain.mapper.AddressMapper;
import com.xdaocloud.futurechain.model.Address;
import com.xdaocloud.futurechain.service.AddressService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 地址 业务逻辑
 *
 * @author LuoFuMin
 * @data 2018/9/12
 */
@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    AddressMapper addressMapper;

    /**
     * 添加地址
     *
     * @param createAddress
     * @return Address
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public Address create(CreateAddressRequest createAddress, HttpServletRequest httpServletRequest) throws Exception {
        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        Address address = new Address();
        address.setAppId(appId);
        address.setUserId(createAddress.getUserid());
        address.setCounty(createAddress.getCounty());
        address.setCity(createAddress.getCity());
        address.setProvince(createAddress.getProvince());
        address.setDetailed(createAddress.getDetailed());
        address.setName(createAddress.getName());
        address.setPhone(createAddress.getPhone());
        address.setChoice(createAddress.getChoice());
        address.setPostcode(createAddress.getPostcode());

        if (createAddress.getChoice().intValue() == 1) {
            addressMapper.updateAllChoiceByUserIdAndAppId(createAddress.getUserid(), appId);
        }
        int i = addressMapper.insertSelective(address);
        if (i > 0) {
            return address;
        }
        return null;
    }


    /**
     * 更新地址
     *
     * @param updateAddress
     * @return Address
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public Address update(UpdateAddressRequest updateAddress, HttpServletRequest httpServletRequest) throws Exception {
        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        Address address = new Address();
        address.setId(updateAddress.getId());

        if (StringUtils.isNoneBlank(updateAddress.getCounty())) {
            address.setCounty(updateAddress.getCounty());
        }
        if (StringUtils.isNoneBlank(updateAddress.getCity())) {
            address.setCity(updateAddress.getCity());
        }
        if (StringUtils.isNoneBlank(updateAddress.getProvince())) {
            address.setProvince(updateAddress.getProvince());
        }
        if (StringUtils.isNoneBlank(updateAddress.getDetailed())) {
            address.setDetailed(updateAddress.getDetailed());
        }
        if (StringUtils.isNoneBlank(updateAddress.getName())) {
            address.setName(updateAddress.getName());
        }
        if (StringUtils.isNoneBlank(updateAddress.getPhone())) {
            address.setPhone(updateAddress.getPhone());
        }
        if (updateAddress.getChoice() != null) {
            address.setChoice(updateAddress.getChoice());
            if (updateAddress.getChoice().intValue() == 1) {
                addressMapper.updateAllChoiceByUserIdAndAppId(updateAddress.getUserid(), appId);
            }
        }
        if (StringUtils.isNoneBlank(updateAddress.getPostcode())) {
            address.setPostcode(updateAddress.getPostcode());
        }
        int i = addressMapper.updateByPrimaryKeySelective(address);
        if (i > 0) {
            address.setUserId(updateAddress.getUserid());
            address.setAppId(appId);
            return address;
        }
        return null;
    }

    /**
     * 删除地址
     *
     * @param deleteAddressRequest
     * @return
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    @Override
    @Transactional
    public Boolean delete(DeleteAddressRequest deleteAddressRequest, HttpServletRequest httpServletRequest) {
        for (Long id : deleteAddressRequest.getIds()) {
            addressMapper.deleteByPrimaryKey(id);
        }
        return true;
    }

    /**
     * 查询地址
     *
     * @param userid
     * @return
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    @Override
    @Transactional(readOnly=true)
    public List<Address> getList(Long userid, HttpServletRequest httpServletRequest) {
        String appId = httpServletRequest.getHeader(Constant.APPID_KEY);
        List<Address> list = addressMapper.findListByUserIdAndAppId(userid, appId);
        return list;
    }
}
