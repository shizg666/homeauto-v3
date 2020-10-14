package com.landleaf.homeauto.center.device.service.mybatis.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.MsgReleaseStatusEnum;
import com.landleaf.homeauto.center.device.enums.MsgTerminalTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.FamilyTerminalDO;
import com.landleaf.homeauto.center.device.model.domain.HomeAutoFamilyDO;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.dto.msg.*;
import com.landleaf.homeauto.center.device.model.mapper.MsgNoticeMapper;
import com.landleaf.homeauto.center.device.service.bridge.IAppService;
import com.landleaf.homeauto.center.device.service.mybatis.*;
import com.landleaf.homeauto.center.device.util.MessageIdUtils;
import com.landleaf.homeauto.center.device.util.MsgTargetFactory;
import com.landleaf.homeauto.common.domain.dto.adapter.ack.AdapterConfigUpdateAckDTO;
import com.landleaf.homeauto.common.domain.dto.adapter.request.AdapterConfigUpdateDTO;
import com.landleaf.homeauto.common.enums.msg.MsgTypeEnum;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
import com.landleaf.homeauto.common.web.context.TokenContext;
import com.landleaf.homeauto.common.web.context.TokenContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.RocketMqConst.TAG_FAMILY_CONFIG_UPDATE;
import static com.landleaf.homeauto.common.enums.screen.ContactScreenConfigUpdateTypeEnum.NEWS;

/**
 * <p>
 * 公告消息表 服务实现类
 * </p>
 *
 * @author 张洪滨
 * @since 2020-09-01
 */
@Service
@Slf4j
public class MsgNoticeServiceImpl extends ServiceImpl<MsgNoticeMapper, MsgNoticeDO> implements IMsgNoticeService {


    @Autowired
    private IMsgTargetService msgTargetService;

    @Autowired
    private IFamilyTerminalService terminalService;


    @Autowired
    private IHomeAutoFamilyService familyService;

    @Autowired
    private IAppService iAppService;

    @Autowired
    private IMsgReadNoteService iMsgReadNoteService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMsgNotice(MsgWebSaveDTO msgWebSaveDTO) {
        MsgNoticeDO msgNotice = new MsgNoticeDO();
        BeanUtils.copyProperties(msgWebSaveDTO, msgNotice);
        msgNotice.setTerminalType(MsgTerminalTypeEnum.All.getType());//默认全平台
        msgNotice.setReleaseUser(TokenContextUtil.getUserId());
        String id = IdGeneratorUtil.getUUID32();
        msgNotice.setId(id);

        Integer flag = msgWebSaveDTO.getReleaseFlag();


        if (flag == MsgReleaseStatusEnum.PUBLISHED.getType()) {//默认发布
            msgNotice.setReleaseFlag(MsgReleaseStatusEnum.PUBLISHED.getType());
            msgNotice.setSendTime(LocalDateTime.now());
            msgNotice.setReleaseUser(TokenContextUtil.getUserId());
        } else {

            msgNotice.setReleaseFlag(MsgReleaseStatusEnum.UNPUBLISHED.getType());
        }


        log.info("msgNotice:{}", msgNotice);
        //存入公告消息对象
        this.save(msgNotice);
        //存入target对象
        List<MsgTargetDO> msgTargets = msgWebSaveDTO.getProjectList().stream().map(
                sa -> MsgTargetFactory.newMsgTarget(sa, msgNotice.getId(), MsgTypeEnum.NOTICE))
                .collect(Collectors.toList());
        msgTargetService.saveBatch(msgTargets);

        if (flag == MsgReleaseStatusEnum.PUBLISHED.getType()) {
            //如果是发布，则调用发布接口
            publish(id);
        }

    }

