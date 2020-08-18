package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.common.domain.dto.address.AreaDTO;
import com.landleaf.homeauto.center.device.model.domain.address.HomeAutoArea;
import com.landleaf.homeauto.common.domain.vo.common.CascadeVo;

import java.util.List;

/**
 * <p>
 * 地址表 服务类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
public interface IAreaService extends IService<HomeAutoArea> {

    /**
     * 根据code获取区域列表
     * @param code
     * @return
     */
    List<AreaDTO> getAreaList(String code);

    /**
     * 根据行政区code获取子列表 只保留有业务的（0代表查询所有国家列表）
     * @param code
     * @return
     */
    List<AreaDTO> getListAreafilterProject(String code);
    /**
     * 获取地区路径path
     * @param code
     * @return
     */
    String getAreaPath(String code);

    /**
     * 获取地区path名称
     * @param code
     * @return
     */
    String getAreaPathName(String code);


    List<CascadeVo> cascadeList();
}
