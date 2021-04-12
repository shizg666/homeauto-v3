package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;

import java.util.List;

/**
 * 处理大屏请求业务类
 *
 * @author wenyilu
 */
public interface IContactScreenService {



    /**
     * 获取家庭所在城市天气信息
     *
     * @param familyId 家庭id
     * @return
     */
    ScreenHttpWeatherResponseDTO getWeather(String familyId);

    /**
     * 获取家庭的场景定时配置
     *
     * @param familyId 家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> getTimingSceneList(Long familyId);

    /**
     * 家庭定时场景批量删除，返回现有定时场景列表
     * @param timingIds      定时场景id集合
     * @param familyId       家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> deleteTimingScene(List<Long> timingIds, Long familyId);

    /**
     * 新增或修改家庭定时场景配置
     * @param dtos      数据
     * @param familyId  家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> saveOrUpdateTimingScene(List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos, Long familyId);


    /**
     * 获取家庭下消息公告
     * @param familyId
     * @return
     */
    List<ScreenHttpNewsResponseDTO> getNews(String familyId);

    /**
     * 获取场景
     * @param houseTemplateId
     * @return
     */
    List<SyncSceneInfoDTO> getSceneList(String houseTemplateId);

    /**
     * 节假日判定
     * @param date
     * @return
     */
    ScreenHttpHolidaysCheckResponseDTO holidayCheck(String date);

    /**
     * 获取楼层房间设备信息
     *
     * @param templateId 戶型ID
     * @return
     */
    List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(String templateId);

    /**
     * 获取在线大屏数目
     */
    int getOnlineScreenNum();

    /**
     * 根据终端类型及终端值获取家庭BO
     *
     * @param mac  mac地址
     * @return com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO
     * @author wenyilu
     * @date 2020/12/28 16:20
     */
    ScreenFamilyBO getFamilyInfoByTerminalMac(String mac);;

    /**
     *  获取家庭下某个设备
     *
     * @param houseTemplateId  户型ID
     * @param familyId  家庭ID
     * @param deviceSn  设备号
     * @return com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO
     * @author wenyilu
     * @date 2021/3/31 15:08
     */
    ScreenTemplateDeviceBO getFamilyDeviceBySn(String houseTemplateId, String familyId, String deviceSn);

    /**
     * 根据productCode获取所有属性
     * @param productCode
     * @return com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO
     * @author wenyilu
     * @date 2021/3/31 17:29
     */
    List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode);

    /**
     * 通知大屏定时场景配置更新
     * @param familyId  家庭ID
     * @param typeEnum  通知类型
     * @return void
     * @author wenyilu
     * @date  2021/1/7 9:31
     */
    void notifySceneTimingConfigUpdate(String familyId, ContactScreenConfigUpdateTypeEnum typeEnum);
}
