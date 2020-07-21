package com.landleaf.homeauto.center.oauth.util;/**
 * Create by eval on 2019/3/20
 */

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.landleaf.homeauto.common.exception.BusinessException;
import com.landleaf.homeauto.common.util.HttpClientUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WechatUtil
 * @Description TODO
 * @Author eval
 * @Date 9:44 2019/3/20
 * @Version 1.0
 */
@ConditionalOnProperty(prefix = "homeauto.security.oauth2.extend.wechat", name = "enable")
@Component
public class WechatUtil {

    private static final Logger log = LoggerFactory.getLogger(WechatUtil.class);

    @Value("${homeauto.security.oauth2.extend.wechat.appid}")
    private static String appid;
    @Value("${homeauto.security.oauth2.extend.wechat.secret}")
    private static String secret;
    @Value("${homeauto.security.oauth2.extend.wechat.getJscodeSessionUrl}")
    private static String getJscodeSessionUrl;


    public static JSONObject getSessionKeyOrOpenId(String code) {
        log.info("getSessionKeyOrOpenId，sessionKey等信息，code:{},secret:{},appid:{}", code, secret, appid);
//        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>();
        // https://mp.weixin.qq.com/wxopen/devprofile?action=get_profile&token=164113089&lang=zh_CN
        //小程序appId
        requestUrlParam.put("appid", appid);
        //小程序secret
        requestUrlParam.put("secret", secret);
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        log.info("getSessionKeyOrOpenId，sessionKey等信息，code:{},secret:{},appid:{}", code, secret, appid);
        JSONObject jsonObject = JSON.parseObject(HttpClientUtil.doGet(getJscodeSessionUrl, requestUrlParam));
        log.info("getSessionKeyOrOpenId返回数据：{}", jsonObject.toJSONString());
        return jsonObject;
    }

    public static JSONObject getEncryptedDataInfo(String encryptedData, String sessionKey, String iv) {
        log.info("手机号解密，加密数据：{}，sessionKey:{}，iv:{}", encryptedData, sessionKey, iv);
        encryptedData.replace(" ", "+");
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
            log.info("手机号解密，解密失败报错：{}", e.getMessage());
            throw new BusinessException("数据解密失败！");
        }
        return null;
    }


    public void setAppid(String appid) {
        WechatUtil.appid = appid;
    }


    public void setSecret(String secret) {
        WechatUtil.secret = secret;
    }
}
