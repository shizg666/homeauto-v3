package com.landleaf.homeauto.center.device.service.mybatis.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.bo.FamilyForAppBO;
import com.landleaf.homeauto.center.device.model.bo.SimpleFamilyBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.mapper.HomeAutoFamilyMapper;
import com.landleaf.homeauto.center.device.model.vo.app.FamilyVO;
import com.landleaf.homeauto.center.device.service.mybatis.IHomeAutoFamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 家庭表 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Service
public class HomeAutoFamilyServiceImpl extends ServiceImpl<HomeAutoFamilyMapper, HomeAutoFamilyDO> implements IHomeAutoFamilyService {

    private HomeAutoFamilyMapper homeAutoFamilyMapper;

    @Override
    public FamilyVO getFamilyListByUserId(String userId) {
        List<FamilyForAppBO> familyForAppBOList = homeAutoFamilyMapper.getFamilyByUserId(userId);
        FamilyVO familyVO = new FamilyVO();
        for (FamilyForAppBO familyForAppBO : familyForAppBOList) {
            SimpleFamilyBO family = new SimpleFamilyBO();
            family.setFamilyId(familyForAppBO.getFamilyId());
            family.setFamilyName(familyForAppBO.getFamilyName());
            if (Objects.equals(familyForAppBO.getLastChecked(), 1)) {
                // 如果是最后一次选择的,就显示当前家庭
                // 这里做深拷贝,如果直接把family对象设值,会引起序列化问题
                SimpleFamilyBO simpleFamilyBO = new SimpleFamilyBO();
                simpleFamilyBO.setFamilyId(family.getFamilyId());
                simpleFamilyBO.setFamilyName(family.getFamilyName());
                familyVO.setCurrent(simpleFamilyBO);
            }
            if (Objects.nonNull(familyVO.getList())) {
                // 如果家庭列表不为空,就添加到
                familyVO.getList().add(family);
            } else {
                familyVO.setList(CollectionUtil.list(true, family));
            }
        }
        return familyVO;
    }

    @Autowired
    public void setHomeAutoFamilyMapper(HomeAutoFamilyMapper homeAutoFamilyMapper) {
        this.homeAutoFamilyMapper = homeAutoFamilyMapper;
    }
}
