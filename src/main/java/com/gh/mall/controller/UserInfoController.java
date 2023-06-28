package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.service.UserInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/userInfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<UserInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
//        System.out.println(pageNum + " " + pageSize + " " + name);
        PageInfo<UserInfo> list = userInfoService.findPage(pageNum,pageSize,name);
        System.out.println(list.getSize());
        return Result.success(userInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * add
     */
    @PostMapping("/add")
    public Result<UserInfo> add(@RequestBody UserInfo userInfo){
        userInfoService.add(userInfo);
        return Result.success(userInfo);
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result update(@RequestBody UserInfo userInfo){
        userInfoService.update(userInfo);
        return Result.success();
    }

    /**
     * delete
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        userInfoService.delete(id);
        return Result.success();
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/{id}")
    public Result<UserInfo> detail(@PathVariable Long id){
        UserInfo userInfo = userInfoService.findById(id);
        return Result.success(userInfo);
    }

}
