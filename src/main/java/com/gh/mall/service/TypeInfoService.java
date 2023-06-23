package com.gh.mall.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.TypeInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.mapper.TypeInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service about user
 */
@Service
public class TypeInfoService {
    @Resource
    private TypeInfoMapper typeInfoMapper;

    public PageInfo<TypeInfo> findPage(Integer pageNum, Integer pageSize, String name){
        PageHelper.startPage(pageNum, pageSize);
        List<TypeInfo> list = typeInfoMapper.findByName(name);
        return PageInfo.of(list);
    }

    /**
     * add
     */

    public TypeInfo add(TypeInfo typeInfo){
        typeInfoMapper.insertSelective(typeInfo);
        return typeInfo;
    }

    /**
     * update
     */
    public void update(TypeInfo typeInfo){
        typeInfoMapper.updateByPrimaryKeySelective(typeInfo);
    }

    /**
     * delete
     */
    public void delete(Long id){
        System.out.println(id);
        typeInfoMapper.deleteByPrimaryKey(id);
    }

    public TypeInfo findById(Long id){
        return typeInfoMapper.selectByPrimaryKey(id);
    }
}
