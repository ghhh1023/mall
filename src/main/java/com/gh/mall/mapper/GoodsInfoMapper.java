package com.gh.mall.mapper;

import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.entity.TypeInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface GoodsInfoMapper extends Mapper<GoodsInfo> {
    List<GoodsInfo> findByName(@Param("name") String name,@Param("id") Long id);
}