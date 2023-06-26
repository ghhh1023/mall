package com.gh.mall.controller;

import com.gh.mall.common.Result;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.entity.OrderInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.service.OrderInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * ����������
 */
@RestController
@RequestMapping(value = "orderInfo")
public class OrderInfoController {
    @Resource
    private OrderInfoService orderInfoService;

    /**
     * �µ�
     */
    @PostMapping
    public Result<OrderInfo> add(@RequestBody OrderInfo orderInfo){
        Long userId = orderInfo.getUserid();
        List<GoodsInfo> goodsList = orderInfo.getGoodsList();
        if(userId==null||goodsList==null||goodsList.size()==0){
            throw new CustomException(ResultCode.PARAM_ERROR);
        }
        orderInfo.setState("������");
        return Result.success(orderInfoService.add(orderInfo));
    }

    /**
     * ��ѯ������Ϣ����ҳ��
     */
    @GetMapping("/page/front")
    public Result<PageInfo<OrderInfo>> findFrontPages(@RequestParam(required = false) Long userId,
                                                      @RequestParam(required = false)String state,
                                                      @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                      @RequestParam(required = false,defaultValue = "10")Integer pageSize){
        return Result.success(orderInfoService.findFrontPages(userId,state,pageNum,pageSize));
    }

    /**
     * �޸Ķ���״̬
     */
    @PostMapping("/state/{id}/{state}")
    public Result state(@PathVariable Long id,@PathVariable String state){
        orderInfoService.changeState(id, state);
        return Result.success();
    }

    /**
     * ��ѯ������Ϣ����ҳ��
     */
    @GetMapping("/page")
    public Result<PageInfo<OrderInfo>> findFrontPages(@RequestParam(required = false) Long userId,
                                                      @RequestParam(required = false,defaultValue = "1")Integer pageNum,
                                                      @RequestParam(required = false,defaultValue = "10")Integer pageSize,
                                                      HttpServletRequest request){
        return Result.success(orderInfoService.findPages(userId,pageNum,pageSize,request));
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        orderInfoService.delete(id);
        return Result.success();
    }
}
