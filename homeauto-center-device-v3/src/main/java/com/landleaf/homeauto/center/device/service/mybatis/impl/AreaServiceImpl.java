package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoArea;
import com.landleaf.homeauto.center.device.model.mapper.AreaMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IAreaService;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.dto.address.AreaDTO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, HomeAutoArea> implements IAreaService {

    @Autowired
    private RedisUtils redisUtils;


    @Override
    public List<AreaDTO> getAreaList(String code) {
        QueryWrapper<HomeAutoArea> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(HomeAutoArea::getParentCode, code);
        List<AreaDTO> areaVOS = Lists.newArrayList();
        List<HomeAutoArea> areaList = list(queryWrapper);
        for (HomeAutoArea area : areaList) {
            AreaDTO areaVO = new AreaDTO();
            areaVO.setLabel(area.getName());
            areaVO.setValue(area.getCode());
            areaVOS.add(areaVO);
        }
        return areaVOS;
    }

    @Override
    public List<AreaDTO> getListAreafilterProject(String code) {
        int type = 0;
        if (!(String.valueOf(type).equals(code))) {
            HomeAutoArea smarthomeArea = getOne(new LambdaQueryWrapper<HomeAutoArea>().eq(HomeAutoArea::getCode, code).select(HomeAutoArea::getType));
            type = Integer.valueOf(smarthomeArea.getType()) + 1;
        }
        List<AreaDTO> data = this.baseMapper.getAreafilterProject(code, type);
        return data;
    }

    @Override
    public String getAreaPath(String code) {
        return this.baseMapper.getAreaPath(code);
    }

    @Override
    public String getAreaPathName(String code) {
        return this.baseMapper.getAreaPathName(code);
    }

    @Override
    public List<CascadeVo> cascadeList() {
        String str = (String) redisUtils.get(RedisCacheConst.AREA_CASCADE_INFO);
        if (StringUtil.isEmpty(str)) {
            List<CascadeVo> result = this.baseMapper.cascadeList();
            redisUtils.set(RedisCacheConst.AREA_CASCADE_INFO, JSON.toJSONString(result));
            return result;
        }
        List<CascadeVo> cascadeVos = JSON.parseArray(str, CascadeVo.class);
        return cascadeVos;
    }

    @Override
    public HomeAutoArea getByCode(String code) {
        QueryWrapper<HomeAutoArea> homeAutoAreaQueryWrapper = new QueryWrapper<>();
        homeAutoAreaQueryWrapper.eq("code", code);
        return getOne(homeAutoAreaQueryWrapper);
    }

}
