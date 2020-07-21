package com.landleaf.homeauto.center.oauth.mapper;

import com.landleaf.homeauto.common.domain.dto.oauth.sysuser.SysPersonalInformationDTO;
import com.landleaf.homeauto.common.domain.po.oauth.SysUser;
import com.landleaf.homeauto.common.mysql.HomeAutoBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 后台账号表 Mapper 接口
 * </p>
 *
 * @author wyl
 */
public interface SysUserMapper extends HomeAutoBaseMapper<SysUser> {


    List<SysPersonalInformationDTO> listSysUsers(@Param("roleId") String roleId,
                                                 @Param("name") String name,
                                                 @Param("mobile") String mobile,
                                                 @Param("status") Integer status,
                                                 @Param("email")String email,
                                                 @Param("startTime")String startTime,
                                                 @Param("endTime")String endTime);
}
