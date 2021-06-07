package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.vo.FamilyUserInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceManageQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceMangeFamilyPageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.device.FamilyDeviceQryDTO;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticPageVO;
import com.landleaf.homeauto.center.device.model.vo.space.SpaceManageStaticQryDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.CascadeStringVo;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteBatchDTO;
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
     * 根据家庭id获取家庭BO对象
     *
     * @param familyId
     * @return com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO
     * @author wenyilu
     * @date 2020/12/28 16:12
     */
    HomeAutoFamilyBO getHomeAutoFamilyBO(Long familyId);


    /**
     * 根据家庭获取城市天气Code
     *
     * @param familyId
     * @return java.lang.String
     * @author wenyilu
     * @date 2020/12/28 16:15
     */
    String getWeatherCodeByFamilyId(String familyId);

    /**
     * 根据终端类型及终端值获取家庭BO
     *
     * @param mac  mac地址
     * @return com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO
     * @author wenyilu
     * @date 2020/12/28 16:20
     */
    FamilyInfoBO getFamilyInfoByTerminalMac(String mac);
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
     * 智齿客服根据家庭id获取家庭信息
     *
     * @param familyId
     * @return com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO
     * @author wenyilu
     * @date 2020/12/28 16:52
     */
    FamilyInfoForSobotDTO getFamilyInfoForSobotById(Long familyId);


    void add(FamilyAddDTO request);

    void update(FamilyAddDTO request);

    void delete(ProjectConfigDeleteDTO request);

    /**
     * 查看单元下的家庭列表
     *
     * @param id
     * @return
     */
    List<FamilyPageVO> getListByUnitId(String id);



    /**
     * 根据path查询家庭id集合
     *
     * @param path
     * @return
     */
    List<Long> getListIdByPaths(List<String> path);
    /**
     * 根据path查询家庭id集合
     *
     * @param path
     * @return
     */
    List<Long> getListIdByPathsAndType(List<String> paths,Integer type);


    /**
     * 根据工程id查询家庭基本信息
     *
     * @param familyId
     * @return
     */
    List<FamilyBaseInfoDTO> getBaseInfoByProjectId(String familyId);


    HomeAutoFamilyDO getFamilyByCode(String familyCode);


    /**
     * App用户查看绑定的家庭列表
     *
     * @param userId
     * @return
     */
    List<FamilyUserVO> getListByUser(String userId);

    /**
     * 下载家庭批量导入模板
     *
     * @param request
     * @param response
     */
    void downLoadImportTemplate(TemplateQeyDTO request, HttpServletResponse response);

    /**
     * 根据模板文件批量导入家庭
     *
     * @param file
     * @param response
     */
    void importBatch(MultipartFile file, HttpServletResponse response) throws IOException;




    /**
     * 家庭下拉列表
     *projectId 为null 则查全部
     * @return
     */
    List<SelectedVO> getListFamilySelects(String projectId);

    /**
     * 家庭分页查询
     *
     * @param familyQryDTO
     * @return
     */
    BasePageVO<FamilyPageVO> getListPage(FamilyQryDTO familyQryDTO);


    /****************************************v2新增，勿动！！！***********************************************************/

    /**
     * 根据用户及家庭状态获取家庭列表
     *
     * @param userId       用户ID
     * @param enableStatus 家庭状态(启用/停用)
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO>
     * @author wenyilu
     * @date 2020/12/25 9:24
     */
    List<HomeAutoFamilyBO> listByUserId(String userId,Integer enableStatus);


    /**
     * 获取家庭下楼层下房间信息
     *
     * @param familyId
     * @param templateId
     * @param floorId
     * @param deviceFilterFlag
     * @return java.util.List<com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO>
     * @author wenyilu
     * @date 2021/1/6 11:29
     */
    List<FamilyRoomBO> getFamilyRoomBOByTemplateAndFloor(Long familyId, Long templateId, String floorId, Integer deviceFilterFlag);



    /**
     * 根据家庭id获取绑定的户型id
     * @param familyId
     * @return
     */
    String getTemplateIdById(String familyId);

    /**
     *  根据家庭及设备编码获取设备
     * @param familyId    家庭Id
     * @param deviceSn  设备号
     * @return com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO
     * @author wenyilu
     * @date 2021/1/15 15:19
     */
    TemplateDeviceDO getDeviceByDeviceCode(Long familyId, String deviceSn);

    /**
     * 根据家庭id获取家庭通信大屏mac
     * @param familyId
     * @return
     */
    String getScreenMacByFamilyId(String familyId);

    /**
     * 大屏绑定家庭
     * @param terminalMac
     * @param fimilyCode
     */
    void bind(String terminalMac, String fimilyCode);

    /**
     * 启停用状态下拉列表
     * @return
     */
    List<SelectedIntegerVO> getEnableStatus();

    /**
     *  获取家庭设备的产品编码
     * @param familyId    家庭ID
     * @param deviceCode  设备编码
     * @return com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct
     * @author wenyilu
     * @date 2021/1/20 13:09
     */
    HomeAutoProduct getFamilyDeviceProduct(Long familyId, String deviceCode);

    /**
     * 查看家庭基本信息
     * @param familyId
     * @return
     */
    FamilyBaseInfoVO getfamilyBaseInfoById(String familyId);


    /**
     * 设备管理页面----家庭列表查询
     * @param deviceManageQryDTO
     * @return
     */
    BasePageVO<DeviceMangeFamilyPageVO> getListDeviceMangeFamilyPage(DeviceManageQryDTO deviceManageQryDTO);

    /**
     * 查询家庭下的设别列表
     * @param familyId
     * @return
     */
    BasePageVO<TemplateDevicePageVO> getListDeviceByFamilyId(String familyId,Integer pageSize,Integer pageNum);

    /**
     * 获取screenMac列表
     * @return
     */
    List<String> getScreenMacList();


    /**
     * 根据家庭code获取户型id
     * @return
     */
    String getTemplateIdByFamilyCode(String familyCode);

    /**
     *  获取家庭设备需要在app展示属性
     * @param deviceId
     * @return java.util.List<java.lang.String>
     * @author wenyilu
     * @date 2021/3/19 10:39
     */
    List<String> getAppShowDeviceAttrs( String deviceId);

    /**
     * 修改家庭户型
     * @param request
     */
    void updateFamilysTempalteId(FamilyTempalteUpdateDTO request);

    /**
     * 项目楼房管理
     * @param projcetId
     * @return
     */
    List<ProjectFamilyTotalVO> getProjectFamilyTotal(Long projcetId);

    void deleteBatch(ProjectConfigDeleteBatchDTO request);

    /**
     * 批量新增家庭
     * @param request
     */
    void addBatch(FamilyAddBatchDTO request);

    /**
     * 分页统计家庭在空间管理维度值
     * @param spaceManageStaticQryDTO
     * @return
     */
    BasePageVO<SpaceManageStaticPageVO> spaceManageStatistics(SpaceManageStaticQryDTO spaceManageStaticQryDTO);

    /**
     * 分页查询家庭设备列表
     * @param familyDeviceQryDTO
     * @return
     */
    BasePageVO<FamilyDevicePageVO> listFamilyDevicePage(FamilyDeviceQryDTO familyDeviceQryDTO);

    /**
     * 大屏mac绑定家庭
     * @param projectId
     * @param buildingCode
     * @param unitCode
     * @param roomNo
     * @param terminalMac
     * @param prefix
     * @param suffix
     */
    void bindMac(Long projectId, String buildingCode, String unitCode, String floor, String roomNo, String terminalMac, String prefix, String suffix);

    /**
     * 获取项目下的楼栋列表（去重）
     * @return
     */
    List<String> getListBuildByProjectId(Long projectId);

    /**
     * 楼栋单元下拉列表
     * @param projectId
     * @param buildCode
     * @return
     */
    List<SelectedVO> getSelectsUnitByBuild(Long projectId, String buildCode);

    /**
     * 楼栋楼层下拉列表
     * @param projectId
     * @param buildCode
     * @return
     */
    List<SelectedVO> getSelectsfloorByBuild(Long projectId, String buildCode);

    /**
     * 删除楼栋
     * @param familyBuildDTO
     */
    void removeBuilding(FamilyBuildDTO familyBuildDTO);

    /**
     * 获取该户型下以绑定大屏的家庭id集合
     * @param templateId
     * @return
     */
    List<Long> getFamilyIdsBind(Long templateId);

    /**
     * 获取项目下楼栋下拉列表
     * @param projectId
     * @return
     */
    List<SelectedVO> getSelectBuildByProjectId(Long projectId);

    /**
     * 获取家庭通过mac地址
     * @param mac
     * @return
     */
    HomeAutoFamilyDO getFamilyByMac(String mac);

    /**
     * 根据楼盘id获取楼栋单元家庭级联信息
     * @param realestateId
     * @return
     */
    List<CascadeStringVo> getCascadeBuildUnit(Long realestateId);

    /**
     * 根据 楼盘id/楼栋/单元/家庭id path 获取家庭信息
     * @param pathList
     * @return
     */
    List<Long> getListFamilyIdsByPath2(List<String> pathList);


    List<MyFamilyInfoVO> getMyFamily(String userId);

    List<FamilyUserInfoVO> getMyFamilyUserInfo(Long familyId);

    List<HomeAutoFamilyDO> getFamilyByProject(Long projectId);
}
