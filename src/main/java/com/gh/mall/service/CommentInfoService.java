package com.gh.mall.service;

import cn.hutool.core.date.DateUtil;
import com.gh.mall.entity.CommentInfo;
import com.gh.mall.mapper.CommentInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * ��Ʒ���۷�����
 */
@Service
public class CommentInfoService {

    @Resource
    private CommentInfoMapper commentInfoMapper;

    /**
     * �¼�һ������
     */
    public CommentInfo add(CommentInfo commentInfo){
        commentInfo.setCreatetime(DateUtil.formatDateTime(new Date()));
        commentInfoMapper.insertSelective(commentInfo);
        return commentInfo;
    }

    /**
     * ��������ģ����ѯ�����б�
     */
    public PageInfo<CommentInfo> findPage(Integer pageNum,Integer pageSize,String name){
        PageHelper.startPage(pageNum,pageSize);
        List<CommentInfo> list = commentInfoMapper.findByContent(name);
        return PageInfo.of(list);
    }

    /**
     * ɾ��һ������
     */
    public void delete(Long id) {
        commentInfoMapper.deleteByPrimaryKey(id);
    }

    /**
     * ������Ʒid��ȡ�����б�
     */
    public List<CommentInfo> findByGoodsid(Long goodsid){
        return commentInfoMapper.findByGoodsid(goodsid);
    }

    /*��������*/
    public Integer count(){
        return commentInfoMapper.count();
    }
}
