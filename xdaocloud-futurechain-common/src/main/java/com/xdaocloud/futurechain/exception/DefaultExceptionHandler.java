package com.xdaocloud.futurechain.exception;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.futurechain.common.XdaoException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.ResourceAccessException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 监听全部异常
 */
@ControllerAdvice
public class DefaultExceptionHandler {


    public static final String NETWORK_BAD = "网络请求失败";

    public static final String NETWORK_DELAYED = "网络延时，请稍后再试";


    public static final String TIME_OUT = "连接超时！";

    private static Logger LOG = LoggerFactory.getLogger(DefaultExceptionHandler.class);


    /**
     * 小道自定义异常（主要捕获请求参数错误）
     *
     * @param e
     * @return
     * @date 2018年7月17日
     * @author LiMaoDa
     */
    @ExceptionHandler(value = XdaoException.class)
    @ResponseBody
    private ResultInfo<Object> xdaoExceptionHandler(XdaoException e) {
        LOG.error("》》》 xdaoExceptionHandler ");
        if (LOG.isDebugEnabled()) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }
        return new ResultInfo<>(e.getCode(), e.getMessage(), e.getData());
    }


    /**
     * 捕获参数异常
     *
     * @param e
     * @return
     * @date 2018年9月21日
     * @author LuoFuMin
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultInfo<Object> handleValidException(MethodArgumentNotValidException e) {
        LOG.error("》》》 handleValidException");
        return new ResultInfo(ResultInfo.FAILURE, e.getBindingResult().getFieldError().getDefaultMessage());
    }

    /**
     * 捕获未知异常
     *
     * @param e
     * @return
     * @date 2018年7月17日
     * @author LuoFuMin
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private ResultInfo<Object> defaultExceptionHandler(Exception e) {
        LOG.error("》》》 defaultExceptionHandler ");
        e.printStackTrace();
        LOG.error(JSON.toJSONString(e.getStackTrace()));
        LOG.error(e.getMessage());
     /*   if (LOG.isDebugEnabled()) {
            e.printStackTrace();
            LOG.error(JSON.toJSONString(e.getStackTrace()));
        }*/
        ResultInfo<Object> resultInfo = new ResultInfo<Object>(ResultInfo.ERROR, e.getMessage());
        return resultInfo;
    }

    /**
     * 捕获eos请求异常
     *
     * @param request
     * @param response
     * @param e
     * @return
     * @date 2018年7月17日
     * @author LuoFuMin
     */
    @ExceptionHandler(value = ResourceAccessException.class)
    @ResponseBody
    private ResultInfo<Object> esourceAccessExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        LOG.error("》》》 ResourceAccessException ");
        if (LOG.isDebugEnabled()) {
            e.printStackTrace();
            LOG.error(JSON.toJSONString(e.getStackTrace()));
            LOG.error(e.getMessage());
        }
        ResultInfo<Object> resultInfo = new ResultInfo<Object>(ResultInfo.ERROR, TIME_OUT);
        return resultInfo;
    }

    /**
     * 捕获无权限异常
     *
     * @param e
     * @return
     * @date 2018年7月17日
     * @author LuoFuMin
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    @ResponseBody
    private ResultInfo<Object> unauthorizedExceptionHandler(Exception e) {
        LOG.error("》》》 UnauthorizedException ：捕获无权限异常");
        if (LOG.isDebugEnabled()) {
            e.printStackTrace();
            LOG.error(JSON.toJSONString(e.getStackTrace()));
            LOG.error(e.getMessage());
        }
        ResultInfo<Object> resultInfo = new ResultInfo<Object>(403, "您没有权限访问！");
        return resultInfo;
    }


}
