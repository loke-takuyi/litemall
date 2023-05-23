package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.config.WxUserThreadLocal;
import org.linlinjava.litemall.db.dao.GoodsProductMapper;
import org.linlinjava.litemall.db.dao.LitemallGoodsProductMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsProduct;
import org.linlinjava.litemall.db.domain.LitemallGoodsProductExample;
import org.linlinjava.litemall.db.domain.LitemallGoodsProductVo;
import org.linlinjava.litemall.db.enums.UserLevelEnum;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LitemallGoodsProductService {
    @Resource
    private LitemallGoodsProductMapper litemallGoodsProductMapper;
    @Resource
    private GoodsProductMapper goodsProductMapper;

    public List<LitemallGoodsProduct> queryByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return litemallGoodsProductMapper.selectByExample(example);
    }

    public List<LitemallGoodsProductVo> queryVoByGid(Integer gid) {
        List<LitemallGoodsProduct> goodsProducts = queryByGid(gid);
        if (CollectionUtils.isEmpty(goodsProducts)){
            return null;
        }
        ArrayList<LitemallGoodsProductVo> productVos = new ArrayList<>();
        goodsProducts.forEach(goodsProduct -> {
            LitemallGoodsProductVo goodsProductVo = new LitemallGoodsProductVo();
            productVos.add(goodsProductVo);
            BeanUtils.copyProperties(goodsProduct, goodsProductVo);
            goodsProductVo.setPrice(getCovertPrice(goodsProduct));
        });

        return productVos;

    }

    public LitemallGoodsProduct findById(Integer id) {
        LitemallGoodsProduct goodsProduct = litemallGoodsProductMapper.selectByPrimaryKey(id);
        goodsProduct.setPrice(getCovertPrice(goodsProduct));
        return goodsProduct;
    }

    private BigDecimal getCovertPrice(LitemallGoodsProduct goodsProduct) {
        Integer userLevel = WxUserThreadLocal.getUserLevel();
        if (UserLevelEnum.wholesale_user.code.equals(userLevel)){
            return goodsProduct.getWholesalePrice();
        }
        if (UserLevelEnum.retail_user.code.equals(userLevel)){
            return goodsProduct.getRetailPrice();
        }
        return goodsProduct.getTagPrice();

    }

    public void deleteById(Integer id) {
        litemallGoodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        litemallGoodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) litemallGoodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        litemallGoodsProductMapper.logicalDeleteByExample(example);
    }

    public int addStock(Integer id, Short num){
        return goodsProductMapper.addStock(id, num);
    }

    public int reduceStock(Integer id, Short num){
        return goodsProductMapper.reduceStock(id, num);
    }

    public void updateById(LitemallGoodsProduct product) {
        product.setUpdateTime(LocalDateTime.now());
        litemallGoodsProductMapper.updateByPrimaryKeySelective(product);
    }
}