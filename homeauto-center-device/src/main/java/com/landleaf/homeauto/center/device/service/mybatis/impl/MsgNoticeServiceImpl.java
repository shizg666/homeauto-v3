package com.landleaf.homeauto.center.device.service.mybatis.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.enums.MsgReleaseStatusEnum;
import com.landleaf.homeauto.center.device.enums.MsgTerminalTypeEnum;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgNoticeDO;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.dto.msg.*;
import com.landleaf.homeauto.center.device.model.mapper.MsgNoticeMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgNoticeService;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgTargetService;
import com.landleaf.homeauto.center.device.util.MsgTargetFactory;
import com.landleaf.homeauto.common.enums.msg.MsgTypeEnum;
import com.landleaf.homeauto.common.util.IdGeneratorUtil;
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


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveMsgNotice(MsgWebSaveDTO msgWebSaveOrUpdateDTO) {
        MsgNoticeDO msgNotice = new MsgNoticeDO();
        BeanUtils.copyProperties(msgWebSaveOrUpdateDTO, msgNotice);
        msgNotice.setTerminalType(MsgTerminalTypeEnum.All.getType());//默认全平台
        msgNotice.setReleaseUser(TokenContextUtil.getUserId());
        msgNotice.setId(IdGeneratorUtil.getUUID32());
        msgNotice.setReleaseFlag(MsgReleaseStatusEnum.UNPUBLISHED.getType());//默认未发布
        log.info("msgNotice:{}", msgNotice);
        //存入公告消息对象
        this.save(msgNotice);
        //存入target对象
        List<MsgTargetDO> msgTargets = msgWebSaveOrUpdateDTO.getProjectDTOList().stream().map(
                sa -> MsgTargetFactory.newMsgTarget(sa, msgNotice.getId(), MsgTypeEnum.NOTICE,
                        msgWebSaveOrUpdateDTO.getRealestateId(), msgWebSaveOrUpdateDTO.getRealestateName()))
                .collect(Collectors.toList());

        msgTargetService.saveBatch(msgTargets);

    }

    @Override
    public PageInfo<MsgNoticeWebDTO> queryMsgNoticeWebDTOList(MsgWebQry msgWebQry) {

        List<MsgNoticeWebDTO> msgNoticeWebDTOS = Lists.newArrayList();

        QueryWrapper<MsgNoticeDO> queryWrapper = new QueryWrapper<>();
        Integer releaseFlag = msgWebQry.getReleaseFlag();
        String name = msgWebQry.getName();
        String startTime = msgWebQry.getStartTime();
        String endTime = msgWebQry.getEndTime();

        String realestateName = msgWebQry.getRealestateName();

        List<String> projectNames = msgWebQry.getProjectNames();

        if (StringUtils.isNotBlank(startTime)) {
            queryWrapper.apply("send_time>= TO_TIMESTAMP('" + startTime + "','yyyy-mm-dd hh24:mi:ss')");
        }

        if (StringUtils.isNotBlank(startTime)) {
            queryWrapper.apply("send_time<= TO_TIMESTAMP('" + endTime + "','yyyy-mm-dd hh24:mi:ss')");
        }
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }
        queryWrapper.eq("release_flag", releaseFlag);

        List<MsgNoticeDO> msgNoticeDOS = this.baseMapper.selectList(queryWrapper);

        log.info("size:{}", msgNoticeDOS.size());


        msgNoticeDOS.forEach(s -> {

            String msg_id = s.getId();
            List<MsgTargetDO> msgTargetDOS = msgTargetService.getList(msg_id, realestateName, projectNames);

            if (msgTargetDOS.size() >= 0) {
                MsgNoticeWebDTO msgNoticeWebDTO = new MsgNoticeWebDTO();

                BeanUtils.copyProperties(s, msgNoticeWebDTO);

                msgNoticeWebDTO.setMsgId(s.getId());

                List<ProjectDTO> projectDTOList = Lists.newArrayList();

                msgTargetDOS.forEach(p -> {
                    ProjectDTO projectDTO = new ProjectDTO();
                    projectDTO.setProjectId(p.getProjectId());
                    projectDTO.setProjectName(p.getProjectName());
                    projectDTO.setTargetId(p.getId());
                    projectDTOList.add(projectDTO);
                });

                MsgTargetDO msgTargetDO1 = msgTargetDOS.get(0);


                msgNoticeWebDTO.setProjectDTOList(projectDTOList);

                msgNoticeWebDTO.setRealestateId(msgTargetDO1.getRealestateId());
                msgNoticeWebDTO.setRealestateName(msgTargetDO1.getRealestateName());

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
            msgTargetService.removeByIds(targetIds);

            this.baseMapper.deleteById(id);
        }

    }

    @Override
    public void releaseState(String id, Integer releaseFlag) {

        MsgNoticeDO msgNoticeDO = this.baseMapper.selectById(id);

        if (msgNoticeDO != null) {
            msgNoticeDO.setReleaseFlag(releaseFlag);

            msgNoticeDO.setSendTime(LocalDateTime.now());

            msgNoticeDO.setReleaseUser(TokenContextUtil.getUserId());
            saveOrUpdate(msgNoticeDO);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMsg(MsgWebUpdateDTO requestBody) {
        //1.先修改msgNotice的标题和内容
        // 2.在修改msgTarget的项目id，全部删除，然后新增
        String msgId = requestBody.getMsgId();
        List<ProjectDTO> projectNames = requestBody.getProjectDTOList();
        MsgNoticeDO msgNoticeDO = this.baseMapper.selectById(msgId);
        if (msgNoticeDO != null) {
            msgNoticeDO.setContent(requestBody.getContent());
            msgNoticeDO.setName(requestBody.getName());
            msgNoticeDO.setUpdateTime(LocalDateTime.now());
            saveOrUpdate(msgNoticeDO);

            List<MsgTargetDO> msgTargetDOS = msgTargetService.getListById(msgId);

            msgTargetService.removeByIds(msgTargetDOS.stream().map(s -> s.getId()).collect(Collectors.toList()));


            //存入target对象
            List<MsgTargetDO> msgTargets = projectNames.stream().map(
                    sa -> MsgTargetFactory.newMsgTarget(sa, msgId, MsgTypeEnum.NOTICE,
                            requestBody.getRealestateId(), requestBody.getRealestateName()))
                    .collect(Collectors.toList());

            msgTargetService.saveBatch(msgTargets);

        }

    }

    @Override
    public List<MsgNoticeDO> queryMsgNoticeByProjectIdForScreen(String projectId) {

        return this.baseMapper.queryMsgNoticeByProjectIdForScreen(projectId);
    }


}
