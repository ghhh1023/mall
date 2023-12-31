package com.gh.mall.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.mapper.UserInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * Service about user
 */
@Service
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * login
     */
    public UserInfo login(String name,String password){
        //判断数据库里是否有该用户
        List<UserInfo> list = userInfoMapper.findByName(name);
        if(CollectionUtil.isEmpty(list)){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        //判断密码是否正确
        if(!SecureUtil.md5(password).equals(list.get(0).getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return list.get(0);
    }

    /**
     * reset
     */
    public UserInfo resetPassword(String name,String password,String newPassword){
        //判断数据库里是否有该用户
        List<UserInfo> list = userInfoMapper.findByName(name);
        if(CollectionUtil.isEmpty(list)){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        System.out.println(password);
        //判断密码是否正确
        if(!SecureUtil.md5(password).equals(list.get(0).getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        System.out.println(newPassword);
        list.get(0).setPassword(SecureUtil.md5(newPassword));
        userInfoMapper.updateByPrimaryKeySelective(list.get(0));
        return list.get(0);
    }

    public PageInfo<UserInfo> findPage(Integer pageNum, Integer pageSize, String name){
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> list = userInfoMapper.findByName(name);
        return PageInfo.of(list);
    }

    /**
     * add
     */

    public UserInfo add(UserInfo userInfo){
        //判断数据库里是否有该用户
        List<UserInfo> list = userInfoMapper.findByName(userInfo.getName());
        if(CollectionUtil.isNotEmpty(list)){
            return list.get(0);
        }
        if(StrUtil.isBlank(userInfo.getPassword())){
            userInfo.setPassword(SecureUtil.md5("123456"));
        }else {
            userInfo.setPassword(SecureUtil.md5(userInfo.getPassword()));
        }
        userInfo.setLevel(3);
        userInfoMapper.insertSelective(userInfo);
        return userInfo;
    }

    /**
     * update
     */
    public void update(UserInfo userInfo){
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    /**
     * delete
     */
    public void delete(Long id){
        System.out.println(id);
        userInfoMapper.deleteByPrimaryKey(id);
    }

    public UserInfo findById(int id){
        return userInfoMapper.findById(id);
    }

    /**
     * 根据id获取用户
     */
    public UserInfo findById(Long id){
        return userInfoMapper.selectByPrimaryKey(id);
    }

    /*用户总数*/
    public Integer count(){
        return userInfoMapper.count();
    }
}
