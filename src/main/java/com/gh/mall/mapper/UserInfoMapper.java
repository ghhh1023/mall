package com.gh.mall.mapper;

import com.gh.mall.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserInfoMapper extends Mapper<UserInfo> {
    List<UserInfo> findByName(@Param("name") String name);

    int checkRepeat(@Param("column")String column,@Param("value")String value);

    UserInfo findById(@Param("id") int id);
}