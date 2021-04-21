package com.landleaf.homeauto.center.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.landleaf.homeauto.center.oauth.cache.SysTypePermissionsProvider;
import com.landleaf.homeauto.center.oauth.mapper.SysPermissionMapper;
import com.landleaf.homeauto.center.oauth.service.ISysPermissionService;
import com.landleaf.homeauto.center.oauth.service.ISysRolePermissionService;
import com.landleaf.homeauto.center.oauth.service.ISysRoleService;
import com.landleaf.homeauto.center.oauth.service.ISysUserRoleService;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionMenuAndPageDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionMenuDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.syspermission.SysPermissionPageDTO;
import com.landleaf.homeauto.common.domain.po.oauth.SysPermission;
import com.landleaf.homeauto.common.domain.po.oauth.SysRole;
import com.landleaf.homeauto.common.domain.po.oauth.SysRolePermission;
import com.landleaf.homeauto.common.domain.po.oauth.SysUserRole;
import com.landleaf.homeauto.common.domain.vo.oauth.TreeNodeVO;
import com.landleaf.homeauto.common.enums.StatusEnum;
import com.landleaf.homeauto.common.enums.oauth.PermissionTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.landleaf.homeauto.common.constant.enums.ErrorCodeEnumConst.*;

/**
 * <p>
 * 后台账号操作权限表 服务实现类
 * </p>
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private SysTypePermissionsProvider sysTypePermissionsProvider;
    @Autowired
    private ISysUserRoleService sysUserRoleService;
    @Autowired
    private ISysRoleService sysRoleService;

    @Override
    public List<SysPermission> getSysUserPermissions(String userId, Integer permissionType) {
        List<SysPermission> result = Lists.newArrayList();
        SysUserRole userRole = sysUserRoleService.getByUserAndRole(userId);
        if (userRole != null) {
            SysRole role = sysRoleService.getById(userRole.getRoleId());
            //角色必须可用才行
            if (role != null && role.getStatus() == StatusEnum.ACTIVE.getType()) {
                List<SysRolePermission> rolePermissions = sysRolePermissionService.getRolePermissionByRole(role.getId());
                if (!CollectionUtils.isEmpty(rolePermissions)) {
                    List<String> permisssionIds = Lists.newArrayList();
                    permisssionIds.addAll(rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList()));
                    List<SysPermission> allSysPermissions = sysTypePermissionsProvider.getAllSysPermissions(permissionType);
                    if (!CollectionUtils.isEmpty(permisssionIds) && !CollectionUtils.isEmpty(allSysPermissions)) {
                        result.addAll(allSysPermissions.stream().filter(i -> permisssionIds.contains(i.getId())).collect(Collectors.toList()));
                    }
                }

            }
        }
        return result;
    }


    @Override
    public List<SysPermission> queryPermissionByIds(List<String> permissionIds) {
        List<SysPermission> result = Lists.newArrayList();
        Collection<SysPermission> sysPermissions = listByIds(permissionIds);
        if (!CollectionUtils.isEmpty(sysPermissions)) {
            result.addAll(sysPermissions);
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean savePermission(SysPermission permission) {
        if (!saveOrUpdateValidParams(permission, false)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        save(permission);
        return true;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updatePermission(SysPermission permission) {

        if (!saveOrUpdateValidParams(permission, true)) {
            throw new BusinessException(CHECK_PARAM_ERROR);
        }
        return updateById(permission);
    }


    @Override
    public List<TreeNodeVO> listUserPermissions(String userId, Integer permissionType) {
        //放入缓存中去
        Set<SysPermission> permissions = Sets.newHashSet();
        List<SysPermission> sysUserPermissions = getSysUserPermissions(userId, permissionType);
        for (SysPermission sysUserPermission : sysUserPermissions) {
            permissions.add(sysUserPermission);
        }
        //将菜单进行转换成前台菜单需要的
        List<TreeNodeVO> treeNodeVOS = convertPermissions2Tree(permissions);

        return treeNodeVOS;
    }

    @Override
    public List<TreeNodeVO> listAllPermissions(Integer permissionType) {
        List<TreeNodeVO> results = Lists.newArrayList();
        //所有菜单的去重集合
        Set<SysPermission> allPermission = new HashSet<SysPermission>();
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        if (permissionType != null) {
            queryWrapper.eq("permission_type", permissionType);
        }
        List<SysPermission> all = list(queryWrapper);
        for (SysPermission permission : all) {
            allPermission.add(permission);
        }
        if (!CollectionUtils.isEmpty(allPermission)) {
            //将菜单进行转换成前台菜单需要的
            results = convertPermissions2Tree(allPermission);
        }
        return results;
    }

    /**
     * 过滤掉不包含该名称的节点
     * @param name
     * @return
     */
    @Override
    public List<TreeNodeVO> findPermissionsByName(String name) {
        List<TreeNodeVO> data = Lists.newArrayList();
        List<TreeNodeVO> treeNodeVOS = listAllPermissions(null);
        if(StringUtils.isEmpty(name)){
            return treeNodeVOS;
        }
        Set<String> HAV_FILTER_PERMISSION= Sets.newHashSet();
        addIncludePermission(data,treeNodeVOS,name,HAV_FILTER_PERMISSION);
        return data;
    }

    private void addIncludePermission(List<TreeNodeVO> data, List<TreeNodeVO> treeNodeVOS, String name, Set<String> HAV_FILTER_PERMISSION) {
        if(!CollectionUtils.isEmpty(treeNodeVOS)){
            for (TreeNodeVO item : treeNodeVOS) {
                String permissionName = item.getPermissionName();
                if(!StringUtils.isEmpty(permissionName)&&permissionName.contains(name)){
                    TreeNodeVO addVo = new TreeNodeVO();
                    BeanUtils.copyProperties(item,addVo);
                    if(!HAV_FILTER_PERMISSION.contains(permissionName)){
                        //这里要递归放入它下面所有的permissionName
                        addSelfAndChildName(item,HAV_FILTER_PERMISSION);
                        data.add(addVo);
                    }
                }
                List<TreeNodeVO> children = item.getChildren();
                addIncludePermission(data,children,name, HAV_FILTER_PERMISSION);
            }
        }
    }

    private void addSelfAndChildName(TreeNodeVO item, Set<String> HAV_FILTER_PERMISSION) {
        HAV_FILTER_PERMISSION.add(item.getPermissionName());
        List<TreeNodeVO> children = item.getChildren();
        if(!CollectionUtils.isEmpty(children)){
            for (TreeNodeVO child : children) {
                addSelfAndChildName(child,HAV_FILTER_PERMISSION);
            }
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(List<String> ids) {
        int count = sysRolePermissionService.count(new QueryWrapper<SysRolePermission>().in("permission_id", ids));
        if (count > 0) {
            throw new BusinessException(PERMISSION_BIND_ROLE_ERROR);
        }
        return removeByIds(ids);
    }

    @Override
    public SysPermissionMenuAndPageDTO listUserMenuAndPagePermissions(String userId) {
        SysPermissionMenuAndPageDTO result = new SysPermissionMenuAndPageDTO();
        List<SysPermission> menus = sysPermissionService.getSysUserPermissions(userId, PermissionTypeEnum.MENU.getType());
        List<SysPermission> pages = sysPermissionService.getSysUserPermissions(userId, PermissionTypeEnum.PAGE.getType());
        if (!CollectionUtils.isEmpty(pages)) {
            result.setPages(pages.stream().map(i -> {
                SysPermissionPageDTO buttonDTO = new SysPermissionPageDTO();
                BeanUtils.copyProperties(i, buttonDTO);
                return buttonDTO;
            }).collect(Collectors.toList()));
        }
        if (!CollectionUtils.isEmpty(menus)) {
            result.setMenus(menus.stream().map(i -> {
                SysPermissionMenuDTO menuDTO = new SysPermissionMenuDTO();
                BeanUtils.copyProperties(i, menuDTO);
                return menuDTO;
            }).collect(Collectors.toList()));
        }
        return result;
    }


    private List<TreeNodeVO> convertPermissions2Tree(Set<SysPermission> permissions) {
        if (permissions == null) {
            return null;
        }
        List<TreeNodeVO> tree = new ArrayList<TreeNodeVO>(permissions.size());
        setChildren(tree, permissions, null, null);
        Collections.sort(tree);
        return tree;
    }

    private void setChildren(List<TreeNodeVO> tree, Set<SysPermission> permissions, SysPermission parentPermission, TreeNodeVO parentNode) {
        for (SysPermission permission : permissions) {
            //添加第一层目录
            if (parentPermission == null) {
                if (org.apache.commons.lang3.StringUtils.isEmpty(permission.getPid())) {
                    TreeNodeVO node = convertResource2TreeNode(permission);
                    tree.add(node);
                    //同时递归设置下层目录
                    setChildren(tree, permissions, permission, node);
                }
            } else {
                //将节点的上层目录和给的的父级目录相同时，添加为其子节点
                if (org.apache.commons.lang3.StringUtils.equals(parentPermission.getId(), permission.getPid())) {
                    TreeNodeVO node = convertResource2TreeNode(permission);
                    parentNode.addChild(node);

                    //同时递归设置下层
                    setChildren(tree, permissions, permission, node);
                }
            }
        }
    }

    private TreeNodeVO convertResource2TreeNode(SysPermission permission) {
        if (permission == null) {
            return null;
        }
        TreeNodeVO node = new TreeNodeVO();
        BeanUtils.copyProperties(permission, node);
        return node;
    }

    private boolean saveOrUpdateValidParams(SysPermission params, boolean update) {
        if (params == null) {
            return false;
        }
        String id = params.getId();

        if (StringUtils.isEmpty(id) && update) {
            return false;
        }
        if (org.apache.commons.lang3.StringUtils.isEmpty(params.getPermissionName()) ||
                params.getPermissionType() == null||
                org.apache.commons.lang3.StringUtils.isEmpty(params.getPermissionCode()) ) {
            return false;
        }
        //校验权限名称唯一性
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(i->i.eq("permission_name", params.getPermissionName())
                .or()
                .eq("permission_code", params.getPermissionCode()));
        if (update) {
            List<String> ids = Lists.newArrayList();
            ids.add(params.getId());
            queryWrapper.notIn("id", ids);
        }
        int count = count(queryWrapper);
        if (count > 0) {
            throw new BusinessException(PERMISSION_EXIST_ERROE);
        }
        return true;
    }
}
