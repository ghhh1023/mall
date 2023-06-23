package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.entity.TypeInfo;
import com.gh.mall.service.TypeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/typeInfo")
public class TypeInfoController {

    @Resource
    private TypeInfoService typeInfoService;

    /**
     * 分页查询用户列表
     * @param pageNum
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page/{name}")
    public Result<PageInfo<TypeInfo>> page(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @PathVariable String name){
//        System.out.println(pageNum + " " + pageSize + " " + name);
        PageInfo<TypeInfo> list = typeInfoService.findPage(pageNum,pageSize,name);
        System.out.println(list.getSize());
        return Result.success(typeInfoService.findPage(pageNum,pageSize,name));
    }

    /**
     * add
     */
    @PostMapping("/add")
    public Result<TypeInfo> add(@RequestBody TypeInfo typeInfo){
        typeInfoService.add(typeInfo);
        return Result.success(typeInfo);
    }

    /**
     * update
     */
    @PutMapping("/update")
    public Result update(@RequestBody TypeInfo typeInfo){
        typeInfoService.update(typeInfo);
        return Result.success();
    }

    /**
     * delete
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Long id){
        typeInfoService.delete(id);
        return Result.success();
    }

    /**
     * find
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable Long id){
        return Result.success(typeInfoService.findById(id));
    }

}
