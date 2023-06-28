package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.CommentInfo;
import com.gh.mall.entity.OrderInfo;
import com.gh.mall.service.CartInfoService;
import com.gh.mall.service.CommentInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 商品评价控制器
 */
@RestController
@RequestMapping(value = "/commentInfo")
public class CommentInfoController {

    @Resource
    private CommentInfoService commentInfoService;

    @PostMapping
    public Result<CommentInfo> add(@RequestBody CommentInfo commentInfo){
        commentInfoService.add(commentInfo);
        return Result.success(commentInfo);
    }

    /**
     * 查询订单信息（分页）
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<CommentInfo>> findPages(@RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                 @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                                 @PathVariable String name){
        return Result.success(commentInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * 删除一条评论
     */
    @DeleteMapping("/id")
    public Result delete(@PathVariable Long id){
        commentInfoService.delete(id);
        return Result.success();
    }

    /**
     * 根据商品id获取评论列表
     */
    @GetMapping("/all/{goodsid}")
    public Result<List<CommentInfo>> findByGoodsid(@PathVariable Long goodsid){
        return Result.success(commentInfoService.findByGoodsid(goodsid));
    }
}

