package com.company.project.interceptor;

import com.alibaba.fastjson.JSON;
import com.company.project.annotation.IgnoreLogin;
import com.company.project.bean.BaseResult;
import com.company.project.bean.LoginUser;
import com.company.project.bean.SpringBean;
import com.company.project.bean.UserContext;
import com.company.project.biz.util.UserUtilService;
import com.company.project.exception.BizException;
import com.company.project.exception.ErrorCode;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录拦截器
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = Logger.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "*");
        response.addHeader("Access-Control-Max-Age", "120");
        response.addHeader("Access-Control-Allow-Headers", "Origin,X-Requested-With,Content-Type,Accept,X-TOKEN");
        response.addHeader("Access-Control-Allow-Credentials", "false");
        response.setContentType("application/json;charset=utf-8");

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IgnoreLogin ignoreLogin = handlerMethod.getMethodAnnotation(IgnoreLogin.class);
        //请求方法不需要登录
        if (null != ignoreLogin && ignoreLogin.ignore()) {
            return true;
        }
        //采用请求头中携带token的方式登录
        String token = request.getHeader("X-TOKEN");
        UserUtilService userUtilService = SpringBean.getBean(UserUtilService.class);
        LoginUser loginUser = userUtilService.getLoginUserInfo(token);
        if (null == loginUser) {
            this.writeResult(response, new BaseResult(ErrorCode.LOG_OUT));
            return false;
        }
        UserContext.setUser(loginUser);
        userUtilService.setLoginUserInfo(token, loginUser);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clean();
        if (null == ex) {
            return;
        }
        if (ex instanceof BizException) {
            BizException bizException = (BizException) ex;
            if (BaseResult.FAILED != bizException.getErrorCode()) {
                this.writeResult(response, new BaseResult(bizException.getErrorCode(), ex.getMessage()));
            } else {
                this.writeResult(response, new BaseResult(BaseResult.FAILED, ex.getMessage()));
            }
        } else {
            ex.printStackTrace();
            logger.error("system error", ex);
            this.writeResult(response, new BaseResult(BaseResult.FAILED, "系统异常，请联系在线客服"));
        }
    }

    private void writeResult(HttpServletResponse response, BaseResult baseResult) {
        PrintWriter writer = null;
        try {
            response.setCharacterEncoding("utf-8");
            writer = response.getWriter();
            writer.write(JSON.toJSONString(baseResult));
            writer.flush();
        } catch (Exception e) {
            logger.info("BaseInterceptor exception", e);
        } finally {
            if (null != writer) {
                writer.close();
            }
        }
    }

}
