//package com.github.dubbo.task;
//
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import com.github.dubbo.mapper.GoodsItemMapper;
//import com.github.dubbo.mapper.GoodsItemStatMapper;
//import com.github.dubbo.model.GoodsItemStatVo;
//import com.github.dubbo.service.IGoodsItemService;
//
///**
// * @ClassName: TestTask
// * @Description: spring task 定时任务测试，适用于单系统 注意：不适合用于集群
// * @author: yuzhu.peng
// * @date: 2017年11月15日 上午10:57:26
// */
//@Component
//public class CacheCorrectTask {
//    
//    private static Logger logger = LoggerFactory.getLogger(CacheCorrectTask.class);
//    
//    @Autowired
//    private GoodsItemMapper goodsItemMapper;
//    
//    @Autowired
//    private GoodsItemStatMapper goodsItemStatMapper;
//    
//    /**
//     * Per 30 min
//     */
//    @Scheduled(cron = "0 0/60 * * * ?")
//    public void correctCache() {
//        logger.info("correctCache begins");
//        List<GoodsItemStatVo> selectStockAndSaleCount = goodsItemMapper.selectStockAndSaleCount();
//        for (GoodsItemStatVo goodsItemStatVo : selectStockAndSaleCount) {
//            
//        }
//        logger.info("correctCache finishes");
//    }
// }
