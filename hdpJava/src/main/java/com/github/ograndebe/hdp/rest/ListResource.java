package com.github.ograndebe.hdp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/hdr")
public class ListResource {

    @Autowired
    private Jedis jedis;

    @GetMapping(value = "/{pageId}/{tableId}", produces = "application/json")
    public ResponseEntity<String> get(@PathVariable("pageId") String pageId, @PathVariable("tableId") String tableId) throws DataNotFoundException {
        final String json = jedis.get(String.format("values:%s:%s", pageId, tableId));
        if (json == null) throw new DataNotFoundException();
        return  ResponseEntity.ok(json);
    }
}
