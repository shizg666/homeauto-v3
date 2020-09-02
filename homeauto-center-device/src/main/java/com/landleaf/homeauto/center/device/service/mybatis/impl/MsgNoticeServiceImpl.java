package com.landleaf.homeauto.center.device.service.mybatis.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.landleaf.homeauto.center.device.enums.MsgReleaseStatusEnum;
import com.landleaf.homeauto.center.device.enums.MsgTerminalTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgNoticeWebDTO;
import com.landleaf.homeauto.center.device.model.dto.msg.MsgWebSaveOrUpdateDTO;
import com.landleaf.homeauto.center.device.model.mapper.MsgNoticeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgNoticeService;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgTargetService;
import com.landleaf.homeauto.center.device.util.MsgTargetFactory;
import com.landleaf.homeauto.common.domain.qry.MsgQry;
import com.landleaf.homeauto.common.enums.msg.MsgTypeEnum;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.web.context.TokenContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 公告消息表 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Service
@Slf4j
public class MsgNoticeServiceImpl extends ServiceImpl<MsgNoticeMapper, MsgNoticeDO> implements IMsgNoticeService {


    @Autowired
    private IMsgTargetService msgTargetService;



//
//
//
//    @Override
//    public PageInfo<MsgNoticeWebDTO> queryMsgNoticeWebDTOList(MsgQry msgQry) {
//
//        msgCommonService.perfectPaths(msgQry);
//
//        List<MsgNoticeWebDTO> queryList = this.baseMapper.queryMsgNoticeWebDTOList(msgQry);
//
//        //查询包含所有的地址
//        List<String> ids = queryList.stream().map(MsgNoticeWebDTO::getId).collect(Collectors.toList());
//        List<MsgNoticeWebDTO> tempList = new ArrayList<>();
//        if(CollectionUtil.isNotEmpty(queryList)){
//            tempList = this.baseMapper.queryMsgNoticeWebDTOListByIds(ids);
//        }
//
//        //切片分页
//        List<MsgNoticeWebDTO> msgNoticeWebDTOList = MsgCommonUtil.getPageList(tempList, msgQry);
//
//        List<String> userIds = msgNoticeWebDTOList.stream()
//                .map(MsgNoticeWebDTO::getReleaseUser)
//                .collect(Collectors.toList());
//
//        //请求uc获取用户名字
//        Response<List<SysUser>> response = mcUcRemote.getSysUserByIds(userIds);
//        Map<String, String> userNameMap =
//                Optional.ofNullable(response.getResult())
//                .orElse(new ArrayList<>())
//                .stream()
//                .collect(Collectors.toMap(SysUser::getId, SysUser::getName));
//
//        msgNoticeWebDTOList.forEach(mnw -> {
//            if(StringUtils.isNotBlank(userNameMap.get(mnw.getReleaseUser()))){
//                mnw.setReleaseUser(userNameMap.get(mnw.getReleaseUser()));
//            }
//
//            msgCommonService.fillShAddress(mnw.getShAddresses());
//        });
//        return MsgCommonUtil.getPageInfo(msgNoticeWebDTOList, tempList, msgQry);
//    }

//    @Override
//    public MsgNoticeWebDTO queryMsgNoticeWebDTO(String id) {
//        MsgNoticeWebDTO msgNoticeWebDTO = this.baseMapper.selectById(id);
//        Response<List<SysUser>> response = mcUcRemote.getSysUserByIds(new ArrayList<String>(){{add(msgNoticeWebDTO.getReleaseUser());}});
//        if(CollectionUtil.isNotEmpty(response.getResult())){
//            msgNoticeWebDTO.setReleaseUser(response.getResult().get(0).getName());
//        }
//
//        msgCommonService.fillShAddress(msgNoticeWebDTO.getShAddresses());
//        return msgNoticeWebDTO;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMsgNotice(MsgWebSaveOrUpdateDTO msgWebSaveOrUpdateDTO) {
        MsgNoticeDO msgNotice = new MsgNoticeDO();
        BeanUtils.copyProperties(msgWebSaveOrUpdateDTO, msgNotice);
        msgNotice.setTerminalType(MsgTerminalTypeEnum.All.getType());//默认全平台
        msgNotice.setReleaseUser(TokenContextUtil.getUserId());
        msgNotice.setId(IdGeneratorUtil.getUUID32());
        msgNotice.setReleaseFlag(MsgReleaseStatusEnum.UNPUBLISHED.getType());//默认未发布
        log.info("msgNotice:{}",msgNotice);
        //存入公告消息对象
        this.save(msgNotice);
        //存入target对象
        List<MsgTargetDO> msgTargets =  msgWebSaveOrUpdateDTO.getProjectDTOList().stream().map(
               sa  -> MsgTargetFactory.newMsgTarget(sa, msgNotice.getId(), MsgTypeEnum.NOTICE,
                       msgWebSaveOrUpdateDTO.getRealestateId(),msgWebSaveOrUpdateDTO.getRealestateName()))
                .collect(Collectors.toList());

        msgTargetService.saveBatch(msgTargets);

    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    @ShChangeNotifyTopic(changeNotifyType = ShChangeNotifyTypeEnum.CHANGE_NOTICE_MSG, operationChangeType = OperationChangeTypeEnum.UPDATE)
//    public void updateMsgNotice(MsgNoticeWebDTO msgNoticeWebDTO) {
//        //更新用户校验
//        msgCommonService.msgOperatePermission(msgNoticeWebDTO.getId());
//
//        MsgNotice msgNotice = new MsgNotice();
//        BeanUtils.copyProperties(msgNoticeWebDTO, msgNotice);
//        msgNotice.setReleaseUser(TokenContextUtil.getUserId());
//        //更新原始对象
//        this.updateById(msgNotice);
//
//        List<String> delIds = msgTargetService.getIdsByMsgId(msgNoticeWebDTO.getId());
//        //删除就地址
//        msgTargetService.removeByIds(delIds);
//        //新增新地址
//        List<MsgTarget> msgTargets = msgNoticeWebDTO.getShAddresses().stream().map(
//                sa  -> MsgTargetFactory.newMsgTarget(sa, msgNotice.getId(), MsgTypeEnum.NOTICE)
//        ).collect(Collectors.toList());
//        msgTargetService.saveBatch(msgTargets);
//
//        notifyKey(msgNoticeWebDTO.getId());
//    }
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    @ShChangeNotifyTopic(changeNotifyType = ShChangeNotifyTypeEnum.CHANGE_NOTICE_MSG, operationChangeType = OperationChangeTypeEnum.DELETE)
//    public void deleteMsgNotice(String id) {
//        //更新用户校验
//        msgCommonService.msgOperatePermission(id);
//
//        //逻辑删除公告消息
//        this.removeById(id);
//        //逻辑删除绑定推送范围
//        msgTargetService.remove(
//                new LambdaQueryWrapper<MsgTarget>()
//                        .eq( MsgTarget::getMsgId, id));
//
//        notifyKey(id);
//    }
//
//    @Override
//    public void releaseState(String id, Integer releaseFlag) {
//        new MsgNotice(){{
//            setId(id);
//            setReleaseFlag(releaseFlag);
//        }}.updateById();
//        notifyKey(id);
//    }


}
