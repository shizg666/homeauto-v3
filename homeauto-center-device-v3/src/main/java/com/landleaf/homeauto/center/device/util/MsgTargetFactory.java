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


    public static MsgTargetDO newMsgTarget(ProjectDTO projectDTO, String msgId, MsgTypeEnum msgTypeEnum) {
        MsgTargetDO temp = new MsgTargetDO();
        temp.setId(IdGeneratorUtil.getUUID32());
        temp.setMsgId(msgId);
        temp.setMsgType(msgTypeEnum.getType());
        String projectName = projectDTO.getProjectName();
        String path = projectDTO.getPath();
        String[] strings = path.split("/");

        int length = strings.length;

        if (length >= 2) {
            temp.setRealestateId(strings[length - 2]);
            temp.setProjectId(strings[length - 1]);
        }

        temp.setProjectName(projectName);

        temp.setPath(path);
        return temp;
    }


    public static void main(String[] args) {
        String path = "CN/320000/320100/320113/2077e251d7984e8b96a5d1798b97484a";


        String[] strings = path.split("/");

        int length = strings.length;

        System.out.println(strings[length - 1]);

        System.out.println(strings[length - 2]);
    }
}
