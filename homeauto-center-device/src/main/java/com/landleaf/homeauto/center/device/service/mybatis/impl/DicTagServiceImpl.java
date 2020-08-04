package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.mapper.DicTagMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDicTagService;
import com.landleaf.homeauto.common.domain.dto.device.DicTagDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicTagQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicTagPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagVO;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Service
public class DicTagServiceImpl extends ServiceImpl<DicTagMapper, DicTagPO> implements IDicTagService {

    @Override
    public String addDicTag(DicTagDTO dicTagDTO, String operator) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setName(dicTagDTO.getName());
        dicTagPo.setValue(dicTagDTO.getValue());
        dicTagPo.setSort(dicTagDTO.getSort());
        dicTagPo.setEnabled(Objects.equals(dicTagDTO.getEnabled(), 1));
        dicTagPo.setParent(dicTagDTO.getParent());
        dicTagPo.setDicCode(dicTagDTO.getDicCode());
        dicTagPo.setCreateUser(operator);
        dicTagPo.setUpdateUser(operator);
        save(dicTagPo);
        return dicTagPo.getId();
    }

    @Override
    public void enable(String id, String operator) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(id);
        dicTagPo.setEnabled(true);
        dicTagPo.setUpdateUser(operator);
        updateById(dicTagPo);
    }

    @Override
    public void disable(String id, String operator) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(id);
        dicTagPo.setEnabled(false);
        dicTagPo.setUpdateUser(operator);
        updateById(dicTagPo);
    }

    @Override
    public void update(DicTagDTO dicTagDTO, String operator) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(dicTagDTO.getId());
        dicTagPo.setName(dicTagDTO.getName());
        dicTagPo.setValue(dicTagDTO.getValue());
        dicTagPo.setSort(dicTagDTO.getSort());
        dicTagPo.setEnabled(Objects.equals(dicTagDTO.getEnabled(), 1));
        dicTagPo.setUpdateUser(operator);
        updateById(dicTagPo);
    }

    @Override
    public BasePageVO<DicTagVO> getDicTagList(DicTagQueryDTO dicTagQueryDTO) {
        if (Objects.isNull(dicTagQueryDTO.getDicCode())) {
            throw new NullPointerException("dicCode字段不可为空");
        }
        BasePageVO<DicTagVO> basePageVO = new BasePageVO<>();
        // 1. 分页
        PageHelper.startPage(dicTagQueryDTO.getPageNum(), dicTagQueryDTO.getPageSize());
        // 2. 构建查询条件
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dic_code", dicTagQueryDTO.getDicCode());

        if (!StringUtils.isEmpty(dicTagQueryDTO.getName())) {
            queryWrapper.like("name", dicTagQueryDTO.getName());
        } else {
            queryWrapper.isNull("parent");
        }
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        List<DicTagVO> dicTagVoList = new LinkedList<>();
        for (DicTagPO dicTagPo : dicTagPoList) {
            DicTagVO dicTagVO = new DicTagVO();
            dicTagVO.setId(dicTagPo.getId());
            dicTagVO.setName(dicTagPo.getName());
            dicTagVO.setValue(dicTagPo.getValue());
            dicTagVO.setSort(dicTagPo.getSort());
            dicTagVO.setEnabled(dicTagPo.isEnabled() ? 1 : 0);
            dicTagVO.setChildList(getChildList(dicTagPo.getId()));
            dicTagVoList.add(dicTagVO);
        }

        PageInfo<DicTagPO> dicTagPoPageInfo = new PageInfo<>(dicTagPoList);
        basePageVO.setList(dicTagVoList);
        basePageVO.setPages(dicTagPoPageInfo.getPages());
        basePageVO.setTotal(dicTagPoPageInfo.getTotal());
        return basePageVO;
    }

    /**
     * 递归查询子级
     *
     * @param id 主键
     * @return 标签集合
     */
    private List<DicTagVO> getChildList(String id) {
        List<DicTagVO> dicTagVoList = new LinkedList<>();
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", id);
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        for (DicTagPO dicTagPo : dicTagPoList) {
            DicTagVO dicTagVO = new DicTagVO();
            dicTagVO.setId(dicTagPo.getId());
            dicTagVO.setName(dicTagPo.getName());
            dicTagVO.setValue(dicTagPo.getValue());
            dicTagVO.setSort(dicTagPo.getSort());
            dicTagVO.setEnabled(dicTagPo.isEnabled() ? 1 : 0);
            dicTagVO.setChildList(getChildList(dicTagPo.getId()));
            dicTagVoList.add(dicTagVO);
        }
        return dicTagVoList;
    }
}
