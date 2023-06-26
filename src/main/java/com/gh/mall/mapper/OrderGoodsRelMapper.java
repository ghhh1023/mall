package com.gh.mall.mapper;

import com.gh.mall.entity.OrderGoodsRel;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderGoodsRelMapper extends Mapper<OrderGoodsRel> {

    /*根据订单id获取商品列表*/
    List<OrderGoodsRel> findByOrderId(Long orderId);
}