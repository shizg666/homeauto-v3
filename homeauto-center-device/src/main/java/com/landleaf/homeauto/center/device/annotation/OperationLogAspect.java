package com.landleaf.homeauto.center.device.annotation;

import com.landleaf.homeauto.center.device.context.LogStrategyFactory;
import com.landleaf.homeauto.common.domain.dto.log.OperationLog;
import com.landleaf.homeauto.center.device.enums.log.OperationLogTypeEnum;
import com.landleaf.homeauto.common.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName OperationLogAspect
 * @Description: TODO
 * @Author shizg
 * @Date 2020/8/13
 * @Version V1.0
 **/
@Slf4j
@Component
@Aspect
public class OperationLogAspect {


    @Pointcut("@annotation(com.landleaf.homeauto.center.device.annotation.LogAnnotation)")
    public void point(){

    }

    /**
     * 后置通知
     *
     * @param joinPoint
     * @return
     */
    @AfterReturning(pointcut = "point()", returning = "retVal")
    public void afterReturning(JoinPoint joinPoint, Object retVal) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //从切面中获取当前方法
            Method method = signature.getMethod();
            LogAnnotation logAnnotation = method.getAnnotation(LogAnnotation.class);
            OperationLogTypeEnum type = logAnnotation.type();
            String name = logAnnotation.name();
            OperationLog operatorLog = bulidOperationLog(type,name,request);
            LogStrategyFactory.getLogServiceByType(type.getType()).saveLog(operatorLog);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private OperationLog bulidOperationLog(OperationLogTypeEnum type, String name, HttpServletRequest request) {
        String params = getParams(request);
        String ip = getIpAddr(request);
        return OperationLog.builder().ip(ip).params(params).name(name).type(type.getType()).build();
    }


    private String getParams(HttpServletRequest request) {
        byte[] body = new byte[0];
        try {
            body = StreamUtils.getByteByStream(request.getInputStream());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        String data = new String(body, StandardCharsets.UTF_8);
        return data;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        log.error(e.getMessage(), e);
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        return ipAddress;
    }
}
