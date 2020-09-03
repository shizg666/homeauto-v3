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
 *  服务实现类
 * </p>
 *
 * @author lokiy
 * @since 2019-08-12
 */
@Service
public class MsgTargetServiceImpl extends ServiceImpl<MsgTargetMapper, MsgTargetDO> implements IMsgTargetService {

    @Override
    public List<MsgTargetDO> getList(String msgId, String realestateName, List<String> projectNames) {

        List<MsgTargetDO> lists = Lists.newArrayList();
        QueryWrapper<MsgTargetDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("msg_id",msgId);

        if (StringUtils.isNotBlank(realestateName)) {
            queryWrapper.like("realestate_name", realestateName);
        }

        Set<MsgTargetDO> set = new HashSet<MsgTargetDO>();
        if (projectNames.size() <=0 ){
            lists =  this.baseMapper.selectList(queryWrapper);
        }else {

            //使用set去重
            for(String projectName:projectNames){
                if (StringUtils.isNotBlank(projectName)) {
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("msg_id", msgId);

                    if (StringUtils.isNotBlank(realestateName)) {
                        queryWrapper.like("realestate_name", realestateName);
                    }
                    queryWrapper.eq("project_name", projectName);
                    set.addAll(this.baseMapper.selectList(queryWrapper));
                }else {
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("msg_id", msgId);

                    if (StringUtils.isNotBlank(realestateName)) {
                        queryWrapper.like("realestate_name", realestateName);
                    }
                    set.addAll(this.baseMapper.selectList(queryWrapper));
                }
            }

            lists.addAll(set);

        }

        return lists;
    }

    @Override
    public List<MsgTargetDO> getListById(String msgId) {
        List<MsgTargetDO> lists = Lists.newArrayList();
        QueryWrapper<MsgTargetDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("msg_id",msgId);

        return this.baseMapper.selectList(queryWrapper);
    }
}
