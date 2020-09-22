package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.excel.importfamily.HouseTemplateConfig;
import com.landleaf.homeauto.center.device.excel.importfamily.ImportFamilyModel;
import com.landleaf.homeauto.center.device.model.bo.FamilyBO;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.vo.*;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.common.domain.dto.device.family.FamilyAuthStatusDTO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    /**
     * app 修改家庭名称
     * @param request
     */
    void updateFamilyName(FamilyUpdateVO request);

    /**
     * 根据path查询家庭id集合
     * @param path
     * @return
     */
    List<String> getListIdByPaths(List<String> path);

    /**
     * 查看家庭配置信息
     * @param familyId
     * @return
     */
    FamilyConfigDetailVO getConfigInfo(String familyId);

    /**
     * 根据工程id查询家庭基本信息
     * @param familyId
     * @return
     */
    List<FamilyBaseInfoDTO> getBaseInfoByProjectId(String familyId);

    /**
     * 根据path查询家庭基本信息
     * @param paths
     * @return
     */
    List<FamilyBaseInfoDTO> getBaseInfoByPath(List<String> paths);

    Boolean checkFamilyConfig(String familyId);

    HomeAutoFamilyDO getFamilyByCode(String familyCode);

    /**
     * 获取家庭授权状态
     * @param familyId
     * @return
     */
    FamilyAuthStatusDTO getAuthorizationState(String familyId);

    /**
     * App用户查看绑定的家庭列表
     * @param userId
     * @return
     */
    List<FamilyUserVO> getListByUser(String userId);

    /**
     * 下载家庭批量导入模板
     * @param request
     * @param response
     */
    void downLoadImportTemplate(TemplateQeyDTO request, HttpServletResponse response);

    /**
     * 根据模板文件批量导入家庭
     * @param file
     * @param response
     */
    void importBatch(MultipartFile file, HttpServletResponse response) throws IOException;

    /**
     * 批量导入家庭
     * @param dataList
     * @param config
     * @return
     */
    List<ImportFamilyModel> importBatchFamily(List<ImportFamilyModel> dataList, HouseTemplateConfig config);

    /**
     * 同步家庭配置数据
     * @param familyId
     */
    void syncFamilyConfig(String familyId);

    /**
     * 获取家庭编号
     * @param familyId
     * @return
     */
    String getFamilyCodeByid(String familyId);

    /**
     * 下载家庭批量导入模板
     * @param request
     * @param response
     */
    void downLoadImportBuildingTemplate(TemplateQeyDTO request, HttpServletResponse response);


    /**
     *
     * @param file
     * @param response
     */
    void importBuildingBatch(MultipartFile file, HttpServletResponse response) throws IOException;

    /**
     * 家庭下拉列表
     * @return
     */
    List<SelectedVO> getListFamilySelects();
}
