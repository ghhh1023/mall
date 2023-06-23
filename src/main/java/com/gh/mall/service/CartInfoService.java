package com.gh.mall.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.gh.mall.entity.CartInfo;
import com.gh.mall.mapper.CartInfoMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * ���ﳵ����
 */
@Service
public class CartInfoService {

    @Resource
    private CartInfoMapper cartInfoMapper;

    /**
     * ���빺�ﳵ
     */
    public CartInfo add(CartInfo detailInfo){
        Long userId = detailInfo.getUserid();
        Long goodsId = detailInfo.getGoodsid();
        //�Ȳ�ѯ���ﳵ����û�и���Ʒ���о͸���������û�о����
        Example example = new Example(CartInfo.class);
        example.createCriteria().andEqualTo("userId",userId).andEqualTo("goodsId",goodsId);
        List<CartInfo> infos = cartInfoMapper.selectByExample(example);
        if(CollectionUtil.isEmpty(infos)){
            //����
            detailInfo.setCreatetime(DateUtil.formatDateTime(new Date()));
            cartInfoMapper.insertSelective(detailInfo);
        }else {
            //����
            CartInfo cartInfo = infos.get(0);
            cartInfo.setCount(cartInfo.getCount() + detailInfo.getCount());
            cartInfoMapper.updateByPrimaryKeySelective(cartInfo);
        }
        return detailInfo;
    }
}
