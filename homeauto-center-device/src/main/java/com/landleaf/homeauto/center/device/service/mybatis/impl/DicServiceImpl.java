package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.mapper.DicMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDicService;
import com.landleaf.homeauto.common.domain.dto.device.DicDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
    public String save(DicDTO dicDto, String operator) {
        DicPO dicPo = new DicPO();
        dicPo.setId(dicDto.getId());
        dicPo.setName(dicDto.getName());
        dicPo.setCode(dicDto.getCode());
        dicPo.setSystemCode(Objects.equals(dicDto.getIsSystemCode(), 1));
        dicPo.setEnabled(Objects.equals(dicDto.getEnabled(), 1));
        dicPo.setCreateUser(operator);
        dicPo.setUpdateUser(operator);
        save(dicPo);
        return dicPo.getId();
    }

    @Override
    public void enableDic(String id, String operator) {
        DicPO dicPo = new DicPO();
        dicPo.setEnabled(true);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public void disableDic(String id, String operator) {
        DicPO dicPo = new DicPO();
        dicPo.setEnabled(false);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public void updateDic(DicDTO dicDTO, String operator) {
        DicPO dicPo = new DicPO();
        dicPo.setId(dicDTO.getId());
        dicPo.setName(dicDTO.getName());
        dicPo.setCode(dicDTO.getCode());
        updateById(dicPo);
    }

    @Override
    public void enableSystemDic(String id, String operator) {
        DicPO dicPo = new DicPO();
        dicPo.setSystemCode(true);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public void cancelSystemDic(String id, String operator) {
        DicPO dicPo = new DicPO();
        dicPo.setSystemCode(false);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public BasePageVO<DicVO> getDicList(DicQueryDTO dicQueryDTO) {
        BasePageVO<DicVO> basePageVO = new BasePageVO<>();
        // 1. 分页
        PageHelper.startPage(dicQueryDTO.getPageNum(), dicQueryDTO.getPageSize());
        // 2. 构建查询条件
        QueryWrapper<DicPO> queryWrapper = new QueryWrapper<>();
        if (!Objects.isNull(dicQueryDTO.getName())) {
            queryWrapper.like("name", dicQueryDTO.getName());
        }
        List<DicPO> dicPoList = list(queryWrapper);
        List<DicVO> dicVoList = new LinkedList<>();
        for (DicPO dicPo : dicPoList) {
            DicVO dicVo = new DicVO();
            dicVo.setId(dicPo.getId());
            dicVo.setCode(dicPo.getCode());
            dicVo.setName(dicPo.getName());
            dicVo.setIsSystemCode(dicPo.isSystemCode() ? 1 : 0);
            dicVo.setEnabled(dicPo.isEnabled() ? 1 : 0);
            dicVoList.add(dicVo);
        }

        PageInfo<DicPO> pageInfo = new PageInfo<>(dicPoList);
        basePageVO.setPages(pageInfo.getPages());
        basePageVO.setTotal(pageInfo.getTotal());
        basePageVO.setList(dicVoList);

        return basePageVO;
    }

}
