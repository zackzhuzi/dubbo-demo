package com.github.dubbo.webmagic.pageProcessors;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

/**
 * @author code4crafter@gmail.com
 * @since 0.5.0
 */
public class AngularJSProcessor implements PageProcessor {

    private Site                site     = Site.me();

    private static final String LIST_URL = "http://angularjs\\.cn/api/article/latest.*";

    public void process(Page page) {
        if (page.getUrl().regex(LIST_URL).match()) {
            List<String> ids = new JsonPathSelector("$.data[*]._id").selectList(page.getRawText());
            if (CollectionUtils.isNotEmpty(ids)) {
                for (String id : ids) {
                    page.addTargetRequest("http://angularjs.cn/api/article/" + id);
                }
            }
        } else {
            page.putField("title", new JsonPathSelector("$.data.title").select(page.getRawText()));
            page.putField("content", new JsonPathSelector("$.data.content").select(page.getRawText()));
        }

    }

    public Site getSite() {
        return site;
    }
}
