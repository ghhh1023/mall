package com.gh.mall.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.crypto.SecureUtil;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.mapper.UserInfoMapper;
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
    public UserInfo resetPassword(String name,String oldPassword,String newPassword){
        //判断数据库里是否有该用户
        List<UserInfo> list = userInfoMapper.findByName(name);
        if(CollectionUtil.isEmpty(list)){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        //判断密码是否正确
        if(!SecureUtil.md5(oldPassword).equals(list.get(0).getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        list.get(0).setPassword(SecureUtil.md5(newPassword));
        userInfoMapper.updateByPrimaryKeySelective(list.get(0));
        return list.get(0);
    }
}
