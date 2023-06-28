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
        //�����ݿ��в�ѯ�˺������Ƿ���ȷ���ŵ�session
        UserInfo login = userInfoService.login(userInfo.getName(), userInfo.getPassword());
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,login);
        session.setMaxInactiveInterval(60*24);
        return Result.success(login);
    }

    /**
     * ����code��ȡopenIdȥ����Ƿ��ƽ̨�û�
     * @param code
     * @return JSONObject
     */
    @GetMapping("/checkBind")
    public Result checkBind(@RequestParam String code){
        Map map = new LinkedHashMap();
        try {
            //��ȡopenId��Ϣ
            String resultStr = weChatUtils.getOpenId(code);
            JSONObject resultObj = JSON.parseObject(resultStr);
            if(StringUtils.isNotEmpty(resultObj.getString("openid"))){
                //����openId�ж��Ƿ��ƽ̨�û���
                String openId = resultObj.getString("openid");
                String sessionKey = resultObj.getString("session_key");
                Integer uid = weChatTokenService.getUidByOpenId(openId);
                if(uid != null){
                    //�Ѱ�
//                    try {
                        String token= JwtUtils.createToken(openId,sessionKey);
                        //��uuid��user�Լ�ֵ�Ե���ʽ�����redis��
                        redisService.set("token "+ token, sessionKey + openId,60*60*24*7);
                        map.put("token",token);
                        UserInfo userInfo = userInfoService.findById(uid);
                        map.put("userInfo", userInfo);
                        map.put("code", 0);
                        return Result.success(map);
//                    }catch (Exception e){
//                        return Result.error("-2","��¼ʧ��,�����û���������");
//                    }
                }else{
                    //δ��
                    System.out.println("δ��");
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
     * С������û�ע��
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
     * �ж��û��Ƿ��¼
     */
    @GetMapping("/auth")
    public Result getAuth(HttpServletRequest request){
        Object user = request.getSession().getAttribute(Common.USER_INFO);
        if(user == null){
            return Result.error("401","δ��¼");
        }
        return Result.success(user);
    }

    /**
     * �޸�����
     */
    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody Reset userInfo, HttpServletRequest request){
        Object user1 = request.getSession().getAttribute(Common.USER_INFO);
        if(user1 == null){
            return Result.error("401","δ��¼");
        }
        UserInfo user = (UserInfo)user1;
        String oldPassword = SecureUtil.md5(userInfo.getPassword());
        if(!oldPassword.equals((user.getPassword()))){
            return Result.error(ResultCode.USER_ACCOUNT_ERROR.code,ResultCode.USER_ACCOUNT_ERROR.msg);
        }
        user.setPassword(SecureUtil.md5(userInfo.getNewPassword()));
        userInfoService.update(user);
        //���session�����û����µ�½
        request.getSession().setAttribute(Common.USER_INFO,null);
        return Result.success();
    }
}
