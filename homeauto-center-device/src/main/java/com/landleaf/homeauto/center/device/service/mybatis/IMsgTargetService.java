package com.landleaf.homeauto.center.device.service.mybatis;

import com.baomidou.mybatisplus.extension.service.IService;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
public interface IMsgTargetService extends IService<MsgTargetDO> {

    /**
     *  根据msgid获取目标id
     * @param msgId
     * @return
     */
    List<MsgTargetDO> getList(String msgId,String realestateName,List<String> projectNames);


}
