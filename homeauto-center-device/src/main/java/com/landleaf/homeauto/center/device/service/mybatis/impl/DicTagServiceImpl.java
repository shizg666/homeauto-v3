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
import org.springframework.stereotype.Service;

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
    public String addDicTag(DicTagDTO dicTagDTO) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setName(dicTagDTO.getName());
        dicTagPo.setValue(dicTagDTO.getValue());
        dicTagPo.setSort(dicTagDTO.getSort());
        dicTagPo.setEnabled(dicTagDTO.getEnabled());
        dicTagPo.setParent(dicTagDTO.getParent());
        dicTagPo.setDicCode(dicTagDTO.getDicCode());
        dicTagPo.setCreateUser(dicTagDTO.getOperator());
        dicTagPo.setUpdateUser(dicTagDTO.getOperator());
        save(dicTagPo);
        return dicTagPo.getId();
    }

    @Override
    public void enable(String id) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(id);
        dicTagPo.setEnabled(true);
        updateById(dicTagPo);
    }

    @Override
    public void disable(String id) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(id);
        dicTagPo.setEnabled(false);
        updateById(dicTagPo);
    }

    @Override
    public void update(DicTagDTO dicTagDTO) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(dicTagDTO.getId());
        dicTagPo.setName(dicTagDTO.getName());
        dicTagPo.setValue(dicTagDTO.getValue());
        dicTagPo.setSort(dicTagDTO.getSort());
        dicTagPo.setEnabled(dicTagDTO.getEnabled());
        dicTagPo.setCreateUser(dicTagDTO.getOperator());
        updateById(dicTagPo);
    }

    @Override
    public BasePageVO<DicTagVO> getDicTagList(DicTagQueryDTO dicTagQueryDTO) {
        BasePageVO<DicTagVO> basePageVO = new BasePageVO<>();
        // 1. 分页
        PageHelper.startPage(dicTagQueryDTO.getPageNum(), dicTagQueryDTO.getPageSize());
        // 2. 构建查询条件
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dic_code", dicTagQueryDTO.getDicCode());

        if (!Objects.isNull(dicTagQueryDTO.getName())) {
            queryWrapper.like("name", dicTagQueryDTO.getName());
        } else {
            queryWrapper.isNull("parent");
        }
        if (!dicTagQueryDTO.getIsAdmin()) {
            queryWrapper.eq("enabled", '1');
        }
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        List<DicTagVO> dicTagVoList = new LinkedList<>();
        for (DicTagPO dicTagPo : dicTagPoList) {
            DicTagVO dicTagVO = new DicTagVO();
            dicTagVO.setId(dicTagPo.getId());
            dicTagVO.setName(dicTagPo.getName());
            dicTagVO.setValue(dicTagPo.getValue());
            dicTagVO.setSort(dicTagPo.getSort());
            dicTagVO.setEnabled(dicTagPo.isEnabled());
            dicTagVO.setChildList(getChildList(dicTagPo.getId(), dicTagQueryDTO.getIsAdmin()));
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
     * @param id      主键
     * @param isAdmin 是否为管理员
     * @return 标签集合
     */
    private List<DicTagVO> getChildList(String id, boolean isAdmin) {
        List<DicTagVO> dicTagVoList = new LinkedList<>();
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", id);
        if (!isAdmin) {
            queryWrapper.eq("enabled", '1');
        }
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        for (DicTagPO dicTagPo : dicTagPoList) {
            DicTagVO dicTagVO = new DicTagVO();
            dicTagVO.setId(dicTagPo.getId());
            dicTagVO.setName(dicTagPo.getName());
            dicTagVO.setValue(dicTagPo.getValue());
            dicTagVO.setSort(dicTagPo.getSort());
            dicTagVO.setEnabled(dicTagPo.isEnabled());
            dicTagVO.setChildList(getChildList(dicTagPo.getId(), isAdmin));
            dicTagVoList.add(dicTagVO);
        }
        return dicTagVoList;
    }
}
