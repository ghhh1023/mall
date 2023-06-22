package com.gh.mall.controller;

import cn.hutool.core.util.StrUtil;
import com.gh.mall.common.Common;
import com.gh.mall.common.Result;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.service.UserInfoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AccountController {

    @Resource
    private UserInfoService userInfoService;

    @PostMapping("/login")
    public Result<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if (StrUtil.isBlank(userInfo.getName()) || StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        //从数据库中查询账号密码是否正确，放到session
        UserInfo login = userInfoService.login(userInfo.getName(), userInfo.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,login);
        session.setMaxInactiveInterval(60*24);
        return Result.success(login);
    }

    /**
     * reset
     */
    @PostMapping("/resetPassword")
    public Result<UserInfo> resetPassword(@RequestBody UserInfo userInfo, HttpServletRequest request){
        return Result.success(userInfoService.resetPassword(userInfo.getName(),userInfo.getPassword(),userInfo.getPassword()));
    }
}
