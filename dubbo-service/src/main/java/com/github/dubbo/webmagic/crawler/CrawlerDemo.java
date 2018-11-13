package com.github.dubbo.webmagic.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.pipeline.JsonFilePipeline;

import com.github.dubbo.webmagic.pageProcessors.SinaBlogProcessor;
import com.github.dubbo.webmagic.pipeline.SinaBlogDaoPipeline;

/**
 * @author yuzhu.peng
 * @since 2017/11/08
 */
@Component
public class CrawlerDemo {
    @Autowired
    private SinaBlogDaoPipeline sinaBlogDaoPipeline;
    
    public static void main(String[] args) {
        CrawlerDemo crawlerDemo = new CrawlerDemo();
        crawlerDemo.sinaBlogSpider();
    }
    
    public void sinaBlogSpider() {
        Crawler.createSpider(new SinaBlogProcessor(),
            new JsonFilePipeline("D:\\logs\\webmagic-output\\"),
            "http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html");
    }
    
    public void sinaBlogSpiderDB() {
        Crawler.createSpider(new SinaBlogProcessor(),
            sinaBlogDaoPipeline,
            "http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html");
    }
    
}
