package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.bo.screen.ScreenFamilyBO;
import com.landleaf.homeauto.center.device.model.bo.screen.ScreenTemplateDeviceBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO;
import com.landleaf.homeauto.center.device.model.bo.screen.attr.sys.ScreenSysProductAttrBO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpApkVersionCheckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpFamilyBindDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.HomeAutoFaultDeviceCurrentDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO;
import com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusUpdateDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.request.ScreenHttpProjectHouseTypeDTO;
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
     * 大屏apk更新检测
     *
     * @param adapterHttpApkVersionCheckDTO
     * @return
     */
    ScreenHttpApkVersionCheckResponseDTO apkVersionCheck(AdapterHttpApkVersionCheckDTO adapterHttpApkVersionCheckDTO);

    /**
     * 获取家庭所在城市天气信息
     *
     * @param familyId 家庭id
     * @return
     */
    ScreenHttpWeatherResponseDTO getWeather(Long familyId);


    ScreenHttpWeatherResponseDTO getCityWeather(String city);

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
    List<ScreenHttpNewsResponseDTO> getNews(Long familyId);

    /**
     * 获取场景
     * @param houseTemplateId
     * @return
     */
    List<SyncSceneInfoDTO> getSceneList(Long houseTemplateId, Long familyId);

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
    List<ScreenHttpFloorRoomDeviceResponseDTO> getFloorRoomDeviceList(Long templateId,Long familyId);

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
    ScreenTemplateDeviceBO getFamilyDeviceBySn(Long houseTemplateId, Long familyId, String deviceSn);

    /**
     * 根据productCode获取所有属性
     * @param productCode 非系统产品
     * @return com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO
     * @author wenyilu
     * @date 2021/3/31 17:29
     */
    List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode);
    /**
     * 根据productCode获取所有属性
     * @param productCode
     * @param systemFlag   设备类型  0、1：非系统产品（普通设备、系统子设备）；2：系统产品（系统设备）
     * @return com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrCategoryBO
     * @author wenyilu
     * @date 2021/3/31 17:29
     */
    List<ScreenProductAttrCategoryBO> getDeviceAttrsByProductCode(String productCode,Integer systemFlag);

    /**
     * 通知大屏定时场景配置更新
     * @param familyId  家庭ID
     * @param typeEnum  通知类型
     * @return void
     * @author wenyilu
     * @date  2021/1/7 9:31
     */
    void notifySceneTimingConfigUpdate(Long familyId, ContactScreenConfigUpdateTypeEnum typeEnum);

    /**
     * @param: productCode
     * @description: 获取非系统产品（普通设备、系统子设备）属性
     * @return: java.util.List<com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO>
     * @author: wyl
     * @date: 2021/5/26
     */
    List<ScreenProductAttrBO> getDeviceFunctionAttrsByProductCode(String productCode);
    /**
     * @param: productCode  系统产品Code
     * @description: 获取系统产品相关属性
     * @return: java.util.List<com.landleaf.homeauto.center.device.model.bo.screen.attr.ScreenProductAttrBO>
     * @author: wyl
     * @date: 2021/5/26
     */
    List<ScreenSysProductAttrBO> getSysDeviceFunctionAttrsByProductCode(String productCode);

    /**
     * 大屏绑定家庭
     * @param adapterHttpFamilyBindDTO
     */
    void bindFamily(AdapterHttpFamilyBindDTO adapterHttpFamilyBindDTO);

    /**
     * @param: deviceId
     * @description: 获取设备状态信息（是否暖通故障、是否数值异常、是否在线）
     * @return: com.landleaf.homeauto.common.domain.dto.device.status.ScreenDeviceInfoStatusDTO
     * @author: wyl
     * @date: 2021/6/1
     */
    ScreenDeviceInfoStatusDTO getFamilyDeviceInfoStatus(Long familyId, Long deviceId);

    /**
     * 删除家庭设备状态信息缓存
     * @param familyId 家庭id
     */
    void delFamilyDeviceInfoStatusCache(Long familyId);


    void storeOrUpdateDeviceInfoStatus(ScreenDeviceInfoStatusUpdateDTO param);

    void removeCurrentFaultValue(Long familyId, Long deviceId, String code, int type);

    long countCurrentFault(Long familyId, Long deviceId,  int type);

    void storeOrUpdateCurrentFaultValue(HomeAutoFaultDeviceCurrentDTO deviceCurrentDTO);

    /**
     * 根据项目id获取模板及家庭信息
     * @param projectId 项目id
     * @return 模板楼层信息
     */
    List<ScreenFamilyModelResponseDTO> getProjectTemplates(Long projectId);

    /**
     * 获取模板全局配置
     * @param dto 项目id及模板名称
     * @return 全局配置
     */
    ScreenHttpFloorRoomDeviceSceneResponseDTO getTemplateConfig(ScreenHttpProjectHouseTypeDTO dto);

}
