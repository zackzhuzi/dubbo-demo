package com.github.dubbo.webmagic.pipeline;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.github.dubbo.commons.utils.Map2ObjectUtils;
import com.github.dubbo.mapper.SinaBlogMapper;
import com.github.dubbo.model.SinaBlog;

/**
 * 写入DB
 * 
 * @author Administrator
 */
@Component
public class SinaBlogDaoPipeline implements Pipeline {

    private Logger      logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SinaBlogMapper sinaBlogDao;

    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            Map<String, Object> all = resultItems.getAll();
            SinaBlog mapToObject = Map2ObjectUtils.mapToObject(all, SinaBlog.class);
            sinaBlogDao.add(mapToObject);
        } catch (Exception e) {
            logger.error("write db error", e);
        }
    }
}
