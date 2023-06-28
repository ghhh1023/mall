package com.gh.mall.controller;

import cn.hutool.core.util.StrUtil;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gh.mall.common.Common;
import com.gh.mall.common.Result;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.service.RedisService;
import com.gh.mall.service.UserInfoService;
import com.gh.mall.service.WeChatTokenService;
import com.gh.mall.utils.HttpUtils;
import com.gh.mall.utils.JwtUtils;
import com.gh.mall.utils.WeChatUtils;
import com.gh.mall.vo.Reset;
import com.gh.mall.vo.WeChatTokenVo;
import io.lettuce.core.ScriptOutputType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class AccountController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private WeChatTokenService weChatTokenService;

    @Resource
    private RedisService redisService;

    @Resource
    private WeChatUtils weChatUtils;


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
     * 根据code获取openId去检查是否绑定平台用户
     * @param code
     * @return JSONObject
     */
    @GetMapping("/checkBind")
    public Result checkBind(@RequestParam String code){
        Map map = new LinkedHashMap();
        try {
            //获取openId信息
            String resultStr = weChatUtils.getOpenId(code);
            JSONObject resultObj = JSON.parseObject(resultStr);
            if(StringUtils.isNotEmpty(resultObj.getString("openid"))){
                //根据openId判断是否和平台用户绑定
                String openId = resultObj.getString("openid");
                String sessionKey = resultObj.getString("session_key");
                Integer uid = weChatTokenService.getUidByOpenId(openId);
                if(uid != null){
                    //已绑定
//                    try {
                        String token= JwtUtils.createToken(openId,sessionKey);
                        //将uuid和user以键值对的形式存放在redis中
                        redisService.set("token "+ token, sessionKey + openId,60*60*24*7);
                        map.put("token",token);
                        UserInfo userInfo = userInfoService.findById(uid);
                        map.put("userInfo", userInfo);
                        map.put("code", 0);
                        return Result.success(map);
//                    }catch (Exception e){
//                        return Result.error("-2","登录失败,请检查用户名或密码");
//                    }
                }else{
                    //未绑定
                    System.out.println("未绑定");
                    return Result.success();
                }
            }else{
                map.put("code",resultObj.getString("errcode"));
                map.put("msg",resultObj.getString("errmsg"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.error(map);
    }

    /**
     * reset
     */
    @PostMapping("/resetPassword")
    public Result<UserInfo> resetPassword(@RequestBody Reset userInfo, HttpServletRequest request){
        return Result.success(userInfoService.resetPassword(userInfo.getName(),userInfo.getPassword(), userInfo.getNewPassword()));
    }

    /**
     * logout
     */
    @GetMapping("/logout")
    public Result<UserInfo> logout(HttpServletRequest request){
        request.getSession().setAttribute(Common.USER_INFO,null);
        return Result.success();
    }

    /**
     * 小程序端用户注册
     */
    @PostMapping("/register")
    public Result<UserInfo> register(@RequestBody UserInfo userInfo, HttpServletRequest request){
        System.out.println("hello");
        if(StrUtil.isBlank(userInfo.getName())||StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        UserInfo register = userInfoService.add(userInfo);
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,register);
        session.setMaxInactiveInterval(60*60*24);
        return Result.success(register);
    }

    /**
     * 判断用户是否登录
     */
    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request){
        Object user = request.getSession().getAttribute(Common.USER_INFO);
        if(user == null){
            return Result.error("401","未登录");
        }
        return Result.success(user);
    }

    /**
     * 修改密码
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Reset userInfo, HttpServletRequest request){
        Object user1 = request.getSession().getAttribute(Common.USER_INFO);
        if(user1 == null){
            return Result.error("401","未登录");
        }
        UserInfo user = (UserInfo)user1;
        String oldPassword = SecureUtil.md5(userInfo.getPassword());
        if(!oldPassword.equals((user.getPassword()))){
            return Result.error(ResultCode.USER_ACCOUNT_ERROR.code,ResultCode.USER_ACCOUNT_ERROR.msg);
        }
        user.setPassword(SecureUtil.md5(userInfo.getNewPassword()));
        userInfoService.update(user);
        //清空session，让用户重新登陆
        request.getSession().setAttribute(Common.USER_INFO,null);
        return Result.success();
    }
}
