package com.gh.mall.mapper;

import com.gh.mall.entity.CartInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CartInfoMapper extends Mapper<CartInfo> {

    /*根据用户id获取购物车列表*/
    List<CartInfo> findCartByUserId(Long userId);
}