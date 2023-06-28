package com.gh.mall.mapper;

import com.gh.mall.entity.CartInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CartInfoMapper extends Mapper<CartInfo> {

    /*�����û�id��ȡ���ﳵ�б�*/
    List<CartInfo> findCartByUserId(Long userId);

    /*ɾ��ĳ�û����ﳵ���ĳ����Ʒ*/
    @Delete("delete from cart_info where userId = #{userId} and goodsId = #{goodsId}")
    int deleteGoods(@Param("userId") Long useId,@Param("goodsId") Long goodsId);

    /*根据用户id删除他的购物车*/
    @Delete("delete from cart_info where userId = #{userId}")
    int deleteByUserId(@Param("userId") Long useId);

    /*查询所有人的购物车列表*/
    List<CartInfo> findAll();

}