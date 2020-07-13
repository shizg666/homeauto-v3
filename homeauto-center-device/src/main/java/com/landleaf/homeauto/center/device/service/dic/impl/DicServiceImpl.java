package com.landleaf.homeauto.center.device.service.dic.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.mapper.dic.DicMapper;
import com.landleaf.homeauto.center.device.service.dic.IDicService;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

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
        dicpo.setEnabled(true);
        dicpo.setCreateTime(LocalDateTime.now());
        dicpo.setUpdateTime(LocalDateTime.now());
        save(dicpo);
        return dicpo.getId();
    }

    @Override
    public BasePageVO<DicVO> getDicList(String name, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<DicPO> queryWrapper = null;
        if (name != null && !"".equals(name)) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("dic_name", name);
        }
        List<DicPO> dicPoList = list(queryWrapper);
        List<DicVO> dicVoList = new LinkedList<>();
        for (DicPO dicPo : dicPoList) {
            DicVO dicVo = new DicVO();
            dicVo.setId(dicPo.getId());
            dicVo.setName(dicPo.getDicName());
            dicVo.setCode(dicPo.getDicCode());
            dicVo.setParentCode(dicPo.getDicParentCode());
            dicVo.setDescription(dicPo.getDicDesc());
            dicVo.setSysCode(dicPo.getSysCode());
            dicVo.setOrder(dicPo.getDicOrder());
            dicVo.setValue(dicPo.getDicValue());
            dicVo.setEnabled(dicPo.getEnabled());
            dicVoList.add(dicVo);
        }
        return new BasePageVO<>(new PageInfo<>(dicVoList));
    }
}
