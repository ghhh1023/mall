package com.gh.mall.mapper;

import com.gh.mall.entity.OrderInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface OrderInfoMapper extends Mapper<OrderInfo> {

    /*根据订单id查询一条订单数据*/
    @Select("select * from order_info where orderId = #{orderId}")
    List<OrderInfo> findByOrderId(@Param("orderId") String orderId);

    /*根据终端用户id和状态查询订单列表*/
    List<OrderInfo> findByEndUserId(@Param("userId")Long userId,@Param("state")String state);

    /*根据主键查询*/
    @Select("select * from order_info where id = #{id}")
    OrderInfo findById(@Param("id")Long id);

    /*更新订单状态*/
    @Update("update order_info set state = #{state} where id = #{id}")
    void updateState(@Param("id")Long id, @Param("state")String state);

    /*删除订单*/
    void deleteById(Long id);
}