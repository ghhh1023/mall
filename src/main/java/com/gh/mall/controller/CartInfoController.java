package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.CartInfo;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.entity.OrderInfo;
import com.gh.mall.service.CartInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车控制器
 */
@RestController
@RequestMapping(value = "/cartInfo")
public class CartInfoController {

    @Resource
    private CartInfoService cartInfoService;

    /**
     * 添加购物车
     */
    @PutMapping
    public Result<CartInfo> add(@RequestBody CartInfo cartInfo){
        return Result.success(cartInfoService.add(cartInfo));
    }

    /**
     * 查询某用户的购物车（不分页）
     */
    @GetMapping("/findAll")
    public Result<List<GoodsInfo>> findAll(@RequestParam Long userId){
        return Result.success(cartInfoService.findAll(userId));

    }

    /**
     * 删除某用户购物车里的某个商品
     */
    @DeleteMapping("/goods/{userId}/{goodsId}")
    public Result deleteGoods(@PathVariable Long userId,@PathVariable Long goodsId){
        cartInfoService.deleteGoods(userId, goodsId);
        return Result.success();
    }

    /**
     * 查询购物车列表
     */
    @GetMapping("/page")
    public Result<PageInfo<CartInfo>> findPages(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                 @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                                 HttpServletRequest request){
        return Result.success(cartInfoService.findPageDetails(pageNum,pageSize,request));
    }

    /**
     * 根据购物车id删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        cartInfoService.delete(id);
        return Result.success();
    }
}
