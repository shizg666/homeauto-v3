package com.landleaf.homeauto.center.oauth.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.center.oauth.util.WechatUtil;
import com.landleaf.homeauto.common.constance.RedisCacheConst;
import com.landleaf.homeauto.common.context.TokenContext;
import com.landleaf.homeauto.common.controller.BaseController;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 微信账号登录信息记录 前端控制器
 * </p>
 *
 * @author wenyilu
 * @since 2020-07-20
 */
@RestController
@RequestMapping("/auth/wechat")
public class HomeAutoWechatRecordController extends BaseController {

    @Autowired
    private IHomeAutoWechatRecordService homeAutoWechatRecordService;
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 微信用户绑定家庭
     */
    @ApiOperation(value = "手机号解密绑定", notes = "")
    @PostMapping("/v1/wx/bindPhone")
    public Response bingPhoneNum(@RequestParam(value = "openId") String openId,
                                 @RequestParam(value = "encrypteData", required = false) String encrypteData,
                                 @RequestParam(value = "iv", required = false) String iv) {

        HomeAutoWechatRecord record = this.homeAutoWechatRecordService.getRecordByOpenId(openId);
        if (record == null) {
            throw new BusinessException("根据openid查询不到信息！openID：" + openId);
        }
        JSONObject dataInfo = WechatUtil.getEncryptedDataInfo(encrypteData, record.getSessionKey(), iv);
        if (dataInfo == null) {
            throw new BusinessException("数据解密失败！");
        }
        String phone = dataInfo.getString("phoneNumber");
        // 绑定手机号
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.bindOpenId(openId, phone);
        // 更新token里的userId
        String key = String.format(RedisCacheConst.USER_TOKEN, UserTypeEnum.WECHAT.getType(), openId);
        Object hget = redisUtil.hget(key, TokenContext.getToken().getAccessToken());
        if (hget != null) {
            HomeAutoToken homeAutoToken = JSON.parseObject(JSON.toJSONString(hget), HomeAutoToken.class);
            homeAutoToken.setUserId(customer.getId());
            homeAutoToken.setUserName(customer.getName());
            redisUtil.hset(key, homeAutoToken.getAccessToken(), homeAutoToken);
        }
        return returnSuccess();
    }
}
