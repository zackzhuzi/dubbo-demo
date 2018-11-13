package com.github.dubbo.service;

import com.baomidou.mybatisplus.service.IService;
import com.github.dubbo.model.GoodsBrand;

/**
 * <p>
 * 品牌表 服务类
 * </p>
 *
 * @author will.li
 * @since 2018-01-05
 */
public interface IGoodsBrandService extends IService<GoodsBrand> {
	public boolean addGoodsBrand(GoodsBrand goodsBrand);
}
