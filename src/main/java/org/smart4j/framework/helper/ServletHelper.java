package org.smart4j.framework.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet 助手类
 * Created by yuezhang on 17/11/1.
 */
public final class ServletHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServletHelper.class);

    private static final ThreadLocal<ServletHelper> SERVLET_HELPER_HOLDER = new ThreadLocal<>();

    private HttpServletRequest request;

    private HttpServletResponse response;

    private ServletHelper(HttpServletRequest request , HttpServletResponse response){
        this.request = request;
        this.response = response;
    }

    /**
     * 初始化
     * @param request
     * @param response
     */
    public static void init(HttpServletRequest request , HttpServletResponse response){
        SERVLET_HELPER_HOLDER.set(new ServletHelper(request,response));
    }

    /**
     * 销毁
     */
    public static void destory(){
        SERVLET_HELPER_HOLDER.remove();
    }

    /**
     * 将属性放入Request中
     * @param name
     * @param value
     */
    public static void setRequestAttribute(String name , Object value){
        getRequest().setAttribute(name,value);
    }

    /**
     * 从Request中获取属性
     * @param name
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getRequestAttribute(String name){
        return (T) getRequest().getAttribute(name);
    }

    /**
     * 从Request中移除属性
     * @param name
     */
    public static void remoteRequestAttribute(String name){
        getRequest().removeAttribute(name);
    }

    /**
     * 发送重定向响应
     * @param location
     */
    public static void sendRedirect(String location){
        try {
            getResponse().sendRedirect(getRequest().getContextPath() + location);
        } catch (IOException e) {
            LOGGER.error("redirect failure",e);
        }
    }

    /**
     * 将属性放入session中
     * @param name
     * @param value
     */
    public static void setSessionAttribute(String name , Object value){
        getSession().setAttribute(name,value);
    }

    /**
     * 从session中获取属性
     * @param name
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionAttribute(String name){
        return (T) getRequest().getSession().getAttribute(name);
    }

    /**
     * 从session中移除属性
     * @param name
     */
    public static void removeSessionAttribute(String name){
        getRequest().getSession().removeAttribute(name);
    }

    /**
     * 使session失效
     */
    public static void invalidateSession(){
        getRequest().getSession().invalidate();
    }

    /**
     * 获取request对象
     * @return
     */
    private static HttpServletRequest getRequest(){
        return SERVLET_HELPER_HOLDER.get().request;
    }

    /**
     * 获取response对象
     * @return
     */
    private static HttpServletResponse getResponse(){
        return SERVLET_HELPER_HOLDER.get().response;
    }

    /**
     * 获取session对象
     * @return
     */
    private static HttpSession getSession(){
        return getRequest().getSession();
    }

    /**
     * 获取ServletContext对象
     * @return
     */
    private static ServletContext getServletContext(){
        return getRequest().getServletContext();
    }

}
