package com.github.dubbo.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.dubbo.model.GoodsCategory;

/**
 * <p>
 * 商品类目表 服务类
 * </p>
 *
 * @author will.li
 * @since 2018-01-05
 */
public interface IGoodsCategoryService extends IService<GoodsCategory> {

    public int insertGoodsCategory(GoodsCategory goodsCategory);

}
