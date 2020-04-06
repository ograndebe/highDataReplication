package com.github.ograndebe.hdp;

import com.github.ograndebe.hdp.converter.TableToJsonConverter;
import com.github.ograndebe.hdp.factories.RedisFactory;
import com.github.ograndebe.hdp.observer.PageObserver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisNotificator implements PageObserver {
    private final Logger logger = LoggerFactory.getLogger(RedisNotificator.class);

    @Autowired
    private TableToJsonConverter tableToJsonConverter;

    @Autowired
    private RedisFactory redisFactory;

    @Autowired
    private JedisPool jedisPool;

    @Override
    public void pageChanged(String pageId, String pageSource) {

        Document doc = Jsoup.parse(pageSource);
        Elements tables = doc.select("table");
        int tableId = 0;
        for (Element table : tables) {
            final String json = tableToJsonConverter.convert(table);

            updateRedis(pageId, tableId, json);

            tableId++;
        }
    }

    private void updateRedis(String pageId, int tableId, String json) {
        logger.info("Table[{}] in Page[{}] was updated on Redis (size: {})", tableId, pageId, json.length());
        try (final Jedis jedis = jedisPool.getResource()) {
            jedis.set(String.format("values:%s:%s", pageId, tableId), json);
        }
    }

}
