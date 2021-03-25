package com.landleaf.homeauto.center.device.context;

import com.landleaf.homeauto.center.device.annotation.LogAnnotation;
import com.landleaf.homeauto.center.device.annotation.RequestCheckAnnotation;
import com.landleaf.homeauto.center.device.enums.OperationLogTypeEnum;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.log.OperationLog;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.aspectj.lang.reflect.SourceLocation;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @ClassName CustomerRequestFilterAspect
 * @Description: 自定义业务检查
 * @Author wyl
 * @Date 2021/1/18
 * @Version V1.0
 **/
@Component
@Order(value = 999)
@Aspect
public class CustomerRequestFilterAspect {

    /**
     * @Pointcut ：切入点声明，即切入到哪些目标方法。value 属性指定切入点表达式，默认为 ""。
     * 用于被下面的通知注解引用，这样通知注解只需要关联此切入点声明即可，无需再重复写切入点表达式
     * <p>
     * 切入点表达式常用格式举例如下：
     * - * com.wmx.aspect.EmpService.*(..))：表示 com.wmx.aspect.EmpService 类中的任意方法
     * - * com.wmx.aspect.*.*(..))：表示 com.wmx.aspect 包(不含子包)下任意类中的任意方法
     * - * com.wmx.aspect..*.*(..))：表示 com.wmx.aspect 包及其子包下任意类中的任意方法
     * </p>
     */
    @Pointcut(value = "@annotation(com.landleaf.homeauto.center.device.annotation.RequestCheckAnnotation)")
    private void aspectPointcut() {
    }

    /**
     * 前置通知：目标方法执行之前执行以下方法体的内容。
     * value：绑定通知的切入点表达式。可以关联切入点声明，也可以直接设置切入点表达式
     * <br/>
     *
     * @param joinPoint：提供对连接点处可用状态和有关它的静态信息的反射访问<br/> <p>
     *                                                 Object[] getArgs()：返回此连接点处（目标方法）的参数
     *                                                 Signature getSignature()：返回连接点处的签名。
     *                                                 Object getTarget()：返回目标对象
     *                                                 Object getThis()：返回当前正在执行的对象
     *                                                 StaticPart getStaticPart()：返回一个封装此连接点的静态部分的对象。
     *                                                 SourceLocation getSourceLocation()：返回与连接点对应的源位置
     *                                                 String toLongString()：返回连接点的扩展字符串表示形式。
     *                                                 String toShortString()：返回连接点的缩写字符串表示形式。
     *                                                 String getKind()：返回表示连接点类型的字符串
     *                                                 </p>
     */
    @Before(value = "aspectPointcut()")
    public void aspectBefore(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //从切面中获取当前方法
        Method method = signature.getMethod();
        RequestCheckAnnotation requestCheckAnnotation = method.getAnnotation(RequestCheckAnnotation.class);
        boolean requireUserId = requestCheckAnnotation.requireUserId();
        boolean familyEnable = requestCheckAnnotation.familyEnable();

    }
}
