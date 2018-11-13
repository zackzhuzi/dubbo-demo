package com.github.dubbo.webmagic.pageProcessors;

import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class RooNoProcessor implements PageProcessor {

    private Site                site       = Site.me().setRetryTimes(3).setSleepTime(100);
    private static final String URL_STRING = "//www.runoob.com/\\w+/\\w+-tutorial\\.html";

    public void process(Page page) {
        // <a class="item-top item-1"
        // page.putField("name",
        // page.getHtml().xpath("//a[@class='item-top item-1']/h4/text()").all());
        // page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());

        List<String> all = page.getHtml().xpath("//a[@class='item-top item-1']").links().regex(URL_STRING).all();
        page.addTargetRequests(all);

        if (page.getUrl().regex(URL_STRING).match()) {
            // <div class="tutintro">
            // <p>PHP 是一种创建动态交互性站点的强有力的服务器端脚本语言。</p>
            // <p>PHP 是免费的，并且使用非常广泛。同时，对于像微软 ASP 这样的竞争者来说，PHP 无疑是另一种高效率的选项。</p>
            //
            // <p><a
            // href="http://www.runoob.com/w3cnote/php-learning-recommend.html"
            // target="_blank"><strong>适用于PHP初学者的学习线路和建议</strong></a></p><p><a
            // href="/w3cnote/php-develop-tools.html"
            // target="_blank"><strong>PHP 开发工具推荐</strong></a></p>
            // <p><a href="/try/runcode.php?filename=demo_intro&amp;type=php"
            // target="_blank"><strong>PHP 在线工具</strong></a></p>
            // </div>
            page.putField("intro", page.getHtml().xpath("//div[@class='tutintro']/p/text()").all().toString());
        }
    }

    public Site getSite() {
        return site;
    }
}
