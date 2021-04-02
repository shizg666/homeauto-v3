package com.landleaf.homeauto.center.oauth.web.controller.app;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoAppCustomerService;
import com.landleaf.homeauto.center.oauth.service.IHomeAutoWechatRecordService;
import com.landleaf.homeauto.center.oauth.util.WechatUtil;
import com.landleaf.homeauto.common.constant.RedisCacheConst;
import com.landleaf.homeauto.common.domain.HomeAutoToken;
import com.landleaf.homeauto.common.domain.Response;
import com.landleaf.homeauto.common.domain.dto.oauth.customer.CustomerWechatLoginResDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.wechat.WechatBindPhoneRequestDTO;
import com.landleaf.homeauto.common.domain.dto.oauth.wechat.WechatSaveUserInfoRequestDTO;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoAppCustomer;
import com.landleaf.homeauto.common.domain.po.oauth.HomeAutoWechatRecord;
import com.landleaf.homeauto.common.enums.oauth.AppTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.CustomerThirdTypeEnum;
import com.landleaf.homeauto.common.enums.oauth.UserTypeEnum;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.redis.RedisUtils;
import com.landleaf.homeauto.common.web.BaseController;
import com.landleaf.homeauto.common.web.context.TokenContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信小程序控制入口
 * </p>
 *
 * @author wyl
 */
@RestController
@RequestMapping("/auth/customer/wechat")
@Api(value = "/auth/customer/wechat", tags = {"微信小程序控制入口"})
public class WechatCustomerController extends BaseController {
    @Autowired
    private IHomeAutoAppCustomerService homeAutoAppCustomerService;
    @Autowired
    private IHomeAutoWechatRecordService homeAutoWechatRecordService;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired(required = false)
    private WechatUtil wechatUtil;

    /**
     * 微信用户绑定家庭
     */
    @ApiOperation(value = "微信用户绑定家庭", notes = "微信用户绑定家庭")
    @PostMapping("/bindPhone")
    public Response<CustomerWechatLoginResDTO> bingPhoneNum(@RequestBody WechatBindPhoneRequestDTO requestDTO) {
        String openId = requestDTO.getOpenId();
        String bindAuthroizeCode=requestDTO.getBindAuthroizeCode();
        String phone=requestDTO.getPhone();
        String encrypteData=requestDTO.getEncrypteData();
        String iv=requestDTO.getIv();
        CustomerWechatLoginResDTO result = null;
        HomeAutoWechatRecord record = this.homeAutoWechatRecordService.getRecordByOpenId(openId);
        if (record == null) {
            throw new BusinessException("根据openid查询不到信息！openID：" + openId);
        }
        if (!StringUtils.equals(bindAuthroizeCode, record.getCode())) {
            throw new BusinessException(String.format("bindAuthroizeCode[%s]不正确", bindAuthroizeCode));
        }
        if(StringUtils.isEmpty(phone)){
            JSONObject dataInfo = wechatUtil.getEncryptedDataInfo(encrypteData, record.getSessionKey(), iv);
            if (dataInfo == null) {
                throw new BusinessException("数据解密失败！");
            }
            phone = dataInfo.getString("phoneNumber");
        }
        // 绑定手机号
        HomeAutoAppCustomer customer = homeAutoAppCustomerService.bindOpenId(openId, phone, AppTypeEnum.SMART.getCode(), CustomerThirdTypeEnum.WECHAT.getCode(),
                requestDTO.getName(),requestDTO.getAvatar());
        // 更新token里的userId
        String key = String.format(RedisCacheConst.USER_TOKEN, UserTypeEnum.WECHAT.getType(), openId);
        Object hget = redisUtils.hget(key, record.getAccessToken());
        if (hget != null) {
            HomeAutoToken homeAutoToken = JSON.parseObject(JSON.toJSONString(hget), HomeAutoToken.class);
            homeAutoToken.setUserId(customer.getId());
            homeAutoToken.setUserName(customer.getName());
            redisUtils.hset(key, homeAutoToken.getAccessToken(), homeAutoToken);
        }
        result = new CustomerWechatLoginResDTO(customer.getId(), customer.getName(), customer.getMobile(), customer.getAvatar(), openId, true, true, false, record.getAccessToken());
        return returnSuccess(result);
    }
    @ApiOperation(value = "保存用户信息")
    @PostMapping("/save/userinfo")
    public Response saveWechatUserInfo(@RequestBody WechatSaveUserInfoRequestDTO requestDTO ){
        String userId = TokenContext.getToken().getUserId();
        HomeAutoAppCustomer customer = new HomeAutoAppCustomer();
        BeanUtils.copyProperties(requestDTO,customer);
        customer.setId(userId);
        homeAutoAppCustomerService.updateById(customer);
        return returnSuccess();
    }
}
