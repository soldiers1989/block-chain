package com.xdaocloud.futurechain.controller;

import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.dto.req.address.CreateAddressRequest;
import com.xdaocloud.futurechain.dto.req.address.DeleteAddressRequest;
import com.xdaocloud.futurechain.dto.req.address.UpdateAddressRequest;
import com.xdaocloud.futurechain.model.Address;
import com.xdaocloud.futurechain.service.AddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户地址管理
 *
 * @author LuoFuMin
 * @data 2018/9/11
 */
@Api(description = "用户管理API")
@RestController
@RequestMapping("/api/app/")
public class AddressController {

    @Autowired
    AddressService addressService;

    /**
     * 添加地址
     *
     * @param createAddressRequest
     * @return
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    @ApiOperation(value = "添加地址", notes = "province：省、city：市、county：区/县、detailed：详细地址、postcode：邮编、name：名字、phone：手机号码、choice：默认地址（1是默认，0非默认）、userid：用户id")
    @PostMapping("v2/address")
    public ResultInfo<?> create(@Valid @RequestBody CreateAddressRequest createAddressRequest, HttpServletRequest httpServletRequest) throws Exception {
        Address address = addressService.create(createAddressRequest, httpServletRequest);
        if (address == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, address);
    }

    /**
     * 修改地址
     *
     * @param updateAddressRequest
     * @return
     * @throws Exception
     * @date 2018年9月12日
     * @author LuoFuMin
     */
    @ApiOperation(value = "修改地址", notes = "id：地址id、province：省、city：市、county：区/县、detailed：详细地址、postcode：邮编、name：名字、phone：手机号码、choice：默认地址（1是默认，0非默认）")
    @PutMapping("v2/address")
    public ResultInfo<?> update(@Valid @RequestBody UpdateAddressRequest updateAddressRequest, HttpServletRequest httpServletRequest) throws Exception {
        Address address = addressService.update(updateAddressRequest, httpServletRequest);
        if (address == null) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, address);
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
    @ApiOperation(value = "删除地址", notes = "userid：用户id、ids：Long[] 地址id数组")
    @DeleteMapping("v2/address")
    public ResultInfo<?> delete(@Valid @RequestBody DeleteAddressRequest deleteAddressRequest, HttpServletRequest httpServletRequest) throws Exception {
        Boolean aBoolean = addressService.delete(deleteAddressRequest, httpServletRequest);
        if (!aBoolean) {
            return new ResultInfo<>(ResultInfo.FAILURE, ResultInfo.MSG_FAILURE);
        }
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS);
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
    @ApiOperation(value = "查询地址", notes = "userid：用户id")
    @GetMapping("v2/address/{userid}")
    public ResultInfo<?> getList(@NotNull @PathVariable Long userid, HttpServletRequest httpServletRequest) throws Exception {
        List<Address> list = addressService.getList(userid, httpServletRequest);
        return new ResultInfo<>(ResultInfo.SUCCESS, ResultInfo.MSG_SUCCESS, list);
    }
}
