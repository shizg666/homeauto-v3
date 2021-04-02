package com.landleaf.homeauto.center.device.model.vo.family;

import com.landleaf.homeauto.center.device.enums.RoomTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.base.BaseDO;
import com.landleaf.homeauto.center.device.model.vo.project.TemplateDevicePageVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 家庭终端表
 * </p>
 *
 * @author Yujiumin
 * @since 2020-08-14
 */
@Data
@Accessors(chain = true)
@ApiModel(value="FamilyRoomDetailVO", description="家庭楼层房间设备对象")
public class FamilyRoomDetailVO  {

    private static final long serialVersionUID = -2867798131122564419L;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "类型")
    private Integer type;


    @ApiModelProperty(value = "类型str")
    private String typeStr;

    @ApiModelProperty(value = "设备信息")
    List<FamilyDeviceDetailVO> devices;

    public void setType(Integer type) {
        this.type = type;
        this.typeStr = RoomTypeEnum.getInstByType(type)!=null?RoomTypeEnum.getInstByType(type).getName():"";
    }
}
