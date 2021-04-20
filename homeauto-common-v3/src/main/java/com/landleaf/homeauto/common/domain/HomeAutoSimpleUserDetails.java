package com.landleaf.homeauto.common.domain;

import lombok.Data;

/**
 * @ClassName HomeautoUserDetails
 * @Description: 扩展springsecurity的UserDetail
 * @Author wyl
 * @Date 2020/6/9
 * @Version V1.0
 **/
@Data
public class HomeAutoSimpleUserDetails  {

    private static final long serialVersionUID = -7979833199343460404L;
    private String username;
    private String userId;
    private String source;
    private String openId;


}


