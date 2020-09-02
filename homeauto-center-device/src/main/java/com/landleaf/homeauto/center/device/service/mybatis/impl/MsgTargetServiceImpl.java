package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.mapper.MsgTargetMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgTargetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Service
public class MsgTargetServiceImpl extends ServiceImpl<MsgTargetMapper, MsgTargetDO> implements IMsgTargetService {

//    @Override
//    public List<String> getIdsByMsgId(String msgId) {
//        List<MsgTargetDO> oriMsgTargets = this.list(new LambdaQueryWrapper<MsgTargetDO>()
//                        .eq(MsgTargetDO::getMsgId, msgId));
//        return oriMsgTargets.stream()
//                .map(MsgTargetDO::getId)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public List<String> getPathsByMsgId(String msgId) {
//        List<MsgTargetDO> oriMsgTargets = this.list(new LambdaQueryWrapper<MsgTargetDO>()
//                .eq(MsgTargetDO::getMsgId, msgId));
//        return oriMsgTargets.stream()
//                .map(MsgTargetDO::getTargetPath)
//                .collect(Collectors.toList());
//    }
}
