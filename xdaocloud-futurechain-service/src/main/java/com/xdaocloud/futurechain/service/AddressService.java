package com.xdaocloud.futurechain.service;

import com.xdaocloud.futurechain.dto.req.address.CreateAddressRequest;
import com.xdaocloud.futurechain.dto.req.address.DeleteAddressRequest;
import com.xdaocloud.futurechain.dto.req.address.UpdateAddressRequest;
import com.xdaocloud.futurechain.model.Address;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 地址 接口
 *
 * @author LuoFuMin
 * @data 2018/9/12
 */
public interface AddressService {
    /**
     * 添加地址
     *
     * @param createAddressRequest
     * @return Address
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    Address create(CreateAddressRequest createAddressRequest, HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 更新地址
     *
     * @param updateAddressRequest
     * @return Address
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    Address update(UpdateAddressRequest updateAddressRequest, HttpServletRequest httpServletRequest) throws Exception;

    /**
     * 删除地址
     *
     * @param deleteAddressRequest
     * @return
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    Boolean delete(DeleteAddressRequest deleteAddressRequest, HttpServletRequest httpServletRequest);

    /**
     * 查询地址
     *
     * @param userid
     * @return
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    List<Address> getList(Long userid, HttpServletRequest httpServletRequest);
}
