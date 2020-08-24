package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoAppVersionDO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionQry;
import com.landleaf.homeauto.center.device.model.vo.SelectedVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-24
 */
public interface IHomeAutoAppVersionService extends IService<HomeAutoAppVersionDO> {

    AppVersionDTO getCurrentVersion(Integer appType);

    /**
     * 分布查询
     * @param appVersionQry
     * @return
     */
    PageInfo<AppVersionDTO> queryAppVersionDTOList(AppVersionQry appVersionQry);

    /**
     * 获取单个版本信息
     * @param id
     * @return
     */
    AppVersionDTO queryAppVersionDTO(String id);

    /**
     * 新增版本信息
     * @param appVersionDTO
     */
    void saveAppVersion(AppVersionDTO appVersionDTO);

    /**
     * 根据id修改版本信息
     * @param appVersionDTO
     */
    void updateAppVersion(AppVersionDTO appVersionDTO);

    /**
     * 更改版本启用状态
     * @param id
     * @param enableFlag
     */
    void enableState(String id, Integer enableFlag);

    /**
     * 删除版本信息
     * @param id
     */
    void deleteAppVersion(String id);

    /**
     * app版本下拉框
     * @return
     */
    List<SelectedVO> getAppVersionsSelect();

    /**
     * 更新推送状态
     * @param id
     * @param pushStatus
     */
    void updatePushStatus(String id, Integer pushStatus);
}
