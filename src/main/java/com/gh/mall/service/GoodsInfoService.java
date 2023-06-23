package com.gh.mall.service;

import cn.hutool.core.collection.CollectionUtil;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.mapper.GoodsInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Service about user
 */
@Service
public class GoodsInfoService {
    @Resource
    private GoodsInfoMapper goodsInfoMapper;

    public PageInfo<GoodsInfo> findPage(Integer pageNum, Integer pageSize, String name){
        PageHelper.startPage(pageNum, pageSize);
        List<GoodsInfo> list = goodsInfoMapper.findByName(name,null);
        return PageInfo.of(list);
    }

    /**
     * add
     */

    public GoodsInfo add(GoodsInfo goodsInfo){
        convertFileListToFields(goodsInfo);
        goodsInfoMapper.insertSelective(goodsInfo);
        return goodsInfo;
    }

    /**
     * update
     */
    public void update(GoodsInfo goodsInfo){
        convertFileListToFields(goodsInfo);
        goodsInfoMapper.updateByPrimaryKeySelective(goodsInfo);
    }

    /**
     * 页面传来的上传文件列表转换成以逗号隔开的id列表
     * @param goodsInfo
     */
    private void convertFileListToFields(GoodsInfo goodsInfo){
        List<Long> fileList = goodsInfo.getFileList();
        if(!CollectionUtil.isEmpty(fileList)){
            goodsInfo.setFieids(fileList.toString());
        }
    }
    /**
     * delete
     */
    public void delete(Long id){
        System.out.println(id);
        goodsInfoMapper.deleteByPrimaryKey(id);
    }

    public GoodsInfo findById(Long id){
        List<GoodsInfo> list = goodsInfoMapper.findByName(null,id);
        if(list==null||list.size()==0){
            return null;
        }
        return list.get(0);
    }
}
