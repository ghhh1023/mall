package com.gh.mall.mapper;

import com.gh.mall.entity.UserInfo;
import com.gh.mall.vo.WeChatTokenVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface WeChatTokenMapper extends Mapper<WeChatTokenVo> {

    WeChatTokenVo findByOpenId(@Param("openId")String openId);

    Integer getUidByOpenId(@Param("openId")String openId);

}