package com.landleaf.homeauto.center.device.service.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.landleaf.homeauto.center.device.model.domain.msg.MsgTargetDO;
import com.landleaf.homeauto.center.device.model.mapper.MsgTargetMapper;
import com.landleaf.homeauto.center.device.service.mybatis.IMsgTargetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Service
public class MsgTargetServiceImpl extends ServiceImpl<MsgTargetMapper, MsgTargetDO> implements IMsgTargetService {

    @Override
    public List<MsgTargetDO> getList(String msgId, String projectName) {

        List<MsgTargetDO> lists = Lists.newArrayList();
        QueryWrapper<MsgTargetDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("msg_id", msgId);

        Set<MsgTargetDO> set = new HashSet<MsgTargetDO>();
        if (StringUtils.isBlank(projectName)) {

            lists = this.baseMapper.selectList(queryWrapper);

        } else {

            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("msg_id", msgId);
            queryWrapper.like("project_name", projectName);
            lists = this.baseMapper.selectList(queryWrapper);
        }

        return lists;
    }

    @Override
    public List<MsgTargetDO> getListById(String msgId) {
        List<MsgTargetDO> lists = Lists.newArrayList();
        QueryWrapper<MsgTargetDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("msg_id", msgId);

        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public List<MsgTargetDO> getByProject(String projectId) {
        List<MsgTargetDO> lists = Lists.newArrayList();
        QueryWrapper<MsgTargetDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", projectId);

        return this.baseMapper.selectList(queryWrapper);
    }
}
