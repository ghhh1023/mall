package com.gh.mall.service;

import com.gh.mall.mapper.WeChatTokenMapper;
import com.gh.mall.vo.WeChatTokenVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WeChatTokenService {
    @Resource
    WeChatTokenMapper weChatTokenMapper;

    public Integer getUidByOpenId(String openId){
        return weChatTokenMapper.getUidByOpenId(openId);
    }

    public WeChatTokenVo findByOpenId(String openId){
        return weChatTokenMapper.findByOpenId(openId);
    }
}
