package com.gh.mall.service;

import com.gh.mall.entity.NxSystemFileInfo;
import com.gh.mall.mapper.NxSystemFileInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service about user
 */
@Service
public class NxSystemFileInfoService {
    @Resource
    private NxSystemFileInfoMapper nxSystemFileInfoMapper;
    /**
     * add
     */

    public NxSystemFileInfo add(NxSystemFileInfo nxSystemFileInfo){
        nxSystemFileInfoMapper.insertSelective(nxSystemFileInfo);
        return nxSystemFileInfo;
    }

    /**
     * update
     */
    public void update(NxSystemFileInfo nxSystemFileInfo){
        nxSystemFileInfoMapper.updateByPrimaryKeySelective(nxSystemFileInfo);
    }

    /**
     * delete
     */
    public void delete(Long id){
        System.out.println(id);
        nxSystemFileInfoMapper.deleteByPrimaryKey(id);
    }

    public NxSystemFileInfo findById(Long id){
        return nxSystemFileInfoMapper.selectByPrimaryKey(id);
    }
}
