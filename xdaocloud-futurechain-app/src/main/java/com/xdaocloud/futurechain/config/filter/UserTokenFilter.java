package com.xdaocloud.futurechain.config.filter;

import com.alibaba.fastjson.JSON;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.base.utils.JsonFormatUtils;
import com.xdaocloud.base.utils.RedisUtils;
import com.xdaocloud.futurechain.constant.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(urlPatterns = "/api/*", filterName = "userTokenFilter")
public class UserTokenFilter implements Filter {

    private static Logger log = LoggerFactory.getLogger(UserTokenFilter.class);

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void init(FilterConfig filterConfig)
            throws ServletException {
        log.info("init UserTokenFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException,
            ServletException {
        log.info("doFilter UserTokenFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setHeader("content-type", "application/json;charset=UTF-8");

        String requestURI = request.getRequestURI();
        // 接口白名单
        String[] whiteList = new String[]{"login", "logout", "register", "sms", "password", "token", "QR_code", "v1", "alipayNotify", "wxpayNotify", "version","api"};
        log.info("requestURI = " + requestURI);
        for (String string : whiteList) {
            if (requestURI.contains(string)) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        String token = request.getHeader("Authorization");
        String appId = request.getHeader("appId");
   /*     String timeStamp = request.getHeader("timeStamp");
        String secretCode = request.getHeader("secretCode");

        try {
            AesTool.decrypt(secretCode).contains(timeStamp);
        } catch (Exception e) {
            e.printStackTrace();
            String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "拒绝请求"));
            log.info(JsonFormatUtils.formatJson(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            return;
        }*/

        log.info("Header token = " + token);
        log.info("Header appId = " + appId);
        if (StringUtils.isBlank(token)) {
            String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "请检查请求Header中是否包含Token"));
            log.info(JsonFormatUtils.formatJson(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            return;
        }
        if (StringUtils.isBlank(appId)) {
            String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "请检查请求Header中是否包含appId"));
            log.info(JsonFormatUtils.formatJson(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            return;
        }
        token = token.replace("Bearer ", "");
        try {
            String userId = Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
        } catch (MalformedJwtException e) {
            String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "Token不合法"));
            log.info(JsonFormatUtils.formatJson(jsonString));
            PrintWriter out = response.getWriter();
            out.print(jsonString);
            return;
        } catch (ExpiredJwtException e) {
            overdueLogin(response);
            return;
        }

        String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.SUCCESS, "Token验证通过"));
        log.info(JsonFormatUtils.formatJson(jsonString));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void overdueLogin(HttpServletResponse response) throws IOException {
        String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "Token已过期，请重新登录"));
        log.info(JsonFormatUtils.formatJson(jsonString));
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    @Override
    public void destroy() {
        log.info("UserTokenFilter has destroyed.");
    }
}