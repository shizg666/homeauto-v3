package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.vo.*;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;

import java.util.List;

/**
 * <p>
 * 家庭表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IHomeAutoFamilyService extends IService<HomeAutoFamilyDO> {

    /**
     * 通过用户ID获取家庭列表
     *
     * @param userId 用户ID
     * @return 家庭列表
     */
    List<FamilyBO> getFamilyListByUserId(String userId);

    /**
     * 通过家庭ID获取城市天气
     *
     * @param familyId 家庭ID
     * @return 城市天气码
     */
    String getWeatherCodeByFamilyId(String familyId);

    /**
     * 通过终端的mac地址获取家庭信息
     *
     * @param mac      mac地址
     * @param terminal 终端类型
     * @return
     */
    FamilyInfoBO getFamilyInfoByTerminalMac(String mac, Integer terminal);

    /**
     * app我的-我的家庭列表查询
     *
     * @return
     */
    List<MyFamilyInfoVO> getListFamily();

    /**
     * 我的家庭-获取楼层房间设备信息
     *
     * @param familyId
     * @return
     */
    MyFamilyDetailInfoVO getMyFamilyInfo(String familyId);


    FamilyInfoForSobotDTO getFamilyInfoForSobotById(String familyId);


    void add(FamilyAddDTO request);

    void update(FamilyUpdateDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 查看单元下的家庭列表
     * @param id
     * @return
     */
    List<FamilyPageVO> getListByUnitId(String id);

    /**
     * 审核家庭
     * @param request
     */
    void review(FamilyOperateDTO request);

    /**
     * 交付家庭
     * @param request
     */
    void submit(FamilyOperateDTO request);

    FamilyDetailVO detail(String familyId);
}
