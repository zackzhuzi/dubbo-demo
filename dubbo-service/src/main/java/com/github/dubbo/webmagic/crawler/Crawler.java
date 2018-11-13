package com.github.dubbo.webmagic.crawler;

import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;

public class Crawler {

    public static void createSpider(PageProcessor pageProcessor, Pipeline pipeline, String... urls) {
        Spider.create(pageProcessor)
            .addUrl(urls)
            .setScheduler(new QueueScheduler())
            .addPipeline(pipeline)
            .thread(5)
            .run();
    }
    
}
