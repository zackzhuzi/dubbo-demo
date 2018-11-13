package test.redis.singleRedisTemplateTest;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

import model.GoodsConstant;
import model.GoodsItemDTO;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import test.base.BaseTest;

import com.github.dubbo.commons.utils.ExecutorUtils;
import com.github.dubbo.service.IGoodsCacheService;
import com.github.dubbo.service.IGoodsItemService;

public class SingleRedisTest extends BaseTest {
    @Autowired
    private IGoodsItemService goodsItemService;
    
    @Autowired
    private IGoodsCacheService goodsCacheService;
    
    private void testGetGoodsItem() {
        List<GoodsItemDTO> goodsItemDTOs = testGetGoodsItemWithTimes(3);
        if (goodsItemDTOs.size() != 10) {
            throw new RuntimeException("数目不对=" + goodsItemDTOs.size() + "|||" + goodsItemDTOs + "|||");
        }
    }
    
    private void testRedisQuery() {
        GoodsItemDTO goodsCacheTest = goodsCacheService.getGoodsCacheTest(341);
        if (goodsCacheTest == null || StringUtils.isBlank(goodsCacheTest.getGoodsItemName())) {
            System.out.println("goodsCacheTest数据不对=" + goodsCacheTest);
        }
    }
    
    private List<GoodsItemDTO> testGetGoodsItemWithTimes(int times) {
        List<GoodsItemDTO> goodsItemDTOs = null;
        int failTime = 0;
        while (failTime < times) {
            goodsItemDTOs = goodsItemService.getGoodsItemDTOs(1, 10, 3, 0, GoodsConstant.SORT_PUBLISHTIME);
            if (goodsItemDTOs.size() == 10) {
                break;
            }
            failTime++;
        }
        if (failTime > 0) {
            System.out.println(Thread.currentThread().getName() + " retry times=" + failTime);
        }
        return goodsItemDTOs;
    }
    
    @Test
    public void testMultiThread() {
        testMultiThread(2000000);
    }
    
    public void testMultiThread(int count) {
        CountDownLatch countDownLatch = new CountDownLatch(count);
        AtomicLong atomicLong = new AtomicLong();
        AtomicLong atomicLongError = new AtomicLong();
        for (int i = 0; i < count; i++) {
            ExecutorUtils.execute(new TestThread(countDownLatch, atomicLong, atomicLongError));
        }
        try {
            System.out.println("等待所有线程执行完毕");
            countDownLatch.await();
            System.out.println("所有线程执行完毕");
            long l = atomicLong.get();
            System.out.println("线程数=" + count + ";总耗时=" + l + ";平均=" + l / count + ";核心线程数目="
                + ExecutorUtils.CORE_POOL_SIZE + ";查询失败数目=" + atomicLongError.get());
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        
    }
    
    class TestThread extends Thread {
        private CountDownLatch countDownLatch;
        
        private AtomicLong atomicLong;
        
        private AtomicLong atomicLongError;
        
        public TestThread(CountDownLatch countDownLatch, AtomicLong atomicLong, AtomicLong atomicLongError) {
            this.countDownLatch = countDownLatch;
            this.atomicLong = atomicLong;
            this.atomicLongError = atomicLongError;
        }
        
        public void run() {
            try {
                long epochMilli = Instant.now().toEpochMilli();
                // testGetGoodsItem();
                testRedisQuery();
                long cost = Instant.now().toEpochMilli() - epochMilli;
                atomicLong.getAndAdd(cost);
            }
            catch (Exception e) {
                System.out.println(Thread.currentThread().getName() + " test get goods items failed " + e.getMessage());
                atomicLongError.getAndIncrement();
            }
            finally {
                countDownLatch.countDown();
            }
        }
        
    }
}
