package com.landleaf.homeauto.center.oauth.util;



import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.util.Base64;

/**
 * @ClassName Commonutils
 * @Description: 自定义公共工具类
 * @Author wyl
 * @Date 2020/6/9
 * @Version V1.0
 **/
public class Commonutils {


    /**
     * 解码
     *
     * @param header
     * @return
     * @throws IOException
     */
    public static String[] extractAndDecodeHeader(String header) throws IOException {
        byte[] base64Token = header.substring(6).getBytes("UTF-8");

        byte[] decoded;
        try {
            Base64.Decoder decoder = Base64.getDecoder();
            decoded = decoder.decode(base64Token);
        } catch (IllegalArgumentException var7) {
            throw new BadCredentialsException("Failed to decode basic authentication token");
        }

        String token = new String(decoded, "UTF-8");
        int delim = token.indexOf(":");
        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        } else {
            return new String[]{token.substring(0, delim), token.substring(delim + 1)};
        }
    }

    /**
     * 编码
     *
     * @return
     * @throws IOException
     */
    public static String extractAndencodeToHeader(String clientId, String clientSecret) throws IOException {

        String data = String.format("%s:%s", clientId, clientSecret);

        byte[] bytes = data.getBytes("utf-8");
        Base64.Encoder encoder = Base64.getEncoder();
        String encodeStr = encoder.encodeToString(bytes);

        return String.format("Basic %s", encodeStr);
    }

    public static void main(String[] args) {
        try {
            String s = extractAndencodeToHeader("app", "app123");

            System.out.println(s);
            String[] strings = extractAndDecodeHeader(s);
            System.out.println(strings);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
