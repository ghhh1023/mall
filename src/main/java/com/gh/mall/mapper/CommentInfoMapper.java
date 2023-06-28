package com.gh.mall.mapper;

import com.gh.mall.entity.CommentInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface CommentInfoMapper extends Mapper<CommentInfo> {

    /*��������ģ����ѯ�����б�*/
    List<CommentInfo> findByContent(@Param("name") String name);

    /*������Ʒid��ȡ�����б�*/
    @Select("select a.*,u.name as userName from comment_info as a left join user_info as u on a.userId=u.id where goodsId = #{goodsid}")
    List<CommentInfo> findByGoodsid(@Param("goodsid") Long goodsid);

    /*��������*/
    @Select("select count(*) from comment_info")
    Integer count();
}