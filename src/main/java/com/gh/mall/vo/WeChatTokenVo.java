package com.gh.mall.vo;

public class WeChatTokenVo {
    // 用户id
    private Integer uid;

    // 微信小程序OpenID
    private String openId;

    // 微信小程序sessionKey
    private String sessionKey;

    public WeChatTokenVo() {
    }

    public WeChatTokenVo(int uid, String openId, String sessionKey) {
        this.uid = uid;
        this.openId = openId;
        this.sessionKey = sessionKey;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
