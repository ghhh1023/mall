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
     * ��ѯ�Ƽ���Ʒ
     * @return
     */
    @Select("select * from goods_info where recommend = '��'")
    List<GoodsInfo> findRecommendGoods();

    /**
     * ��ѯ������Ʒ
     * @return
     */
    @Select("select * from goods_info order by sales desc")
    List<GoodsInfo> findHotSalesGoods();

    /**
     * �������Ͳ�ѯ��Ʒ�б�
     */
    @Select("select * from goods_info where typeId = #{typeId}")
    List<GoodsInfo> findByType(@Param("typeId") Integer typeId);
}