package com.github.ograndebe.hdp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/hdr")
public class ListResource {

    @Autowired
    private JedisPool jedisPool;

    @GetMapping(value = "/{pageId}/{tableId}", produces = "application/json")
    public ResponseEntity<String> get(@PathVariable("pageId") String pageId, @PathVariable("tableId") String tableId) throws DataNotFoundException {
        try (Jedis jedis = jedisPool.getResource()) {
            final String json = jedis.get(String.format("values:%s:%s", pageId, tableId));
            jedis.incr(String.format("table:counter:%s:%s", pageId, tableId));
            if (json == null) throw new DataNotFoundException();
            return  ResponseEntity.ok(json);
        }
    }

    @GetMapping(value = "/{pageId}/{tableId}/counter", produces = "application/json")
    public ResponseEntity<String> getCounter(@PathVariable("pageId") String pageId, @PathVariable("tableId") String tableId) throws DataNotFoundException {
        try (Jedis jedis = jedisPool.getResource()) {
            final String amount = jedis.get(String.format("table:counter:%s:%s", pageId, tableId));
            System.out.printf(String.format("table:counter:%s:%s = %s\n", pageId, tableId, amount));
            if (amount == null) throw new DataNotFoundException();
            return  ResponseEntity.ok(amount);
        }
    }

    @DeleteMapping(value = "/{pageId}/{tableId}/counter", produces = "application/json")
    public void resetCounter(@PathVariable("pageId") String pageId, @PathVariable("tableId") String tableId) throws DataNotFoundException {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(String.format("table:counter:%s:%s", pageId, tableId));
            System.out.printf(String.format("deleting table:counter:%s:%s\n", pageId, tableId));
        }
    }
}
