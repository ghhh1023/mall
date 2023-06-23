package com.gh.mall.mapper;

import com.gh.mall.entity.CartInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CartInfoMapper extends Mapper<CartInfo> {

    /*根据用户id获取购物车列表*/
    List<CartInfo> findCartByUserId(Long userId);

    /*删除某用户购物车里的某个商品*/
    @Delete("delete from cart_info where userId = #{userId} and goodsId = #{goodsId}")
    int deleteGoods(@Param("userId") Long useId,@Param("goodsId") Long goodsId);
}