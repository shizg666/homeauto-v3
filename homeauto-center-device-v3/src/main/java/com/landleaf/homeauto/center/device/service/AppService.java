package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.TimingSceneDTO;
import com.landleaf.homeauto.center.device.model.dto.appversion.AppVersionDTO;
import com.landleaf.homeauto.center.device.model.dto.maintenance.FamilyMaintenanceAddRequestDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgReadNoteDTO;
import com.landleaf.homeauto.center.device.model.smart.vo.*;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.error.AlarmMessageRecordVO;
import com.landleaf.homeauto.center.device.model.vo.family.FamilyUserOperateDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluseAddDTO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamiluserDeleteVO;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO;
import com.landleaf.homeauto.center.device.model.vo.scene.SceneTimingDetailVO;
import com.landleaf.homeauto.center.device.model.vo.scene.family.PicVO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;

import java.util.List;
import java.util.Map;

/**
 * @className: AppService
 * @description: TODO 类描述
 * @author: wenyilu
 * @date: 2021/6/4
 **/
public interface AppService {
    /**
     * APP获取用户家庭列表及当前家庭
     *
     * @param userId  用户ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilySelectVO
     * @author wenyilu
     * @date 2020/12/28 15:59
     */
    FamilySelectVO getUserFamily4VO(String userId);
    /**
     *  切換家庭
     *  获取APP首页信息（天气、常用设备、常用场景）
     * @param userId     用戶ID
     * @param familyId   家庭ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilyCheckoutVO
     * @author wenyilu
     * @date 2021/1/12 10:04
     */
    FamilyCheckoutVO switchFamily(String userId, Long familyId);

    /**
     * APP获取楼层及所属房间信息
     *
     * @param familyId  家庭ID
     * @param deviceFilterFlag  设备包含过滤
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyFloorVO>
     * @author wenyilu
     * @date 2020/12/25 13:24
     */
    List<FamilyFloorVO> getFamilyFloor4VO(Long familyId, Integer deviceFilterFlag);

    /**
     * APP获取房间下所有设备
     *
     * @param familyId   家庭ID
     * @param roomId     房间ID
     * @param systemFlag 是否系统设备(1:是，0:否)
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilyDeviceVO>
     * @author wenyilu
     * @date 2021/1/6 9:29
     */
    List<FamilyDeviceSimpleVO> getFamilyDevices4VO(Long familyId, Long roomId, Integer systemFlag);

    /**
     * APP获取我的家庭家庭列表統計信息
     *获取家庭的统计信息：房间数、设备数、用户数
     * @param userId  用户ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO>
     * @author wenyilu
     * @date 2020/12/28 16:23
     */
    List<MyFamilyInfoVO> getMyFamily4VO(String userId);

    /**
     * APP我的家庭-获取楼层房间设备信息
     *获取某个家庭详情：楼层、房间、设备、用户信息等
     * @param familyId  家庭ID
     * @return com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO
     * @author wenyilu
     * @date 2020/12/25 15:00
     */
    MyFamilyDetailInfoVO getMyFamilyInfo4VO(Long familyId);

    /**
     * APP移除家庭成员
     * @param familuserDeleteVO 移除成员信息
     * @return void
     * @author wenyilu
     * @date  2020/12/28 17:07
     */
    void deleteFamilyMember(FamiluserDeleteVO familuserDeleteVO);
    /**
     *  退出家庭
     * @param familyId  家庭ID
     * @param userId    用户ID
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:30
     */
    void quitFamily(Long familyId,String userId);

    /**
     * 扫码绑定家庭（渠道app/大屏）
     * @param familyId       type:familyId/家庭编号
     * @param userId         用户ID
     * @return void
     * @author wenyilu
     * @date  2021/1/6 10:32
     */
    void addFamilyMember(String familyId,String userId);
    /**
     *  绑定家庭
     * @param familuseAddDTO   家庭ID/用户类型
     * @param userId            用户ID
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:33
     */
    void addFamilyMember(FamiluseAddDTO familuseAddDTO, String userId);
    /**
     *  通过APP设置管理员
     * @param familyUserOperateDTO  家庭Id、记录Id
     * @return void
     * @author wenyilu
     * @date 2021/1/12 11:40
     */
    void settingAdmin(FamilyUserOperateDTO familyUserOperateDTO);

    /**
     * app 修改家庭名称
     *
     * @param request
     */
    void updateFamilyName(FamilyUpdateVO request);

