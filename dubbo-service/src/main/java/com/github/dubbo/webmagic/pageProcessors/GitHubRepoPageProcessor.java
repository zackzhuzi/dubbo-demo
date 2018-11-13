package com.github.dubbo.webmagic.pageProcessors;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * GitHub页面解析器：抽取有用信息，以及发现新的链接。<br>
 * 
 * @author yuzhu.peng
 * @since 2017/11/8
 */
public class GitHubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    public void process(Page page) {
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name") == null) {
            // skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
        // 一个站点的页面是很多的，一开始我们不可能全部列举出来，于是如何发现后续的链接，是一个爬虫不可缺少的一部分。
        // 获取所有满足"(https:/
        // /github\.com/\w+/\w+)"这个正则表达式的链接，page.addTargetRequests()则将这些链接加入到待抓取的队列中去。
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
    }

    public Site getSite() {
        return site;
    }

}
