package com.landleaf.homeauto.center.device.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.landleaf.homeauto.center.device.model.domain.realestate.HomeAutoRealestate;
import com.landleaf.homeauto.center.device.model.vo.family.PathBO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeQryDTO;
import com.landleaf.homeauto.center.device.model.vo.realestate.RealestateModeStatusVO;
import com.landleaf.homeauto.common.domain.vo.SelectedLongVO;
import com.landleaf.homeauto.common.domain.vo.common.CascadeLongVo;
import com.landleaf.homeauto.common.domain.vo.realestate.CascadeStringVo;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateDeveloperVO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateQryDTO;
import com.landleaf.homeauto.common.domain.vo.realestate.RealestateVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 楼盘表 Mapper 接口
 * </p>
 *
 * @author wenyilu
 * @since 2020-08-11
 */
public interface HomeAutoRealestateMapper extends BaseMapper<HomeAutoRealestate> {

    List<RealestateVO> page(RealestateQryDTO request);

    @Select("SELECT re.path_name,dc.NAME AS developerName FROM home_auto_realestate re LEFT JOIN sys_dic_tag dc ON re.developer_code = dc.VALUE WHERE dc.dic_code = 'developer' AND re.id = #{id}")
    RealestateDeveloperVO getDeveloperInfoById(Long id);

    List<SelectedLongVO> getListSeclects(@Param("paths") List<String> path);

    @Select("SELECT r.code from home_auto_realestate r where r.id = #{realestateId} ")
    String getRealestateCodeById(@Param("realestateId") Long realestateId);

    @Select("SELECT r.name,r.code,r.path_oauth as path,r.path_name from home_auto_realestate r where r.id = #{realestateId} ")
    PathBO getRealestatePathInfoById(@Param("realestateId")Long realestateId);

    List<CascadeLongVo> getListCascadeSeclects(@Param("ids") List<Long> ids);

    List<RealestateModeStatusVO> getListSeclectsByProject(RealestateModeQryDTO request);

    /**
     * 楼盘项目级联数据获取 根据楼盘名称模糊查询
     * @return
     */
    List<CascadeLongVo> cascadeRealestateProject(String name);

    /**
     * 某一楼盘项目下 楼栋 单元 家庭名称 级联数据获取
     * @return
     */
    List<CascadeStringVo> cascadeRealestateProjectFamily(@Param("realestateId") Long realestateId, @Param("projectId") Long projectId);

    /**
     * 某一楼盘项目下楼栋 单元 家庭房号 级联数据
     * @return
     */
    List<CascadeStringVo> cascadeRealestateFamilyRoom(@Param("realestateId") Long realestateId, @Param("projectId") Long projectId);
}
