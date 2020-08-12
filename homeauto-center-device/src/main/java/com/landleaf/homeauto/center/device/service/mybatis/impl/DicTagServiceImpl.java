package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.mapper.DicTagMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDicTagService;
import com.landleaf.homeauto.common.domain.dto.device.DicTagDTO;
import com.landleaf.homeauto.common.domain.dto.device.DicTagQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicTagPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagForAppVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicTagVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author Yujiumin
 * @version 2020/7/31
 */
@Service
public class DicTagServiceImpl extends ServiceImpl<DicTagMapper, DicTagPO> implements IDicTagService {

    @Override
    public String addDicTag(DicTagDTO dicTagDTO) {
        checkDicId(dicTagDTO.getDicId());
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setName(dicTagDTO.getName());
        dicTagPo.setValue(dicTagDTO.getValue());
        dicTagPo.setSort(dicTagDTO.getSort());
        dicTagPo.setEnabled(Objects.equals(dicTagDTO.getEnabled(), 1));
        dicTagPo.setParent(dicTagDTO.getParent());
        dicTagPo.setDicCode(dicTagDTO.getDicCode());
        dicTagPo.setDicId(dicTagDTO.getDicId());
        save(dicTagPo);
        return dicTagPo.getId();
    }

    @Override
    public void enable(String id) {
        UpdateWrapper<DicTagPO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("enabled", '1');
        updateWrapper.eq("id", id);
        updateWrapper.or();
        updateWrapper.eq("parent", id);
        update(updateWrapper);
    }

    @Override
    public void disable(String id) {
        UpdateWrapper<DicTagPO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("enabled", '0');
        updateWrapper.eq("id", id);
        updateWrapper.or();
        updateWrapper.eq("parent", id);
        update(updateWrapper);
    }

    @Override
    public void update(DicTagDTO dicTagDTO) {
        DicTagPO dicTagPo = new DicTagPO();
        dicTagPo.setId(dicTagDTO.getId());
        dicTagPo.setName(dicTagDTO.getName());
        dicTagPo.setValue(dicTagDTO.getValue());
        dicTagPo.setSort(dicTagDTO.getSort());
        dicTagPo.setEnabled(Objects.equals(dicTagDTO.getEnabled(), 1));
        updateById(dicTagPo);
    }

    @Override
    public BasePageVO<DicTagVO> getDicTagList(DicTagQueryDTO dicTagQueryDTO) {
        // 1. 检查条件
        checkDicId(dicTagQueryDTO.getDicId());
        BasePageVO<DicTagVO> basePageVO = new BasePageVO<>();
        // 2. 分页
        PageHelper.startPage(dicTagQueryDTO.getPageNum(), dicTagQueryDTO.getPageSize());
        // 3. 构建查询条件
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        //// 3.1 根据字典ID查询字典标签
        queryWrapper.eq("dic_id", dicTagQueryDTO.getDicId());
        if (!StringUtils.isEmpty(dicTagQueryDTO.getName())) {
            //// 3.3-1 如果搜索了名称,则按名称模糊查询
            queryWrapper.like("name", dicTagQueryDTO.getName());
        } else {
            ////3.3-2 如果不带名称,则直接从根节点开始查
            queryWrapper.isNull("parent");
        }
        queryWrapper.orderByAsc("sort");
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        List<DicTagVO> dicTagVoList = copyProperties(dicTagPoList);

        PageInfo<DicTagPO> dicTagPoPageInfo = new PageInfo<>(dicTagPoList);
        basePageVO.setList(dicTagVoList);
        basePageVO.setPages(dicTagPoPageInfo.getPages());
        basePageVO.setTotal(dicTagPoPageInfo.getTotal());
        return basePageVO;
    }

    @Override
    public List<DicTagForAppVO> getDicTagList(String dicId) {
        // 1. 检查参数
        checkDicId(dicId);
        // 2. 构建查询条件
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dic_id", dicId);
        queryWrapper.eq("enabled", '1');
        queryWrapper.isNull("parent");
        queryWrapper.orderByAsc("sort");
        // 3. 查询
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        // 4. 值拷贝
        return copyPropertiesForApp(dicTagPoList);
    }


    /**
     * 递归查询子级
     *
     * @param id 主键
     * @return 标签集合
     */
    private List<DicTagVO> getChildList(String id) {
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", id).orderByAsc("sort");
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        return copyProperties(dicTagPoList);
    }

    /**
     * 将PO对象转换为VO对象
     *
     * @param dicTagPoList
     * @return
     */
    private List<DicTagVO> copyProperties(List<DicTagPO> dicTagPoList) {
        List<DicTagVO> dicTagVoList = new LinkedList<>();
        for (DicTagPO dicTagPo : dicTagPoList) {
            DicTagVO dicTagVO = new DicTagVO();
            dicTagVO.setId(dicTagPo.getId());
            dicTagVO.setSort(dicTagPo.getSort());
            dicTagVO.setEnabled(dicTagPo.getEnabled() ? 1 : 0);
            dicTagVO.setName(dicTagPo.getName());
            dicTagVO.setValue(dicTagPo.getValue());
            dicTagVO.setChildList(getChildList(dicTagPo.getId()));
            dicTagVoList.add(dicTagVO);
        }
        return dicTagVoList;
    }

    private List<DicTagForAppVO> getChildListForApp(String id) {
        QueryWrapper<DicTagPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent", id);
        queryWrapper.eq("enabled", '1');
        queryWrapper.orderByAsc("sort");
        List<DicTagPO> dicTagPoList = list(queryWrapper);
        return copyPropertiesForApp(dicTagPoList);
    }

    /**
     * 将PO对象转换为VO对象
     *
     * @param dicTagPoList
     * @return
     */
    private List<DicTagForAppVO> copyPropertiesForApp(List<DicTagPO> dicTagPoList) {
        List<DicTagForAppVO> dicTagForAppVoS = new LinkedList<>();
        for (DicTagPO dicTagPo : dicTagPoList) {
            DicTagForAppVO dicTagVO = new DicTagForAppVO();
            dicTagVO.setLabel(dicTagPo.getName());
            dicTagVO.setValue(dicTagPo.getValue());
            dicTagVO.setChildren(getChildListForApp(dicTagPo.getId()));
            dicTagForAppVoS.add(dicTagVO);
        }
        return dicTagForAppVoS;
    }

    /**
     * 检查dicCode字段是否为空
     *
     * @param dicId 字典码
     */
    private void checkDicId(String dicId) {
        if (StringUtils.isEmpty(dicId)) {
            throw new NullPointerException("dicId字段不可为空");
        }
    }
}
