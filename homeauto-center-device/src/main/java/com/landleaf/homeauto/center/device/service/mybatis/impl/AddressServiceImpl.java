package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.mapper.AddressMapper;
import com.landleaf.homeauto.center.device.service.mybatis.AddressService;
import com.landleaf.homeauto.common.domain.po.address.HomeAutoAddress;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * <p>
 * 地址表 服务实现类
 * </p>
 *
 * @author shizg
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, HomeAutoAddress> implements AddressService {

    @Override
    public List<CascadeVo> cascadeList(List<String> paths) {
        Map<String, String> data = new HashMap<>();
        Map<String, Set<String>> provices = new HashMap<>();
        Map<String, Set<String>> cities = new HashMap<>();
        Map<String, Set<String>> areas = new HashMap<>();
        Map<String, List<CascadeVo>> roads = new HashMap<>();
        QueryWrapper<HomeAutoAddress> queryWrapper = new QueryWrapper<>();

        if (!CollectionUtils.isEmpty(paths)){
            paths.forEach(o->{
                queryWrapper.or().like("path",o);
            });
        }
        List<HomeAutoAddress> all = list(queryWrapper);
        all.forEach(v -> {
            data.put(v.getCountryCode(),v.getCountry());
            data.put(v.getProvinceCode(),v.getProvince());
            data.put(v.getCityCode(),v.getCity());
            data.put(v.getAreaCode(),v.getArea());
            Set<String> proviceSet = provices.get(v.getCountryCode());
            if (proviceSet == null) {
                proviceSet = new HashSet<>();
                provices.put(v.getCountryCode(), proviceSet);
            }
            proviceSet.add(v.getProvinceCode());
            Set<String> citySet = cities.get(v.getProvinceCode());
            if (citySet == null) {
                citySet = new HashSet<>();
                cities.put(v.getProvinceCode(), citySet);
            }
            citySet.add(v.getCityCode());

            Set<String> areaSet = areas.get(v.getCityCode());
            if (areaSet == null) {
                areaSet = new HashSet<>();
                areas.put(v.getCityCode(), areaSet);
            }
            areaSet.add(v.getAreaCode());

            List<CascadeVo> roadList = roads.get(v.getAreaCode());
            if (roadList == null) {
                roadList = new ArrayList<>();
                roads.put(v.getAreaCode(), roadList);
            }
            roadList.add(new CascadeVo(v.getRoad(),v.getId()));
        });

        List<CascadeVo> vos = new ArrayList<>();
        provices.forEach((countryCode, proviceList) -> {
            List<CascadeVo> provinceVos = new ArrayList<>();
            proviceList.forEach(proviceCode -> {
                List<CascadeVo> cityVos = new ArrayList<>();
                Set<String> citySet = cities.get(proviceCode);
                citySet.forEach(cityCode -> {
                    List<CascadeVo> areaVos = new ArrayList<>();
                    Set<String> areaSet = areas.get(cityCode);
                    areaSet.forEach(areaCode -> {
                        areaVos.add(new CascadeVo(data.get(areaCode),areaCode, roads.get(areaCode)));
                    });
                    cityVos.add(new CascadeVo(data.get(cityCode),cityCode, areaVos));
                });
                provinceVos.add(new CascadeVo(data.get(proviceCode),proviceCode, cityVos));
            });

            vos.add(new CascadeVo(data.get(countryCode),countryCode, provinceVos));
        });

        return vos;
    }


}
