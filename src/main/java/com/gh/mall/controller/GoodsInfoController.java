package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.service.GoodsInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/goodsInfo")
public class GoodsInfoController {

    @Resource
    private GoodsInfoService goodsInfoService;

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<GoodsInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
//        System.out.println(pageNum + " " + pageSize + " " + name);
        PageInfo<GoodsInfo> list = goodsInfoService.findPage(pageNum,pageSize,name);
        System.out.println(list.getSize());
        return Result.success(goodsInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * add
     */
    @PostMapping("/add")
    public Result<GoodsInfo> add(@RequestBody GoodsInfo goodsInfo){
        goodsInfoService.add(goodsInfo);
        return Result.success(goodsInfo);
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result update(@RequestBody GoodsInfo goodsInfo){
        goodsInfoService.update(goodsInfo);
        return Result.success();
    }

    /**
     * delete
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        goodsInfoService.delete(id);
        return Result.success();
    }

    /**
     * find
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id){
        return Result.success(goodsInfoService.findById(id));
    }

}