    /**
     * 获取家庭设备属性状态
     *
     * @param familyId  家庭ID
     * @param deviceId  设备ID
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @author wenyilu
     * @date 2021/1/6 13:49
     */
    Map<String, Object> getDeviceStatus4VO(Long familyId, Long deviceId);
    /**
     * @param: familyId  家庭ID
     * @description: 获取家庭系统运行状态
     * @return: java.util.Map<java.lang.String,java.lang.Object>
     * @author: wyl
     * @date: 2021/5/26
     */
    Map<String, Object> getSystemStatusVO(Long familyId);


    /**
     *  读取设备状态
     * @param familyId  家庭ID
     * @param deviceId   设备ID
     * @return void
     * @author wenyilu
     * @date 2021/2/4 16:26
     */
    AdapterDeviceStatusReadAckDTO readDeviceStatus(Long familyId, Long deviceId);

    /**
     *  App获取家庭消息列表
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeAppDTO>
     * @author wenyilu
     * @date 2021/1/12 13:27
     */
    List<MsgNoticeAppDTO> getMsgList4VO(Long familyId);

    /**
     *  添加消息已读记录
     * @param msgReadNoteDTO  消息
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:29
     */
    void addReadNote(MsgReadNoteDTO msgReadNoteDTO);

    /**
     *  APP保存常用场景
     * @param familyId  家庭ID
     * @param sceneIds 场景Ids
     * @return void
     * @author wenyilu
     * @date 2021/1/12 13:30
     */
    void saveCommonSceneList(Long familyId, List<Long> sceneIds);
    /**
     * APP获取不常用场景
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO>
     * @author wenyilu
     * @date  2020/12/25 17:04
     */
    List<FamilySceneVO> getFamilyUncommonScenes4VOByFamilyId(Long familyId);

    /**
     * APP下发场景
     *
     * @param sceneId    场景ID
     * @param familyId   家庭ID
     * @return void
     * @author wenyilu
     * @date 2021/1/6 15:26
     */
    void executeScene(Long sceneId, Long familyId);

    /**获取场景图片集合
     * @param
     * @return
     */
    List<PicVO> getListScenePic();

    /**
     * APP获取场景定时列表
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO>
     * @author wenyilu
     * @date  2020/12/28 10:41
     */
    List<FamilySceneTimingVO> getTimingSceneList(Long familyId);

    /**
     * APP查看场景定时记录详情
     * @param timingId  场景定时记录ID
     * @return com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneTimingVO
     * @author wenyilu
     * @date  2020/12/28 10:48
     */
    SceneTimingDetailVO getTimingSceneDetail(Long timingId);

    /**
     * APP添加场景定时记录
     * @param timingSceneDTO
     * @return boolean
     * @author wenyilu
     * @date  2020/12/28 10:56
     */
    boolean saveTimingScene(TimingSceneDTO timingSceneDTO);

    /**
     * 通知大屏定时场景配置更新
     * @param familyId  家庭ID
     * @param typeEnum  通知类型
     * @return void
     * @author wenyilu
     * @date  2021/1/7 9:31
     */
    void notifySceneTimingConfigUpdate(Long familyId, ContactScreenConfigUpdateTypeEnum typeEnum);

    void deleteFamilySceneTiming(Long timingSceneId);

    void enableSceneTiming(Long sceneTimingId);

    /**
     * 查询家庭下场景
     *
     * @param familyId  家庭ID
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.vo.FamilySceneVO>
     * @author wenyilu
     * @date 2021/1/6 15:54
     */
    List<FamilySceneVO> listWholeHouseScene(Long familyId);

    List<AlarmMessageRecordVO> getAlarmlistByDeviceId(String deviceId, String familyId);


    AppVersionDTO getCurrentVersion(Integer appType, String belongApp);

    /*
     * @param: requestDTO
     * @description: 新增
     * @return: void
     * @author: wyl
     * @date: 2021/5/25
     */
    void addMaintenanceRecord(FamilyMaintenanceAddRequestDTO requestDTO);

    List<FamilyMaintenanceRecordVO> listMaintenanceRecords(Long familyId);

    /*
     * @param: id 维保记录主键ID
     * @description: 获取详情
     * @return: com.landleaf.homeauto.center.device.model.vo.maintenance.FamilyMaintenanceRecordVO
     * @author: wyl
     * @date: 2021/5/25
     */
    FamilyMaintenanceRecordVO getMaintenanceDetail(Long id);

    /**
     * APP下发指令
     *
     * @param deviceCommandDTO  设备控制数据传输对象
     * @return void
     * @author wenyilu
     * @date 2021/1/6 15:03
     */
    void sendCommand(DeviceCommandDTO deviceCommandDTO);
}
