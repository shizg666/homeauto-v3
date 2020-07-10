package com.landleaf.homeauto.center.device.service.dic.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.mapper.dic.DicMapper;
import com.landleaf.homeauto.center.device.service.dic.IDicService;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, DicPO> implements IDicService {

    @Override
    public Integer addDic(DicDTO dicDTO) {
        DicPO dicpo = new DicPO();
        dicpo.setId(0);
        dicpo.setDicName(dicDTO.getName());
        dicpo.setDicValue(dicDTO.getValue());
        dicpo.setDicCode(dicDTO.getCode());
        dicpo.setDicParentCode(dicDTO.getParentCode());
        dicpo.setDicDesc(dicDTO.getDesc());
        dicpo.setSysCode(dicDTO.getSysCode());
        dicpo.setDicOrder(dicDTO.getOrder());
        dicpo.setEnabled("1");
        dicpo.setCreateTime(LocalDateTime.now());
        dicpo.setUpdateTime(LocalDateTime.now());
        save(dicpo);
        return dicpo.getId();
    }
}
