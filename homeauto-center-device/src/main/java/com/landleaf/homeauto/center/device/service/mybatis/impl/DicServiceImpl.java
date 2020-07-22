package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.mapper.DicMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IDicService;
import com.landleaf.homeauto.common.domain.Pagination;
import com.landleaf.homeauto.common.domain.dto.dic.DicDTO;
import com.landleaf.homeauto.common.domain.dto.dic.DicQueryDTO;
import com.landleaf.homeauto.common.domain.po.device.DicPO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.dic.DicVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
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

    private static final String TAG_ADMIN = "admin";

    private static final Character BOOLEAN_TRUE = '1';

    @Override
    public Integer addDic(DicDTO dicDto) {
        DicPO dicPo = new DicPO();
        BeanUtils.copyProperties(dicDto, dicPo);
        dicPo.setEnabled(true);
        dicPo.setCreateTime(LocalDateTime.now());
        dicPo.setUpdateTime(LocalDateTime.now());
        save(dicPo);
        return dicPo.getId();
    }

    @Override
    public Object getDicList(DicQueryDTO dicQueryDTO) {
        QueryWrapper<DicPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("id", "dic_group");
        queryWrapper.orderByAsc("id", "dic_order");
        Pagination pagination = dicQueryDTO.getPagination();
        if (!Objects.isNull(pagination)) {
            // 如果分页信息不为空，则分页查询
            PageHelper.startPage(pagination.getPageNum(), pagination.getPageSize());
        }

        if (!StringUtils.isEmpty(dicQueryDTO.getName())) {
            // 根据名称模糊查询
            queryWrapper.like("dic_name", dicQueryDTO.getName());
        }

        if (!StringUtils.isEmpty(dicQueryDTO.getGroupCode())) {
            // 根据字典组查询
            queryWrapper.eq("dic_group", dicQueryDTO.getGroupCode());
        } else if (!StringUtils.isEmpty(dicQueryDTO.getParentGroupCode())) {
            // 根据父级字典组查询
            queryWrapper.eq("dic_parent_group", dicQueryDTO.getGroupCode());
        }

        if (!StringUtils.isEmpty(dicQueryDTO.getCode())) {
            // 根据字典码查询
            queryWrapper.eq("dic_code", dicQueryDTO.getCode());
        } else if (!StringUtils.isEmpty(dicQueryDTO.getParentCode())) {
            // 根据父级字典码查询
            queryWrapper.eq("dic_parent_code", dicQueryDTO.getParentCode());
        }

        if (!Objects.equals(TAG_ADMIN, dicQueryDTO.getTag())) {
            // 如果不是管理员，只能获取到未禁用的字典
            queryWrapper.eq("is_enabled", BOOLEAN_TRUE);
        }
        List<DicPO> dicPoList = list(queryWrapper);
        List<DicVO> dicVoList = copyProperties(dicPoList);

        if (!Objects.isNull(pagination)) {
            PageInfo<DicPO> dicPoPageInfo = new PageInfo<>(dicPoList);
            PageInfo<DicVO> dicVoPageInfo = new PageInfo<>(dicVoList);
            dicVoPageInfo.setPages(dicPoPageInfo.getPages());
            dicVoPageInfo.setTotal(dicPoPageInfo.getTotal());
            return new BasePageVO<>(dicVoPageInfo);
        }
        return dicVoList;
    }

    /**
     * PO对象转换为VO对象
     *
     * @param dicPoList 查询出来的数据库对象
     * @return 转化完的视图层对象
     */
    private List<DicVO> copyProperties(List<DicPO> dicPoList) {
        List<DicVO> dicVoList = new LinkedList<>();
        for (DicPO dicPo : dicPoList) {
            DicVO dicVo = new DicVO();
            BeanUtils.copyProperties(dicPo, dicVo);
            dicVoList.add(dicVo);
        }
        return dicVoList;
    }

    @Override
    public void updateDic(Integer id, DicDTO dicDto) {
        DicPO dicPo = new DicPO();
        BeanUtils.copyProperties(dicDto, dicPo);
        dicPo.setId(id);
        dicPo.setUpdateTime(LocalDateTime.now());
        updateById(dicPo);
    }

    @Override
    public void enableDic(Integer id) {
        DicPO dicPo = new DicPO();
        dicPo.setId(id);
        dicPo.setEnabled(true);
        updateById(dicPo);
    }

    @Override
    public void disableDic(Integer id) {
        DicPO dicPo = new DicPO();
        dicPo.setId(id);
        dicPo.setEnabled(false);
        updateById(dicPo);
    }

}
