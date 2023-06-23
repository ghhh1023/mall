package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.AdvertiserInfo;
import com.gh.mall.service.AdvertiserInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/advertiserInfo")
public class AdvertiserInfoController {

    @Resource
    private AdvertiserInfoService advertiserInfoService;

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<AdvertiserInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
//        System.out.println(pageNum + " " + pageSize + " " + name);
        PageInfo<AdvertiserInfo> list = advertiserInfoService.findPage(pageNum,pageSize,name);
        System.out.println(list.getSize());
        return Result.success(advertiserInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * add
     */
    @PostMapping("/add")
    public Result<AdvertiserInfo> add(@RequestBody AdvertiserInfo advertiserInfo){
        advertiserInfoService.add(advertiserInfo);
        return Result.success(advertiserInfo);
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result update(@RequestBody AdvertiserInfo advertiserInfo){
        advertiserInfoService.update(advertiserInfo);
        return Result.success();
    }

    /**
     * delete
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        advertiserInfoService.delete(id);
        return Result.success();
    }

    /**
     * find
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id){
        return Result.success(advertiserInfoService.findById(id));
    }

}
