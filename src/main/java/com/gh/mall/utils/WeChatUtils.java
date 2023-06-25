package com.gh.mall.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class WeChatUtils{

    private String getOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    private String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
    private String getUserPhoneNumberUrl = "https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=ACCESS_TOKEN&code=CODE";

    @Value("${wechat.app-id}")
    private String appId;

    @Value("${wechat.app-secret}")
    private String appSecret;

    public String getOpenId(String code) {
        String url = getOpenIdUrl.replace("APPID",appId).replace("SECRET",appSecret).replace("JSCODE",code);
        return HttpUtils.doGet(url);
    }

    // 注：获取手机号的code与获取openid的code不能混用
    public String getMobile(String code) {
        String token = getAccessToken();
        if (StrUtil.isBlank(token)) {
            return "";
        }
        String url = getUserPhoneNumberUrl.replace("ACCESS_TOKEN", token).replace("CODE", code);
        return HttpUtils.doGet(url);
    }

    public String getAccessToken() {
        String url = getAccessTokenUrl.replace("APPID",appId).replace("SECRET",appSecret);
        return HttpUtils.doGet(url);
    }
}
