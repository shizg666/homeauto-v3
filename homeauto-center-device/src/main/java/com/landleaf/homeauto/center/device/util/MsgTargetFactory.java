package com.landleaf.homeauto.center.device.util;


import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.dto.msg.ProjectDTO;
import com.landleaf.homeauto.common.enums.msg.MsgTypeEnum;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;

/**
 * @author Lokiy
 * @date 2019/8/16 16:10
 * @description: MsgTarget生产工程
 */
public class MsgTargetFactory {


    public static MsgTargetDO newMsgTarget(ProjectDTO sa, String msgId, MsgTypeEnum msgTypeEnum, String realestateId,String realestateName){
        MsgTargetDO temp = new MsgTargetDO();
        temp.setId( IdGeneratorUtil.getUUID32());
        temp.setMsgId(msgId);
        temp.setMsgType(msgTypeEnum.getType());
        temp.setRealestateId(realestateId);
        temp.setRealestateName(realestateName);
        temp.setProjectId(sa.getProjectId());
        temp.setProjectName(sa.getProjectName());
        return temp;
    }
}
