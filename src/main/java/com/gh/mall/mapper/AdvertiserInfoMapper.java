package com.gh.mall.mapper;

import com.gh.mall.entity.AdvertiserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface AdvertiserInfoMapper extends Mapper<AdvertiserInfo> {
    List<AdvertiserInfo> findByName(@Param("name") String name);
}