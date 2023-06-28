package com.gh.mall.mapper;

import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.entity.TypeInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GoodsInfoMapper extends Mapper<GoodsInfo> {
    List<GoodsInfo> findByName(@Param("name") String name,@Param("id") Long id);

    /**
     * 查询推荐商品
     * @return
     */
    @Select("select * from goods_info where recommend = '是'")
    List<GoodsInfo> findRecommendGoods();

    /**
     * 查询热卖商品
     * @return
     */
    @Select("select * from goods_info order by sales desc")
    List<GoodsInfo> findHotSalesGoods();

    /**
     * 根据类型查询商品列表
     */
    @Select("select * from goods_info where typeId = #{typeId}")
    List<GoodsInfo> findByType(@Param("typeId") Integer typeId);
}