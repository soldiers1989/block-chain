package com.xdaocloud.futurechain.config.filter;


import com.alibaba.fastjson.JSON;
import com.xdaocloud.base.info.ResultInfo;
import com.xdaocloud.base.utils.JsonFormatUtils;
import com.xdaocloud.futurechain.constant.Constant;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * spring 拦截器
 *
 * @author LuoFuMin
 * @data 2018/7/13
 */
public class WebHandlerInterceptor implements HandlerInterceptor {

    private static Logger LOG = LoggerFactory.getLogger(Filter.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 该方法在请求处理之前进行调用。SpringMVC 中的 Interceptor 是链式调用的，在一个应用中或者说是在一个请求中可以同时存在多个 Interceptor 。
     * 每个 Interceptor 的调用会依据它的声明顺序依次执行，而且最先执行的都是 Interceptor 中的 preHandle 方法，所以可以在这个方法中进行一些前置初始化操作或者是对当前请求做一个预处理，
     * 也可以在这个方法中进行一些判断来决定请求是否要继续进行下去。该方法的返回值是布尔值 Boolean 类型的，当它返回为 false 时，表示请求结束，后续的 Interceptor 和 Controller 都不会再执行；
     * 当返回值为 true 时，就会继续调用下一个 Interceptor 的 preHandle 方法，如果已经是最后一个 Interceptor 的时候，就会是调用当前请求的 Controller 中的方法。
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestURI = httpServletRequest.getRequestURI();
        LOG.info("requestURI = " + requestURI);
        // 接口白名单
        String[] whiteList = new String[]{"login", "logout", "register", "swagger", "api-docs", "get_erify_code","shiro","error"};
        for (String string : whiteList) {
            if (requestURI.contains(string)) {
                return true;
            }
        }

        String token = "";
        token = httpServletRequest.getHeader("Authorization");
        LOG.info("Header token = " + token);
        if (StringUtils.isEmpty(token)) {
            LOG.error("》》》 token 为空");
            errorToken(httpServletResponse);
            return false;
        }

        try {
            String jwt_user_id = Jwts.parser().setSigningKey(Constant.JWT_SECRET).parseClaimsJws(token).getBody().getSubject();
            LOG.info("》》》 jwt_user_id ==" + jwt_user_id);
            String redis_jwt_token = stringRedisTemplate.opsForValue().get(Constant.WEB_USER_ID+jwt_user_id);
            LOG.info("》》》 redis_jwt_token ==" + redis_jwt_token);
            if (StringUtils.isEmpty(redis_jwt_token)) {
                overdueLogin(httpServletResponse);
            }
            if (!token.equals(redis_jwt_token)) {
                errorToken(httpServletResponse);
                return false;
            }
        } catch (Exception e) {
            errorToken(httpServletResponse);
            return false;
        }

        //忽略大小写的比较
       /* if (!token.equalsIgnoreCase(String.valueOf("token"))) {
            String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "账号已在其它地方登录，如不是本人操作请修改密码"));
            LOG.info(JsonFormatUtils.formatJson(jsonString));
            PrintWriter out = httpServletResponse.getWriter();
            out.print(jsonString);
            return false;
        }*/

        return true;
    }

    /**
     * 通过 preHandle 方法的解释咱们知道这个方法包括后面要说到的 afterCompletion 方法都只能在当前所属的 Interceptor 的 preHandle 方法的返回值为 true 的时候，
     * 才能被调用。postHandle 方法在当前请求进行处理之后，也就是在 Controller 中的方法调用之后执行，但是它会在 DispatcherServlet 进行视图返回渲染之前被调用，
     * 所以咱们可以在这个方法中对 Controller 处理之后的 ModelAndView 对象进行操作。postHandle 方法被调用的方向跟 preHandle 是相反的，也就是说，
     * 先声明的 Interceptor 的 postHandle 方法反而会后执行。这和 Struts2 里面的 Interceptor 的执行过程有点类型，Struts2 里面的 Interceptor 的执行过程也是链式的，
     * 只是在 Struts2 里面需要手动调用 ActionInvocation 的 invoke 方法来触发对下一个 Interceptor 或者是 action 的调用，然后每一个 Interceptor 中在 invoke 方法调用之前的内容都是按照声明顺序执行的，
     * 而 invoke 方法之后的内容就是反向的。
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * afterCompletion方法，也是需要当前对应的 Interceptor 的 preHandle 方法的返回值为 true 时才会执行。因此，该方法将在整个请求结束之后，
     * 也就是在 DispatcherServlet 渲染了对应的视图之后执行，这个方法的主要作用是用于进行资源清理的工作。
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void overdueLogin(HttpServletResponse response) throws IOException {
        String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "Token已过期，请重新登录"));
        LOG.info(JsonFormatUtils.formatJson(jsonString));
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }

    public void errorToken(HttpServletResponse response) throws IOException {
        String jsonString = JSON.toJSONString(new ResultInfo<>(ResultInfo.UNAUTHORIZED, "请重新登录"));
        LOG.info(JsonFormatUtils.formatJson(jsonString));
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        out.print(jsonString);
    }
}
