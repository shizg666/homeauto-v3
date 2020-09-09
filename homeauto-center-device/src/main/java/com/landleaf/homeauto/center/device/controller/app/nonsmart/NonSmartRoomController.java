package com.landleaf.homeauto.center.device.controller.app.nonsmart;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.landleaf.homeauto.center.device.model.bo.FamilySimpleRoomBO;
import com.landleaf.homeauto.center.device.model.domain.FamilyFloorDO;
import com.landleaf.homeauto.center.device.model.domain.FamilyRoomDO;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyFloorService;
import com.landleaf.homeauto.center.device.service.mybatis.IFamilyRoomService;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.web.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yujiumin
 * @version 2020/9/9
 */
@Slf4j
@RestController
@RequestMapping("/app/non-smart/room")
@Api(tags = "自由方舟APP房间接口")
public class NonSmartRoomController extends BaseController {

    @Autowired
    private IFamilyFloorService familyFloorService;

    @Autowired
    private IFamilyRoomService familyRoomService;

    @GetMapping("/list/{familyId}")
    @ApiOperation("通过家庭ID获取房间")
    public Response<List<FamilySimpleRoomBO>> getRoomByFamilyId(@PathVariable String familyId) {
        QueryWrapper<FamilyFloorDO> floorQueryWrapper = new QueryWrapper<>();
        floorQueryWrapper.eq("family_id", familyId);
        List<FamilyFloorDO> familyFloorDOList = familyFloorService.list(floorQueryWrapper);
        List<String> floorIdList = familyFloorDOList.stream().map(FamilyFloorDO::getId).collect(Collectors.toList());

        QueryWrapper<FamilyRoomDO> roomQueryWrapper = new QueryWrapper<>();
        roomQueryWrapper.in("floor_id", floorIdList);
        List<FamilyRoomDO> roomDOList = familyRoomService.list(roomQueryWrapper);

        List<FamilySimpleRoomBO> familySimpleRoomBOList = new LinkedList<>();
        for (FamilyRoomDO familyRoomDO : roomDOList) {
            FamilySimpleRoomBO familySimpleRoomBO = new FamilySimpleRoomBO();
            familySimpleRoomBO.setRoomId(familyRoomDO.getId());
            familySimpleRoomBO.setRoomName(familyRoomDO.getName());
            familySimpleRoomBOList.add(familySimpleRoomBO);
        }
        return returnSuccess(familySimpleRoomBOList);
    }

}
