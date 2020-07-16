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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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
    public BasePageVO<DicVO> getDicList(String name, String tag, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<DicPO> queryWrapper = new QueryWrapper<>();
        if (name != null && !"".equals(name)) {
            // 根据名称模糊查询
            queryWrapper.like("dic_name", name);
        }
        if (!Objects.equals(TAG_ADMIN, tag)) {
            // 如果不是管理员，只能获取到未禁用的字典
            queryWrapper.eq("is_enabled", '1');
        }
        queryWrapper.orderByAsc("dic_order");
        List<DicPO> dicPoList = list(queryWrapper);
        PageInfo<DicPO> dicPoPageInfo = new PageInfo<>(dicPoList);
        PageInfo<DicVO> dicVoPageInfo = new PageInfo<>(copyProperties(dicPoList));
        dicVoPageInfo.setPages(dicPoPageInfo.getPages());
        dicVoPageInfo.setTotal(dicPoPageInfo.getTotal());
        return new BasePageVO<>(dicVoPageInfo);
    }

    @Override
    public List<DicVO> getChildDicList(String dicCode) {
        QueryWrapper<DicPO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dic_parent", dicCode);
        queryWrapper.eq("is_enabled", '1');
        List<DicPO> dicPoList = list(queryWrapper);
        return copyProperties(dicPoList);
    }

    /**
     * PO对象转换为VO对象
     *
     * @param dicPoList
     * @return
     */
    private List<DicVO> copyProperties(List<DicPO> dicPoList) {
        List<DicVO> dicVoList = new LinkedList<>();
        for (DicPO dicPo : dicPoList) {
            DicVO dicVo = new DicVO();
            BeanUtils.copyProperties(dicPo, dicVo);
            dicVo.setValue(parseValue(dicPo.getValueType(), dicPo.getValue()));
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

    /**
     * 转化数据
     *
     * @param type  数据类型
     * @param value 数据值
     * @return 返回给前端的对象
     */
    private Object parseValue(String type, String value) {
        if (!"num".equals(type)) {
            // 非数值类型返回数组
            return value.split(",");
        }
        return value;
    }
}