    @Override
    public PageInfo<MsgNoticeWebDTO> queryMsgNoticeWebDTOList(MsgWebQry msgWebQry) {

        List<MsgNoticeWebDTO> msgNoticeWebDTOS = Lists.newArrayList();

        QueryWrapper<MsgNoticeDO> queryWrapper = new QueryWrapper<>();
        Integer releaseFlag = msgWebQry.getReleaseFlag();
        String name = msgWebQry.getName();
        String startTime = msgWebQry.getStartTime();
        String endTime = msgWebQry.getEndTime();

        String projectName = msgWebQry.getProjectName();

        if (StringUtils.isNotBlank(startTime)) {
            queryWrapper.apply("send_time>= TO_TIMESTAMP('" + startTime + "','yyyy-mm-dd hh24:mi:ss')");
        }

        if (StringUtils.isNotBlank(startTime)) {
            queryWrapper.apply("send_time<= TO_TIMESTAMP('" + endTime + "','yyyy-mm-dd hh24:mi:ss')");
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        if (releaseFlag == MsgReleaseStatusEnum.UNPUBLISHED.getType() ||
                releaseFlag == MsgReleaseStatusEnum.PUBLISHED.getType()) {
            queryWrapper.eq("release_flag", releaseFlag);
        }

        queryWrapper.orderByDesc("create_time");

        List<MsgNoticeDO> msgNoticeDOS = this.baseMapper.selectList(queryWrapper);

        log.info("size:{}", msgNoticeDOS.size());


        msgNoticeDOS.forEach(s -> {

            String msg_id = s.getId();
            List<MsgTargetDO> msgTargetDOS = msgTargetService.getList(msg_id, projectName);

            if (msgTargetDOS.size() > 0) {
                MsgNoticeWebDTO msgNoticeWebDTO = new MsgNoticeWebDTO();

                BeanUtils.copyProperties(s, msgNoticeWebDTO);

                msgNoticeWebDTO.setMsgId(s.getId());

                msgNoticeWebDTO.setReleaseTime(s.getSendTime());

                List<ProjectDTO> projectDTOList = Lists.newArrayList();

                msgTargetDOS.forEach(p -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setProjectName(p.getProjectName());
                    projectDTO.setPath(p.getPath());
                    projectDTOList.add(projectDTO);
                });

                msgNoticeWebDTO.setProjectDTOList(projectDTOList);

                msgNoticeWebDTOS.add(msgNoticeWebDTO);
            }
        });

        PageInfo pageInfo = new PageInfo(msgNoticeWebDTOS);

        return pageInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMsgByIds(List<String> ids) {

        // 1.通过id查询出msgTarget，然后删除msg_target记录
        // 2. 删除msg_notice记录

        for (String id : ids) {
            List<MsgTargetDO> msgTargetDOS = msgTargetService.getListById(id);
            List<String> targetIds = msgTargetDOS.stream().map(s -> s.getId()).collect(Collectors.toList());

            List<String> familyIds = getFamilyIds(id);
            msgTargetService.removeByIds(targetIds);

            this.baseMapper.deleteById(id);

            publishByFamilyIds(familyIds);//删除也通知大屏删除公告
        }

    }

    @Override
    public void releaseState(String id, Integer releaseFlag) throws Exception {

        MsgNoticeDO msgNoticeDO = this.baseMapper.selectById(id);

        if (msgNoticeDO != null) {
            msgNoticeDO.setReleaseFlag(releaseFlag);

            msgNoticeDO.setSendTime(LocalDateTime.now());

            msgNoticeDO.setReleaseUser(TokenContextUtil.getUserId());
            saveOrUpdate(msgNoticeDO);
        }
        //通知大屏幕更新

        publish(id);


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMsg(MsgWebUpdateDTO requestBody) {
        //1.先修改msgNotice的标题和内容
        // 2.在修改msgTarget的项目id，全部删除，然后新增
        String msgId = requestBody.getMsgId();
        List<ProjectDTO> projectDTOS = requestBody.getProjectList();

        Integer releaseFlag = requestBody.getReleaseFlag();
        MsgNoticeDO msgNoticeDO = this.baseMapper.selectById(msgId);
        if (msgNoticeDO != null) {
            msgNoticeDO.setContent(requestBody.getContent());
            msgNoticeDO.setName(requestBody.getName());
            msgNoticeDO.setUpdateTime(LocalDateTime.now());
            msgNoticeDO.setReleaseFlag(releaseFlag);

            if (releaseFlag == MsgReleaseStatusEnum.PUBLISHED.getType()) {
                msgNoticeDO.setSendTime(LocalDateTime.now());
                msgNoticeDO.setReleaseUser(TokenContextUtil.getUserId());
            }
            saveOrUpdate(msgNoticeDO);

            List<MsgTargetDO> msgTargetDOS = msgTargetService.getListById(msgId);

            msgTargetService.removeByIds(msgTargetDOS.stream().map(s -> s.getId()).collect(Collectors.toList()));

            //存入target对象
            List<MsgTargetDO> msgTargets = projectDTOS.stream().map(
                    sa -> MsgTargetFactory.newMsgTarget(sa, msgId, MsgTypeEnum.NOTICE))
                    .collect(Collectors.toList());
            msgTargetService.saveBatch(msgTargets);


            if (releaseFlag == MsgReleaseStatusEnum.PUBLISHED.getType()) {
                publish(msgId);
            }

        }

    }

    @Override
    public List<MsgNoticeDO> queryMsgNoticeByProjectIdForScreen(String projectId) {

        return this.baseMapper.queryMsgNoticeByProjectIdForScreen(projectId);
    }

    @Override
    public List<MsgNoticeAppDTO> getMsglist(String familyId) {
        HomeAutoFamilyDO familyDO = familyService.getById(familyId);
        if (familyDO == null){
            return Lists.newArrayListWithExpectedSize(0);
        }
        List<MsgNoticeAppDTO> data = this.baseMapper.getMsglist(familyDO.getProjectId());
        List<String> msgIds = iMsgReadNoteService.getListUserAndType(TokenContext.getToken().getUserId(),MsgTypeEnum.NOTICE.getType());
        if (CollectionUtils.isEmpty(msgIds)){
            return data;
        }
        data.stream().forEach(obj->{
            if (msgIds.contains(obj.getMsgId())){
                obj.setReadFlag(1);
            }
        });
        return data;
    }

    public List<String> getFamilyIds(String id) {
        List<MsgTargetDO> targetDOList = msgTargetService.getListById(id);

        List<String> familyIds = Lists.newArrayList();

        if (targetDOList.size() > 0) {

            List<String> paths = targetDOList.stream().map(s -> s.getPath()).collect(Collectors.toList());

            if (paths.size() > 0) {
                log.info("path:{}", paths.get(0));
            }
            familyIds = familyService.getListIdByPaths(paths);

            log.info("familyIds:{}", familyIds.size());


        }

        return familyIds;
    }

    public void publishByFamilyIds(List<String> familyIds) {
        if (familyIds.size() > 0) {

            familyIds.forEach(p -> {

                FamilyTerminalDO terminalDO = terminalService.getMasterTerminal(p);

                //如果mac不为空则进行消息更新发布
                if (terminalDO != null && StringUtils.isNotBlank(terminalDO.getMac())) {
                    String code = familyService.getById(p).getCode();

                    AdapterConfigUpdateDTO updateDTO = new AdapterConfigUpdateDTO();
                    updateDTO.setUpdateType(NEWS.code);
                    updateDTO.setFamilyId(p);
                    updateDTO.setFamilyCode(code);
                    updateDTO.setMessageName(TAG_FAMILY_CONFIG_UPDATE);
                    updateDTO.setMessageId(MessageIdUtils.genMessageId());
                    updateDTO.setTerminalMac(terminalDO.getMac());
                    updateDTO.setTerminalType(terminalDO.getType());
                    iAppService.configUpdateConfig(updateDTO);
                }

            });
        }
    }


    public void publish(String id) {
        List<String> familyIds = getFamilyIds(id);
        publishByFamilyIds(familyIds);
    }


}
