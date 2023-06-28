package com.gh.mall.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.gh.mall.common.Common;
import com.gh.mall.entity.CartInfo;
import com.gh.mall.entity.GoodsInfo;
import com.gh.mall.entity.UserInfo;
import com.gh.mall.exception.CustomException;
import com.gh.mall.mapper.CartInfoMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * ���ﳵ����
 */
@Service
public class CartInfoService {

    @Resource
    private CartInfoMapper cartInfoMapper;

    @Resource
    private GoodsInfoService goodsInfoService;

    /**
     * ���빺�ﳵ
     */
    public CartInfo add(CartInfo detailInfo){
        Long userId = detailInfo.getUserid();
        Long goodsId = detailInfo.getGoodsid();
        //�Ȳ�ѯ���ﳵ����û�и���Ʒ���о͸���������û�о����
        Example example = new Example(CartInfo.class);
        example.createCriteria().andEqualTo("userid",userId).andEqualTo("goodsid",goodsId);
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

    /*�����û�id��ȡ���ﳵ�������Ʒ�б�������������*/
    public List<GoodsInfo> findAll(Long userId){
        List<CartInfo> cartInfoList = cartInfoMapper.findCartByUserId(userId);
        List<GoodsInfo> goodsList = new ArrayList<>();
        for(CartInfo cartInfo:cartInfoList){
            long goodsId = cartInfo.getGoodsid();
            GoodsInfo goodsInfo = goodsInfoService.findById(goodsId);
            if(goodsInfo != null){
                //ע�⣬�����count���û����빺�ﳵ������
                goodsInfo.setCount(cartInfo.getCount());
                //�����id�ǹ��ﳵ����Ʒ�Ĺ�ϵid
                goodsInfo.setId(cartInfo.getGoodsid());
                goodsList.add(goodsInfo);
            }
        }
        return goodsList;
    }

    /*ɾ��ĳ�û����ﳵ���ĳ����Ʒ*/
    public void deleteGoods(Long useId,Long goodsId){
        cartInfoMapper.deleteGoods(useId, goodsId);
    }

    /*��չ��ﳵ*/
    public void empty(Long userId){
        cartInfoMapper.deleteByUserId(userId);
    }

    /*��ҳ��ѯ���ﳵ�б�*/
    public PageInfo<CartInfo> findPageDetails(Integer pageNum, Integer pageSize, HttpServletRequest request){
        UserInfo user = (UserInfo) request.getSession().getAttribute(Common.USER_INFO);
        if(user == null){
            throw new CustomException("1001","seesion��ʧЧ,������ʧЧ");
        }
        Integer level = user.getLevel();
        PageHelper.startPage(pageNum,pageSize);
        List<CartInfo> list;
        if(level == 1){
            list = cartInfoMapper.findAll();
        }else {
            list = cartInfoMapper.findCartByUserId(user.getId());
        }
        return PageInfo.of(list);
    }

    /**
     * ���ݹ��ﳵidɾ��
     */
    public void delete(Long id) {
        cartInfoMapper.deleteByPrimaryKey(id);
    }
}
