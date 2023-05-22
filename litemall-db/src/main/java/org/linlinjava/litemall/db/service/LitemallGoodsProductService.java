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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public List<LitemallGoodsProductVo> queryVoByGid(Integer gid, Integer userLevel) {
        List<LitemallGoodsProduct> goodsProducts = queryByGid(gid);
        if (CollectionUtils.isEmpty(goodsProducts)){
            return null;
        }
        ArrayList<LitemallGoodsProductVo> productVos = new ArrayList<>();
        goodsProducts.forEach(goodsProduct -> {
            LitemallGoodsProductVo goodsProductVo = new LitemallGoodsProductVo();
            productVos.add(goodsProductVo);
            BeanUtils.copyProperties(goodsProduct, goodsProductVo);
            if (UserLevelEnum.tag_user.code.equals(userLevel) || Objects.isNull(userLevel)){
                goodsProductVo.setPrice(goodsProduct.getTagPrice());
            }
            if (UserLevelEnum.retail_user.code.equals(userLevel)){
                goodsProductVo.setPrice(goodsProduct.getRetailPrice());
            }
            if (UserLevelEnum.wholesale_user.code.equals(userLevel)){
                goodsProductVo.setPrice(goodsProduct.getWholesalePrice());
            }

        });

        return productVos;

    }

    public LitemallGoodsProduct findById(Integer id) {
        LitemallGoodsProduct goodsProduct = litemallGoodsProductMapper.selectByPrimaryKey(id);
        Integer userLevel = WxUserThreadLocal.getUserLevel();
        if (UserLevelEnum.tag_user.code.equals(userLevel) || Objects.isNull(userLevel)){
            goodsProduct.setPrice(goodsProduct.getTagPrice());
        }
        if (UserLevelEnum.retail_user.code.equals(userLevel)){
            goodsProduct.setPrice(goodsProduct.getRetailPrice());
        }
        if (UserLevelEnum.wholesale_user.code.equals(userLevel)){
            goodsProduct.setPrice(goodsProduct.getWholesalePrice());
        }
        return goodsProduct;
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