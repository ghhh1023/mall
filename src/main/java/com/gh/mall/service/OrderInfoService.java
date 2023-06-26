package com.gh.mall.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.gh.mall.common.ResultCode;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.entity.OrderGoodsRel;
import com.gh.mall.entity.OrderInfo;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.mapper.OrderGoodsRelMapper;
import com.gh.mall.mapper.OrderInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ������صķ�����
 */
@Service
public class OrderInfoService {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private GoodsInfoService goodsInfoService;

    @Resource
    private CartInfoService cartInfoService;

    @Resource
    private OrderGoodsRelMapper orderGoodsRelMapper;


    /**
     * �µ�
     */
    @Transactional
    public OrderInfo add(OrderInfo orderInfo){
        //1������������Ķ�����Ϣ���û���Ϣ���ŵ�orderInfo��
        Long userId = orderInfo.getUserid();
        //����id:�û�id+��ǰ������ʱ��+4λ��ˮ��
        String orderId = userId + DateUtil.format(new Date(),"yyyyMMddHHmm") + RandomUtil.randomNumbers(8);
        orderInfo.setOrderid(orderId);
        //�û����
        UserInfo userInfo = userInfoService.findById(userId);
        orderInfo.setLinkaddress(userInfo.getAddress());
        orderInfo.setLinkman(userInfo.getNickname());
        orderInfo.setLinkphone(userInfo.getPhone());

        //2�����涩����
        orderInfo.setCreatetime(DateUtil.formatDateTime(new Date()));
        orderInfoMapper.insertSelective(orderInfo);
        List<OrderInfo> orderInfoList = orderInfoMapper.findByOrderId(orderId);

        //3����ѯ�����������Ʒ�б�����
        List<GoodsInfo> goodsList = orderInfo.getGoodsList();
        for(GoodsInfo orderGoodsVO : goodsList){
            Long goodsId = orderGoodsVO.getId();
            //��Ʒ��Ϣ
            GoodsInfo goodsDetail = goodsInfoService.findById(goodsId);
            if(goodsDetail == null){
                continue;
            }
            Integer orderCount = orderGoodsVO.getCount()==null?0:orderGoodsVO.getCount();  //�������
            Integer goodsCount = goodsDetail.getCount() ==null?0:goodsDetail.getCount();   //�ж��ٿ��
            //4���ۿ��
            if(orderCount>goodsCount){
                throw new CustomException(ResultCode.ORDER_PAY_ERROR);
            }
            goodsDetail.setCount(goodsCount-orderCount);
            //5����������
            int sales = goodsDetail.getSales()==null?0:goodsDetail.getSales();
            goodsDetail.setSales(sales+orderCount);
            goodsInfoService.update(goodsDetail);
            //6����Ʒ�������������ӹ�ϵ
            OrderGoodsRel orderGoodsRel = new OrderGoodsRel();
            orderGoodsRel.setOrderid(orderInfoList.get(0).getId());
            orderGoodsRel.setGoodsid(goodsId);
            orderGoodsRel.setCount(orderCount);
            orderGoodsRelMapper.insertSelective(orderGoodsRel);
        }

        //7����չ��ﳵ
        cartInfoService.empty(userId);
        return orderInfo;
    }

    /**
     * �����ն��û�id��״̬��ѯ�����б�
     */
    public PageInfo<OrderInfo> findFrontPages(Long userId,String state,Integer pageNum,Integer pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<OrderInfo> orderInfos;
        if(userId==null){
            orderInfos = new ArrayList<>();
        }else {
            orderInfos = orderInfoMapper.findByEndUserId(userId,state);
        }
        for(OrderInfo orderInfo : orderInfos){
            packOrder(orderInfo);
        }
        return PageInfo.of(orderInfos);
    }

    /**
     * ��װ�������û�����Ʒ��Ϣ
     */
    private void packOrder(OrderInfo orderInfo){
        //�û���Ϣ
        orderInfo.setUserInfo(userInfoService.findById(orderInfo.getUserid()));
        //��Ʒ��Ϣ
        Long orderId = orderInfo.getId();
        List<OrderGoodsRel> rels = orderGoodsRelMapper.findByOrderId(orderId);
        List<GoodsInfo> goodsInfoList = new ArrayList<>();
        for (OrderGoodsRel rel : rels){
            GoodsInfo goodsInfo = goodsInfoService.findById(rel.getGoodsid());
            if(goodsInfo!=null){
                //ע�������count���û�������Ʒ��������������Ʒ�Ŀ��
                goodsInfo.setCount(rel.getCount());
                goodsInfoList.add(goodsInfo);
            }
        }
        orderInfo.setGoodsList(goodsInfoList);
    }
}
