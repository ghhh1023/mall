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
 * ��̨ͳ��
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
     * ͳ�Ƹ�������
     */
    @GetMapping("getTotal")
    public Result<Map<String,Object>> getTotal(){
        Map<String,Object> map = new HashMap<>();
        //��ȡ�û�����
        map.put("totalUser",userInfoService.count());
        //��ȡ��������
        map.put("totalComment",commentInfoService.count());
        //��ȡ�ܽ��׶�
        map.put("totalPrice",orderInfoService.totalPrice());
        return Result.success(map);
    }


}
