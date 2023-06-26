package com.gh.mall.mapper;

import com.gh.mall.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderInfoMapper extends Mapper<OrderInfo> {

    /*根据订单id查询一条订单数据*/
    @Select("select * from order_info where orderId = #{orderId}")
    List<OrderInfo> findByOrderId(@Param("orderId") String orderId);
}