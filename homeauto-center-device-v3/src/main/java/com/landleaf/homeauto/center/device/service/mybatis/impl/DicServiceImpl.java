package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.mapper.DicMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDicService;
import com.landleaf.homeauto.center.device.service.mybatis.IDicTagService;
import com.landleaf.homeauto.common.domain.dto.device.DicDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * 字典表接口服务实现类
 *
 * @author Yujiumin
 * @since 2020-07-10
 */
@Service
public class DicServiceImpl extends ServiceImpl<DicMapper, DicPO> implements IDicService {

    private IDicTagService dicTagService;

    @Autowired
    public void setDicTagService(IDicTagService dicTagService) {
        this.dicTagService = dicTagService;
    }

    @Override
    public Long save(DicDTO dicDto) {
        DicPO dicPo = new DicPO();
        dicPo.setId(dicDto.getId());
        dicPo.setName(dicDto.getName());
        dicPo.setCode(dicDto.getCode());
        dicPo.setSystemCode(Objects.equals(dicDto.getIsSystemCode(), 1));
        dicPo.setEnabled(Objects.equals(dicDto.getEnabled(), 1));
        save(dicPo);
        return dicPo.getId();
    }

    @Override
    public void enableDic(Long id) {
        DicPO dicPo = new DicPO();
        dicPo.setEnabled(true);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public void disableDic(Long id) {
        DicPO dicPo = new DicPO();
        dicPo.setEnabled(false);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public void updateDic(DicDTO dicDTO) {
        DicPO dicPo = new DicPO();
        dicPo.setId(dicDTO.getId());
        dicPo.setName(dicDTO.getName());
        dicPo.setCode(dicDTO.getCode());
        dicPo.setEnabled(Objects.equals(dicDTO.getEnabled(), 1));
        dicPo.setSystemCode(Objects.equals(dicDTO.getIsSystemCode(), 1));

        dicTagService.updateDicCodeByDicId(dicDTO.getCode(), dicDTO.getId());
        updateById(dicPo);
    }

    @Override
    public void enableSystemDic(Long id) {
        DicPO dicPo = new DicPO();
        dicPo.setSystemCode(true);
        dicPo.setId(id);
        updateById(dicPo);
    }

    @Override
    public void cancelSystemDic(Long id) {
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
        if (!StringUtils.isEmpty(dicQueryDTO.getName())) {
            queryWrapper.like("name", dicQueryDTO.getName());
        }
        queryWrapper.orderByDesc("create_time");

        List<DicPO> dicPoList = list(queryWrapper);
        List<DicVO> dicVoList = new LinkedList<>();
        for (DicPO dicPo : dicPoList) {
            DicVO dicVo = new DicVO();
            dicVo.setId(dicPo.getId());
            dicVo.setCode(dicPo.getCode());
            dicVo.setName(dicPo.getName());
            dicVo.setIsSystemCode(dicPo.getSystemCode() ? 1 : 0);
            dicVo.setEnabled(dicPo.getEnabled() ? 1 : 0);
            dicVoList.add(dicVo);
        }

        PageInfo<DicPO> pageInfo = new PageInfo<>(dicPoList);
        basePageVO.setPages(pageInfo.getPages());
        basePageVO.setTotal(pageInfo.getTotal());
        basePageVO.setList(dicVoList);

        return basePageVO;
    }

}
