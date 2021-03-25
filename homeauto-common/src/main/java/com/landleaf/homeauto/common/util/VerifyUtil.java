package com.landleaf.homeauto.common.util;

import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.dto.BaseDto;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mqtt.annotation.Verify;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 校验参数
 *
 * @author hebin
 */
public class VerifyUtil {
    public static void verify(BaseDto dto) throws BusinessException {
        Class<?> clz = dto.getClass();
        Field[] fields = clz.getDeclaredFields();
        Verify verify = null;
        String tempFieldName = null;
        String tempMethod = null;
        Object value = null;
        Method method = null;

        try {
            for (Field field : fields) {
                verify = field.getAnnotation(Verify.class);
                if (null != verify) {
                    tempFieldName = field.getName();
                    tempMethod = CommonConst.GET_METHOD_START.concat(StringUtils.capitalize(tempFieldName));
                    method = clz.getMethod(tempMethod);
                    value = method.invoke(dto);
                    // 判断参数是否为空
                    if (!verify.nullable()) {
                        if (null == value || StringUtils.isEmpty(String.valueOf(value))) {
                            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
                        }
                    }

                    if (null == value || StringUtils.isEmpty(String.valueOf(value))) {
                        continue;
                    }

                    // 判断参数是否是数字
                    if (verify.isNum()) {
                        if (!String.valueOf(value).matches(CommonConst.NUMBER_PATTERN)) {
                            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
                        }
                    }
                }
            }
            dto.check();
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            throw new BusinessException(ErrorCodeEnumConst.CHECK_PARAM_ERROR);
        }
    }
}
