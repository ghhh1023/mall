package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.CartInfo;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.service.CartInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * ���ﳵ������
 */
@RestController
@RequestMapping(value = "/cartInfo")
public class CartInfoController {

    @Resource
    private CartInfoService cartInfoService;

    /**
     * ��ӹ��ﳵ
     */
    @PostMapping
    public Result<CartInfo> add(@RequestBody CartInfo cartInfo){
        return Result.success(cartInfoService.add(cartInfo));
    }

    /**
     * ��ѯĳ�û��Ĺ��ﳵ������ҳ��
     */
    @GetMapping
    public Result<List<GoodsInfo>> findAll(@RequestParam Long userId){
        return Result.success(cartInfoService.findAll(userId));

    }
}
