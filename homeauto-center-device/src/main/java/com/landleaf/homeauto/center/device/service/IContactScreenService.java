package com.landleaf.homeauto.center.device.service;

import com.landleaf.homeauto.common.domain.dto.adapter.http.AdapterHttpSaveOrUpdateTimingSceneDTO;
import com.landleaf.homeauto.common.domain.dto.screen.http.response.*;
import com.landleaf.homeauto.common.domain.dto.sync.SyncSceneInfoDTO;

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
    List<ScreenHttpTimingSceneResponseDTO> getTimingSceneList(String familyId);

    /**
     * 家庭定时场景批量删除，返回现有定时场景列表
     * @param timingIds      定时场景id集合
     * @param familyId       家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> deleteTimingScene(List<String> timingIds, String familyId);

    /**
     * 新增或修改家庭定时场景配置
     * @param dtos      数据
     * @param familyId  家庭id
     * @return
     */
    List<ScreenHttpTimingSceneResponseDTO> saveOrUpdateTimingScene(List<AdapterHttpSaveOrUpdateTimingSceneDTO> dtos, String familyId);

    /**
     * 更新家庭终端的上下线状态
     * @param familyId     家庭id
     * @param terminalMac  终端mac
     * @param status       在线状态
     */
    void updateTerminalOnLineStatus(String familyId, String terminalMac, Integer status);

    /**
     * 获取家庭下消息公告
     * @param familyId
     * @return
     */
    List<ScreenHttpNewsResponseDTO> getNews(String familyId);

    /**
     * 获取场景
     * @param familyId
     * @return
     */
    List<SyncSceneInfoDTO> getSceneList(String familyId);

    /**
     * 节假日判定
     * @param date
     * @return
     */
    ScreenHttpHolidaysCheckResponseDTO holidayCheck(String date);

    /**
     * 获取在线大屏数目
     */
    int getOnlineScreenNum();
}
