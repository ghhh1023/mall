package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.service.CommentInfoService;
import com.gh.mall.service.OrderInfoService;
import com.gh.mall.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 后台统计
 */
@RestController
@RequestMapping("/echarts")
public class EChartsController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private CommentInfoService commentInfoService;

    @Resource
    private OrderInfoService orderInfoService;

    /**
     * 统计各种总数
     */
    @GetMapping("getTotal")
    public Result<Map<String,Object>> getTotal(){
        Map<String,Object> map = new HashMap<>();
        //获取用户总数
        map.put("totalUser",userInfoService.count());
        //获取评论总数
        map.put("totalComment",commentInfoService.count());
        //获取总交易额
        map.put("totalPrice",orderInfoService.totalPrice());
        return Result.success(map);
    }


}
