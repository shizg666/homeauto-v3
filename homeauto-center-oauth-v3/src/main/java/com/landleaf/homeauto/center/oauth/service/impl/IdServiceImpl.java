package com.landleaf.homeauto.center.oauth.service.impl;

import com.landleaf.homeauto.center.oauth.remote.IdRemote;
import com.landleaf.homeauto.common.constant.CommonConst;
import com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.mybatis.mp.IdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典表接口服务实现类
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@Service
public class IdServiceImpl implements IdService {

    @Autowired
    private IdRemote idRemote;


    @Override
    public List<Long> getListSegmentId(int count) {
        Response result = idRemote.getSegmentId(CommonConst.BIZ_CODE);
        if (!result.isSuccess()){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_ID_ERROR.getCode()), ErrorCodeEnumConst.CHECK_ID_ERROR.getMsg());
        }
        return (List<Long>) result.getResult();
    }

    @Override
    public long getSegmentId() {
        Response result = idRemote.getSegmentId(CommonConst.BIZ_CODE);
        if (!result.isSuccess()){
            throw new BusinessException(String.valueOf(ErrorCodeEnumConst.CHECK_ID_ERROR.getCode()), ErrorCodeEnumConst.CHECK_ID_ERROR.getMsg());
        }
        return (long) result.getResult();
    }

    @Override
    public long getSegmentId(String biztype) {
        return 0;
    }
}
