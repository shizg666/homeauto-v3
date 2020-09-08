package com.landleaf.homeauto.center.device.service;


/**
 * @Description 极光短信调用接口
 * @Author zhanghongbin
 * @Date 2020/9/8 12:15
 */
public interface IJSMSService {




    /**
     * //家庭组新增用户
     * @param projectName
     * @param username
     * @param mobile
     */
    void groupAddUser(String projectName,String username,String mobile) ;



}
