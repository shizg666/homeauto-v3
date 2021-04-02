package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.excel.importfamily.ImportFamilyModel;
import com.landleaf.homeauto.center.device.model.bo.FamilyInfoBO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.category.HomeAutoProduct;
import com.landleaf.homeauto.center.device.model.domain.housetemplate.TemplateDeviceDO;
import com.landleaf.homeauto.center.device.model.domain.mqtt.MqttUser;
import com.landleaf.homeauto.center.device.model.dto.DeviceCommandDTO;
import com.landleaf.homeauto.center.device.model.dto.FamilyInfoForSobotDTO;
import com.landleaf.homeauto.center.device.model.smart.bo.FamilyRoomBO;
import com.landleaf.homeauto.center.device.model.smart.bo.HomeAutoFamilyBO;
import com.landleaf.homeauto.center.device.model.smart.vo.*;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyDetailInfoVO;
import com.landleaf.homeauto.center.device.model.vo.MyFamilyInfoVO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceManageQryDTO;
import com.landleaf.homeauto.center.device.model.vo.device.DeviceMangeFamilyPageVO;
import com.landleaf.homeauto.center.device.model.vo.family.*;
import com.landleaf.homeauto.center.device.model.vo.family.app.FamilyUpdateVO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterDeviceStatusReadAckDTO;
import com.landleaf.homeauto.common.domain.vo.BasePageVO;
import com.landleaf.homeauto.common.domain.vo.SelectedIntegerVO;
import com.landleaf.homeauto.common.domain.vo.SelectedVO;
import com.landleaf.homeauto.common.domain.vo.realestate.ProjectConfigDeleteDTO;
import com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 家庭表 服务类
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
public interface IMqttUserService extends IService<MqttUser> {


    void removeByFamilyCode(String code);
}
