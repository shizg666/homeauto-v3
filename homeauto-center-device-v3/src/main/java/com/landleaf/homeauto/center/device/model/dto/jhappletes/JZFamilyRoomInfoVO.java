package com.landleaf.homeauto.center.device.model.dto.jhappletes;

import com.landleaf.homeauto.center.device.model.smart.vo.FamilyRoomVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 家庭所在城市天气信息
 *
 * @author Yujiumin
 * @version 2020/10/15
 */
@Data
@ApiModel(value="JZFamilyRoomInfoVO", description="家庭房间信息")
public class JZFamilyRoomInfoVO {

    @ApiModelProperty("房间数量")
    private int roomNum;

    @ApiModelProperty("房间列表")
    private List<JZRoomInfoVO> rooms;


}
