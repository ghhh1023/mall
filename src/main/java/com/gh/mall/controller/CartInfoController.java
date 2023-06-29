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
    @PutMapping
    public Result<CartInfo> add(@RequestBody CartInfo cartInfo){
        return Result.success(cartInfoService.add(cartInfo));
    }

    /**
     * ��ѯĳ�û��Ĺ��ﳵ������ҳ��
     */
    @GetMapping("/findAll")
    public Result<List<GoodsInfo>> findAll(@RequestParam Long userId){
        return Result.success(cartInfoService.findAll(userId));

    }

    /**
     * ɾ��ĳ�û����ﳵ���ĳ����Ʒ
     */
    @DeleteMapping("/goods/{userId}/{goodsId}")
    public Result deleteGoods(@PathVariable Long userId,@PathVariable Long goodsId){
        cartInfoService.deleteGoods(userId, goodsId);
        return Result.success();
    }

    /**
     * ��ѯ���ﳵ�б�
     */
    @GetMapping("/page")
    public Result<PageInfo<CartInfo>> findPages(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                 @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                                 HttpServletRequest request){
        return Result.success(cartInfoService.findPageDetails(pageNum,pageSize,request));
    }

    /**
     * ���ݹ��ﳵidɾ��
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        cartInfoService.delete(id);
        return Result.success();
    }
}
